package revai.integration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import revai.ApiClient;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiJobStatus;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JobSubmission {

  private final String LOCAL_FILE = "./src/test/java/revai/resources/sampleAudio.mp3";
  private final String MEDIA_URL = "https://www.rev.ai/FTC_Sample_1.mp3";

  @Rule public TestName testName = new TestName();

  @Test
  public void canSubmitLocalFileJob() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitLocalFileWithoutOptions() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitUrlJob() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitUrlJobWithoutOptions() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }
}
