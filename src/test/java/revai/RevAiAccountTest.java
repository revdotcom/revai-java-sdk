package revai;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.mockito.*;
import revai.models.asynchronous.RevAiAccount;

import static org.mockito.Mockito.*;
//import java.net.URL;


public class RevAiAccountTest {
    @InjectMocks
    private ApiRequest requestHandler;

    //class to be tested
    private ApiClient validClient;

    private String testUrl;
    private String version;

    @Before
    public void setup(){
        requestHandler = mock(ApiRequest.class);
        version = "v1";
        testUrl  = String.format("https://api.rev.ai/revspeech/%s/account", version);
    }

    @Test
    public void AccountValidTest(){
        try {
            //initializes a mocked valid response
            JSONObject sampleResponse = new JSONObject("{email: example.com, balance_seconds: 10 }");
            RevAiAccount sampleAccount = new RevAiAccount("0", 0);
            sampleAccount.from_json(sampleResponse);
            Mockito.when(requestHandler.makeApiRequest("GET")).thenReturn(sampleResponse);

            validClient = new ApiClient(requestHandler, version);

            Assert.assertEquals(sampleAccount, validClient.getAccount());
        }catch(Exception e){
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

}
