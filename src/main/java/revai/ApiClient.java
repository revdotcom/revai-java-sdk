package revai;

import revai.models.asynchronous.*;
import java.net.URL;
import org.json.JSONObject;

public class ApiClient {
    public String accessToken;
    public ApiRequest apiHandler;
    public String baseUrl;

    public ApiClient(String accessToken, String version) {
        apiHandler = new ApiRequest(accessToken);
        baseUrl = String.format("https://api.rev.ai/revspeech/%s/", version);
    }

    public RevAiAccount getAccount() throws Exception {
        String method = "GET";
        RevAiAccount account = new RevAiAccount("", 0);
        URL accountUrl = new URL(baseUrl + "account");
        JSONObject response = apiHandler.makeApiRequest(method, accountUrl);
        account.from_json(response);
        return account;
    }
}