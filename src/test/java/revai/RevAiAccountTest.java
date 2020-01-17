package revai;

import com.google.gson.Gson;
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

    private JSONObject sampleResponse;
    private Gson gson;

    @Before
    public void setup() throws IOException, XmlPullParserException {
        gson = new Gson();
        sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
        mockInterceptor = new MockInterceptor(sampleResponse);
        sut = new ApiClient("validToken");
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(mockInterceptor)
                .build();
        Retrofit mockRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/").addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        sut.apiInterface = mockRetrofit.create(ApiInterface.class);
    }

    @Test
    public void AccountValidTest() throws Exception {
        RevAiAccount sampleAccount = gson.fromJson(String.valueOf(sampleResponse), RevAiAccount.class);
        RevAiAccount mockAccount = sut.getAccount();
        Assert.assertEquals(sampleAccount, mockAccount);
    }
}
