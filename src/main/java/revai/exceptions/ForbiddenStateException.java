package revai.exceptions;

import org.json.JSONObject;

/**
 * The ForbiddenStateException occurs when a user attempts to retrieve the transcript or caption of
 * a unprocessed job.
 */
public class ForbiddenStateException extends RevAiApiException {

  public ForbiddenStateException(JSONObject errorResponse) {
    super("Forbidden State Exception", errorResponse, 409);
  }
}
