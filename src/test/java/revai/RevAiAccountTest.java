package revai;

import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import revai.models.asynchronous.RevAiAccount;

import java.io.IOException;


public class RevAiAccountTest {
    @InjectMocks
    private OkHttpClient httpClient;
    private MockInterceptor mockInterceptor;


    //class to be tested
    private ApiClient sut;

    private String testUrl;
    private String version = "v1";
    private JSONObject sampleResponse;

    @Before
    public void setup() throws IOException, XmlPullParserException {
        sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
        mockInterceptor = new MockInterceptor(sampleResponse);
        testUrl = String.format("https://api.rev.ai/revspeech/%s/account", version);
        //mocks valid response
        sut = new ApiClient("validToken");

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(mockInterceptor)
                .build();

        Retrofit mockRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/").addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        sut.apiService = mockRetrofit.create(ApiService.class);
    }

    @Test
    public void AccountValidTest() throws Exception {

        RevAiAccount sampleAccount = new RevAiAccount("", 0);
        sampleAccount.from_json(sampleResponse);
        RevAiAccount mockAccount = sut.getAccount();

        Assert.assertEquals(sampleAccount, mockAccount);
    }

}
