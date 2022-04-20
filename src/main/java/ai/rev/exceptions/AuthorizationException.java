package ai.rev.exceptions;

import org.json.JSONObject;

/**
 * The AuthorizationException happens when an invalid token access is used to query any
 * endpoint.
 */
public class AuthorizationException extends RevAiApiException {

  public AuthorizationException(JSONObject errorResponse) {
    super("Authorization Exception", errorResponse, 401);
  }
}
