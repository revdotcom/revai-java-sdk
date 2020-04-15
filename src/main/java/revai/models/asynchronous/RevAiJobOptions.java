package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;
import revai.models.CustomVocabulary;

import java.util.List;

/** A RevAiJobOptions object presents parameters that are submitted along a new job. */
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

  /**
   * Optional array of {@link CustomVocabulary} objects.
   */
  @SerializedName("custom_vocabularies")
  private List<CustomVocabulary> customVocabularies;

  /**
   * Optional information that can be provided.
   */
  @SerializedName("metadata")
  private String metadata;


  public String getMediaUrl() {
    return mediaUrl;
  }

  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  public Boolean getSkipDiarization() {
    return skipDiarization;
  }

  public void setSkipDiarization(Boolean skipDiarization) {
    this.skipDiarization = skipDiarization;
  }

  public Boolean getSkipPunctuation() {
    return skipPunctuation;
  }

  public void setSkipPunctuation(Boolean skipPunctuation) {
    this.skipPunctuation = skipPunctuation;
  }

  public Integer getSpeakerChannelsCount() {
    return speakerChannelsCount;
  }

  public void setSpeakerChannelsCount(Integer speakerChannelsCount) {
    this.speakerChannelsCount = speakerChannelsCount;
  }

  public List<CustomVocabulary> getCustomVocabularies() {
    return customVocabularies;
  }

  public void setCustomVocabularies(List<CustomVocabulary> customVocabularies) {
    this.customVocabularies = customVocabularies;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }
}
