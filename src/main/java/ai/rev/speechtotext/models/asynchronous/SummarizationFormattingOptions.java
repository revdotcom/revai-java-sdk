package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/** Summarization formatting options. * */
public enum SummarizationFormattingOptions {

  /** Paragraph formatting * */
  @SerializedName("paragraph")
  PARAGRAPH("paragraph"),

  /** Bullet points formatting * */
  @SerializedName("bullets")
  BULLETS("bullets");

  private final String formatting;

  SummarizationFormattingOptions(String formatting) {
    this.formatting = formatting;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getFormatting() {
    return formatting;
  }

  @Override
  public String toString() {
    return "{" + "formatting='" + formatting + '\'' + '}';
  }
}
