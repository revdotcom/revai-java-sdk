package revai;

public class StreamContentType {

  public String content_type;
  public String layout;
  public Integer rate;
  public String format;
  public Integer channels;

  public StreamContentType(
      String content_type, String layout, Integer rate, String format, Integer channels) {
    this.content_type = content_type;
    this.layout = layout;
    this.rate = rate;
    this.format = format;
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
