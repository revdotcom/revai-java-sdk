package ai.rev.speechtotext.models.vocabulary;

import java.util.List;

/**
 * A CustomVocabulary object provides all the custom phrases that are submitted along with a job.
 */
public class CustomVocabulary {
  private List<String> phrases;

  /**
   * Creates a new custom vocabulary with a list of phrases limited to alphabetic characters and
   * spaces. List size cannot exceed 20000 items.
   *
   * @param phrases A list of strings limited to alphabetic characters and spaces.
   */
  public CustomVocabulary(List<String> phrases) {
    this.phrases = phrases;
  }

  /**
   * Returns a list of phrases
   *
   * @return A list of phrases
   */
  public List<String> getPhrases() {
    return phrases;
  }

  /**
   * Sets the list of phrases to be used in the custom vocabulary. Phrases are limited to alphabetic
   * characters and spaces. List size cannot exceed 20000 items.
   *
   * @param phrases The list of strings to set as the custom vocabulary phrases.
   */
  public void setPhrases(List<String> phrases) {
    this.phrases = phrases;
  }
}
