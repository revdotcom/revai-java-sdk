package revai;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class ApiRequest{
    public String accessToken;
    public String sdkVersion;
    public HttpURLConnection con;

    public ApiRequest(String AccessToken){
        accessToken = AccessToken;
        sdkVersion = "1.0.0";
    }

    public void setConnection(HttpURLConnection connection){
        con = connection;
    }


    public JSONObject makeApiRequest(String method) throws Exception {
        try {
            //initializes HTTP connection and request parameters
            con.setRequestMethod(method);
            con.setRequestProperty("Authorization", String.format("Bearer %s", accessToken));
            con.setRequestProperty("User-Agent", String.format("RevAi-JavaSDK/%s", sdkVersion));
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            //parses input into a string in JSON format
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
            con.disconnect();
            return jsonResponse;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("cannot retrieve account information");
        }
    }
}

