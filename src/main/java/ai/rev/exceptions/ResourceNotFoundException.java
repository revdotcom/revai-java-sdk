package ai.rev.exceptions;

import org.json.JSONObject;

/**
 * The ResourceNotFoundException occurs when a job ID queried cannot be found.
 */
public class ResourceNotFoundException extends RevAiApiException {

  public ResourceNotFoundException(JSONObject errorResponse) {
    super("Resource Not Found Exception", errorResponse, 404);
  }
}
