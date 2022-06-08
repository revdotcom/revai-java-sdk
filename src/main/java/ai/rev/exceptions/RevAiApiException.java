package ai.rev.exceptions;

import org.json.JSONObject;

import java.io.IOException;

/**
 * The RevAiApiException wraps standard Java IOException and enriches them with custom information.
 * You can use this code to retrieve details of exceptions when calling the Rev AI API.
 */
public class RevAiApiException extends IOException {

  public RevAiApiException(String message, JSONObject errorResponse, int responseCode) {
    super(formatErrorDetails(message, errorResponse, responseCode));
  }

  private static String formatErrorDetails(
      String message, JSONObject errorResponse, int responseCode) {
    return String.format("Response code: %s, Error: %s, Api response: %s", responseCode, message, errorResponse);
  }
}
