package ai.rev.speechtotext.models.asynchronous;

/** Specifies constants that define support caption formats. */
public enum RevAiCaptionType {

  /** The SubRip caption file format. */
  SRT("application/x-subrip"),

  /** The WebVTT caption file format. */
  VTT("text/vtt");

  private String captionType;

  RevAiCaptionType(String captionType) {
    this.captionType = captionType;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getContentType() {
    return captionType;
  }

  @Override
  public String toString() {
    return "{" + "captionType='" + captionType + '\'' + '}';
  }
}
