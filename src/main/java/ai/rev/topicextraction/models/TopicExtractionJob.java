package ai.rev.topicextraction.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/** A Topic Extraction Job object provides all the information associated with a job submitted by the user. */
public class TopicExtractionJob {

  @SerializedName("id")
  private String jobId;

  @SerializedName("status")
  private RevAiJobStatus jobStatus;

  @SerializedName("created_on")
  private String createdOn;

  @SerializedName("completed_on")
  private String completedOn;

  @SerializedName("callback_url")
  private String callbackUrl;
  
  @SerializedName("word_count")
  private Integer wordCount;

  @SerializedName("metadata")
  private String metadata;

  @SerializedName("type")
  private RevAiJobType type;

  @SerializedName("failure_details")
  private String failureDetails;

  @SerializedName("failure")
  private RevAiFailureType failure;

  @SerializedName("delete_after_seconds")
  private Integer deleteAfterSeconds;

  @SerializedName("language")
  private String language;

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
   * Returns the {@link RevAiJobStatus} enumeration value.
   *
   * @return The {@link RevAiJobStatus} enumeration value.
   * @see RevAiJobStatus
   */
  public RevAiJobStatus getJobStatus() {
    return jobStatus;
  }

  /**
   * Sets the job status to the provided {@link RevAiJobStatus} enumeration value.
   *
   * @param jobStatus The enumeration value to set as the job status.
   * @see RevAiJobStatus
   */
  public void setJobStatus(RevAiJobStatus jobStatus) {
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
     * Returns the word count of the submission.
     *
     * @return A String containing the word count of the submission.
     */
    public String getWordCount() {
      return wordCount;
    }
  
    /**
     * Sets the word count.
     *
     * @param callbackUrl An Integer value to set as the word count.
     */
    public void setWordCount(Integer wordCount) {
      this.wordCount = wordCount;
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

  /**
   * Returns language of the job
   *
   * @return language of the job
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Sets the language for job
   *
   * @param language An String value to set for language
   */
  public void setLanguage(String language) {
    this.language = language;
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
        + ", wordCount='"
        + wordCount
        + '\''
        + ", metadata='"
        + metadata
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
        + ", language='"
        + language
        + '\''
        + '}';
  }
}
