package revai.models.streaming;

import com.google.gson.annotations.SerializedName;
import revai.models.asynchronous.Element;

import java.util.List;

public class Hypothesis {

    @SerializedName("type")
    private String type;

    @SerializedName("ts")
    private Double ts;

    @SerializedName("end_ts")
    private Double endTs;

    @SerializedName("elements")
    private Element[] elements;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTs() {
        return ts;
    }

    public void setTs(Double ts) {
        this.ts = ts;
    }

    public Double getEndTs() {
        return endTs;
    }

    public void setEndTs(Double endTs) {
        this.endTs = endTs;
    }

    public Element[] getElements() {
        return elements;
    }

    public void setElements(Element[] elements) {
        this.elements = elements;
    }
}
