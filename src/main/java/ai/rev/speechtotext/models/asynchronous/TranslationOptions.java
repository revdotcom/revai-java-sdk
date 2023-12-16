package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A TranslationOptions object represents translation parameters that are submitted along a new job.
 *
 * @see <a
 *     href="https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob">https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob</a>
 */
public class TranslationOptions {

  @SerializedName("target_languages")
  private final List<TranslationLanguageOptions> targetLanguages;

  /**
   * Returns a list of language specific parameters
   *
   * @return List of language specific parameters
   */
  public List<TranslationLanguageOptions> getTargetLanguages() {
    return targetLanguages;
  }

  /**
   * Creates TranslationOptions object.
   *
   * @param targetLanguages a list of language specific parameters.
   */
  public TranslationOptions(List<TranslationLanguageOptions> targetLanguages) {
    this.targetLanguages = targetLanguages;
  }
}
