package revai.exceptions;

import org.json.JSONObject;

public class ForbiddenStateException extends RevAiApiException {

  public ForbiddenStateException(JSONObject errorResponse) {
    super("Forbidden State Exception", errorResponse, 429);
  }
}
