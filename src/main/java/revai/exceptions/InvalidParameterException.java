package revai.exceptions;

import org.json.JSONObject;

public class InvalidParameterException extends RevAiApiException {

    public InvalidParameterException(JSONObject errorResponse) {
        super("Invalid Parameter Exception ", errorResponse, 401);
    }
}
