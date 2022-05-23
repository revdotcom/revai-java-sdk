package ai.rev.languageid.models;

import java.util.List;

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
     * Set the top language of the result.
     *
     * @param topLanguage the top language to be set for the result.
     */
    public void setTopLanguage(String topLanguage) {
        this.topLanguage = topLanguage;
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
