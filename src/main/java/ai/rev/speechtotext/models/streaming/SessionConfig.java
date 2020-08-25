package ai.rev.speechtotext.models.streaming;

/** The SessionConfig represents additional streaming options that can be provided. */
public class SessionConfig {

  private String metaData;
  private Boolean filterProfanity;
  private String customVocabularyId;
  private Boolean removeDisfluencies;
  private Integer deleteAfterSeconds;

  /**
   * Returns the metadata.
   *
   * @return The metadata.
   */
  public String getMetaData() {
    return metaData;
  }

  /**
   * Specifies the metadata to be used in the submission request to /jobs.
   *
   * @param metaData The metadata to send with the request.
   */
  public void setMetaData(String metaData) {
    this.metaData = metaData;
  }

  /**
   * Returns the value of the filter profanity option.
   *
   * @return The value of the filter profanity option.
   */
  public Boolean getFilterProfanity() {
    return filterProfanity;
  }

  /**
   * Specifies whether or not the speech engine should filter profanity in the output. Setting the
   * profanity filter is optional.
   *
   * @param filterProfanity The option to filter profanity.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Filter-Profanity">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Filter-Profanity</a>
   */
  public void setFilterProfanity(Boolean filterProfanity) {
    this.filterProfanity = filterProfanity;
  }

  /**
   * Returns the custom vocabulary ID.
   *
   * @return The custom vocabulary ID.
   */
  public String getCustomVocabularyId() {
    return customVocabularyId;
  }

  /**
   * Specifies the ID of the custom vocabulary the speech engine should use while processing audio
   * samples. Custom vocabularies are submitted prior to usage in the stream and assigned an Id.
   *
   * @param customVocabularyId The ID of the custom vocabulary.
   */
  public void setCustomVocabularyId(String customVocabularyId) {
    this.customVocabularyId = customVocabularyId;
  }

  /**
   * Returns the value of the remove disfluencies option.
   *
   * @return The value of the remove disfluencies option.
   */
  public Boolean getRemoveDisfluencies() {
    return removeDisfluencies;
  }

  /**
   * Specifies whether or not the speech engine should remove disfluencies in the output. Setting
   * the option to remove disfluencies is optional.
   *
   * @param removeDisfluencies The option to filter profanity.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Remove-Disfluencies-(Beta)">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Remove-Disfluencies-(Beta)</a>
   */
  public void setRemoveDisfluencies(Boolean removeDisfluencies) {
    this.removeDisfluencies = removeDisfluencies;
  }

  /**
   * Returns the value of deleteAfterSeconds.
   *
   * @return The deleteAfterSeconds value.
   */
  public Integer getDeleteAfterSeconds() {
    return deleteAfterSeconds;
  }

  /**
   * Specifies the number of seconds to be waited until the job is auto-deleted after its
   * completion.
   *
   * @param deleteAfterSeconds The number of seconds after job completion when job is auto-deleted.
   */
  public void setDeleteAfterSeconds(Integer deleteAfterSeconds) {
    this.deleteAfterSeconds = deleteAfterSeconds;
  }
}
