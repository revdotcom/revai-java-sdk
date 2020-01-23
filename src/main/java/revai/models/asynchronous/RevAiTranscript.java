package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class RevAiTranscript {
  public List<Monologue> monologues;

  public RevAiTranscript(List<Monologue> monologues) {
    this.monologues = monologues;
  }
}

class Monologue {
  Integer speaker;
  List<Element> elements;
}

class Element {
  @SerializedName("timestamp")
  Timestamp startTimestamp;

  @SerializedName("end_timestamp")
  Timestamp endTimestamp;

  String type;
  String value;
  Double confidence;
}
