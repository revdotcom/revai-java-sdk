package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class RevAiTranscript {
  @SerializedName("monologues")
  private List<Monologue> monologues;

  private RevAiTranscript(List<Monologue> monologues) {
    this.monologues = monologues;
  }

  public List<Monologue> getMonologues() {
    return monologues;
  }

  public void setMonologues(List<Monologue> monologues) {
    this.monologues = monologues;
  }
}

class Monologue {
  @SerializedName("speaker")
  Integer speaker;

  @SerializedName("elements")
  List<Element> elements;

  public Integer getSpeaker() {
    return speaker;
  }

  public void setSpeaker(Integer speaker) {
    this.speaker = speaker;
  }

  public List<Element> getElements() {
    return elements;
  }

  public void setElements(List<Element> elements) {
    this.elements = elements;
  }
}

class Element {
  @SerializedName("ts")
  Timestamp startTimestamp;

  @SerializedName("end_ts")
  Timestamp endTimestamp;

  @SerializedName("type")
  String type;

  @SerializedName("value")
  String value;

  @SerializedName("confidence")
  Double confidence;

  public Timestamp getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(Timestamp startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public Timestamp getEndTimestamp() {
    return endTimestamp;
  }

  public void setEndTimestamp(Timestamp endTimestamp) {
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
}
