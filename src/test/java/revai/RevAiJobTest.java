package revai;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import revai.models.asynchronous.RevAiJob;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RevAiJobTest {
  @InjectMocks
  private OkHttpClient httpClient;
  private MockInterceptor mockInterceptor;

  // class to be tested
  private ApiClient sut;

  private JSONObject sampleResponse;
  private Gson gson;
  private String JOB_ID = "testingID";
  private String STATUS = "transcribed";
  private String CREATED_ON = "2020-01-22T11:10:22.29Z";


  @Before
  public void setup() throws IOException, XmlPullParserException {
    gson = new Gson();

    Map<String, String> sampleJobA = new HashMap<String, String>();
    sampleJobA.put("id", JOB_ID);
    sampleJobA.put("status", STATUS);
    sampleJobA.put("created_on", CREATED_ON);
    sampleResponse = new JSONObject(sampleJobA);
    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(sampleResponse);
    httpClient = new OkHttpClient.Builder()
              .addInterceptor(mockInterceptor)
              .build();
    Retrofit mockRetrofit = new Retrofit.Builder()
              .baseUrl("https://api.rev.ai/revspeech/v1/")
              .addConverterFactory(GsonConverterFactory.create())
              .client(httpClient)
              .build();
    sut.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void getJobDetailsTest () throws IOException {
      JSONObject mockResponse = new JSONObject(gson.toJson(sut.getJobDetails(JOB_ID)));
      Assert.assertTrue(sampleResponse.similar(mockResponse));

    }
}

