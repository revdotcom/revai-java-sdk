package revai;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.Exception;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.HttpURLConnection;


public class ApiRequestTest {

    @InjectMocks
    private HttpURLConnection validCon;
    private HttpURLConnection invalidCon;
    private HttpUrlConnectionFactory mockedFactory;

    // class to be tested
    private ApiRequestHandler requestHandler;
    private String testUrl;
    private String version = "v1";


    @Before
    public void setup() {
        validCon = mock(HttpURLConnection.class);
        invalidCon = mock(HttpURLConnection.class);
        mockedFactory = mock(HttpUrlConnectionFactory.class);
        testUrl = String.format("https://api.rev.ai/revspeech/%s/account", version);
    }

    @Test
    public void ValidRequestTest() {
        try {
            //initializes a mocked valid response
            JSONObject sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
            String str = sampleResponse.toString();
            InputStream is = new ByteArrayInputStream(str.getBytes());
            //injects mocked services
            when(validCon.getInputStream()).thenReturn(is);
            when(mockedFactory.createConnection(testUrl)).thenReturn(validCon);
            when(validCon.getResponseCode()).thenReturn(200);
            //initializes the api request using the mocked connection factory
            requestHandler = new ApiRequestHandler("validToken");
            requestHandler.connectionFactory = mockedFactory;
            JSONObject mockedResponse = requestHandler.makeApiRequest("GET", testUrl);

            Assert.assertEquals(sampleResponse.get("email"), mockedResponse.get("email"));
            Assert.assertEquals(sampleResponse.get("balance_seconds"), mockedResponse.get("balance_seconds"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void InvalidRequestTest() {
        try {
            when(invalidCon.getInputStream()).thenThrow(new RuntimeException("exception testing"));
            when(mockedFactory.createConnection(testUrl)).thenReturn(invalidCon);
            requestHandler = new ApiRequestHandler("invalidToken");
            requestHandler.connectionFactory = mockedFactory;
            requestHandler.makeApiRequest("GET", testUrl);
            Assert.fail("exception expected");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Rev.AI API error");
        }
    }

}
