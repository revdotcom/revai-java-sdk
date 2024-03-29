package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.CustomVocabulariesClient;
import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyFailureType;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyInformation;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyStatus;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularySubmission;
import ai.rev.testutils.EnvHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomVocabulariesTest {

  @Rule public TestName testName = new TestName();

  private static final List<String> PHRASES = Arrays.asList("test", "this", "vocab");
  private static final String NOTIFICATION_URL = "https://www.example.com";
  private static final Map<String, String> NOTIFICATION_AUTH = Collections.singletonMap("Authorization",
          "Bearer <token>");
  private CustomVocabulariesClient customVocabularyClient =
      new CustomVocabulariesClient(EnvHelper.getToken());

  @Test
  public void
      submitCustomVocabulary_VocabAndAllOptionsAreIncluded_ReturnCustomVocabularyInformationInProgress() {
    CustomVocabularySubmission customVocabularySubmission = createCustomVocabularySubmission();

    CustomVocabularyInformation customVocabularyInformation =
        submitCustomVocabulary(customVocabularySubmission);

    assertThat(customVocabularyInformation.getStatus())
        .as("Custom vocabulary status")
        .isEqualTo(CustomVocabularyStatus.IN_PROGRESS);
  }

  @Test
  public void getCustomVocabularyInformation_IdIsSpecified_CustomVocabularyInformation() {
    CustomVocabularySubmission customVocabularySubmission = createCustomVocabularySubmission();
    CustomVocabularyInformation submittedVocabulary =
        submitCustomVocabulary(customVocabularySubmission);
    CustomVocabularyInformation retrievedVocabulary;

    try {
      retrievedVocabulary =
          customVocabularyClient.getCustomVocabularyInformation(submittedVocabulary.getId());
    } catch (IOException e) {
      throw new RuntimeException(
          "Error occurred getting the custom of vocabulary: " + e.getMessage());
    }

    assertThat(retrievedVocabulary.getMetadata())
        .as("Vocabulary metadata")
        .isEqualTo(submittedVocabulary.getMetadata());
    assertThat(retrievedVocabulary.getId())
        .as("Vocabulary Id")
        .isEqualTo(submittedVocabulary.getId());
    assertThat(retrievedVocabulary.getCreatedOn())
        .as("Created on date")
        .isEqualTo(submittedVocabulary.getCreatedOn());
    assertThat(retrievedVocabulary.getFailure()).as("Failure").isNull();
    assertThat(retrievedVocabulary.getFailureDetail()).as("Failure detail").isNull();
  }

  @Test
  public void getListOfCustomVocabulary_Called_ReturnListOfCustomVocabularyInformation() {
    List<CustomVocabularyInformation> customVocabularies;

    try {
      customVocabularies = customVocabularyClient.getListOfCustomVocabularyInformation();
    } catch (IOException e) {
      throw new RuntimeException(
          "Error occurred getting the list of vocabularies: " + e.getMessage());
    }

    assertThat(customVocabularies.size()).as("List size").isGreaterThan(1);
    for (CustomVocabularyInformation customVocabulary : customVocabularies) {
      CustomVocabularyStatus status = customVocabulary.getStatus();
      assertThat(customVocabulary.getId()).as("Vocabulary Id").isNotNull();
      assertThat(customVocabulary.getCreatedOn()).as("Created on").isNotNull();
      assertThat(status).as("Status").isNotNull();
      if (status == CustomVocabularyStatus.FAILED) {
        assertThat(customVocabulary.getFailure())
            .as("Failure")
            .isInstanceOf(CustomVocabularyFailureType.class);
        assertThat(customVocabulary.getFailureDetail()).as("Failure detail").isNotNull();
      } else if (status == CustomVocabularyStatus.COMPLETE) {
        assertThat(customVocabulary.getCompletedOn()).as("Completed on").isNotNull();
      }
    }
  }

  @Test
  public void deleteCustomVocabulary_IdIsValid_DoesNotTriggerExceptions() {
    CustomVocabularySubmission customVocabularySubmission = createCustomVocabularySubmission();
    CustomVocabularyInformation customVocabularyInformation =
        submitCustomVocabulary(customVocabularySubmission);
    String id = customVocabularyInformation.getId();
    CustomVocabularyStatus status = waitForVocabularyProcessingToComplete(id);

    assertThat(status).as("Custom vocabulary status").isNotNull();

    try {
      customVocabularyClient.deleteCustomVocabulary(id);
    } catch (IOException e) {
      throw new RuntimeException(
          "Error occurred deleting the custom vocabulary [" + id + "] " + e.getMessage());
    }
  }

  private CustomVocabularySubmission createCustomVocabularySubmission() {
    CustomVocabulary customVocabulary = new CustomVocabulary(PHRASES);
    CustomVocabularySubmission submission = new CustomVocabularySubmission();
    submission.setCustomVocabularies(Collections.singletonList(customVocabulary));
    submission.setMetadata(testName.getMethodName());
    submission.setNotificationConfig(NOTIFICATION_URL, NOTIFICATION_AUTH);
    return submission;
  }

  private CustomVocabularyInformation submitCustomVocabulary(
      CustomVocabularySubmission customVocabularySubmission) {
    try {
      return customVocabularyClient.submitCustomVocabularies(customVocabularySubmission);
    } catch (IOException e) {
      throw new RuntimeException(
          "Error occurred submitting the custom vocabulary: " + e.getMessage());
    }
  }

  private CustomVocabularyStatus waitForVocabularyProcessingToComplete(String customVocabularyId) {
    boolean isProcessingComplete = false;
    int numberOfAttempts = 0;
    CustomVocabularyStatus status = null;

    while (!isProcessingComplete && numberOfAttempts < 60) {
      CustomVocabularyInformation retrievedVocabularyInfo;
      try {
        retrievedVocabularyInfo =
            customVocabularyClient.getCustomVocabularyInformation(customVocabularyId);
      } catch (IOException e) {
        throw new RuntimeException(
            "Failed to retrieve custom vocabulary info ["
                + customVocabularyId
                + "] "
                + e.getMessage());
      }

      CustomVocabularyStatus retrievedVocabularyInfoStatus = retrievedVocabularyInfo.getStatus();
      if (retrievedVocabularyInfoStatus == CustomVocabularyStatus.COMPLETE
          || retrievedVocabularyInfoStatus == CustomVocabularyStatus.FAILED) {
        status = retrievedVocabularyInfoStatus;
        isProcessingComplete = true;
      } else {
        numberOfAttempts++;
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    return status;
  }
}
