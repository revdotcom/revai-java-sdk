package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public class RevAiJobOptions {
    @SerializedName("media_url")
    String mediaUrl;

    @SerializedName("callback_url")
    String callbackUrl;

    @SerializedName("skip_diarization")
    Boolean skipDiarization;

    @SerializedName(""
    Boolean skipPunctuation;
    int speakerChannelsCount;

    String metadata;
    media_url?: string;
    metadata?: string;
    callback_url?: string;
    skip_diarization?: boolean;
    skip_punctuation?: boolean;
    speaker_channels_count?: number;
    custom_vocabularies?: CustomVocabulary[];

}
