package ai.rev;

import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.NlpModel;
import ai.rev.speechtotext.models.asynchronous.*;
import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AsyncSummarizeMediaUrl {

  public static void main(String[] args) {
    // Assign your access token to a String
    String accessToken = "<YOUR_ACCESS_TOKEN>";

    // Initialize the ApiClient with your access token
    ApiClient apiClient = new ApiClient(accessToken);

    // Set up source configuration parameters
    String mediaUrl = "https://www.rev.ai/FTC_Sample_1.mp3";

    // Initialize the RevAiJobOptions object and assign
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setSourceConfig(mediaUrl, null);
    revAiJobOptions.setDeleteAfterSeconds(2592000); // 30 days in seconds
    revAiJobOptions.setLanguage("en");
    revAiJobOptions.setSummarizationOptions(new SummarizationOptions().setModel(NlpModel.STANDARD));

    RevAiJob submittedJob;

    try {
      // Submit job with transcription options
      submittedJob = apiClient.submitJobUrl(revAiJobOptions);
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
      RevAiJob retrievedJob;
      try {
        retrievedJob = apiClient.getJobDetails(jobId);
      } catch (IOException e) {
        throw new RuntimeException("Failed to retrieve job [" + jobId + "] " + e.getMessage());
      }

      RevAiJobStatus retrievedJobStatus = retrievedJob.getJobStatus();
      if (retrievedJobStatus == RevAiJobStatus.TRANSCRIBED
          || retrievedJobStatus == RevAiJobStatus.FAILED) {
        isJobComplete = true;
      } else {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    // Waits 5 seconds between each status check to see if summarization job is complete
    isJobComplete = false;
    while (!isJobComplete) {
      RevAiJob retrievedJob;
      try {
        retrievedJob = apiClient.getJobDetails(jobId);
      } catch (IOException e) {
        throw new RuntimeException("Failed to retrieve job [" + jobId + "] " + e.getMessage());
      }

      SummarizationJobStatus summarizationJobStatus = retrievedJob.getSummarization().getJobStatus();
      if (summarizationJobStatus == SummarizationJobStatus.COMPLETED
              || summarizationJobStatus == SummarizationJobStatus.FAILED) {
        isJobComplete = true;
      } else {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    // Get the transcript and caption outputs
    Summary objectSummary;
    String textSummary;

    try {
      objectSummary = apiClient.getTranscriptSummaryObject(jobId);
      textSummary = apiClient.getTranscriptSummaryText(jobId);

      System.out.println("Summary:" + textSummary);
    } catch (IOException e) {
      e.printStackTrace();
    }


    /*
     * The job can now be deleted. Deleting the job will remove ALL information
     * about the job from the Rev AI servers. Subsequent requests to Rev AI that
     * use the deleted jobs Id will return 404's.
     */
    // apiClient.deleteJob(jobId);
  }
}
