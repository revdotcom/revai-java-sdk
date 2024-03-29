package ai.rev.exceptions;

import org.json.JSONObject;

/**
 * ThrottlingLimitException occurs when the number of queries within a given period exceeds a
 * throttling limit.
 */
public class ThrottlingLimitException extends RevAiApiException {

  public ThrottlingLimitException(JSONObject errorResponse) {
    super("Throttling Limit Exception", errorResponse, 429);
  }
}
