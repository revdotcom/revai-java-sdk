package ai.rev.speechtotext.models.streaming;

import com.google.gson.annotations.SerializedName;

/** Represents the connected message sent from Rev AI and contains the id of the stream */
public class ConnectedMessage extends StreamingResponseMessage {

  @SerializedName("id")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "{" + "type='" + getType() + '\'' + ", id='" + id + '\'' + '}';
  }
}
