package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A Segment object represents a segment of transcript that needs to be transcribed.
 * Used for speech-to-text jobs submitted to be transcribed by a human.
 * */
public class SegmentToTranscribe {
  @SerializedName("start")
  private Double startTimestamp;

  @SerializedName("end")
  private Double endTimestamp;

  /**
   * Returns the starting timestamp of the segment to transcribe.
   *
   * @return The starting timestamp of the segment.
   */
  public Double getStartTimestamp() {
    return startTimestamp;
  }

  /**
   * Sets the starting timestamp for the segment to transcribe.
   *
   * @param startTimestamp The timestamp to set as the starting timestamp.
   */
  public void setStartTimestamp(Double startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  /**
   * Returns the end timestamp for the segment to transcribe.
   *
   * @return The end timestamp of the segment.
   */
  public Double getEndTimestamp() {
    return endTimestamp;
  }

  /**
   * Sets the end timestamp for the segment to transcribe.
   *
   * @param endTimestamp The timestamp to set as the end timestamp.
   */
  public void setEndTimestamp(Double endTimestamp) {
    this.endTimestamp = endTimestamp;
  }
}
