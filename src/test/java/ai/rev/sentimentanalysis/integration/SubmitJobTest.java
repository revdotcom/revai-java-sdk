package ai.rev.sentimentanalysis.integration;

import ai.rev.speechtotext.models.asynchronous.RevAiJobType;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import ai.rev.testutils.EnvHelper;
import ai.rev.sentimentanalysis.SentimentAnalysisClient;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJob;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJobOptions;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJobStatus;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.nio.file.Paths;

import static ai.rev.testutils.FileHelper.getFileContents;
import static org.assertj.core.api.Assertions.assertThat;

public class SubmitJobTest {

  private final String TEXT_SOURCE =
          getFileContents(Paths.get("./src/test/java/ai/rev/sentimentanalysis/resources/orange.txt"));
  private final RevAiTranscript JSON_SOURCE =
          (new Gson()).fromJson(
                  getFileContents(Paths.get("./src/test/java/ai/rev/sentimentanalysis/resources/sample.json")),
                  RevAiTranscript.class);
  private final String CALLBACK_URL = "https://example.com";
  private static SentimentAnalysisClient apiClient;

  @Rule public TestName testName = new TestName();

  @Before
  public void setup() {
    apiClient = new SentimentAnalysisClient(EnvHelper.getToken());
  }

  @Test
  public void SubmitJobText_TextAndOptionsSpecified_ReturnsJobInProgress()
      throws IOException {
    SentimentAnalysisJobOptions jobOptions = getJobOptions();

    SentimentAnalysisJob job = apiClient.submitJobText(TEXT_SOURCE, jobOptions);

    assertSentimentAnalysisJob(job);
  }

  @Test
  public void SubmitJobText_TextAndOptionsWithNotificationConfig_ReturnsJobInProgress()
      throws IOException {
    SentimentAnalysisJobOptions jobOptions = getJobOptions();
    jobOptions.setCallbackUrl(null);
    jobOptions.setNotificationConfig(CALLBACK_URL);

    SentimentAnalysisJob job = apiClient.submitJobText(TEXT_SOURCE, jobOptions);

    assertSentimentAnalysisJob(job);
    assert job.getCallbackUrl() == null;
  }

  @Test
  public void SubmitJobText_OnlyTextIsSpecified_ReturnsJobInProgress()
      throws IOException {
    SentimentAnalysisJob job = apiClient.submitJobText(TEXT_SOURCE);

    assertSentimentAnalysisJob(job);
  }

  @Test
  public void SubmitJobJson_JsonAndOptionsSpecified_ReturnsJobInProgress()
          throws IOException {
    SentimentAnalysisJobOptions jobOptions = getJobOptions();

    SentimentAnalysisJob job = apiClient.submitJobJson(JSON_SOURCE, jobOptions);

    assertSentimentAnalysisJob(job);
  }

  @Test
  public void SubmitJobLocalFile_OnlyJsonIsSpecified_ReturnsJobInProgress()
      throws IOException {
    SentimentAnalysisJob job = apiClient.submitJobJson(JSON_SOURCE);

    assertSentimentAnalysisJob(job);
  }

  public void assertSentimentAnalysisJob(SentimentAnalysisJob job) {
    assertThat(job.getJobId()).as("Job Id").isNotNull();
    assertThat(job.getType()).as("Job Type").isEqualTo(RevAiJobType.SENTIMENTANALYSIS);
    assertThat(job.getJobStatus()).as("Job status").isEqualTo(SentimentAnalysisJobStatus.IN_PROGRESS);
  }

  private SentimentAnalysisJobOptions getJobOptions() {
    SentimentAnalysisJobOptions revAiJobOptions = new SentimentAnalysisJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    revAiJobOptions.setCallbackUrl(CALLBACK_URL);
    revAiJobOptions.setDeleteAfterSeconds(0);
    return revAiJobOptions;
  }
}
