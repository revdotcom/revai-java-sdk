package ai.rev.speechtotext.models.asynchronous;

import ai.rev.speechtotext.models.CustomerUrlData;
import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * A RevAiJobOptions object represents parameters that are submitted along a new job.
 *
 * @see <a
 *     href="https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob">https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob</a>
 */
public class RevAiJobOptions {

  /** The media url where the file can be downloaded.
   * @deprecated Use source_config instead
   */
  @SerializedName("media_url")
  @Deprecated
  private String mediaUrl;

  /** Object containing source media file information. */
  @SerializedName("source_config")
  private CustomerUrlData sourceConfig;

  /** The callback url that Rev AI will send a POST to when the job has finished.
   * @deprecated Use notification_config instead
   */
  @SerializedName("callback_url")
  @Deprecated
  private String callbackUrl;

  /** Object containing information on the callback url that Rev AI will send a POST to when the job has finished. */
  @SerializedName("notification_config")
  private CustomerUrlData notificationConfig;

  /** Optional parameter for the speech engine to skip diarization. */
  @SerializedName("skip_diarization")
  private Boolean skipDiarization;

  /** Optional parameter for the speech engine to skip punctuation. */
  @SerializedName("skip_punctuation")
  private Boolean skipPunctuation;
  
  /** Optional parameter for the speech engine to skip all postprocessing */
  @SerializedName("skip_postprocessing")
  private Boolean skipPostprocessing;

  /**
   * Optional parameter to process each audio channel separately. Account will be charged the file
   * duration multiplied by the number of specified channels.
   */
  @SerializedName("speaker_channels_count")
  private Integer speakerChannelsCount;

  /** Optional parameter for the id of a pre-computed custom vocabulary to be used */
  @SerializedName("custom_vocabulary_id")
  private String customVocabularyId;

  /** Optional array of {@link CustomVocabulary} objects. */
  @SerializedName("custom_vocabularies")
  private List<CustomVocabulary> customVocabularies;

  /**
   * Optional information that can be provided.
   */
  @SerializedName("metadata")
  private String metadata;

  /**
   * Optional parameter to filter profanity in the transcript
   */
  @SerializedName("filter_profanity")
  private Boolean filterProfanity;

  /**
   * Optional parameter to remove disfluencies (ums, ahs) in the transcript
   */
  @SerializedName("remove_disfluencies")
  private Boolean removeDisfluencies;

  /**
   * Optional number of seconds after job completion when job is auto-deleted
   */
  @SerializedName("delete_after_seconds")
  private Integer deleteAfterSeconds;

  /**
   * Optional language parameter using a supported ISO 639-1 2-letter or ISO 639-3 (3-letter) language code
   * as defined in the API reference
   */
  @SerializedName("language")
  private String language;

  /**
   * Specifies the type of transcriber to use to transcribe the media file
   */
  @SerializedName("transcriber")
  private String transcriber;

  /**
   * Optional and only available with transcriber "human".
   * Whether transcriber will transcribe every syllable.
   */
  @SerializedName("verbatim")
  private Boolean verbatim;

  /**
   * Optional and only available with transcriber "human".
   * Whether job is given higher priority by human transcriber for higher price.
   */
  @SerializedName("rush")
  private Boolean rush;

  /**
   * Optional and only available with transcriber "human".
   * Whether job is mocked and no real transcription actually happens.
   */
  @SerializedName("test_mode")
  private Boolean testMode;

  /**
   * Optional and only available with transcriber "human".
   * Sections of the transcript that should be transcribed instead of the whole file.
   */
  @SerializedName("segments_to_transcribe")
  private List<SegmentToTranscribe> segmentsToTranscribe;

  /**
   * Optional and only available with transcriber "human".
   * Specifies a list of names for the speakers in an audio file.
   */
  @SerializedName("speaker_names")
  private List<SpeakerName> speakerNames;

  /**
   * Returns the media url.
   *
   * @return The media url.
   * @deprecated Set sourceConfig and use getSourceConfig instead
   */
  @Deprecated
  public String getMediaUrl() {
    return mediaUrl;
  }

