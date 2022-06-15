package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A SpeakerInfo object represents the information related to the speaker in a transcript
 * Used for speech-to-text jobs submitted to be transcribed by a human.
 * */
public class SpeakerInfo {
  @SerializedName("id")
  private String id;

  @SerializedName("display_name")
  private String displayName;

  /**
   * Returns the id of the speaker
   *
   * @return the id of the speaker
   */
  public String getIdName() {
    return id;
  }

  /**
   * Sets the id of the speaker
   *
   * @param displayName the displayed name of the speaker
   */
  public void setId(String id) {
    this.id = id;
  }

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
