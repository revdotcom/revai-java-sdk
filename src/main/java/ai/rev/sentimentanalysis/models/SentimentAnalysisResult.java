package ai.rev.sentimentanalysis.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/** A Sentiment Analysis Result object provides all the information associated with the result of a job. */
public class SentimentAnalysisResult {
    /**
     * Messages from the input text, ordered by appearance in the input. 
     * Each part of the input will be represented in one of the messages
     */
    @SerializedName("messages")
    private List<SentimentMessage> messages;

    /**
     * Get the messages of the result.
     *
     * @return The messages of the result
     */
    public List<SentimentMessage> getMessages() {
        return messages;
    }

    /**
     * Set the messages of the result.
     *
     * @param messages the messages to be set for the result.
     */
    public void setMessages(List<SentimentMessage> messages) {
        this.messages = messages;
    }
}