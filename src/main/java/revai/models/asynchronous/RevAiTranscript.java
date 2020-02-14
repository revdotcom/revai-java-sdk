package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/** A RevAiTranscript object presents a list of monologues as the transcript of a specific job. */
public class RevAiTranscript {
  @SerializedName("monologues")
  private List<Monologue> monologues;

  public List<Monologue> getMonologues() {
    return monologues;
  }

  public void setMonologues(List<Monologue> monologues) {
    this.monologues = monologues;
  }
}
