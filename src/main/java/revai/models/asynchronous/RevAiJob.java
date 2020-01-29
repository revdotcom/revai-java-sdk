package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A RevAi Job object provides all the information associated with a job submitted by the user.  
 */
public class RevAiJob {
  @SerializedName("id")
  private String jobID;

  @SerializedName("created_on")
  private String createdOn;

  @SerializedName("completed_on")
  private String completedOn;

  @SerializedName("duration_seconds")
  private Double durationSeconds;

  @SerializedName("callback_url")
  private String callbackUrl;

  @SerializedName("media_url")
  private String mediaUrl;

  @SerializedName("failure_details")
  private String failureDetails;

  @SerializedName("status")
  private RevAiJobStatus jobStatus;

  @SerializedName("metadata")
  private String metadata;

  @SerializedName("failure")
  private String failure;

  @SerializedName("name")
  private String name;

  @SerializedName("type")
  private String type;

  public String getJobID() {
    return jobID;
  }

  public void setJobID(String jobID) {
    this.jobID = jobID;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  public String getCompletedOn() {
    return completedOn;
  }

  public void setCompletedOn(String completedOn) {
    this.completedOn = completedOn;
  }

  public Double getDurationSeconds() {
    return durationSeconds;
  }

  public void setDurationSeconds(Double durationSeconds) {
    this.durationSeconds = durationSeconds;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  public String getMediaUrl() {
    return mediaUrl;
  }

  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public String getFailureDetails() {
    return failureDetails;
  }

  public void setFailureDetails(String failureDetails) {
    this.failureDetails = failureDetails;
  }

  public RevAiJobStatus getJobStatus() {
    return jobStatus;
  }

  public void setJobStatus(RevAiJobStatus jobStatus) {
    this.jobStatus = jobStatus;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public String getFailure() {
    return failure;
  }

  public void setFailure(String failure) {
    this.failure = failure;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