  /**
   * Specifies the url where the media can be downloaded.
   *
   * @param mediaUrl The direct download url to the file.
   * @deprecated Use setSourceConfig instead
   */
  @Deprecated
  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  /**
   * Returns the source config object.
   *
   * @return the source config.
   */
  public CustomerUrlData getSourceConfig()
  {
    return this.sourceConfig;
  }

  /**
   * Specifies the url and any optional auth headers to access the source media download url.
   *
   * @param sourceMediaUrl The direct download url to the file.
   * @param sourceAuth The auth headers to the source media download url.
   */

  public void setSourceConfig(String sourceMediaUrl, Map<String, String> sourceAuth) {
    this.sourceConfig = new CustomerUrlData(sourceMediaUrl, sourceAuth);
  }

  /**
   * Specifies the source media download url.
   *
   * @param sourceMediaUrl The direct download url to the file.
   */

  public void setSourceConfig(String sourceMediaUrl) {
    this.sourceConfig = new CustomerUrlData(sourceMediaUrl, null);
  }

  /**
   * Returns the callback url.
   *
   * @return the callback url.
   * @deprecated Use notificationConfig and getNotificationConfig instead
   */
  @Deprecated
  public String getCallbackUrl() {
    return callbackUrl;
  }

  /**
   * Specifies the callback url that Rev AI will POST to when job processing is complete. This
   * property is optional.
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   * @deprecated Use setNotificationConfig instead
   */
  @Deprecated
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  /**
   * Returns the notification config object.
   *
   * @return the notification config.
   */
  public CustomerUrlData getNotificationConfig() {
    return notificationConfig;
  }

  /**
   * Optional property to specify the callback url that Rev AI will POST to when job processing is complete
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   * @param authHeaders Optional parameter to authenticate access to the callback url
   */
  public void setNotificationConfig(String callbackUrl, Map<String, String> authHeaders) {
    this.notificationConfig = new CustomerUrlData(callbackUrl, authHeaders);
  }

  /**
   * Optional property to specify the callback url that Rev AI will POST to when job processing is complete
   *
   * @param callbackUrl The url to POST to when job processing is complete.
   */
  public void setNotificationConfig(String callbackUrl) {
    setNotificationConfig(callbackUrl, null);
  }

  /**
   * Returns the value of the skip diarization Boolean.
   *
   * @return The skip diarization value.
   */
  public Boolean getSkipDiarization() {
    return skipDiarization;
  }

  /**
   * Specifies if speaker diarization will be skipped by the speech engine. This property is
   * optional and defaults to false.
   *
   * @param skipDiarization The value of the Boolean.
   */
  public void setSkipDiarization(Boolean skipDiarization) {
    this.skipDiarization = skipDiarization;
  }

  /**
   * Returns the value of the skip punctuation Boolean.
   *
   * @return The skip punctuation value.
   */
  public Boolean getSkipPunctuation() {
    return skipPunctuation;
  }

