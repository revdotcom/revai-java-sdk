package revai.models;

import java.util.List;

/**
 * A CustomVocabulary object provides all the custom phrases that are submitted along with a job.
 */
public class CustomVocabulary {
  private List<String> phrases;

  public CustomVocabulary(List<String> phrases) {
    this.phrases = phrases;
  }

  public List<String> getPhrases() {
    return phrases;
  }

  public void setPhrases(List<String> phrases) {
    this.phrases = phrases;
  }
}
