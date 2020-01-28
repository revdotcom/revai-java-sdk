package revai.exceptions;

import org.json.JSONObject;

public class ThrottlingLimitException extends RevAiApiException {
  /**
   * ThrottlingLimitException occurs when the number of queries within a given period reaches a throttling limit.
   */
  public ThrottlingLimitException(JSONObject errorResponse) {
    super("Throttling Limit Exception", errorResponse, 429);
  }
}
