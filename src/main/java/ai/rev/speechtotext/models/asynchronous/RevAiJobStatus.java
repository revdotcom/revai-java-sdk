package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define Rev.ai job statuses. */
public enum RevAiJobStatus {

  /** The status when custom vocabulary has been processed and completed. */
  @SerializedName("complete")
  COMPLETE("complete"),

  /** The status when the job has failed. */
  @SerializedName("failed")
  FAILED("failed"),

  /** The status when the job is in progress. */
  @SerializedName("in_progress")
  IN_PROGRESS("in_progress"),

  /** The status when the file has been transcribed. */
  @SerializedName("transcribed")
  TRANSCRIBED("transcribed");

  private String status;

  RevAiJobStatus(String status) {
    this.status = status;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "{" + "status='" + status + '\'' + '}';
  }
}
