package revai;

import java.util.ArrayList;
import java.util.List;

/**
 * The StreamContentType describes the format of the audio being sent over the WebSocket.
 * @see <a href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type">https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type</a
 */
public class StreamContentType {

  private String contentType;
  private String layout;
  private Integer rate;
  private String format;
  private Integer channels;

  public StreamContentType() {
    this.contentType = null;
    this.layout = null;
    this.rate = null;
    this.format = null;
    this.channels = null;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getLayout() {
    return layout;
  }

  public void setLayout(String layout) {
    this.layout = layout;
  }

  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Integer getChannels() {
    return channels;
  }

  public void setChannels(Integer channels) {
    this.channels = channels;
  }

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
      content.add("content_type" + "=" + getContentType());
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
