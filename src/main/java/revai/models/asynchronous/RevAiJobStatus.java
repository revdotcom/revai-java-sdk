package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public enum RevAiJobStatus
{
  @SerializedName("failed")
  FAILED("failed"),

  @SerializedName("in_progress")
  IN_PROGRESS("in_progress"),

  @SerializedName("transcribed")
  TRANSCRIBED("transcribed");

  private String status;

  RevAiJobStatus(String envUrl) {
    this.status = envUrl;
  }

  public String getStatus() {
    return status;
  }
}