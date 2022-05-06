package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define Rev AI job types. */
public enum RevAiJobType {

  /** The type used for asynchronous jobs. */
  @SerializedName("async")
  ASYNC("async"),

  /** The type used for streaming jobs. */
  @SerializedName("stream")
  STREAM("stream"),

  /** The type used for topic extraction jobs. */
  @SerializedName("topic_extraction")
  TOPICEXTRACTION("topic_extraction");
    
  /** The Type used for sentiment analysis jobs. */
  @SerializedName("sentiment_analysis")
  SENTIMENTANALYSIS("sentiment_analysis")

  private String jobType;

  RevAiJobType(String type) {
    this.jobType = type;
  }

  /**
   * Returns the String value of the enumeration.
   *
   * @return The String value of the enumeration.
   */
  public String getJobType() {
    return jobType;
  }

  @Override
  public String toString() {
    return "{" + "type='" + jobType + '\'' + '}';
  }
}
