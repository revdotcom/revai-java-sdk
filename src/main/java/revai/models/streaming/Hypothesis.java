package revai.models.streaming;

import com.google.gson.annotations.SerializedName;
import revai.models.asynchronous.Element;

import java.util.Arrays;
import java.util.List;

/**
 * The transcription of audio data returned over the WebSocket connection.
 *
 * @see <a
 *     href="https://www.rev.ai/docs/streaming#section/Rev.ai-to-Client-Response/Hypothesis-Object">Cloud
 *     API documentation</a>
 */
public class Hypothesis extends StreamingResponseMessage {

  @SerializedName("ts")
  private Double ts;

  @SerializedName("end_ts")
  private Double endTs;

  @SerializedName("elements")
  private Element[] elements;

  public Double getTs() {
    return ts;
  }

  public void setTs(Double ts) {
    this.ts = ts;
  }

  public Double getEndTs() {
    return endTs;
  }

  public void setEndTs(Double endTs) {
    this.endTs = endTs;
  }

  public Element[] getElements() {
    return elements;
  }

  public void setElements(Element[] elements) {
    this.elements = elements;
  }

  @Override
  public String toString() {
    return "{"
        + "type='"
        + getType()
        + '\''
        + ", ts="
        + ts
        + ", endTs="
        + endTs
        + ", elements="
        + Arrays.toString(elements)
        + '}';
  }
}
