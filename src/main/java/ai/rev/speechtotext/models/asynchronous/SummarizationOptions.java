package ai.rev.speechtotext.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A SummarizationOptions object represents summarization parameters that are submitted along a new
 * job.
 *
 * @see <a
 *     href="https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob">https://docs.rev.ai/api/asynchronous/reference/#operation/SubmitTranscriptionJob</a>
 */
public class SummarizationOptions {

  /** User defined prompt. */
  @SerializedName("prompt")
  private String prompt;

  /** Standard or Premium AI backend. */
  @SerializedName("model")
  private SummarizationModel model;

  /** Formatting options. Default is Paragraph. */
  @SerializedName("type")
  private SummarizationFormattingOptions type;

  /**
   * Returns custom prompt used for the summarization job.
   *
   * @return Custom prompt used for the summarization job.
   */
  public String getPrompt() {
    return prompt;
  }

  /**
   * Sets custom prompt to use for the summarization job.
   *
   * @param prompt Custom prompt to use for the summarization job.
   */
  public SummarizationOptions setPrompt(String prompt) {
    this.prompt = prompt;
    return this;
  }

  /**
   * Returns backend model used for the summarization job.
   *
   * @return Backend model used for the summarization job.
   * @see TranslationModel
   */
  public SummarizationModel getModel() {
    return model;
  }

  /**
   * Sets backend model used for the summarization job.
   *
   * @param model Backend model used for the summarization job.
   * @see SummarizationModel
   */
  public SummarizationOptions setModel(SummarizationModel model) {
    this.model = model;
    return this;
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
   * Sets summary formatting options requested for the summarization job.
   *
   * @param type Summary formatting options requested for the summarization job.
   * @see SummarizationFormattingOptions
   */
  public SummarizationOptions setType(SummarizationFormattingOptions type) {
    this.type = type;
    return this;
  }
}
