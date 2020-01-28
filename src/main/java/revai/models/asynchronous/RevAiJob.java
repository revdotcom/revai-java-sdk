package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public class RevAiJob {
  @SerializedName("id")
  private String jobID;

  @SerializedName("created_on")
  private String createdOn;

  @SerializedName("completed_on")
  private String completedOn;

  @SerializedName("duration_seconds")
  public Double durationSeconds;

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

  private String getJobID() {
    return jobID;
  }

  public void setJobID(String jobID) {
    this.jobID = jobID;
  }

  private String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  private String getCompletedOn() {
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

  private String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  private String getMediaUrl() {
    return mediaUrl;
  }

  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  private String getFailureDetails() {
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

  private String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  private String getFailure() {
    return failure;
  }

  public void setFailure(String failure) {
    this.failure = failure;
  }

  private String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
