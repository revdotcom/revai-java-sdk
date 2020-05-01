package ai.rev.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * An Element object presents all the information the models inferred from a single interval of
 * audio.
 */
public class Element {
  @SerializedName("ts")
  private Double startTimestamp;

  @SerializedName("end_ts")
  private Double endTimestamp;

  @SerializedName("type")
  private String type;

  @SerializedName("value")
  private String value;

  @SerializedName("confidence")
  private Double confidence;

  /**
   * Returns the starting timestamp of this audio interval.
   *
   * @return The starting timestamp of this audio interval.
   */
  public Double getStartTimestamp() {
    return startTimestamp;
  }

  /**
   * Sets the starting timestamp for this audio interval.
   *
   * @param startTimestamp The timestamp to set as the starting timestamp.
   */
  public void setStartTimestamp(Double startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  /**
   * Returns the end timestamp for this audio interval.
   *
   * @return The end timestamp for this audio interval.
   */
  public Double getEndTimestamp() {
    return endTimestamp;
  }

  /**
   * Sets the end timestamp for this audio interval.
   *
   * @param endTimestamp The timestamp to set as the end timestamp.
   */
  public void setEndTimestamp(Double endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  /**
   * Returns the type of transcript element contained within this audio interval.
   *
   * @return The type of transcript element contained within this audio interval.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type to the String provided
   *
   * @param type the String value to set as the type.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the value of the transcript element.
   *
   * @return The value of the transcript element.
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the transcript element to the String provided.
   *
   * @param value The String to set as the value.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Returns the confidence score of the value provided in this transcript element.
   *
   * @return The confidence score of the value provided in this transcript element.
   */
  public Double getConfidence() {
    return confidence;
  }

  /**
   * Sets the confidence score to the Double provided.
   *
   * @param confidence The Double to set the confidence score to.
   */
  public void setConfidence(Double confidence) {
    this.confidence = confidence;
  }

  @Override
  public String toString() {
    return "{"
        + "startTimestamp="
        + startTimestamp
        + ", endTimestamp="
        + endTimestamp
        + ", type='"
        + type
        + '\''
        + ", value='"
        + value
        + '\''
        + ", confidence="
        + confidence
        + '}';
  }
}
