package ai.rev.topicextraction.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a piece of the input which informed a choice of a topic.
 */
public class Informant {
  /**
   * Content of the input that makes up the informant
   */
  @SerializedName("content")
  private String content;

  /**
   * Start timestamp of the content in the input if available
   */
  @SerializedName("ts")
  private Double startTimestamp;

  /**
   * End timestamp of the content in the input if available
   */
  @SerializedName("end_ts")
  private Double endTimestamp;

  /**
   * Character index at which the content started in the source transcript,
   * excludes invisible characters.
   */
  @SerializedName("offset")
  private Integer offset;

  /**
   * Length of the content in characters, excludes invisible characters.
   */
  @SerializedName("length")
  private Integer length;

  /**
   * Returns the content of the informant
   *
   * @return the content of the informant
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets the content of the informant
   *
   * @param content content to be set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Returns the start timestamp of the content in the input
   *
   * @return the start timestamp of the content in the input
   */
  public Double getStartTimestamp() {
    return startTimestamp;
  }

  /**
   * Sets the start timestamp of the content in the input
   *
   * @param startTimestamp the start timestamp to be set
   */
  public void setStartTimestamp(Double startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  /**
   * Returns the end timestamp of the content in the input
   *
   * @return the end timestamp of the content in the input
   */
  public Double getEndTimestamp() {
    return endTimestamp;
  }

  /**
   * Sets the end timestamp of the content in the input
   *
   * @param endTimestamp the end timestamp to be set
   */
  public void setEndTimestamp(Double endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  /**
   * Returns the character index at which the content started in the input
   *
   * @return the character index at which the content started in the input
   */
  public Integer getOffset() {
    return offset;
  }

  /**
   * Sets the character index at which the content started in the input
   *
   * @param offset the character index to be set
   */
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   * Returns the character length of the content
   *
   * @return the character length of the content
   */
  public Integer getLength() {
    return length;
  }

  /**
   * Sets the character length of the content
   *
   * @param length the character length to be set
   */
  public void setLength(Integer length) {
    this.length = length;
  }
}
