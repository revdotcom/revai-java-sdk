package ai.rev.speechtotext.models.asynchronous;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/** A RevAi Job object provides all the information associated with a job submitted by the user. */
public class RevAiJob {

  @SerializedName("id")
  private String jobId;

  @SerializedName("status")
  private RevAiJobStatus jobStatus;

  @SerializedName("created_on")
  private String createdOn;

  @SerializedName("completed_on")
  private String completedOn;

  @SerializedName("callback_url")
  private String callbackUrl;

  @SerializedName("duration_seconds")
  private Double durationSeconds;

  @SerializedName("media_url")
  private String mediaUrl;

  @SerializedName("metadata")
  private String metadata;

  @SerializedName("name")
  private String name;

  @SerializedName("type")
  private RevAiJobType type;

  @SerializedName("failure_details")
  private String failureDetails;

  @SerializedName("failure")
  private RevAiFailureType failure;

  @SerializedName("delete_after_seconds")
  private Integer deleteAfterSeconds;

  @SerializedName("skip_diarization")
  private Boolean skipDiarization;

  @SerializedName("skip_punctuation")
  private Boolean skipPunctuation;

  @SerializedName("remove_disfluencies")
  private Boolean removeDisfluencies;

  @SerializedName("filter_profanity")
  private Boolean filterProfanity;

  @SerializedName("custom_vocabulary_id")
  private String customVocabularyId;

  @SerializedName("speaker_channels_count")
  private Integer speakerChannelsCount;

  @SerializedName("language")
  private String language;

  @SerializedName("transcriber")
  private String transcriber;

  @SerializedName("verbatim")
  private Boolean verbatim;

  @SerializedName("rush")
  private Boolean rush;

  @SerializedName("segments_to_transcribe")
  private List<SegmentToTranscribe> segmentsToTranscribe;

  /**
   * Returns a String that contains the job ID.
   *
   * @return A String that contains the job ID.
   */
  public String getJobId() {
    return jobId;
  }

  /**
   * Sets the Job ID.
   *
   * @param jobId The String value to set as the job ID.
   */
  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  /**
   * Returns the {@link RevAiJobStatus} enumeration value.
   *
   * @return The {@link RevAiJobStatus} enumeration value.
   * @see RevAiJobStatus
   */
  public RevAiJobStatus getJobStatus() {
    return jobStatus;
  }

  /**
   * Sets the job status to the provided {@link RevAiJobStatus} enumeration value.
   *
   * @param jobStatus The enumeration value to set as the job status.
   * @see RevAiJobStatus
   */
  public void setJobStatus(RevAiJobStatus jobStatus) {
    this.jobStatus = jobStatus;
  }

  /**
   * Returns a String that contains the date and time the job was created on in ISO-8601 UTC form.
   *
   * @return A String that contains the date and time the job was created on in ISO-8601 UTC form.
   */
  public String getCreatedOn() {
    return createdOn;
  }

  /**
   * Sets the time and date the job was created on.
   *
   * @param createdOn The String value to set as the created on date and time.
   */
  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * Returns a String that contains the date and time the job was completed on in ISO-8601 UTC form.
   *
   * @return A String that contains the date and time the job was completed on in ISO-8601 UTC form.
   */
  public String getCompletedOn() {
    return completedOn;
  }

  /**
   * Set the date and time the job was completed on.
   *
   * @param completedOn The String value to set as the date and time the job was completed on.
   */
  public void setCompletedOn(String completedOn) {
    this.completedOn = completedOn;
  }

  /**
   * Returns the callback url provided in the submission request.
   *
   * @return A String containing the callback url provided in the submission request.
   */
  public String getCallbackUrl() {
    return callbackUrl;
  }

  /**
   * Sets the callback url.
   *
   * @param callbackUrl A String value to set as the callback url.
   */
  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  /**
   * Returns the audio duration of the file in seconds.
   *
   * @return The audio duration of the file in seconds.
   */
  public Double getDurationSeconds() {
    return durationSeconds;
  }

  /**
   * Sets the audio duration.
   *
   * @param durationSeconds An Double value to set as audio duration.
   */
  public void setDurationSeconds(Double durationSeconds) {
    this.durationSeconds = durationSeconds;
  }

