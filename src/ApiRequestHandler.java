package src;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;



public class ApiRequestHandler {
    public String accessToken;

    public ApiRequestHandler(String AccessToken){
        accessToken = AccessToken;
    }

    public JSONObject makeApiRequest(String method,  URL url) throws Exception {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Authorization", String.format("Bearer %s", accessToken));
            // con.setRequestProperty("User-Agent", String.format("RevAi-JavaSDK/%s", sdkVersion));
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            InputStream responseStream = con.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null){
                responseStrBuilder.append(inputStr);
            }
            JSONObject jsonResponse = new JSONObject(responseStrBuilder.toString());
            
            // System.out.println("Account info:");
            // System.out.println(jsonResponse.toString());
            return jsonResponse;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("cannot retrieve account information");
        }
    }
}

