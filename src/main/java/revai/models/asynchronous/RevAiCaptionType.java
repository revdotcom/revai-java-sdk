package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public enum RevAiCaptionType {
  SRT("application/x-subrip"),
  VTT("text/vtt");

  private String captionType;

  RevAiCaptionType(String captionType) {
    this.captionType = captionType;
  }

  public String getContentType() {
    return captionType;
  }
}
