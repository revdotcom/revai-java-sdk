package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public class RevAiJob {
  @SerializedName("id")
  public String jobID;

  @SerializedName("created_on")
  public String createdOn;

  @SerializedName("completed_on")
  public String completedOn;

  @SerializedName("duration_seconds")
  public Double durationSeconds;

  @SerializedName("callback_url")
  public String callbackUrl;

  @SerializedName("media_url")
  public String mediaUrl;

  @SerializedName("failure_details")
  public String failureDetails;

  @SerializedName("status")
  public RevAiJobStatus jobStatus;

  @SerializedName("metadata")
  public String metadata;

  @SerializedName("failure")
  public String failure;

  @SerializedName("name")
  public String name;

  @SerializedName("type")
  public String type;
}
