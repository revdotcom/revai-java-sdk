package revai;

import revai.models.asynchronous.*;
import java.net.URL;
import org.json.JSONObject;
import java.net.HttpURLConnection;

public class ApiClient {
//    public String accessToken;
    public ApiRequest apiHandler;
    public String baseUrl;

    public ApiClient(ApiRequest ApiHandler, String version) {
        apiHandler = ApiHandler;
        baseUrl = String.format("https://api.rev.ai/revspeech/%s/", version);
    }

    public RevAiAccount getAccount() throws Exception {
        String method = "GET";
        RevAiAccount account = new RevAiAccount("", 0);
        URL accountUrl = new URL(baseUrl + "account");
        HttpURLConnection con = (HttpURLConnection) accountUrl.openConnection();
        apiHandler.setConnection(con);
        JSONObject response = apiHandler.makeApiRequest(method);
        account.from_json(response);
        return account;
    }
}