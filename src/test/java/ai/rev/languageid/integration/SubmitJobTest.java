package ai.rev.languageid.integration;

import ai.rev.languageid.LanguageIdClient;
import ai.rev.languageid.models.*;
import ai.rev.testutils.EnvHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitJobTest {
  private final String LOCAL_FILE = "./src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";
  private final String SOURCE_URL = "https://www.rev.ai/FTC_Sample_1.mp3";
  private final String CALLBACK_URL = "https://example.com";
  private static LanguageIdClient apiClient;

  @Rule
  public TestName testName = new TestName();

  @Before
  public void setup() {
    apiClient = new LanguageIdClient(EnvHelper.getToken());
  }

  @Test
  public void SubmitJobLocalFile_FilePathAndOptionsAreSpecified_ReturnsLanguageIdJobInProgress()
      throws IOException {
    LanguageIdJobOptions languageIdJobOptions = getJobOptions();

    LanguageIdJob languageIdJob = apiClient.submitJobLocalFile(LOCAL_FILE, languageIdJobOptions);

    assertLanguageIdJob(languageIdJob);
  }

  @Test
  public void SubmitJobLocalFile_OnlyFilePathIsSpecified_ReturnsLanguageIdJobInProgress()
      throws IOException {
    LanguageIdJob languageIdJob = apiClient.submitJobLocalFile(LOCAL_FILE, null);

    assertLanguageIdJob(languageIdJob);
  }

  @Test
  public void
  SubmitJobLocalFile_InputStreamAndFileNameAndOptionsAreSpecified_ReturnsLanguageIdJobInProgress()
      throws IOException {
    LanguageIdJobOptions languageIdJobOptions = new LanguageIdJobOptions();
    languageIdJobOptions.setMetadata(testName.getMethodName());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    LanguageIdJob languageIdJob =
            apiClient.submitJobLocalFile(fileInputStream, file.getName(), languageIdJobOptions);

    assertLanguageIdJob(languageIdJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamAndFileNameAreSpecified_ReturnsLanguageIdJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    LanguageIdJob languageIdJob = apiClient.submitJobLocalFile(fileInputStream, file.getName(), null);

    assertLanguageIdJob(languageIdJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamAndOptionsAreSpecified_ReturnsLanguageIdJobInProgress()
      throws IOException {
    LanguageIdJobOptions languageIdJobOptions = getJobOptions();
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    LanguageIdJob languageIdJob = apiClient.submitJobLocalFile(fileInputStream, null, languageIdJobOptions);

    assertLanguageIdJob(languageIdJob);
  }

  @Test
  public void SubmitJobLocalFile_OnlyInputStreamIsSpecified_ReturnsLanguageIdJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    LanguageIdJob languageIdJob = apiClient.submitJobLocalFile(fileInputStream, null, null);

    assertLanguageIdJob(languageIdJob);
  }

  @Test
  public void SubmitJob_SourceConfigAndNotificationConfigSet_ReturnsLanguageIdJobInProgress()
      throws IOException {
    LanguageIdJobOptions languageIdJobOptions = getJobOptions();
    languageIdJobOptions.setSourceConfig(SOURCE_URL);
    languageIdJobOptions.setNotificationConfig(CALLBACK_URL);

    LanguageIdJob languageIdJob = apiClient.submitJob(languageIdJobOptions);

    assertLanguageIdJob(languageIdJob);
  }

  public void assertLanguageIdJob(LanguageIdJob languageIdJob) {
    assertThat(languageIdJob.getJobId()).as("Job Id").isNotNull();
    assertThat(languageIdJob.getJobStatus()).as("Job status").isEqualTo(LanguageIdJobStatus.IN_PROGRESS);
  }

  private LanguageIdJobOptions getJobOptions() {
    LanguageIdJobOptions languageIdJobOptions = new LanguageIdJobOptions();
    languageIdJobOptions.setMetadata(testName.getMethodName());
    languageIdJobOptions.setDeleteAfterSeconds(0);
    return languageIdJobOptions;
  }
}
