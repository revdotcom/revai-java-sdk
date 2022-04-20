package ai.rev.topicextraction.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/** A Topic Extraction Result object provides all the information associated with the result of a job. */
public class TopicExtractionResult {
    /**
     * Topics detected in the input text, ordered by score
     */
    @SerializedName("topics")
    private List<Topic> topics;

    /**
     * Get the topics of the result.
     *
     * @return The topics of the result
     */
    public List<Topic> getTopics() {
        return topics;
    }

    /**
     * Set the topics of the result.
     *
     * @param topics the topics to be set for the result.
     */
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}