package ai.rev.topicextraction.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents a single topic in the input as well as all the information that comes with it.
 */
public class Topic {
  /**
   * Name of the topic, pulled directly from somewhere in the input text.
   */
  @SerializedName("topic_name")
  private String topicName;

  /**
   * Score of the topic, between 0 and 1. Higher means it is more likely that this
   * is truly a topic.
   */
  @SerializedName("score")
  private Double score;

  /**
   * Pieces of the input text which informed this choice of topic.
   */
  @SerializedName("informants")
  private List<Informant> informants;

  /**
   * Returns the topic name.
   *
   * @return The topic name.
   */
  public String getTopicName() {
    return topicName;
  }

  /**
   * Specifies the topic name.
   *
   * @param topicName the topic name.
   */
  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  /**
   * Returns the score.
   *
   * @return The score.
   */
  public Double getScore() {
    return score;
  }

  /**
   * Specifies the score.
   *
   * @param score score of the topic.
   */
  public void setScore(Double score) {
    this.score = score;
  }

  /**
   * Returns the informants.
   *
   * @return The list of informants.
   * @see Informant
   */
  public List<Informant> getInformants() {
    return informants;
  }

  /**
   * Specifies the list of informants.
   *
   * @param informants the list of informants for the topic.
   */
  public void setInformants(List<Informant> informants) {
    this.informants = informants;
  }
}
