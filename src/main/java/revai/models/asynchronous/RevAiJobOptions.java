package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;
import revai.models.CustomVocabulary;

import java.util.List;

/** A RevAiJobOptions object presents parameters that are submitted along a new job. */
public class RevAiJobOptions {

  @SerializedName("media_url")
  private String mediaUrl;

  @SerializedName("callback_url")
  private String callbackUrl;

  @SerializedName("skip_diarization")
  private Boolean skipDiarization;

  @SerializedName("skip_punctuation")
  private Boolean skipPunctuation;

  @SerializedName("speaker_channels_count")
  private Integer speakerChannelsCount;

  @SerializedName("custom_vocabularies")
  private List<CustomVocabulary> customVocabularies;

  @SerializedName("metadata")
  private String metadata;

  private String getMediaUrl() {
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
