package ai.rev;

import ai.rev.sentimentanalysis.models.SentimentAnalysisJobOptions;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJob;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJobStatus;
import ai.rev.sentimentanalysis.models.SentimentAnalysisResult;
import ai.rev.sentimentanalysis.SentimentAnalysisClient;

import java.io.IOException;
import java.util.Arrays;

public class SentimentAnalysis {

  public static void main(String[] args) {
    // Assign your access token to a String
    String accessToken = "your_access_token";

    // Initialize the SentimentAnalysisClient with your access token
    SentimentAnalysisClient sentimentAnalysisClient = new SentimentAnalysisClient(accessToken);

    // Create SentimentAnalysisJobOptions for your submission
    SentimentAnalysisJobOptions jobOptions = new SentimentAnalysisJobOptions();
    String textSubmission = "An orange is a fruit of various citrus species in the family Rutaceae " +
            "it primarily refers to Citrus sinensis which is also called sweet orange, " +
            "to distinguish it from the related Citrus aurantium, referred to as bitter orange. " +
            "The sweet orange reproduces asexually. varieties of sweet orange arise through mutations. " +
            "The orange is a hybrid between pomelo (Citrus maxima) and mandarin (Citrus reticulata). " +
            "The chloroplast genome, and therefore the maternal line, is that of pomelo. " +
            "The sweet orange has had its full genome sequenced.";


    SentimentAnalysisJob submittedJob;
    try {
      submittedJob = sentimentAnalysisClient.submitJobText(textSubmission, jobOptions);
    } catch (IOException e) {
      throw new RuntimeException("Failed to submit sentiment analysis job " + e.getMessage());
    }

    String jobId = submittedJob.getJobId();
    System.out.println("Job Id: " + jobId);
    System.out.println("Job Status: " + submittedJob.getJobStatus());
    System.out.println("Created On: " + submittedJob.getCreatedOn());

    // Waits 5 seconds between each status check to see if job is complete
    boolean isProcessingComplete = false;
    while (!isProcessingComplete) {
      SentimentAnalysisJob retrievedJob;
      try {
        retrievedJob = sentimentAnalysisClient.getJobDetails(jobId);
      } catch (IOException e) {
        throw new RuntimeException(
                "Failed to retrieve sentiment analysis job info ["
                        + jobId
                        + "] "
                        + e.getMessage());
      }

      SentimentAnalysisJobStatus status = retrievedJob.getJobStatus();
      if (status == SentimentAnalysisJobStatus.COMPLETED
              || status == SentimentAnalysisJobStatus.FAILED) {
        System.out.println(
                "Processing for "
                        + jobId
                        + ": "
                        + status);
        isProcessingComplete = true;
      } else {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    try {
      SentimentAnalysisResult result =
              sentimentAnalysisClient.getResultObject(jobId);
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
