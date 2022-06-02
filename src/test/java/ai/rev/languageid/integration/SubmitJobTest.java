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

  @Test
  public void Example() throws IOException {
      String accessToken = "<YOUR_ACCESS_TOKEN>";

      // Initialize the LanguageIdClient with your access token
      LanguageIdClient languageIdClient = new LanguageIdClient(accessToken);

      // Set up source configuration parameters
      String mediaUrl = "https://www.rev.ai/FTC_Sample_1.mp3";
      // Authorization header is optional; use it if needed to access the source media url
      Map<String, String> sourceAuth = new HashMap<>();
      sourceAuth.put("Authorization", "Bearer <source_token>");

      // Set up notification configuration parameters
      String callbackUrl = "https://example.com";
      // Authorization header is optional; use it if needed to access the callback url
      Map<String, String> notificationAuth = new HashMap<>();
      notificationAuth.put("Authorization", "Bearer <notification_token>");

      // Initialize the LanguageIdJobOptions object and assign
      LanguageIdJobOptions languageIdJobOptions = new LanguageIdJobOptions();
      languageIdJobOptions.setSourceConfig(mediaUrl, sourceAuth);
      languageIdJobOptions.setMetadata("My first submission");
      languageIdJobOptions.setNotificationConfig(callbackUrl, notificationAuth);
      languageIdJobOptions.setDeleteAfterSeconds(2592000); // 30 days in seconds

      LanguageIdJob submittedJob;

      try {
          // Submit job with language id options
          submittedJob = languageIdClient.submitJob(languageIdJobOptions);
      } catch (IOException e) {
          throw new RuntimeException("Failed to submit url [" + mediaUrl + "] " + e.getMessage());
      }
      String jobId = submittedJob.getJobId();
      System.out.println("Job Id: " + jobId);
      System.out.println("Job Status: " + submittedJob.getJobStatus());
      System.out.println("Created On: " + submittedJob.getCreatedOn());

      // Waits 5 seconds between each status check to see if job is complete
      boolean isJobComplete = false;
      while (!isJobComplete) {
          LanguageIdJob retrievedJob;
          try {
              retrievedJob = languageIdClient.getJobDetails(jobId);
          } catch (IOException e) {
              throw new RuntimeException("Failed to retrieve job [" + jobId + "] " + e.getMessage());
          }

          LanguageIdJobStatus retrievedJobStatus = retrievedJob.getJobStatus();
          if (retrievedJobStatus == LanguageIdJobStatus.COMPLETED
                  || retrievedJobStatus == LanguageIdJobStatus.FAILED) {
              isJobComplete = true;
          } else {
              try {
                  Thread.sleep(5000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }

      // Get the language id result
      LanguageIdResult languageIdResult;

      try {
          languageIdResult = languageIdClient.getResultObject(jobId);
          System.out.printf("Top Language: %s\n", languageIdResult.getTopLanguage());
          for (LanguageConfidence languageConfidence : languageIdResult.getLanguageConfidences()) {
              System.out.printf("Language: %s Confidence: %f\n", languageConfidence.getLanguage(), languageConfidence.getConfidence());
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      /*
       * The job can now be deleted. Deleting the job will remove ALL information
       * about the job from the Rev AI servers. Subsequent requests to Rev AI that
       * use the deleted jobs Id will return 404's.
       */
      // languageIdClient.deleteJob(jobId);
      // System.out.printf("Deleted language id job %s", jobId);
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
