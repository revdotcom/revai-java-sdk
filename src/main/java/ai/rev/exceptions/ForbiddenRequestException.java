package ai.rev.exceptions;

import org.json.JSONObject;

/**
 * The ForbiddenRequestException happens when the request contains parameters that do not pass validation
 * or the request is not allowed for the user
 */
public class ForbiddenRequestException extends RevAiApiException {

  public ForbiddenRequestException(JSONObject errorResponse) {
    super("Forbidden Request Exception", errorResponse, 403);
  }
}
