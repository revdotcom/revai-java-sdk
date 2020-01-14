package revai;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import revai.models.asynchronous.*;

import java.io.IOException;

import org.json.JSONObject;

public class ApiClient {
    //    public String accessToken;
    private String accessToken;
    private String baseUrl;
    private String version = "v1";
    public ApiRequestHandler apiHandler;

    public ApiClient(String AccessToken) throws IOException, XmlPullParserException {
        accessToken = AccessToken;
        baseUrl = String.format("https://api.rev.ai/revspeech/%s/", version);
        apiHandler = new ApiRequestHandler(accessToken);
    }

    public RevAiAccount getAccount() throws Exception {
        String method = "GET";
        RevAiAccount account = new RevAiAccount("", 0);
        String accountUrl = baseUrl + "account";
        JSONObject response = apiHandler.makeApiRequest(method, accountUrl);
        account.from_json(response);
        return account;
    }
}