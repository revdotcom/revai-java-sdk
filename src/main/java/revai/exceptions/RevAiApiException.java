package revai.exceptions;

import org.json.JSONObject;

import java.io.IOException;

/**
 * The RevAiApiException wraps standard Java IOException and enriches them with custom information.
 * You can use this code to retrieve details of exceptions when calling the Rev.AI API.
 */

public class RevAiApiException extends IOException {
    public int statusCode;
    public String title;
    public String detail;
    public String type;

    public RevAiApiException(String message, JSONObject errorResponse, int responseCode) {
        super(message);
        statusCode = responseCode;
        if (errorResponse.has("title")) {
            title = errorResponse.get("title").toString();
        }
        if (errorResponse.has("detail")) {
            detail = errorResponse.get("detail").toString();
        }
        if (errorResponse.has("type")) {
            type = errorResponse.get("type").toString();
        }

    }

}


