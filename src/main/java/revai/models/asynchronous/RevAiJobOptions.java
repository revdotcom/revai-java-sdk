package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;
import revai.models.CustomVocabulary;

import java.util.List;

public class RevAiJobOptions {
  @SerializedName("media_url")
  public String mediaUrl;

  @SerializedName("callback_url")
  public String callbackUrl;

  @SerializedName("skip_diarization")
  public Boolean skipDiarization;

  @SerializedName("skip_punctuation")
  public Boolean skipPunctuation;

  @SerializedName("speaker_channels_count")
  public Integer speakerChannelsCount;

  @SerializedName("custom_vocabularies")
  public List<CustomVocabulary> customVocabularies;

  public String metadata;
}
