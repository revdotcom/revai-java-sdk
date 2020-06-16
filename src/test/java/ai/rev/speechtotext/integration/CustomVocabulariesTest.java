package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.CustomVocabulariesClient;
import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyFailureType;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyInformation;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyStatus;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularySubmission;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomVocabulariesTest {

  @Rule public TestName testName = new TestName();

  private static final List<String> PHRASES = Arrays.asList("test", "this", "vocab");
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
    assertThat(retrievedVocabulary.getCallbackUrl())
        .as("Callback URL")
        .isEqualTo(submittedVocabulary.getCallbackUrl());
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

  private CustomVocabularySubmission createCustomVocabularySubmission() {
    CustomVocabulary customVocabulary = new CustomVocabulary(PHRASES);
    CustomVocabularySubmission submission = new CustomVocabularySubmission();
    submission.setCustomVocabularies(Collections.singletonList(customVocabulary));
    submission.setMetadata(testName.getMethodName());
    submission.setCallbackUrl("https://www.example.com");
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
}
