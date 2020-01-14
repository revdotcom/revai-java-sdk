package revai;

import java.io.*;
import java.net.HttpURLConnection;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import org.json.JSONObject;


public class ApiRequest {
    private String accessToken;
    public HttpUrlConnectionFactory connectionFactory;
    public String sdkVersion;

    public ApiRequest(String AccessToken) throws IOException, XmlPullParserException {
        accessToken = AccessToken;
        connectionFactory = new HttpUrlConnectionFactory();
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        sdkVersion = model.getVersion();
    }

    public JSONObject makeApiRequest(String method, String requestUrl) throws Exception {
        try {
            //initializes HTTP connection and request parameters
            HttpURLConnection con = connectionFactory.createConnection(requestUrl);
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
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            JSONObject jsonResponse = new JSONObject(responseStrBuilder.toString());

            con.disconnect();
            return jsonResponse;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("cannot retrieve account information");
        }
    }
}

