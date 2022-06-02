package ai.rev;

import ai.rev.languageid.LanguageIdClient;
import ai.rev.languageid.models.LanguageIdJob;
import ai.rev.languageid.models.LanguageIdJobOptions;
import ai.rev.languageid.models.LanguageIdJobStatus;
import ai.rev.languageid.models.LanguageIdResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LanguageIdMediaUrl {

  public static void main(String[] args) {
    // Assign your access token to a String
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
}