  /**
   * Specifies if the "punct" elements will be skipped by the speech engine. This property is
   * optional and defaults to false.
   *
   * @param skipPunctuation The value of the Boolean.
   */
  public void setSkipPunctuation(Boolean skipPunctuation) {
    this.skipPunctuation = skipPunctuation;
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

  /**
   * Returns the speaker channel count.
   *
   * @return The speaker channel count.
   */
  public Integer getSpeakerChannelsCount() {
    return speakerChannelsCount;
  }

  /**
   * Specifies the number of speaker channels in the audio. Each speaker channel is processed
   * separately. When set the account will be charged the file * duration multiplied by the number
   * of specified channels. This property is optional and defaults to null.
   *
   * @param speakerChannelsCount The number of separate speaker channels in the audio.
   */
  public void setSpeakerChannelsCount(Integer speakerChannelsCount) {
    this.speakerChannelsCount = speakerChannelsCount;
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
   * Returns a list of {@link CustomVocabulary} objects.
   *
   * @return A list of {@link CustomVocabulary} objects.
   * @see CustomVocabulary
   */
  public List<CustomVocabulary> getCustomVocabularies() {
    return customVocabularies;
  }

  /**
   * Provides the custom vocabularies to be used by the speech engine when processing the
   * transcript. List size is limited to 50 items. Providing custom vocabularies is optional.
   *
   * @param customVocabularies A list of custom vocabularies.
   * @see CustomVocabulary
   */
  public void setCustomVocabularies(List<CustomVocabulary> customVocabularies) {
    this.customVocabularies = customVocabularies;
  }

  /**
   * Returns the metadata.
   *
   * @return A String that contains the metadata.
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * Optional metadata that is provided during job submission limited to 512 characters.
   *
   * @param metadata A String to set as the metadata.
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * Returns the value of the filterProfanity Boolean
   *
   * @return The filter profanity value.
   */
  public Boolean getFilterProfanity() {
    return filterProfanity;
  }

  /**
   * Specifies whether or not the speech engine should filter profanity in the output. Setting the
   * profanity filter is optional.
   *
   * @param filterProfanity The option to filter profanity.
   */
  public void setFilterProfanity(Boolean filterProfanity) {
    this.filterProfanity = filterProfanity;
  }

  /**
   * Returns the value of the removeDisfluencies Boolean
   *
   * @return The removeDisfluencies value.
   */
  public Boolean getRemoveDisfluencies() {
    return removeDisfluencies;
  }

  /**
   * Specifies whether or not the speech engine should remove disfluencies in the output. Setting
   * the option to remove disfluencies is optional.
   *
   * @param removeDisfluencies The option to remove disfluencies.
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
   * Returns the value of language.
   *
   * @return the language value.
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Specifies language for ASR system using ISO 639-1 2-letter language code.
   *
   * @param language ISO 639-1 2-letter language code of desired ASR language.
   */
  public void setLanguage(String language) { this.language = language; }

  /**
   * Specifies the type of transcriber to use to transcribe the media file
   *
   * @return the name of the transcriber type
   */
  public String getTranscriber() {
    return transcriber;
  }

  /**
   * Specifies the type of transcriber to use to transcribe the media file
   *
   * @param transcriber the name of the transcriber type
   */
  public void setTranscriber(String transcriber) {
    this.transcriber = transcriber;
  }

  /**
   * Returns the value of verbatim
   *
   * @return Whether verbatim is true or false
   */
  public Boolean getVerbatim() {
    return verbatim;
  }

  /**
   * Sets whether transcriber will transcribe every syllable including disfluencies. This
   * property is optional but can only be used with "human" transcriber.
   * Defaults to false.
   *
   * @param verbatim Whether to transcribe every syllable
   */
  public void setVerbatim(Boolean verbatim) {
    this.verbatim = verbatim;
  }

  /**
   * Returns the value of rush
   *
   * @return Whether rush is true or false
   */
  public Boolean getRush() {
    return rush;
  }

  /**
   * Sets whether job is given higher priority by human transcriber to be worked on sooner in
   * exchange for a higher price. This property is optional but can only be used with "human" transcriber.
   * Defaults to false.
   *
   * @param rush Whether to give higher priority to the job
   */
  public void setRush(Boolean rush) {
    this.rush = rush;
  }

  /**
   * Returns the value of testMode
   *
   * @return Whether testMode is true or false
   */
  public Boolean getTestMode() {
    return testMode;
  }

  /**
   * Sets whether job is a mocked job which will return a mock transcript.
   * This property is optional but can only be used with "human" transcriber.
   * Defaults to false.
   *
   * @param testMode Whether to set job to be test mode
   */
  public void setTestMode(Boolean testMode) {
    this.testMode = testMode;
  }

  /**
   * Returns the segments in media to transcribe
   *
   * @return Segments to transcribe for the job
   */
  public List<SegmentToTranscribe> getSegmentsToTranscribe() {
    return segmentsToTranscribe;
  }

  /**
   * Specifies specific segments of the media to transcribe.
   * This property is optional but can only be used with "human" transcriber.
   *
   * @param segmentsToTranscribe List of segments to transcribe
   */
  public void setSegmentsToTranscribe(List<SegmentToTranscribe> segmentsToTranscribe) {
    this.segmentsToTranscribe = segmentsToTranscribe;
  }

   /**
   * Returns the list of speaker names
   *
   * @return List of speaker names
   */
  public List<SpeakerName> getSpeakerNames() {
    return speakerNames;
  }

  /**
   * Specifies the list of speaker names in an audio file
   * This property is optional but can only be used with "human" transcriber.
   *
   * @param speakerNames List of speaker names
   */
  public void setSpeakerNames(List<SpeakerName> speakerNames) {
    this.speakerNames = speakerNames;
  }
}
