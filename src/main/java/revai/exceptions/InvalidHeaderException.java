package revai.exceptions;

import org.json.JSONObject;

public class InvalidHeaderException extends RevAiApiException {

  public InvalidHeaderException(JSONObject errorResponse) {
    super("Invalid Header Exception", errorResponse, 406);
  }
}
