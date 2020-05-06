package ai.rev.speechtotext.exceptions;

import org.json.JSONObject;

/**
 * The AuthorizationException happens when an invalid token access is used to query the account
 * information endpoint.
 */
public class AuthorizationException extends RevAiApiException {

  public AuthorizationException(JSONObject errorResponse) {
    super("Authorization Exception", errorResponse, 401);
  }
}
