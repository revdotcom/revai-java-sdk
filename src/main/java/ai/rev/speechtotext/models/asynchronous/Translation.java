package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A Translation object represents translation job(s) results.
 *
 * @see TranslationLanguage
 */
public class Translation {

  @SerializedName("target_languages")
  private List<TranslationLanguage> targetLanguages;

  @SerializedName("completed_on")
  private String completedOn;

  /**
   * Returns translation job(s) parameters
   *
   * @return Translation job(s) parameters
   * @see TranslationLanguage
   */
  public List<TranslationLanguage> getTargetLanguages() {
    return targetLanguages;
  }

  /**
   * Returns a String that contains the date and time the job was completed on in ISO-8601 UTC form.
   *
   * @return A String that contains the date and time the job was completed on in ISO-8601 UTC form.
   */
  public String getCompletedOn() {
    return completedOn;
  }
}
