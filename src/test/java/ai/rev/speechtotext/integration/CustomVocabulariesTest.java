package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.CustomVocabulariesClient;
import ai.rev.speechtotext.models.CustomVocabulary;
import ai.rev.speechtotext.models.CustomVocabularyInformation;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
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
      submitCustomVocabulary_WhenMetadataAndVocabAreIncluded_ReturnCustomVocabularyInformationInProgress() {
    CustomVocabularyInformation customVocabularyInformation = submitCustomVocabulary();

    assertThat(customVocabularyInformation.getStatus())
        .as("Custom vocabulary status")
        .isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void getCustomVocabularyInformation_WhenIdIsSpecified_CustomVocabularyInformation() {
    CustomVocabularyInformation submittedVocabulary = submitCustomVocabulary();
    CustomVocabularyInformation retrievedVocabulary = null;

    try {
      retrievedVocabulary =
          customVocabularyClient.getCustomVocabularyInformation(submittedVocabulary.getId());
    } catch (IOException e) {
      e.printStackTrace();
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
  public void getListOfCustomVocabulary_WhenCalled_ReturnListOfCustomVocabularyInformation() {
    List<CustomVocabularyInformation> customVocabularies = null;

    try {
      customVocabularies = customVocabularyClient.getListOfCustomVocabularyInformation();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertThat(customVocabularies.size()).as("List size").isGreaterThan(1);
    for (CustomVocabularyInformation customVocabulary : customVocabularies) {
      assertThat(customVocabulary.getId()).as("Vocabulary Id").isNotNull();
      assertThat(customVocabulary.getCreatedOn()).as("Created on").isNotNull();
      assertThat(customVocabulary.getStatus()).as("Status").isNotNull();
      if (customVocabulary.getStatus() == RevAiJobStatus.FAILED) {
        assertThat(customVocabulary.getFailure()).as("Failure").isNotNull();
        assertThat(customVocabulary.getFailureDetail()).as("Failure detail").isNotNull();
      } else if (customVocabulary.getStatus() == RevAiJobStatus.COMPLETE) {
        assertThat(customVocabulary.getCompletedOn()).as("Completed on").isNotNull();
      }
    }
  }

  public CustomVocabularyInformation submitCustomVocabulary() {
    CustomVocabulary customVocabulary = new CustomVocabulary(PHRASES);
    String metadata = testName.getMethodName();

    try {
      return customVocabularyClient.submitCustomVocabularies(
          metadata, Collections.singletonList(customVocabulary));
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