  /**
   * Returns the media url provided in the submission request.
   *
   * @return A String containing the media url provided in the submission request.
   */
  public String getMediaUrl() {
    return mediaUrl;
  }

  /**
   * Sets the media url.
   *
   * @param mediaUrl A String value to set as the media url.
   */
  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  /**
   * Returns the metadata provided in the submission request.
   *
   * @return A String containing the metadata provided in the submission request.
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * Sets the metadata.
   *
   * @param metadata A String to set as the metadata.
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  /**
   * Returns the name of the file provided in the submission request.
   *
   * @return A String that contains the name of the file provided in the submission request.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the file name.
   *
   * @param name A String to set as the file name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the {@link RevAiJobType} enumeration value.
   *
   * @return the enumeration value.
   * @see RevAiJobType
   */
  public RevAiJobType getType() {
    return type;
  }

  /**
   * Sets the job type to the provided {@link RevAiJobType} enumeration.
   *
   * @param type The enumeration value to set as the job type.
   * @see RevAiJobType
   */
  public void setType(RevAiJobType type) {
    this.type = type;
  }

  /**
   * Returns a detailed, human readable explanation of the failure.
   *
   * @return A detailed, human readable explanation of the failure.
   */
  public String getFailureDetails() {
    return failureDetails;
  }

  /**
   * Sets the failure details to the provided value.
   *
   * @param failureDetails A String to set as the failure details.
   */
  public void setFailureDetails(String failureDetails) {
    this.failureDetails = failureDetails;
  }

  /**
   * Returns the {@link RevAiFailureType} enumeration value.
   *
   * @return The {@link RevAiFailureType} enumeration value.
   * @see RevAiFailureType
   */
  public RevAiFailureType getFailure() {
    return failure;
  }

  /**
   * Sets the failure to the provided {@link RevAiFailureType} enumeration.
   *
   * @param failure The enumeration value to set as the failure.
   * @see RevAiFailureType
   */
  public void setFailure(RevAiFailureType failure) {
    this.failure = failure;
  }

  /**
   * Returns the duration in seconds before job is deleted
   *
   * @return The duration in seconds.
   */
  public Integer getDeleteAfterSeconds() {
    return deleteAfterSeconds;
  }

  /**
   * Sets the duration in seconds before job is deleted
   *
   * @param deleteAfterSeconds An Integer value to set as seconds before deletion.
   */
  public void setDeleteAfterSeconds(Integer deleteAfterSeconds) {
    this.deleteAfterSeconds = deleteAfterSeconds;
  }

  /**
   * Returns value of skip diarization for job
   *
   * @return Whether job is skipping diarization
   */
  public Boolean getSkipDiarization() {
    return skipDiarization;
  }

  /**
   * Sets skip diarization option for job
   *
   * @param skipDiarization An Boolean value to set for skipping diarization
   */
  public void setSkipDiarization(Boolean skipDiarization) {
    this.skipDiarization = skipDiarization;
  }

  /**
   * Returns value of skip punctuation for job
   *
   * @return Whether job is skipping punctuation
   */
  public Boolean getSkipPunctuation() {
    return skipPunctuation;
  }

  /**
   * Sets skip punctuation option for job
   *
   * @param skipPunctuation An Boolean value to set for skipping punctuation
   */
  public void setSkipPunctuation(Boolean skipPunctuation) {
    this.skipPunctuation = skipPunctuation;
  }

  /**
   * Returns value of remove disfluencies for job
   *
   * @return Whether job is removing disfluencies
   */
  public Boolean getRemoveDisfluencies() {
    return removeDisfluencies;
  }

  /**
   * Sets remove disfluencies option for job
   *
   * @param removeDisfluencies An Boolean value to set for remove disfluencies
   */
  public void setRemoveDisfluencies(Boolean removeDisfluencies) {
    this.removeDisfluencies = removeDisfluencies;
  }

  /**
   * Returns value of filter profanity for job
   *
   * @return Whether job is filtering profanity
   */
  public Boolean getFilterProfanity() {
    return filterProfanity;
  }

  /**
   * Sets filter profanity option for job
   *
   * @param filterProfanity An Boolean value to set for filter profanity
   */
  public void setFilterProfanity(Boolean filterProfanity) {
    this.filterProfanity = filterProfanity;
  }

