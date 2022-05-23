package ai.rev;

import ai.rev.languageid.LanguageIdClient;
import ai.rev.languageid.models.LanguageIdJob;
import ai.rev.languageid.models.LanguageIdJobOptions;
import ai.rev.languageid.models.LanguageIdJobStatus;
import ai.rev.languageid.models.LanguageIdResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class LanguageIdLocalMediaFile {

    public static void main(String[] args) {
        // Assign your access token to a String
        String accessToken = "<YOUR_ACCESS_TOKEN>";

        // Initialize the LanguageIdClient with your access token
        LanguageIdClient languageIdClient = new LanguageIdClient(accessToken);

        // Initialize the LanguageIdJobOptions object and assign
        LanguageIdJobOptions languageIdJobOptions = new LanguageIdJobOptions();
        languageIdJobOptions.setMetadata("My first submission");
        languageIdJobOptions.setNotificationConfig("https://example.com");
        languageIdJobOptions.setDeleteAfterSeconds(2592000); // 30 days in seconds

        LanguageIdJob submittedJob;

        String localFile = "path/to/file";

        try {
            // Submit the local file and language id options
            submittedJob = languageIdClient.submitJobLocalFile(localFile, languageIdJobOptions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to submit file [" + localFile + "] " + e.getMessage());
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
            if (retrievedJobStatus == LanguageIdJobStatus.COMPLETE
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * The job can now be deleted. Deleting the job will remove ALL information
         * about the job from the Rev AI servers. Subsequent requests to Rev AI that
         * use the deleted jobs Id will return 404's.
         */
        // languageIdClient.deleteJob(jobId);
    }
}
