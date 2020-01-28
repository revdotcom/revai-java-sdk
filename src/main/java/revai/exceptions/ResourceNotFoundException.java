package revai.exceptions;

import org.json.JSONObject;

public class ResourceNotFoundException extends RevAiApiException {

  /**
   * The ResourceNotFoundException occurs when a job ID queried is not associated with a job submitted by the user.
   */
  public ResourceNotFoundException(JSONObject errorResponse) {
    super("Resource Not Found Exception", errorResponse, 404);
  }
}
