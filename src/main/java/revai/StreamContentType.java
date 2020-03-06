package revai;

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

  public void setContentType(String content_type) {
    this.contentType = content_type;
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
}
