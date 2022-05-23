package ai.rev.languageid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a predicted language and its corresponding confidences score.
 */
public class LanguageConfidence {

    /**
     * Language code of predicted language.
     */
    @SerializedName("language")
    private String language;

    /**
     * Confidence score, between 0 and 1, for predicted language.
     */
    @SerializedName("confidence")
    private Double confidence;
}
