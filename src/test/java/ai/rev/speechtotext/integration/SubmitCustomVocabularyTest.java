package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.CustomVocabularyClient;
import ai.rev.speechtotext.models.CustomVocabulary;
import ai.rev.speechtotext.models.CustomVocabularyInformation;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SubmitCustomVocabularyTest {

  @Rule public TestName testName = new TestName();

  @Test
  public void
      submitCustomVocabulary_WhenMetadataAndVocabAreIncluded_ReturnCustomVocabularyInformationInProgress() {
    List<String> phrases = Arrays.asList("test", "this", "vocab");
    CustomVocabulary customVocabulary = new CustomVocabulary(phrases);
    String metadata = testName.getMethodName();

    CustomVocabularyClient customVocabularyClient =
        new CustomVocabularyClient(EnvHelper.getToken());
    CustomVocabularyInformation customVocabularyInformation;

    try {
      customVocabularyInformation =
          customVocabularyClient.submitCustomVocabularies(
              metadata, Collections.singletonList(customVocabulary));
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    assertThat(customVocabularyInformation.getStatus())
        .as("Custom vocabulary status")
        .isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }
}