  /**
   * Returns custom vocabulary id (if specified) associated to the job
   *
   * @return User-supplied custom vocabulary ID
   */
  public String getCustomVocabularyId() {
    return customVocabularyId;
  }

  /**
   * Sets the user-supplied custom vocabulary ID for job
   *
   * @param customVocabularyId An String value to set for custom vocabulary ID
   */
  public void setCustomVocabularyId(String customVocabularyId) {
    this.customVocabularyId = customVocabularyId;
  }

  /**
   * Returns number of speaker channels (if specified) for job
   *
   * @return Total number of unique speaker channels
   */
  public Integer getSpeakerChannelsCount() {
    return speakerChannelsCount;
  }

  /**
   * Sets speaker channels count for job
   *
   * @param speakerChannelsCount An Integer value to set for speaker channels count
   */
  public void setSpeakerChannelsCount(Integer speakerChannelsCount) {
    this.speakerChannelsCount = speakerChannelsCount;
  }

  /**
   * Returns language of the job
   *
   * @return language of the job
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Sets the language for job
   *
   * @param language An String value to set for language
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Returns transcriber used for the job
   *
   * @return transcriber used for the job
   */
  public String getTranscriber() {
    return transcriber;
  }

  /**
   * Sets the transcriber used for job
   *
   * @param transcriber An String value to set for transcriber
   */
  public void setTranscriber(String transcriber) {
    this.transcriber = transcriber;
  }

  /**
   * Returns value of verbatim for job transcribed by human
   *
   * @return Whether job transcribed by human is verbatim
   */
  public Boolean getVerbatim() {
    return verbatim;
  }

  /**
   * Sets verbatim for job transcribed by human
   *
   * @param verbatim An Boolean value to set for verbatim
   */
  public void setVerbatim(Boolean verbatim) {
    this.verbatim = verbatim;
  }

  /**
   * Returns value of rush for job transcribed by human
   *
   * @return Whether job transcribed by human has rush
   */
  public Boolean getRush() {
    return rush;
  }

  /**
   * Sets rush option for job transcribed by human
   *
   * @param rush An Boolean value to set for rush
   */
  public void setRush(Boolean rush) {
    this.rush = rush;
  }

  /**
   * Returns segments to transcribe for job transcribed by human
   *
   * @return List of segments to be transcribed
   */
  public List<SegmentToTranscribe> getSegmentsToTranscribe() {
    return segmentsToTranscribe;
  }

  /**
   * Sets segments to be transcribed for job transcribed by human
   *
   * @param segmentsToTranscribe List of segments to be transcribed
   */
  public void setSegmentsToTranscribe(List<SegmentToTranscribe> segmentsToTranscribe) {
    this.segmentsToTranscribe = segmentsToTranscribe;
  }

  @Override
  public String toString() {
    return "{"
        + "jobID='"
        + jobId
        + '\''
        + ", jobStatus="
        + jobStatus
        + ", createdOn='"
        + createdOn
        + '\''
        + ", completedOn='"
        + completedOn
        + '\''
        + ", callbackUrl='"
        + callbackUrl
        + '\''
        + ", durationSeconds="
        + durationSeconds
        + ", mediaUrl='"
        + mediaUrl
        + '\''
        + ", metadata='"
        + metadata
        + '\''
        + ", name='"
        + name
        + '\''
        + ", type='"
        + type.getJobType()
        + '\''
        + ", failureDetails='"
        + failureDetails
        + '\''
        + ", failure='"
        + failure.getFailureType()
        + '\''
        + ", deleteAfterSeconds='"
        + deleteAfterSeconds
        + '\''
        + ", skipDiarization='"
        + skipDiarization
        + '\''
        + ", removeDisfluencies='"
        + removeDisfluencies
        + '\''
        + ", filterProfanity='"
        + filterProfanity
        + '\''
        + ", customVocabularyId='"
        + customVocabularyId
        + '\''
        + ", speakerChannelsCount='"
        + speakerChannelsCount
        + '\''
        + ", language='"
        + language
        + '\''
        + ", transcriber='"
        + transcriber
        + '\''
        + ", verbatim='"
        + verbatim
        + '\''
        + ", rush='"
        + rush
        + '\''
        + ", segmentsToTranscribe='"
        + segmentsToTranscribe
        + '\''
        + '}';
  }
}
