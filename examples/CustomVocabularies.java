package ai.rev;

import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyInformation;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyStatus;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularySubmission;

import java.io.IOException;
import java.util.Arrays;

public class CustomVocabularies {

  public static void main(String[] args) {
    // Assign your access token to a String
    String accessToken = "your_access_token";

    // Initialize the CustomVocabulariesClient with your access token
    CustomVocabulariesClient customVocabulariesClient = new CustomVocabulariesClient(accessToken);

    // Create a custom vocabulary for your submission
    CustomVocabulary customVocabulary =
        new CustomVocabulary(Arrays.asList("Robert Berwick", "Noam Chomsky", "Evelina Fedorenko"));

    CustomVocabularySubmission customVocabularySubmission = new CustomVocabularySubmission();
    customVocabularySubmission.setCustomVocabularies(Arrays.asList(customVocabulary));
    // Authorization header is optional; use it if needed to access the callback url
    customVocabularySubmission.setNotificationConfig("https://example.com", "Authorization: Bearer <token>");
    customVocabularySubmission.setMetadata("My first submission");

    CustomVocabularyInformation submittedCustomVocabularyInfo;

    try {
      submittedCustomVocabularyInfo =
          customVocabulariesClient.submitCustomVocabularies(customVocabularySubmission);
    } catch (IOException e) {
      throw new RuntimeException("Failed to submit custom vocabulary " + e.getMessage());
    }

    String customVocabularyId = submittedCustomVocabularyInfo.getId();
    System.out.println("Vocabulary Id: " + customVocabularyId);
    System.out.println("Vocabulary Status: " + submittedCustomVocabularyInfo.getStatus());
    System.out.println("Created On: " + submittedCustomVocabularyInfo.getCreatedOn());

    // Waits 5 seconds between each status check to see if job is complete
    boolean isProcessingComplete = false;
    while (!isProcessingComplete) {
      CustomVocabularyInformation retrievedVocabularyInfo;
      try {
        retrievedVocabularyInfo =
            customVocabulariesClient.getCustomVocabularyInformation(customVocabularyId);
      } catch (IOException e) {
        throw new RuntimeException(
            "Failed to retrieve custom vocabulary info ["
                + customVocabularyId
                + "] "
                + e.getMessage());
      }

      CustomVocabularyStatus retrievedVocabularyInfoStatus = retrievedVocabularyInfo.getStatus();
      if (retrievedVocabularyInfoStatus == CustomVocabularyStatus.COMPLETE
          || retrievedVocabularyInfoStatus == CustomVocabularyStatus.FAILED) {
        System.out.println(
            "Custom vocabulary processing for "
                + customVocabularyId
                + ": "
                + retrievedVocabularyInfoStatus.getStatus());
        isProcessingComplete = true;
      } else {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    // Once the custom vocabulary processing status is "complete" it can now be used in any
    // streaming job
  }
}
