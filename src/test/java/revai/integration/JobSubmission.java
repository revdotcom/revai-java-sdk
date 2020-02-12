package revai.integration;

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

public class JobSubmission {

  private final String LOCAL_FILE = "./src/test/java/revai/resources/sampleAudio.mp3";
  private final String MEDIA_URL = "https://www.rev.ai/FTC_Sample_1.mp3";

  @Rule public TestName testName = new TestName();

  @Test
  public void canSubmitLocalFileJobUsingPathWithOptions() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitLocalFileUsingPath() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitLocalFileUsingStreamWithNameAndOptions() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() +"]");
    }
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, file.getName(), revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitLocalFileUsingStreamWithName() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() +"]");
    }
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, file.getName());
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitLocalFileUsingStreamWithOptions() throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() +"]");
    }
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, revAiJobOptions);
    assertThat(revAiJob.getJobID()).isNotNull();
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.in_progress);
  }

  @Test
  public void canSubmitLocalFileUsingStream() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() +"]");
    }
    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream);
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
