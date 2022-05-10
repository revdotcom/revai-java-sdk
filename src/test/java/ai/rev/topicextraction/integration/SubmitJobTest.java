package ai.rev.topicextraction.integration;

import ai.rev.speechtotext.models.asynchronous.RevAiJobType;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import ai.rev.testutils.EnvHelper;
import ai.rev.topicextraction.TopicExtractionClient;
import ai.rev.topicextraction.models.TopicExtractionJob;
import ai.rev.topicextraction.models.TopicExtractionJobOptions;
import ai.rev.topicextraction.models.TopicExtractionJobStatus;
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
          getFileContents(Paths.get("./src/test/java/ai/rev/topicextraction/resources/orange.txt"));
  private final RevAiTranscript JSON_SOURCE =
          (new Gson()).fromJson(
                  getFileContents(Paths.get("./src/test/java/ai/rev/topicextraction/resources/sample.json")),
                  RevAiTranscript.class);
  private final String CALLBACK_URL ="https://example.com";
  private static TopicExtractionClient apiClient;

  @Rule public TestName testName = new TestName();

  @Before
  public void setup() {
    apiClient = new TopicExtractionClient(EnvHelper.getToken());
  }

  @Test
  public void SubmitJobText_TextAndOptionsSpecified_ReturnsJobInProgress()
      throws IOException {
    TopicExtractionJobOptions jobOptions = getJobOptions();

    TopicExtractionJob job = apiClient.submitJobText(TEXT_SOURCE, jobOptions);

    assertTopicExtractionJob(job);
  }

  @Test
  public void SubmitJobText_TextAndOptionsWithNotificationConfig_ReturnsJobInProgress()
      throws IOException {
    TopicExtractionJobOptions jobOptions = getJobOptions();
    jobOptions.setCallbackUrl(null);
    jobOptions.setNotificationConfig(CALLBACK_URL);

    TopicExtractionJob job = apiClient.submitJobText(TEXT_SOURCE, jobOptions);

    assertTopicExtractionJob(job);
    assert job.getCallbackUrl() == null;
  }

  @Test
  public void SubmitJobText_OnlyTextIsSpecified_ReturnsJobInProgress()
      throws IOException {
    TopicExtractionJob job = apiClient.submitJobText(TEXT_SOURCE);

    assertTopicExtractionJob(job);
  }

  @Test
  public void SubmitJobJson_JsonAndOptionsSpecified_ReturnsJobInProgress()
          throws IOException {
    TopicExtractionJobOptions jobOptions = getJobOptions();

    TopicExtractionJob job = apiClient.submitJobJson(JSON_SOURCE, jobOptions);

    assertTopicExtractionJob(job);
  }

  @Test
  public void SubmitJobLocalFile_OnlyJsonIsSpecified_ReturnsJobInProgress()
      throws IOException {
    TopicExtractionJob job = apiClient.submitJobJson(JSON_SOURCE);

    assertTopicExtractionJob(job);
  }

  public void assertTopicExtractionJob(TopicExtractionJob job) {
    assertThat(job.getJobId()).as("Job Id").isNotNull();
    assertThat(job.getType()).as("Job Type").isEqualTo(RevAiJobType.TOPICEXTRACTION);
    assertThat(job.getJobStatus()).as("Job status").isEqualTo(TopicExtractionJobStatus.IN_PROGRESS);
  }

  private TopicExtractionJobOptions getJobOptions() {
    TopicExtractionJobOptions revAiJobOptions = new TopicExtractionJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    revAiJobOptions.setCallbackUrl("https://example.com");
    revAiJobOptions.setDeleteAfterSeconds(0);
    return revAiJobOptions;
  }
}
