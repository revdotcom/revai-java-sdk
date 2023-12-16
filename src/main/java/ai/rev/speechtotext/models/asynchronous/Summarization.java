package ai.rev.speechtotext.models.asynchronous;

import ai.rev.speechtotext.models.NlpModel;
import com.google.gson.annotations.SerializedName;

/**
 * A Summarization Job object provides all the information associated with a summarization job
 * submitted by the user.
 */
public class Summarization {
  /** User defined prompt. */
  @SerializedName("prompt")
  private String prompt;

  /**
   * Summarization model.
   *
   * @see NlpModel
   */
  @SerializedName("model")
  private NlpModel model;

  /** Formatting options. Default is Paragraph. */
  @SerializedName("type")
  private SummarizationFormattingOptions type;

  @SerializedName("status")
  private SummarizationJobStatus jobStatus;

  @SerializedName("completed_on")
  private String completedOn;

  @SerializedName("failure")
  private String failure;

  /**
   * Returns the {@link SummarizationJobStatus} enumeration value.
   *
   * @return The {@link SummarizationJobStatus} enumeration value.
   * @see SummarizationJobStatus
   */
  public SummarizationJobStatus getJobStatus() {
    return jobStatus;
  }

  /**
   * Returns custom prompt used for the summarization job.
   *
   * @return Custom prompt used for the summarization job.
   */
  public String getPrompt() {
    return prompt;
  }

  /**
   * Returns backend model used for the summarization job.
   *
   * @return Backend model used for the summarization job.
   * @see NlpModel
   */
  public NlpModel getModel() {
    return model;
  }

  /**
   * Returns summary formatting options requested for the summarization job.
   *
   * @return Summary formatting options requested for the summarization job.
   * @see SummarizationFormattingOptions
   */
  public SummarizationFormattingOptions getType() {
    return type;
  }

  /**
   * Returns a String that contains the date and time the job was completed on in ISO-8601 UTC form.
   *
   * @return A String that contains the date and time the job was completed on in ISO-8601 UTC form.
   */
  public String getCompletedOn() {
    return completedOn;
  }

  /**
   * Returns failure details.
   *
   * @return Failure details.
   */
  public String getFailure() {
    return failure;
  }
}
