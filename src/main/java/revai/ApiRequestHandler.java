package revai;

import java.io.*;
import java.net.HttpURLConnection;
import java.security.InvalidParameterException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import org.json.JSONObject;
import revai.exceptions.AuthorizationException;
import revai.exceptions.RevAiApiException;


public class ApiRequestHandler {
    private String accessToken;
    public HttpUrlConnectionFactory connectionFactory;
    public String sdkVersion;
    public final int TIMEOUT_INTERVAL = 5000;

    public ApiRequestHandler(String AccessToken) throws IOException, XmlPullParserException {
        accessToken = AccessToken;
        connectionFactory = new HttpUrlConnectionFactory();
        //reads the current sdk version from pom.xml
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        sdkVersion = model.getVersion();
    }

    public JSONObject parseInputStream(InputStream responseStream) throws Exception {
        if (responseStream == null) {
            return new JSONObject("{}");
        }
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        JSONObject jsonResponse = new JSONObject(responseStrBuilder.toString());
        return jsonResponse;
    }


    public JSONObject makeApiRequest(String method, String requestUrl) throws Exception {
        //initializes HTTP connection and request parameters
        HttpURLConnection con = connectionFactory.createConnection(requestUrl);
        con.setRequestMethod(method);
        con.setRequestProperty("Authorization", String.format("Bearer %s", accessToken));
        con.setRequestProperty("User-Agent", String.format("RevAi-JavaSDK/%s", sdkVersion));
        con.setConnectTimeout(TIMEOUT_INTERVAL);
        con.setReadTimeout(TIMEOUT_INTERVAL);
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            InputStream responseStream = con.getInputStream();
            return parseInputStream(responseStream);
        } else {
            InputStream errorStream = con.getErrorStream();
            JSONObject errorResponse = parseInputStream(errorStream);

            switch (responseCode) {
                case 401:
                    throw new AuthorizationException(errorResponse, responseCode);
                default:
                    throw new RevAiApiException(errorResponse, responseCode);
            }
        }
    }

}

