package revai.integration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import revai.ApiClient;
import revai.helpers.EnvHelper;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiJobStatus;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JobSubmission {

  @Rule public TestName testName = new TestName();

  @Test
  public void canSubmitLocalFileJob() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobLocalFile("./src/test/java/revai/resources/sampleAudio.mp3", revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitUrlJob() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobUrl("https://www.rev.ai/FTC_Sample_1.mp3", revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }
}
