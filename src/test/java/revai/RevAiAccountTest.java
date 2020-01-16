package revai;

import okhttp3.OkHttpClient;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import revai.models.asynchronous.RevAiAccount;

import static org.mockito.Mockito.mock;


public class RevAiAccountTest {
    @InjectMocks
    private OkHttpClient mockHttpClient;
    private MockInterceptor mockInterceptor;


    //class to be tested
    private ApiClient apiClient;

    private String testUrl;
    private String version = "v1";

    @Before
    public void setup() {
        mockHttpClient = mock(OkHttpClient.class);
        mockInterceptor = new MockInterceptor();
        testUrl = String.format("https://api.rev.ai/revspeech/%s/account", version);
    }

    @Test
    public void AccountValidTest() throws Exception {

        //mocks valid response
        JSONObject sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
        apiClient = new ApiClient("validToken");

        mockHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mockInterceptor)
                .build();

        Retrofit mockRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/").addConverterFactory(GsonConverterFactory.create())
                .client(mockHttpClient)
                .build();

        apiClient.apiService = mockRetrofit.create(ApiClient.ApiService.class);

        RevAiAccount sampleAccount = new RevAiAccount("", 0);
        sampleAccount.from_json(sampleResponse);
        RevAiAccount mockAccount = apiClient.getAccount();

        Assert.assertEquals(sampleAccount, mockAccount);
    }

}
