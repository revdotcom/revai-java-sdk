package revai.exceptions;

import org.json.JSONObject;

public class InvalidHeaderException extends RevAiApiException {

  /**
   * The InvalidHeaderException occurs when a header that's passed along a query is not recognized by the API.
   */
  public InvalidHeaderException(JSONObject errorResponse) {
    super("Invalid Header Exception", errorResponse, 406);
  }
}
