package revai.models.streaming;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the types of messages that the Rev.ai server will send the client over the WebSocket
 * connection.
 */
public enum MessageType {
  @SerializedName("connected")
  CONNECTED("connected"),

  @SerializedName("partial")
  PARTIAL("partial"),

  @SerializedName("final")
  FINAL("final");

  private String type;

  MessageType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }
}
