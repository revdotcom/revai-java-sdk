package ai.rev.speechtotext.models.asynchronous;

import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A RevAiJobOptions object represents parameters that are submitted along a new job.
 *
 * @see <a
 *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
 */
public class RevAiJobOptions {

  /** The media url where the file can be downloaded. */
  @SerializedName("media_url")
  private String mediaUrl;

  /** The callback url that Rev.ai will send a POST to when the job has finished. */
  @SerializedName("callback_url")
  private String callbackUrl;

  /** Optional parameter for the speech engine to skip diarization. */
  @SerializedName("skip_diarization")
  private Boolean skipDiarization;

  /** Optional parameter for the speech engine to skip punctuation. */
  @SerializedName("skip_punctuation")
  private Boolean skipPunctuation;

  /**
   * Optional parameter to process each audio channel separately. Account will be charged the file
   * duration multiplied by the number of specified channels.
   */
  @SerializedName("speaker_channels_count")
  private Integer speakerChannelsCount;

  /** Optional array of {@link CustomVocabulary} objects. */
  @SerializedName("custom_vocabularies")
  private List<CustomVocabulary> customVocabularies;

  /** Optional information that can be provided. */
  @SerializedName("metadata")
  private String metadata;

  /**
   * Returns the media url.
   *
   * @return The media url.
   */
  public String getMediaUrl() {
    return mediaUrl;
  }

  /**
   * Specifies the url where the media can be downloaded.
   *
   * @param mediaUrl The direct download url to the file.
   */
  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  /**
   * Returns the callback url.
   *
   * @return the callback url.
   */
  public String getCallbackUrl() {
    return callbackUrl;
  }

  /**
   * Specifies the callback url that Rev.ai will POST to when job processing is complete. This
   * property is optional.
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   */
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  /**
   * Returns the value of the skip diarization Boolean.
   *
   * @return The skip diarization value.
   */
  public Boolean getSkipDiarization() {
    return skipDiarization;
  }

  /**
   * Specifies if speaker diarization will be skipped by the speech engine. This property is
   * optional and defaults to false.
   *
   * @param skipDiarization The value of the Boolean.
   */
  public void setSkipDiarization(Boolean skipDiarization) {
    this.skipDiarization = skipDiarization;
  }

  /**
   * Returns the value of the skip punctuation Boolean.
   *
   * @return The skip punctuation value.
   */
  public Boolean getSkipPunctuation() {
    return skipPunctuation;
  }

  /**
   * Specifies if the "punct" elements will be skipped by the speech engine. This property is
   * optional and defaults to false.
   *
   * @param skipPunctuation The value of the Boolean.
   */
  public void setSkipPunctuation(Boolean skipPunctuation) {
    this.skipPunctuation = skipPunctuation;
  }

  /**
   * Returns the speaker channel count.
   *
   * @return The speaker channel count.
   */
  public Integer getSpeakerChannelsCount() {
    return speakerChannelsCount;
  }

  /**
   * Specifies the number of speaker channels in the audio. Each speaker channel is processed
   * separately. When set the account will be charged the file * duration multiplied by the number
   * of specified channels. This property is optional and defaults to null.
   *
   * @param speakerChannelsCount The number of separate speaker channels in the audio.
   */
  public void setSpeakerChannelsCount(Integer speakerChannelsCount) {
    this.speakerChannelsCount = speakerChannelsCount;
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
   * Provides the custom vocabularies to be used by the speech engine when processing the
   * transcript. List size is limited to 50 items. Providing custom vocabularies is optional.
   *
   * @param customVocabularies A list of custom vocabularies.
   * @see CustomVocabulary
   */
  public void setCustomVocabularies(List<CustomVocabulary> customVocabularies) {
    this.customVocabularies = customVocabularies;
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
}
