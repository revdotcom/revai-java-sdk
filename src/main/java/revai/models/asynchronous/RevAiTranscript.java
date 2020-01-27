package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class RevAiTranscript {
  @SerializedName("monologues")
  public List<Monologue> monologues;

  public RevAiTranscript(List<Monologue> monologues) {
    this.monologues = monologues;
  }
}

class Monologue {
  @SerializedName("speaker")
  Integer speaker;

  @SerializedName("elements")
  List<Element> elements;
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
}
