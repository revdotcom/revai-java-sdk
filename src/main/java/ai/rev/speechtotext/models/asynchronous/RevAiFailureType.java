package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define Rev AI failure types. */
public enum RevAiFailureType {

  /** The failure used when the media provided in the submission request fails to download. */
  @SerializedName("download_failure")
  DOWNLOAD_FAILURE("download_failure"),

  /** The failure used when the media provided doesn't contain any audio. */
  @SerializedName("empty_media")
  EMPTY_MEDIA("empty_media"),

  /** The failure used when the account does not have enough credits remaining. */
  @SerializedName("insufficient_balance")
  INSUFFICIENT_BALANCE("insufficient_balance"),

  /** The failure used when there is a processing error. */
  @SerializedName("internal_processing")
  INTERNAL_PROCESSING("internal_processing"),

  /** The failure used when the file submitted is not a valid or supported media file. */
  @SerializedName("invalid_media")
  INVALID_MEDIA("invalid_media"),

  /** The failure used when the account has reached or exceeded its invoicing limit. */
  @SerializedName("invoicing_limit_exceeded")
  INVOICING_LIMIT_EXCEEDED("invoicing_limit_exceeded"),

  /** The failure used when an error occurs during transcription and prevents job completion. */
  @SerializedName("transcription")
  TRANSCRIPTION("transcription");

  private String failureType;

  RevAiFailureType(String failureType) {
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
