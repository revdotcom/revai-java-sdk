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

  @Before
  public void setup() throws IOException {
    apiClient = new ApiClient(EnvHelper.getToken());
    jobId = getTranscribedJob();
  }

  @Test
  public void GetCaptions_WhenJobIdIsValid_ReturnsCaptionStream() throws IOException {
    InputStream inputStream = apiClient.getCaptions(jobId);
    String captions = convertInputStreamToString(inputStream);

    assertThat(captions).isNotEmpty();
    assertThat(captions).doesNotContain(VTT_CONTAINS);
  }

  @Test
  public void GetCaptions_WhenTypeIsSpecifiedAndJobIdIsValid_ReturnsCaptionStreamInVttFormat()
      throws IOException {
    InputStream inputStream = apiClient.getCaptions(jobId, RevAiCaptionType.VTT);
    String captions = convertInputStreamToString(inputStream);

    assertThat(captions).isNotEmpty();
    assertThat(captions).contains(VTT_CONTAINS);
  }

  private String getTranscribedJob() throws IOException {
    List<RevAiJob> jobs = apiClient.getListOfJobs();
    String transcribedJobId = null;
    for (RevAiJob job : jobs) {
      if (job.getJobStatus().equals(RevAiJobStatus.TRANSCRIBED)) {
        transcribedJobId = job.getJobId();
        break;
      }
    }
    if (transcribedJobId.equals(null)) {
      throw new RuntimeException("Could not find a transcribed job for integration tests");
    }
    return transcribedJobId;
  }
}
