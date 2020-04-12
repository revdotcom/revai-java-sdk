package revai.models.streaming;

import com.google.gson.annotations.SerializedName;

public class StreamingResponseMessage {

    @SerializedName("type")
    private MessageType type;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "'type'='" + type + '\'' +
                '}';
    }
}
