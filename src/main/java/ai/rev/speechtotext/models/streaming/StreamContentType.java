package ai.rev.speechtotext.models.streaming;

import java.util.ArrayList;
import java.util.List;

/**
 * The StreamContentType describes the format of the audio being sent over the WebSocket.
 *
 * @see <a
 *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a>
 */
public class StreamContentType {

  private String contentType;
  private String layout;
  private Integer rate;
  private String format;
  private Integer channels;

  public StreamContentType() {}

  /**
   * Returns the content type.
   *
   * @return The content type.
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * Specifies the content type of the audio.
   *
   * @param contentType The type of audio.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a>
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * Returns the channel layout.
   *
   * @return The channel layout.
   */
  public String getLayout() {
    return layout;
  }

  /**
   * Specifies the layout of channels within the buffer.
   *
   * @param layout The channel layout.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a>
   */
  public void setLayout(String layout) {
    this.layout = layout;
  }

  /**
   * Returns the audio sample rate.
   *
   * @return The audio sample rate.
   */
  public Integer getRate() {
    return rate;
  }

  /**
   * Specifies the sample rate of the audio.
   *
   * @param rate The sample rate of the audio.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a>
   */
  public void setRate(Integer rate) {
    this.rate = rate;
  }

  /**
   * Returns the format of the audio samples.
   *
   * @return The format of the audio samples.
   */
  public String getFormat() {
    return format;
  }

  /**
   * Specifies the format of the audio samples.
   *
   * @param format The format of the audio samples.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a>
   * @see <a
   *     href="https://gstreamer.freedesktop.org/documentation/additional/design/mediatype-audio-raw.html?gi-language=c#formats">Valid
   *     formats</a>
   */
  public void setFormat(String format) {
    this.format = format;
  }

  /**
   * Returns the number of channels.
   *
   * @return The number of channels.
   */
  public Integer getChannels() {
    return channels;
  }

  /**
   * Specifies the number of audio channels that the audio samples contain.
   *
   * @param channels the number of audio channels in the audio.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a>
   */
  public void setChannels(Integer channels) {
    this.channels = channels;
  }

  /**
   * Returns the content string compiled from the {@link StreamContentType} properties to be used in
   * the query parameter for the request to the /stream endpoint.
   *
   * @return The content string used as the query parameter.
   */
  public String buildContentString() {
    List<String> content = getListFromContentType();
    String empty = "";
    if (content.size() == 0) {
      return empty;
    } else {
      return createContentTypeParameter(content);
    }
  }

  private List<String> getListFromContentType() {
    List<String> content = new ArrayList<>();
    if (getContentType() != null) {
      content.add(getContentType());
    }
    if (getLayout() != null) {
      content.add("layout" + "=" + getLayout());
    }
    if (getRate() != null) {
      content.add("rate" + "=" + getRate());
    }
    if (getFormat() != null) {
      content.add("format" + "=" + getFormat());
    }
    if (getChannels() != null) {
      content.add("channels" + "=" + getChannels());
    }
    return content;
  }

  private String createContentTypeParameter(List<String> content) {
    return String.join(";", content);
  }
}
