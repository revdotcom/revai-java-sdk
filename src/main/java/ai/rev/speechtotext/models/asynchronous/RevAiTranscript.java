package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/** A RevAiTranscript object presents a list of monologues as the transcript of a specific job. */
public class RevAiTranscript {
  @SerializedName("monologues")
  private List<Monologue> monologues;

  /**
   * Returns a list on {@link Monologue} objects.
   *
   * @return a list on {@link Monologue} objects.
   * @see Monologue
   */
  public List<Monologue> getMonologues() {
    return monologues;
  }

  /**
   * Sets the monologues to the list of monologues provided.
   *
   * @param monologues the list of monologues to set the monologues to.
   * @see Monologue
   */
  public void setMonologues(List<Monologue> monologues) {
    this.monologues = monologues;
  }
}
