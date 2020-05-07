package ai.rev.speechtotext.models.asynchronous;

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

  /**
   * Returns the speaker number for this monologue.
   *
   * @return The speaker number for this monologue.
   */
  public Integer getSpeaker() {
    return speaker;
  }

  /**
   * Sets the speaker number for this monologue.
   *
   * @param speaker The number to set the speaker to.
   */
  public void setSpeaker(Integer speaker) {
    this.speaker = speaker;
  }

  /**
   * Returns a list of {@link Element} objects.
   *
   * @return A list of {@link Element} objects.
   * @see Element
   */
  public List<Element> getElements() {
    return elements;
  }

  /**
   * Sets elements to the list of {@link Element} objects provided.
   *
   * @param elements The list of {@link Element} objects to set as the elements.
   * @see Element
   */
  public void setElements(List<Element> elements) {
    this.elements = elements;
  }
}
