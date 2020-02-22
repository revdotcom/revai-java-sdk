package revai.integration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import revai.ApiClient;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiJobStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitJobTest {

  private final String LOCAL_FILE = "./src/test/java/revai/resources/sampleAudio.mp3";
  private final String MEDIA_URL = "https://www.rev.ai/FTC_Sample_1.mp3";
  private static ApiClient apiClient;

  @Rule public TestName testName = new TestName();

  @Before
  public void setup() {
    apiClient = new ApiClient(EnvHelper.getToken());
  }

  @Test
  public void SubmitJobLocalFile_WhenFilePathAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void SubmitJobLocalFile_WhenOnlyFilePathIsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, null);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void
      SubmitJobLocalFile_WhenInputStreamAndFileNameAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
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
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void SubmitJobLocalFile_WhenInputStreamAndFileNameAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, file.getName(), null);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void SubmitJobLocalFile_WhenInputStreamAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
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
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, null, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void SubmitJobLocalFile_WhenOnlyInputStreamIsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, null, null);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void SubmitJobUrl_WhenUrlAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  @Test
  public void SubmitJobUrl_WhenOnlyUrlIsSpecified_ReturnsRevAiJobInProgress() throws IOException {
    RevAiJob revAiJob = apiClient.submitJobUrl(MEDIA_URL, null);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }
}
