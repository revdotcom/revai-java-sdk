package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A Monologue object presents information about a segment of a transcript owned by an individual
 * speaker.
 */
public class Monologue {
  @SerializedName("speaker")
  private Integer speaker;

  @SerializedName("elements")
  private List<Element> elements;

  public Integer getSpeaker() {
    return speaker;
  }

  public void setSpeaker(Integer speaker) {
    this.speaker = speaker;
  }

  public List<Element> getElements() {
    return elements;
  }

  public void setElements(List<Element> elements) {
    this.elements = elements;
  }
}
