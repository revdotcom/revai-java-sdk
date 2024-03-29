package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A object representing information provided about a speaker
 * Used for speech-to-text jobs submitted to be transcribed by a human.
 * */
public class SpeakerName {
  @SerializedName("display_name")
  private String displayName;

  /**
   * Returns the displayed name of the speaker
   *
   * @return the displayed name of the speaker
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the displayed name of the speaker
   *
   * @param displayName the displayed name of the speaker
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
