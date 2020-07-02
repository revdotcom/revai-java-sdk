package ai.rev.speechtotext.exceptions;

import org.json.JSONObject;

import java.io.IOException;

/**
 * The RevAiApiException wraps standard Java IOException and enriches them with custom information.
 * You can use this code to retrieve details of exceptions when calling the Rev.AI API.
 */
public class RevAiApiException extends IOException {

  public RevAiApiException(String message, JSONObject errorResponse, int responseCode) {
    super(formatErrorDetails(message, errorResponse, responseCode));
  }

  private static String formatErrorDetails(
      String message, JSONObject errorResponse, int responseCode) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(message);
    if (errorResponse.has("title")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Title: " + errorResponse.get("title"));
    }
    if (errorResponse.has("detail")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Detail: " + errorResponse.get("detail"));
    }
    if (errorResponse.has("type")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Type: " + errorResponse.get("type"));
    }
    if (errorResponse.has("status")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Status: " + errorResponse.get("status"));
    }
    if (errorResponse.has("parameters")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Parameters: " + errorResponse.get("parameters"));
    }
    if (errorResponse.has("allowed_values")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Allowed Values: " + errorResponse.get("allowed_values"));
    }
    if (errorResponse.has("current_value")) {
      stringBuilder.append(System.lineSeparator());
      stringBuilder.append("Current Value: " + errorResponse.get("current_value"));
    }
    return stringBuilder.toString();
  }
}
