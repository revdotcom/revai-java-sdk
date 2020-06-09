package ai.rev.speechtotext.models.vocabulary;

import com.google.gson.annotations.SerializedName;

public enum CustomVocabularyFailureType {

  /** The failure used when there is a processing error. */
  @SerializedName("internal_processing")
  INTERNAL_PROCESSING("internal_processing");

  private String failureType;

  CustomVocabularyFailureType(String failureType) {
    this.failureType = failureType;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getFailureType() {
    return failureType;
  }

  @Override
  public String toString() {
    return "{" + "failureType='" + failureType + '\'' + '}';
  }
}
