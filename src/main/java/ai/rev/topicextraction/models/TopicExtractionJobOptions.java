package ai.rev.topicextraction.models;

import ai.rev.speechtotext.models.CustomerUrlData;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * A TopicExtractionJobOptions object represents parameters that are submitted along a new job.
 *
 * @see <a
 *     href="https://docs.rev.ai/api/topic-extraction/reference/#operation/SubmitTopicExtractionJob">https://docs.rev.ai/api/topic-extraction/reference/#operation/SubmitTopicExtractionJob</a>
 */
public class TopicExtractionJobOptions {
  /**
   * Plain text string to be topic extracted.
   */
  @SerializedName("text")
  private String text;
    
  /**
   * RevAiTranscript from an async speech to text job to be topic extracted
   */
  @SerializedName("json")
  private RevAiTranscript json;

  /**
   * The callback url that Rev AI will send a POST to when the job has finished.
   * @deprecated Use notification_config instead
   */
  @SerializedName("callback_url")
  @Deprecated
  private String callbackUrl;

  /** Object containing information on the callback url that Rev AI will send a POST to when the job has finished. */
  @SerializedName("notification_config")
  private CustomerUrlData notificationConfig;

    
  /**
   * Optional information that can be provided.
   */
  @SerializedName("metadata")
  private String metadata;
  
  /**
   * Optional number of seconds after job completion when job is auto-deleted
   */
  @SerializedName("delete_after_seconds")
  private Integer deleteAfterSeconds;
 
  /**
   * Returns the text.
   *
   * @return the text.
   */
  public String getText() {
    return text;
  }

  /**
   * Specifies plain text that will have topic extraction run on it
   *
   * @param text plain text to be topic extracted.
   */
  public void setText(String text) {
    this.text = text;
  } 
  
  /**
   * Returns the json.
   *
   * @return the json.
   */
  public RevAiTranscript getJson() {
    return json;
  }

  /**
   * Specifies a RevAiTranscript from the async api that will have topic extraction run on it
   *
   * @param json RevAiTranscript to be topic extracted.
   */
  public void setJson(RevAiTranscript json) {
    this.json = json;
  }

  /**
   * Returns the callback url.
   *
   * @return the callback url.
   * @deprecated Use setNotificationConfig instead
   */
  @Deprecated
  public String getCallbackUrl() {
    return callbackUrl;
  }

  /**
   * Specifies the callback url that Rev AI will POST to when job processing is complete. This
   * property is optional.
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   * @deprecated Use setNotificationConfig instead
   */
  @Deprecated
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  /**
   * Returns the notification config object.
   *
   * @return the notification config.
   */
  public CustomerUrlData getNotificationConfig() {
    return notificationConfig;
  }

  /**
   * Optional property to specify the callback url that Rev AI will POST to when job processing is complete
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   * @param authHeaders Optional parameter to authenticate access to the callback url
   */
  public void setNotificationConfig(String callbackUrl, Map<String, String> authHeaders) {
    this.notificationConfig = new CustomerUrlData(callbackUrl, authHeaders);
  }

  /**
   * Optional property to specify the callback url that Rev AI will POST to when job processing is complete
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   */
  public void setNotificationConfig(String callbackUrl) {
    setNotificationConfig(callbackUrl, null);
  }
  
  /**
   * Returns the metadata.
   *
   * @return A String that contains the metadata.
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * Optional metadata that is provided during job submission limited to 512 characters.
   *
   * @param metadata A String to set as the metadata.
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }
  
  /**
   * Returns the value of deleteAfterSeconds.
   *
   * @return The deleteAfterSeconds value.
   */
  public Integer getDeleteAfterSeconds() {
    return deleteAfterSeconds;
  }

  /**
   * Specifies the number of seconds to be waited until the job is auto-deleted after its
   * completion.
   *
   * @param deleteAfterSeconds The number of seconds after job completion when job is auto-deleted.
   */
  public void setDeleteAfterSeconds(Integer deleteAfterSeconds) {
    this.deleteAfterSeconds = deleteAfterSeconds;
  }
}