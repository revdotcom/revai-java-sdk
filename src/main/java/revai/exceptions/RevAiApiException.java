package revai.exceptions;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class RevAiApiException extends Exception {
    public int statusCode;
    public String title = "Rev.AI API error";
    public String detail = "";
    public String type = "";

    public RevAiApiException(JSONObject errorResponse, int responseCode) {
        statusCode = responseCode;
        if(errorResponse.has("title")){
            title = errorResponse.get("title").toString();
        }
        if(errorResponse.has("detail")){
            detail = errorResponse.get("detail").toString();
        }
        if(errorResponse.has("type")){
            type = errorResponse.get("type").toString();
        }
    }

    @Override
    public String getMessage(){
//        System.out.println("here");
        return title;
    }
}


