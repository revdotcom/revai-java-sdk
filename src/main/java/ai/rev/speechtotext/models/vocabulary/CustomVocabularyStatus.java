package ai.rev.speechtotext.models.vocabulary;

import com.google.gson.annotations.SerializedName;

public enum CustomVocabularyStatus {

  /** The status when custom vocabulary has been processed and completed. */
  @SerializedName("complete")
  COMPLETE("complete"),

  /** The status when the custom vocabulary job has failed. */
  @SerializedName("failed")
  FAILED("failed"),

  /** The status when the custom vocabulary job is in progress. */
  @SerializedName("in_progress")
  IN_PROGRESS("in_progress");

  private String status;

  CustomVocabularyStatus(String status) {
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
