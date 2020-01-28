package revai.exceptions;

import org.json.JSONObject;

public class InvalidParameterException extends RevAiApiException {

  /**
   * The InvalidParameterException occurs when a parameter that's passed along a query is not recognized by the API.
   */
  public InvalidParameterException(JSONObject errorResponse) {
    super("Invalid Parameter Exception ", errorResponse, 400);
  }
}
