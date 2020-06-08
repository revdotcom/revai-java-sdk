package ai.rev.speechtotext.models;

import ai.rev.speechtotext.models.asynchronous.RevAiFailureType;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import com.google.gson.annotations.SerializedName;

public class CustomVocabularyInformation {

  @SerializedName("id")
  private String id;

  @SerializedName("status")
  private RevAiJobStatus status;

  @SerializedName("created_on")
  private String createdOn;

  @SerializedName("completed_on")
  private String completedOn;

  @SerializedName("metadata")
  private String metadata;

  @SerializedName("callback_url")
  private String callbackUrl;

  @SerializedName("failure")
  private RevAiFailureType failure;

  @SerializedName("failure_detail")
  private String failureDetail;

  /**
   * Returns a String that contains the Custom Vocabulary Id.
   *
   * @return A String that contains the Custom Vocabulary Id.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the custom vocabulary Id to the provided value.
   *
   * @param id The String value to set as the custom vocabulary Id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns the {@link RevAiJobStatus} enumeration value.
   *
   * @return The {@link RevAiJobStatus} enumeration value.
   */
  public RevAiJobStatus getStatus() {
    return status;
  }

  /**
   * Sets the status to the provided {@link RevAiJobStatus} enumeration value.
   *
   * @param status The enumeration value to set as the custom vocabulary status.
   */
  public void setStatus(RevAiJobStatus status) {
    this.status = status;
  }

  /**
   * Returns a String that contains the date and time the custom vocabulary was created on in
   * ISO-8601 UTC form.
   *
   * @return A String that contains the date and time the custom vocabulary was created on in
   *     ISO-8601 UTC form.
   */
  public String getCreatedOn() {
    return createdOn;
  }

  /**
   * Sets the time and date the custom vocabulary was created on.
   *
   * @param createdOn The String value to set as the created on date and time.
   */
  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * Returns a String that contains the date and time the custom vocabulary was completed on in
   * ISO-8601 UTC form.
   *
   * @return A String that contains the date and time the custom vocabulary was completed on.
   */
  public String getCompletedOn() {
    return completedOn;
  }

  /**
   * Set the date and time the custom vocabulary was completed on.
   *
   * @param completedOn The String value to set as the date and time the job was completed on.
   */
  public void setCompletedOn(String completedOn) {
    this.completedOn = completedOn;
  }

  /**
   * Returns the metadata provided in the submission request.
   *
   * @return A String containing the metadata provided in the submission request.
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * Sets the metadata.
   *
   * @param metadata A String to set as the metadata.
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * Returns the callback url provided in the submission request.
   *
   * @return A String containing the callback url provided in the submission request.
   */
  public String getCallbackUrl() {
    return callbackUrl;
  }

  /**
   * Sets the callback url.
   *
   * @param callbackUrl A String value to set as the callback url.
   */
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  /**
   * Returns the {@link RevAiFailureType} enumeration value.
   *
   * @return The {@link RevAiFailureType} enumeration value.
   */
  public RevAiFailureType getFailure() {
    return failure;
  }

  /**
   * Sets the failure details to the provided value.
   *
   * @param failure A String to set as the failure details.
   */
  public void setFailure(RevAiFailureType failure) {
    this.failure = failure;
  }

  /**
   * Returns a detailed, human readable explanation of the failure.
   *
   * @return A detailed, human readable explanation of the failure.
   */
  public String getFailureDetail() {
    return failureDetail;
  }

  /**
   * Sets the failure detail to the provided value.
   *
   * @param failureDetail A String to set as the failure detail.
   */
  public void setFailureDetail(String failureDetail) {
    this.failureDetail = failureDetail;
  }

  @Override
  public String toString() {
    return "{"
        + "id='"
        + id
        + '\''
        + ", status="
        + status
        + ", createdOn='"
        + createdOn
        + '\''
        + ", callbackUrl='"
        + callbackUrl
        + '\''
        + ", failure="
        + failure
        + ", failureDetail='"
        + failureDetail
        + '\''
        + '}';
  }
}
