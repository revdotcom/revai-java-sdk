package ai.rev.exceptions;

import org.json.JSONObject;

/**
 * The InvalidHeaderException occurs when a header that's passed along a query is not recognized by
 * the API.
 */
public class InvalidHeaderException extends RevAiApiException {

  public InvalidHeaderException(JSONObject errorResponse) {
    super("Invalid Header Exception", errorResponse, 406);
  }
}
