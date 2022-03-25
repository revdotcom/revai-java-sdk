package ai.rev.speechtotext.models.streaming;

import ai.rev.speechtotext.models.asynchronous.Element;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Represents the message returned over WebSocket containing the transcription of audio data.
 *
 * @see <a
 *     href="https://docs.rev.ai/api/streaming/responses/">https://docs.rev.ai/api/streaming/responses/</a>
 */
public class Hypothesis extends StreamingResponseMessage {

  @SerializedName("ts")
  private Double ts;

  @SerializedName("end_ts")
  private Double endTs;

  @SerializedName("elements")
  private Element[] elements;

  /**
   * Returns the starting timestamp of a transcribed audio sample.
   *
   * @return The starting timestamp of a transcribed audio sample.
   */
  public Double getTs() {
    return ts;
  }

  /**
   * Sets the starting timestamp.
   *
   * @param ts The starting timestamp.
   */
  public void setTs(Double ts) {
    this.ts = ts;
  }

  /**
   * Returns the ending timestamp of a transcribed audio sample.
   *
   * @return The ending timestamp of a transcribed audio sample.
   */
  public Double getEndTs() {
    return endTs;
  }

  /**
   * Sets the ending timestamp.
   *
   * @param endTs The ending timestamp.
   */
  public void setEndTs(Double endTs) {
    this.endTs = endTs;
  }

  /**
   * Returns a list of {@link Element} objects.
   *
   * @return A list of {@link Element} objects.
   * @see Element
   */
  public Element[] getElements() {
    return elements;
  }

  /**
   * Sets elements to the list of {@link Element} objects provided.
   *
   * @param elements The list of {@link Element} objects to set as the elements.
   * @see Element
   */
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
