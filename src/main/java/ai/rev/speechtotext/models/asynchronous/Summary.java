package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A Summary object represents summarization job results provided either as paragraph summary of
 * bullet points, depending on the requested format.
 *
 * @see SummarizationOptions
 */
public class Summary {
  @SerializedName("summary")
  private String summary;

  @SerializedName("bullet_points")
  private List<String> bulletPoints;

  /**
   * Returns paragraph summary if it was requested as PARAGRAPH
   *
   * @see SummarizationFormattingOptions
   */
  public String getSummary() {
    return summary;
  }

  /**
   * Returns bullet points summary if it was requested as BULLETS
   *
   * @see SummarizationFormattingOptions
   */
  public List<String> getBulletPoints() {
    return bulletPoints;
  }
}
