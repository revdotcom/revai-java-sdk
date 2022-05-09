package ai.rev.sentimentanalysis.models;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define possible sentiments from our API. */
public enum Sentiment {

  /** Positive sentiment. */
  @SerializedName("positive")
  POSITIVE("positive"),

  /** Neutral sentiment. */
  @SerializedName("neutral")
  NEUTRAL("neutral"),

  /** Negative sentiment. */
  @SerializedName("negative")
  NEGATIVE("negative");

  private String sentiment;

  Sentiment(String sentiment) {
    this.sentiment = sentiment;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getSentiment() {
    return sentiment;
  }

  @Override
  public String toString() {
    return "{" + "sentiment='" + sentiment + '\'' + '}';
  }
}
