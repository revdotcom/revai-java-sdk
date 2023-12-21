package ai.rev.speechtotext.models;

import com.google.gson.annotations.SerializedName;

/** Supported model types for NLP tasks like summarization. */
public enum NlpModel {

    @SerializedName("standard")
    STANDARD("standard"),
    @SerializedName("premium")
    PREMIUM("premium");

    private final String model;

    NlpModel(String model) {
        this.model = model;
    }

    /**
     * Returns the String value of the enumeration.
     *
     * @return The String value of the enumeration.
     */
    public String getModel() { return model; }

    @Override
    public String toString() {
        return "{" + "model='" + model + '\'' + '}';
    }
}
