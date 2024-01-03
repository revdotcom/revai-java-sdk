package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A TranslationLanguage object represents translation job results for a specific language.
 *
 * @see Translation
 */
public class TranslationLanguage {
  /** Standard or Premium AI backend. */
  @SerializedName("model")
  private TranslationModel model;

  @SerializedName("language")
  private String language;

  @SerializedName("status")
  private TranslationJobStatus jobStatus;

  @SerializedName("failure")
  private String failure;

  /**
   * Returns backend model used for the summarization job.
   *
   * @return Backend model used for the summarization job.
   * @see TranslationModel
   */
  public TranslationModel getModel() {
    return model;
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
   * Returns the {@link TranslationJobStatus} enumeration value.
   *
   * @return The {@link TranslationJobStatus} enumeration value.
   * @see TranslationJobStatus
   */
  public TranslationJobStatus getJobStatus() {
    return jobStatus;
  }

  /**
   * Returns failure details.
   *
   * @return Failure details.
   */
  public String getFailure() {
    return failure;
  }
}
