package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class RevAiJob {
    @SerializedName("id")
    private String jobID;

    @SerializedName("created_on")
    public Timestamp createdOn;

    @SerializedName("completed_on")
    public Timestamp completedOn;

    @SerializedName("duration_seconds")
    public double durationSeconds;

    @SerializedName("callback_url")
    public String callbackUrl;

    @SerializedName("media_url")
    public String mediaUrl;

    @SerializedName("failure_details")
    public String failureDetails;

    @SerializedName("status")
    public RevAiJobStatus jobStatus;

    public String metadata;

    public String failure;

    public String name;

    public String type;

}
