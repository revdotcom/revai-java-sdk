package revai;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnectionFactory {

    public HttpUrlConnectionFactory() {
    }

    public HttpURLConnection createConnection(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        return (HttpURLConnection) url.openConnection();
    }
}
