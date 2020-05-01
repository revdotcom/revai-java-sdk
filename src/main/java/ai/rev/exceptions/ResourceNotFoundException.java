package revai.exceptions;

import org.json.JSONObject;

/**
 * The ResourceNotFoundException occurs when a job ID queried is not associated with a job submitted
 * by the user.
 */
public class ResourceNotFoundException extends RevAiApiException {

  public ResourceNotFoundException(JSONObject errorResponse) {
    super("Resource Not Found Exception", errorResponse, 404);
  }
}
