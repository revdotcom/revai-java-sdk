package ai.rev.languageid.models;

import java.util.List;

import ai.rev.topicextraction.models.Topic;
import com.google.gson.annotations.SerializedName;

/**
 * A Topic Extraction Result object provides all the information associated with the result of a job.
 */
public class LanguageIdResult {

    /**
     * Language code of the top predicted language.
     */
    @SerializedName("top_language")
    private String topLanguage;

    /**
     * List of predicted languages and their confidence scores in descending order of confidence.
     */
    @SerializedName("language_confidences")
    private List<LanguageConfidence> languageConfidences;

    /**
     * Get the top language of the result.
     *
     * @return The top language of the result.
     */
    public String getTopLanguage() {
        return topLanguage;
    }

    /**
     * Set the top language of the result.
     *
     * @param topLanguage the top language to be set for the result.
     */
    public void setTopLanguage(String topLanguage) {
        this.topLanguage = topLanguage;
    }

    /**
     * Get the language confidences of the result.
     *
     * @return The language confidences of the result.
     */
    public List<LanguageConfidence> getLanguageConfidences() {
        return languageConfidences;
    }

    /**
     * Set the language confidences of the result;
     *
     * @param languageConfidences the list of language confidences to be set for the result.
     */
    public void setLanguageConfidences(List<LanguageConfidence> languageConfidences) {
        this.languageConfidences = languageConfidences;
    }
}
