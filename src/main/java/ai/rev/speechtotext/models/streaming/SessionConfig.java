package ai.rev.speechtotext.models.streaming;

/** The SessionConfig represents additional streaming options that can be provided. */
public class SessionConfig {

  private String metaData;
  private Boolean filterProfanity;
  private String customVocabularyId;
  private Boolean removeDisfluencies;
  private Integer deleteAfterSeconds;
  private Boolean detailedPartials;
  private Double startTs;
  private String transcriber;
  private String language;
  private Boolean skipPostprocessing;

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
   *     href="https://docs.rev.ai/api/streaming/requests/#profanity-filter">https://docs.rev.ai/api/streaming/requests/#profanity-filter</a>
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
   * @see <a href="https://docs.rev.ai/api/streaming/requests/#disfluencies">
   *        https://docs.rev.ai/api/streaming/requests/#disfluencies
   *      </a>
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

  /**
   * Returns the value of detailed partials option
   *
   * @return The value of the detailed partials option.
   */
  public Boolean getDetailedPartials() {
    return detailedPartials;
  }

  /**
   * Specifies whether or not to return detailed partials. Setting the option is optional.
   *
   * @param detailedPartials The option to enable detailed partials.
   * @see <a href="https://docs.rev.ai/api/streaming/requests/#detailed-partials">
   *        https://docs.rev.ai/api/streaming/requests/#detailed-partials
   *      </a>
   */
  public void setDetailedPartials(Boolean detailedPartials) {
    this.detailedPartials = detailedPartials;
  }

  /**
   * Returns the value of startTs.
   *
   * @return The startTs value.
   */
  public Double getStartTs() {
    return startTs;
  }

  /**
   * Specifies the number of seconds to offset all hypotheses timings.
   *
   * @param startTs The number of seconds to offset all hypotheses timings.
   */
  public void setStartTs(Double startTs) {
    this.startTs = startTs;
  }

  /**
   * Returns the value of transcriber.
   *
   * @return The transcriber value.
   */
  public String getTranscriber() {
    return transcriber;
  }

  /**
   * Specifies the type of transcriber to use to transcribe the media.
   *
   * @param transcriber The type of transcriber to use to transcribe the media.
   */
  public void setTranscriber(String transcriber) {
    this.transcriber = transcriber;
  }

  /**
   * Returns the value of language.
   *
   * @return The language value.
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Specifies the language to use for the streaming job.
   *
   * @param language The language to use for the streaming job.
   */
  public void setLanguage(String language) {
    this.language = language;
  }
  
  /**
   * Returns the value of the skip postprocessing Boolean.
   *
   * @return The skip postprocessing value.
   */
  public Boolean getSkipPostprocessing() {
    return skipPostprocessing;
  }

  /**
   * Specifies if all postprocessing (capitalization, punctuation, ITN) will be skipped by the speech engine. This property is
   * optional and defaults to false.
   *
   * @param skipPostprocessing The value of the Boolean.
   */
  public void setSkipPostprocessing(Boolean skipPostprocessing) {
    this.skipPostprocessing = skipPostprocessing;
  }
}
