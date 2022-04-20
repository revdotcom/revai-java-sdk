package ai.rev;

import ai.rev.topicextraction.models.TopicExtractionJobOptions;
import ai.rev.topicextraction.models.TopicExtractionJob;
import ai.rev.topicextraction.models.TopicExtractionJobStatus;
import ai.rev.topicextraction.models.TopicExtractionResult;
import ai.rev.topicextraction.TopicExtractionClient;

import java.io.IOException;
import java.util.Arrays;

public class TopicExtraction {

  public static void main(String[] args) {
    // Assign your access token to a String
    String accessToken = "your_access_token";

    // Initialize the TopicExtractionClient with your access token
    TopicExtractionClient topicExtractionClient = new TopicExtractionClient(accessToken);

    // Create TopicExtractionJobOptions for your submission
    TopicExtractionJobOptions jobOptions = new TopicExtractionJobOptions();
    String textSubmission = "An orange is a fruit of various citrus species in the family Rutaceae " +
            "it primarily refers to Citrus sinensis which is also called sweet orange, " +
            "to distinguish it from the related Citrus aurantium, referred to as bitter orange. " +
            "The sweet orange reproduces asexually. varieties of sweet orange arise through mutations. " +
            "The orange is a hybrid between pomelo (Citrus maxima) and mandarin (Citrus reticulata). " +
            "The chloroplast genome, and therefore the maternal line, is that of pomelo. " +
            "The sweet orange has had its full genome sequenced.";


    TopicExtractionJob submittedJob;
    try {
      submittedJob = topicExtractionClient.submitJobText(textSubmission, jobOptions);
    } catch (IOException e) {
      throw new RuntimeException("Failed to submit topic extraction job " + e.getMessage());
    }

    String jobId = submittedJob.getJobId();
    System.out.println("Job Id: " + jobId);
    System.out.println("Job Status: " + submittedJob.getJobStatus());
    System.out.println("Created On: " + submittedJob.getCreatedOn());

    // Waits 5 seconds between each status check to see if job is complete
    boolean isProcessingComplete = false;
    while (!isProcessingComplete) {
      TopicExtractionJob retrievedJob;
      try {
        retrievedJob = topicExtractionClient.getJobDetails(jobId);
      } catch (IOException e) {
        throw new RuntimeException(
                "Failed to retrieve topic extraction job info ["
                        + jobId
                        + "] "
                        + e.getMessage());
      }

      TopicExtractionJobStatus status = retrievedJob.getJobStatus();
      if (status == TopicExtractionJobStatus.COMPLETED
              || status == TopicExtractionJobStatus.FAILED) {
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
      TopicExtractionResult result =
              topicExtractionClient.getResultObject(jobId);
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
