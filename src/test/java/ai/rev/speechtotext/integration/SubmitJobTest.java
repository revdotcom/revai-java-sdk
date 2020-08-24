package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitJobTest {

  private final String LOCAL_FILE = "./src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";
  private final String MEDIA_URL = "https://www.rev.ai/FTC_Sample_1.mp3";
  private static ApiClient apiClient;

  @Rule public TestName testName = new TestName();

  @Before
  public void setup() {
    apiClient = new ApiClient(EnvHelper.getToken());
  }

  @Test
  public void SubmitJobLocalFile_FilePathAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();

    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_OnlyFilePathIsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, null);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void
      SubmitJobLocalFile_InputStreamAndFileNameAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
          throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob =
        apiClient.submitJobLocalFile(fileInputStream, file.getName(), revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamAndFileNameAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, file.getName(), null);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, null, revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_OnlyInputStreamIsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, null, null);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobUrl_UrlAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();

    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL, revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobUrl_OnlyUrlIsSpecified_ReturnsRevAiJobInProgress() throws IOException {
    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL, null);

    assertRevAiJob(revAiJob);
  }

  public void assertRevAiJob(RevAiJob revAiJob) {
    assertThat(revAiJob.getJobId()).as("Job Id").isNotNull();
    assertThat(revAiJob.getJobStatus()).as("Job status").isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  private RevAiJobOptions getJobOptions() {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    revAiJobOptions.setFilterProfanity(true);
    revAiJobOptions.setRemoveDisfluencies(true);
    revAiJobOptions.setSkipPunctuation(true);
    revAiJobOptions.setSkipDiarization(true);
    revAiJobOptions.setCallbackUrl("https://example.com");
    revAiJobOptions.setSpeakerChannelsCount(null);
    revAiJobOptions.setDeleteAfterSeconds(0);
    return revAiJobOptions;
  }
}
