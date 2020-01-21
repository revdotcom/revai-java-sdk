package revai.exceptions;

import org.json.JSONObject;

public class AuthorizationException extends RevAiApiException {

    public AuthorizationException(JSONObject errorResponse) {
        super("Authorization Exception", errorResponse, 401);
    }
}