package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define Summarization job statuses. */
public enum SummarizationJobStatus {
  /** The status when transcription has failed. */
  @SerializedName("failed")
  FAILED("failed"),

  /** The status when transcription of the file is in progress. */
  @SerializedName("in_progress")
  IN_PROGRESS("in_progress"),

  /** The status when the file has been transcribed. */
  @SerializedName("completed")
  COMPLETED("completed");

  private String status;

  SummarizationJobStatus(String status) {
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
