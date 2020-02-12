package revai.integration;

import org.junit.Test;
import revai.ApiClient;
import revai.helpers.EnvHelper;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiJobStatus;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JobSubmission {

  @Test
  public void canSubmitLocalFileJob() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata("java-sdk");
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobLocalFile("./src/test/java/revai/resources/sampleAudio.mp3", revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }
}
