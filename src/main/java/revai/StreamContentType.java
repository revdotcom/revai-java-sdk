package revai;

public class StreamContentType {

  private String contentType;
  private String layout;
  private Integer rate;
  private String format;
  private Integer channels;

  public StreamContentType(
      String content_type, String layout, Integer rate, String format, Integer channels) {
    this.contentType = content_type;
    this.layout = layout;
    this.rate = rate;
    this.format = format;
    this.channels = channels;
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

  public static class Builder {

    private String contentType;
    private String layout;
    private Integer rate;
    private String format;
    private Integer channels;

    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public Builder layout(String layout) {
      this.layout = layout;
      return this;
    }

    public Builder rate(Integer rate) {
      this.rate = rate;
      return this;
    }

    public Builder format(String format) {
      this.format = format;
      return this;
    }

    public Builder channels(Integer channels) {
      this.channels = channels;
      return this;
    }

    public StreamContentType build() {
      return new StreamContentType(contentType, layout, rate, format, channels);
    }
  }
}
