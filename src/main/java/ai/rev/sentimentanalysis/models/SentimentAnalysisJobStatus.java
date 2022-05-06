package ai.rev.sentimentanalysis.models;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define Rev AI job statuses. */
public enum SentimentAnalysisJobStatus {

  /** The status when job has failed. */
  @SerializedName("failed")
  FAILED("failed"),

  /** The status when job is in progress. */
  @SerializedName("in_progress")
  IN_PROGRESS("in_progress"),

  /** The status when job processing has been completed. */
  @SerializedName("completed")
  COMPLETED("completed");

  private String status;

  SentimentAnalysisJobStatus(String status) {
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
