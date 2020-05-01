package ai.rev.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/** Specifies constants that define Rev.ai job types. */
public enum RevAiJobType {

  /** The type used for asynchronous jobs. */
  @SerializedName("async")
  ASYNC("async"),

  /** The type used for streaming jobs. */
  @SerializedName("stream")
  STREAM("stream");

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
