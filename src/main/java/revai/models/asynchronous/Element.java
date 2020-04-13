package revai.models.asynchronous;

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

  public Double getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(Double startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public Double getEndTimestamp() {
    return endTimestamp;
  }

  public void setEndTimestamp(Double endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Double getConfidence() {
    return confidence;
  }

  public void setConfidence(Double confidence) {
    this.confidence = confidence;
  }

  @Override
  public String toString() {
    return "{" +
            "startTimestamp=" + startTimestamp +
            ", endTimestamp=" + endTimestamp +
            ", type='" + type + '\'' +
            ", value='" + value + '\'' +
            ", confidence=" + confidence +
            '}';
  }
}
