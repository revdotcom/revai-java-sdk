package revai;

import static org.mockito.Mockito.mock;

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

    // class to be tested
    private ApiRequest requestHandler;


    @Before
    public void setup() {
        validCon = mock(HttpURLConnection.class);
        invalidCon = mock(HttpURLConnection.class);
    }

//    @Test
//    public void ValidRequestTest() {
//
//        try {
//            //initializes a mocked valid response
//            JSONObject sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
//            String str = sampleResponse.toString();
//            InputStream is = new ByteArrayInputStream(str.getBytes());
//            Mockito.when(validCon.getInputStream()).thenReturn(is);
//            requestHandler = new ApiRequest("validToken");
//            requestHandler.setConnection(validCon);
//
//            JSONObject mockedResponse = requestHandler.makeApiRequest("GET");
//            Assert.assertEquals(sampleResponse.get("email"), mockedResponse.get("email"));
//            Assert.assertEquals(sampleResponse.get("balance_seconds"), mockedResponse.get("balance_seconds"));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            Assert.fail();
//        }
//
//    }
//
//    @Test
//    public void InvalidRequestTest() {
//
//        try {
//            Mockito.when(invalidCon.getInputStream()).thenThrow(new RuntimeException());
//            requestHandler = new ApiRequest("invalidToken");
//            requestHandler.setConnection(invalidCon);
//            requestHandler.makeApiRequest("GET");
//            Assert.fail("exception expected");
//        } catch (Exception e) {
//            Assert.assertEquals(e.getMessage(), "cannot retrieve account information");
//        }
//
//    }


}
