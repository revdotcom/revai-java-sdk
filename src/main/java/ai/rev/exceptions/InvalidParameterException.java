package ai.rev.exceptions;

import org.json.JSONObject;

/**
 * The InvalidParameterException occurs when a parameter that's passed along a query is not
 * recognized by the API.
 */
public class InvalidParameterException extends RevAiApiException {

  public InvalidParameterException(JSONObject errorResponse) {
    super("Invalid Parameter Exception ", errorResponse, 400);
  }
}
