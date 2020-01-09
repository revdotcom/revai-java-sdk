package src;

import java.net.URL;
import org.json.JSONObject;
import src.models.asynchronous.RevAiAccount;

public class ApiClient {
    public String accessToken;
    public ApiRequestHandler apiHandler;
    public String baseUrl;

    public ApiClient(String accessToken, String version) {
        apiHandler = new ApiRequestHandler(accessToken);
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