package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.asynchronous.RevAiCaptionType;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static ai.rev.speechtotext.testutils.ConversionUtil.convertInputStreamToString;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCaptionsTest {

  private static ApiClient apiClient;
  private static String jobId;
  private final String VTT_CONTAINS = "WEBVTT";
  private final String MEDIA_URL = "https://www.rev.ai/FTC_Sample_1.mp3";

  @Before
  public void setup() throws IOException {
    apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL);
    jobId = revAiJob.getJobId();
    pollForJobCompletionForFiveMinutes(jobId);
  }

  @Test
  public void GetCaptions_JobIdIsValid_ReturnsCaptionStream() throws IOException {
    InputStream inputStream = apiClient.getCaptions(jobId);
    String captions = convertInputStreamToString(inputStream);

    assertThat(captions).as("Caption output").isNotEmpty();
    assertThat(captions).as("Caption content").doesNotContain(VTT_CONTAINS);
  }

  @Test
  public void GetCaptions_TypeIsSpecifiedAndJobIdIsValid_ReturnsCaptionStreamInVttFormat()
      throws IOException {
    InputStream inputStream = apiClient.getCaptions(jobId, RevAiCaptionType.VTT);
    String captions = convertInputStreamToString(inputStream);

    assertThat(captions).as("Captions output").isNotEmpty();
    assertThat(captions).as("Caption content").contains(VTT_CONTAINS);
  }

  private void pollForJobCompletionForFiveMinutes(String jobId) {
    boolean isComplete = false;
    int pollingAttempts = 0;
    while (!isComplete && pollingAttempts < 60) {
      RevAiJob revAiJob;
      try {
        revAiJob = apiClient.getJobDetails(jobId);
      } catch (IOException e) {
        throw new RuntimeException(
            "Failed to get job details for [" + jobId + "] " + e.getMessage());
      }
      RevAiJobStatus jobStatus = revAiJob.getJobStatus();
      if (jobStatus.equals(RevAiJobStatus.TRANSCRIBED)) {
        isComplete = true;
        break;
      } else if (jobStatus.equals(RevAiJobStatus.FAILED)) {
        throw new RuntimeException("Job [" + jobId + "] failed");
      } else {
        pollingAttempts++;
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          throw new RuntimeException("Thread.sleep failed " + e.getMessage());
        }
      }
    }
  }
}
