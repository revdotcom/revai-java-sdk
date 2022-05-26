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

    /**
     * Get the language of the result.
     *
     * @return The language of the result.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set the language of the result.
     *
     * @param language the language to be set for the result.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Get the confidence of the result.
     *
     * @return The confidence of the result.
     */
    public Double getConfidence() {
        return confidence;
    }

    /**
     * Set the confidence of the result.
     *
     * @param confidence the confidence to be set for the result.
     */
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}
