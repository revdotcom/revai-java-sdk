package revai;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import revai.models.asynchronous.RevAiAccount;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;


public class RevAiAccountTest {
    @InjectMocks
    private HttpUrlConnectionFactory mockedFactory;
    private HttpURLConnection mockedConnection;


    //class to be tested
    private ApiClient validClient;

    private String testUrl;
    private String version = "v1";

    @Before
    public void setup() {
        mockedFactory = mock(HttpUrlConnectionFactory.class);
        mockedConnection = mock(HttpURLConnection.class);
        testUrl = String.format("https://api.rev.ai/revspeech/%s/account", version);
    }

    @Test
    public void AccountValidTest() {
        try {
            //initializes a mocked valid response
            JSONObject sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
            String str = sampleResponse.toString();
            InputStream is = new ByteArrayInputStream(str.getBytes());
            when(mockedConnection.getInputStream()).thenReturn(is);
            when(mockedConnection.getResponseCode()).thenReturn(200);
            when(mockedFactory.createConnection(testUrl)).thenReturn(mockedConnection);

            validClient = new ApiClient("validToken");
            validClient.apiHandler.connectionFactory = mockedFactory;

            RevAiAccount sampleAccount = new RevAiAccount("", 0);
            sampleAccount.from_json(sampleResponse);
            RevAiAccount mockedAccount = validClient.getAccount();

            Assert.assertEquals(sampleAccount, mockedAccount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

}
