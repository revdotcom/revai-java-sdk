package ai.rev.speechtotext.models.vocabulary;

import ai.rev.speechtotext.models.CustomerUrlData;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class CustomVocabularySubmission {

  /** Optional information that can be provided. */
  @SerializedName("metadata")
  private String metadata;

  /**
   * Optional callback url that Rev AI will send a POST to when the job has finished.
   * @deprecated Use notification_config instead
   */
  @SerializedName("callback_url")
  @Deprecated
  private String callbackUrl;

  /** Optional parameter for information on the callback url that Rev AI will send a POST to when the job has finished. */
  @SerializedName("notification_config")
  private CustomerUrlData notificationConfig;

  /** Array of {@link CustomVocabulary} objects. */
  @SerializedName("custom_vocabularies")
  private List<CustomVocabulary> customVocabularies;

  /**
   * Returns the metadata.
   *
   * @return A String that contains the metadata.
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * Optional metadata that is provided during custom vocabulary submission limited to 512
   * characters.
   *
   * @param metadata A String to set as the metadata.
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * Returns the callback url.
   *
   * @return the callback url.
   * @deprecated Set the notificationConfig option with setNotificationConfig, then use getNotificationConfig instead
   */
  @Deprecated
  public String getCallbackUrl() {
    return callbackUrl;
  }

  /**
   * Specifies the callback url that Rev AI will POST to when custom vocabulary processing is
   * complete. This property is optional.
   *
   * @param callbackUrl The url to POST to when custom vocabulary processing is complete.
   * @deprecated Use setNotificationConfig instead
   */
  @Deprecated
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  /**
   * Returns the notification config.
   *
   * @return the notification config.
   */
  public CustomerUrlData getNotificationConfig() {
    return notificationConfig;
  }

  /**
   * Specifies the callback url that Rev AI will POST to when custom vocabulary processing is
   * complete and the auth headers to use when calling the url
   * This property is optional.
   *
   * @param callbackUrl The url to POST to when custom vocabulary processing is complete.
   * @param authHeaders Optional parameter to authenticate access to the callback url
   */
  public void setNotificationConfig(String callbackUrl, Map<String, String> authHeaders) {
    this.notificationConfig = new CustomerUrlData(callbackUrl, authHeaders);
  }

  /**
   * Specifies the callback url that Rev AI will POST to when custom vocabulary processing is
   * complete
   * This property is optional.
   *
   * @param callbackUrl The url to POST to when custom vocabulary processing is complete.
   */
  public void setNotificationConfig(String callbackUrl) {
    this.notificationConfig = new CustomerUrlData(callbackUrl, null);
  }

  /**
   * Returns a list of {@link CustomVocabulary} objects.
   *
   * @return A list of {@link CustomVocabulary} objects.
   * @see CustomVocabulary
   */
  public List<CustomVocabulary> getCustomVocabularies() {
    return customVocabularies;
  }

  /**
   * Provides the custom vocabularies to be used by the speech engine when processing the stream.
   * List size is limited to 50 items.
   *
   * @param customVocabularies A list of custom vocabularies.
   * @see CustomVocabulary
   */
  public void setCustomVocabularies(List<CustomVocabulary> customVocabularies) {
    this.customVocabularies = customVocabularies;
  }

  @Override
  public String toString() {
    return "{"
        + "metadata='"
        + metadata
        + '\''
        + ", callbackUrl='"
        + callbackUrl
        + '\''
        + ", customVocabularies="
        + customVocabularies
        + '}';
  }
}
