package revai;

/**
 * Providing a custom vocabulary is optional. Use object assigns a custom vocabulary to be used
 * during the stream. Custom vocabularies are submitted prior to usage in the stream and assigned an
 * Id.
 *
 * <p>Setting the profanity filter is optional. More information regarding the profanity filter can
 * be found here: <a
 * href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Filter-Profanity">Filter
 * Profanity</a
 */
public class SessionConfig {

  private String metaData;
  private Boolean filterProfanity;
  private String customVocabularyId;

  public String getMetaData() {
    return metaData;
  }

  public void setMetaData(String metaData) {
    this.metaData = metaData;
  }

  public Boolean getFilterProfanity() {
    return filterProfanity;
  }

  public void setFilterProfanity(Boolean filterProfanity) {
    this.filterProfanity = filterProfanity;
  }

  public String getCustomVocabularyId() {
    return customVocabularyId;
  }

  public void setCustomVocabularyId(String customVocabularyId) {
    this.customVocabularyId = customVocabularyId;
  }
}
