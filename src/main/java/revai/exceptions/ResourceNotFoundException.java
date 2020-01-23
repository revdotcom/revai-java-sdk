package revai.exceptions;

import org.json.JSONObject;

public class ResourceNotFoundException extends RevAiApiException {

  public ResourceNotFoundException(JSONObject errorResponse) {
    super("Rescource Not Found Exception", errorResponse, 404);
  }
}
