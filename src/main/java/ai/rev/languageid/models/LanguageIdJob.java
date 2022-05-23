package ai.rev.languageid.models;

import ai.rev.speechtotext.models.asynchronous.RevAiFailureType;
import ai.rev.speechtotext.models.asynchronous.RevAiJobType;
import com.google.gson.annotations.SerializedName;

public class LanguageIdJob {

    @SerializedName("id")
    private String jobId;

    @SerializedName("status")
    private LanguageIdJobStatus jobStatus;

    @SerializedName("created_on")
    private String createdOn;

    @SerializedName("completed_on")
    private String completedOn;

    @SerializedName("callback_url")
    private String callbackUrl;

    @SerializedName("metadata")
    private String metadata;

    @SerializedName("media_url")
    private String mediaUrl;

    @SerializedName("type")
    private RevAiJobType type;

    @SerializedName("failure_details")
    private String failureDetails;

    @SerializedName("failure")
    private RevAiFailureType failure;

    @SerializedName("processed_duration_seconds")
    private Double processedDurationSeconds;

    @SerializedName("delete_after_seconds")
    private Integer deleteAfterSeconds;

    /**
     * Returns a String that contains the job ID.
     *
     * @return A String that contains the job ID.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Sets the Job ID.
     *
     * @param jobId The String value to set as the job ID.
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns the {@link LanguageIdJobStatus} enumeration value.
     *
     * @return The {@link LanguageIdJobStatus} enumeration value.
     * @see LanguageIdJobStatus
     */
    public LanguageIdJobStatus getJobStatus() {
        return jobStatus;
    }

    /**
     * Sets the job status to the provided {@link LanguageIdJobStatus} enumeration value.
     *
     * @param jobStatus The enumeration value to set as the job status.
     * @see LanguageIdJobStatus
     */
    public void setJobStatus(LanguageIdJobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * Returns a String that contains the date and time the job was created on in ISO-8601 UTC form.
     *
     * @return A String that contains the date and time the job was created on in ISO-8601 UTC form.
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the time and date the job was created on.
     *
     * @param createdOn The String value to set as the created on date and time.
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Returns a String that contains the date and time the job was completed on in ISO-8601 UTC form.
     *
     * @return A String that contains the date and time the job was completed on in ISO-8601 UTC form.
     */
    public String getCompletedOn() {
        return completedOn;
    }

    /**
     * Set the date and time the job was completed on.
     *
     * @param completedOn The String value to set as the date and time the job was completed on.
     */
    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    /**
     * Returns the callback url provided in the submission request.
     *
     * @return A String containing the callback url provided in the submission request.
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * Sets the callback url.
     *
     * @param callbackUrl A String value to set as the callback url.
     */
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /**
     * Returns the metadata provided in the submission request.
     *
     * @return A String containing the metadata provided in the submission request.
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata A String to set as the metadata.
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns the media url provided in the submission request.
     *
     * @return A String containing the media url provided in the submission request.
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * Sets the media url.
     *
     * @param mediaUrl A String value to set as the media url.
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     * Returns the {@link RevAiJobType} enumeration value.
     *
     * @return the enumeration value.
     * @see RevAiJobType
     */
    public RevAiJobType getType() {
        return type;
    }

    /**
     * Sets the job type to the provided {@link RevAiJobType} enumeration.
     *
     * @param type The enumeration value to set as the job type.
     * @see RevAiJobType
     */
    public void setType(RevAiJobType type) {
        this.type = type;
    }

    /**
     * Returns a detailed, human readable explanation of the failure.
     *
     * @return A detailed, human readable explanation of the failure.
     */
    public String getFailureDetails() {
        return failureDetails;
    }

    /**
     * Sets the failure details to the provided value.
     *
     * @param failureDetails A String to set as the failure details.
     */
    public void setFailureDetails(String failureDetails) {
        this.failureDetails = failureDetails;
    }

    /**
     * Returns the {@link RevAiFailureType} enumeration value.
     *
     * @return The {@link RevAiFailureType} enumeration value.
     * @see RevAiFailureType
     */
    public RevAiFailureType getFailure() {
        return failure;
    }

    /**
     * Sets the failure to the provided {@link RevAiFailureType} enumeration.
     *
     * @param failure The enumeration value to set as the failure.
     * @see RevAiFailureType
     */
    public void setFailure(RevAiFailureType failure) {
        this.failure = failure;
    }

    /**
     * Returns the processed audio duration of the file in seconds.
     *
     * @return The processed audio duration of the file in seconds.
     */
    public Double getProcessedDurationSeconds() {
        return processedDurationSeconds;
    }

    /**
     * Sets the processed audio duration.
     *
     * @param processedDurationSeconds A Double value to set as processed audio duration.
     */
    public void setDurationSeconds(Double processedDurationSeconds) {
        this.processedDurationSeconds = processedDurationSeconds;
    }

    /**
     * Returns the duration in seconds before job is deleted
     *
     * @return The duration in seconds.
     */
    public Integer getDeleteAfterSeconds() {
        return deleteAfterSeconds;
    }

    /**
     * Sets the duration in seconds before job is deleted
     *
     * @param deleteAfterSeconds An Integer value to set as seconds before deletion.
     */
    public void setDeleteAfterSeconds(Integer deleteAfterSeconds) {
        this.deleteAfterSeconds = deleteAfterSeconds;
    }

    @Override
    public String toString() {
        return "{"
            + "jobID='"
            + jobId
            + '\''
            + ", jobStatus="
            + jobStatus
            + ", createdOn='"
            + createdOn
            + '\''
            + ", completedOn='"
            + completedOn
            + '\''
            + ", callbackUrl='"
            + callbackUrl
            + '\''
            + ", metadata='"
            + metadata
            + '\''
            + ", mediaUrl='"
            + mediaUrl
            + '\''
            + ", type='"
            + type.getJobType()
            + '\''
            + ", failureDetails='"
            + failureDetails
            + '\''
            + ", failure='"
            + failure.getFailureType()
            + '\''
            + ", processedDurationSeconds="
            + processedDurationSeconds
            + '\''
            + ", deleteAfterSeconds='"
            + deleteAfterSeconds
            + '\''
            + '}';
    }
}
