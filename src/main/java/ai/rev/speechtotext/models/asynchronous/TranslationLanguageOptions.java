package ai.rev.speechtotext.models.asynchronous;

import ai.rev.speechtotext.models.NlpModel;
import com.google.gson.annotations.SerializedName;

/**
 * A TranslationLanguageOptions object represents translation parameters for a specific language that are submitted along a new
 * job.
 *
 * @see <a
 *     href="https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob">https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob</a>
 */
public class TranslationLanguageOptions {
  /** Standard or Premium AI backend. * */
  @SerializedName("model")
  private NlpModel model;

  @SerializedName("language")
  private final String language;

  /**
   * Returns backend model used for the summarization job.
   *
   * @return Backend model used for the summarization job.
   * @see NlpModel
   */
  public NlpModel getModel() {
    return model;
  }

  /**
   * Sets backend model to use for the summarization job.
   *
   * @param model Backend model to use for the summarization job
   * @see NlpModel
   */
  public TranslationLanguageOptions setModel(NlpModel model) {
    this.model = model;
    return this;
  }

  /**
   * Returns translation language.
   *
   * @return Translation language.
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Creates TranslationLanguageOptions object.
   *
   * @param language Translation language.
   */
  public TranslationLanguageOptions(String language) {
    this.language = language;
  }
}
