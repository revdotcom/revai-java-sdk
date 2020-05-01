package revai.models.streaming;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define the WebSocket message type. */
public enum MessageType {

  /** The type of message sent when the WebSocket connects. */
  @SerializedName("connected")
  CONNECTED("connected"),

  /** The type of message sent when the WebSocket returns a partial hypotheses. */
  @SerializedName("partial")
  PARTIAL("partial"),

  /** The type of message sent when the WebSocket returns a final hypotheses. */
  @SerializedName("final")
  FINAL("final");

  private String messageType;

  MessageType(String messageType) {
    this.messageType = messageType;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getMessageType() {
    return messageType;
  }

  @Override
  public String toString() {
    return "{" + "messageType='" + messageType + '\'' + '}';
  }
}
