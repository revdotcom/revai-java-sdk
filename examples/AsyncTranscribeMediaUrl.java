package ai.rev;

import ai.rev.speechtotext.models.asynchronous.RevAiCaptionType;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class AsyncTranscribeMediaUrl {

  public static void main(String[] args) {
    // Assign your access token to a String
    String accessToken = "your_access_token";

    // Initialize the ApiClient with your access token
    ApiClient apiClient = new ApiClient(accessToken);

    // Create a custom vocabulary for your submission
    CustomVocabulary customVocabulary =
        new CustomVocabulary(Arrays.asList("Robert Berwick", "Noam Chomsky", "Evelina Fedorenko"));

    // Initialize the RevAiJobOptions object and assign
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setCustomVocabularies(Arrays.asList(customVocabulary));
    revAiJobOptions.setMetadata("My first submission");
    revAiJobOptions.setCallbackUrl("https://example.com");
    revAiJobOptions.setSkipPunctuation(false);
    revAiJobOptions.setSkipDiarization(false);
    revAiJobOptions.setFilterProfanity(true);
    revAiJobOptions.setRemoveDisfluencies(true);
    revAiJobOptions.setSpeakerChannelsCount(null);
    revAiJobOptions.setDeleteAfterSeconds(2592000); // 30 days in seconds
    revAiJobOptions.setLanguage("en");
    revAiJobOptions.setTranscriber("machine_v2");

    RevAiJob submittedJob;

    String mediaUrl = "https://www.rev.ai/FTC_Sample_1.mp3";

    try {
      // Submit the local file and transcription options
      submittedJob = apiClient.submitJobUrl(mediaUrl, revAiJobOptions);
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

    // Get the transcript and caption outputs
    RevAiTranscript objectTranscript;
    String textTranscript;
    InputStream srtCaptions;
    InputStream vttCaptions;

    try {
      objectTranscript = apiClient.getTranscriptObject(jobId);
      textTranscript = apiClient.getTranscriptText(jobId);
      srtCaptions = apiClient.getCaptions(jobId, RevAiCaptionType.SRT);
      vttCaptions = apiClient.getCaptions(jobId, RevAiCaptionType.VTT);
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
