package ai.rev.speechtotext.models.streaming;

import com.google.gson.annotations.SerializedName;

/**
 * A parent class that can be used to serialize and deserialize the "type" in the WebSocket
 * messages.
 */
public class StreamingResponseMessage {

  @SerializedName("type")
  private MessageType type;

  /**
   * Returns the {@link MessageType} enumeration value.
   *
   * @return The enumeration value.
   */
  public MessageType getType() {
    return type;
  }

  /**
   * Specifies the WebSocket message type.
   *
   * @param type The WebSocket message type.
   */
  public void setType(MessageType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "{" + "'type'='" + type + '\'' + '}';
  }
}
