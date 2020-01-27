package revai.exceptions;

import org.json.JSONObject;

public class ThrottlingLimitException extends RevAiApiException {

  public ThrottlingLimitException(JSONObject errorResponse) {
    super("Throttling Limit Exception", errorResponse, 429);
  }
}
