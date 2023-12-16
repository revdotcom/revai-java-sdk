package ai.rev.speechtotext.models;

import com.google.gson.annotations.SerializedName;

/** GPT Model type. STANDARD (GPT 3.5) or PREMIUM (GPT 4) */
public enum NlpModel {

    /** gpt 3.5. This is the default value. */
    @SerializedName("standard")
    STANDARD("standard"),

    /** gpt 4. */
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
