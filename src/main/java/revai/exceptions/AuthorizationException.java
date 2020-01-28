package revai.exceptions;

import org.json.JSONObject;

public class AuthorizationException extends RevAiApiException {

  /**
   * The AuthorizationException happens when an invalid token access is used to query the account
   * information endpoint.
   */
  public AuthorizationException(JSONObject errorResponse) {
    super("Authorization Exception", errorResponse, 401);
  }
}
