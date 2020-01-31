package revai;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.models.asynchronous.RevAiTranscript;

import java.io.IOException;

public class RevAiTranscriptTest {
  @InjectMocks private OkHttpClient mockClient;
  private MockInterceptor mockInterceptor;

  // class to be tested
  private ApiClient sut;

  private Gson gson = new Gson();
  private String SAMPLE_TEXT = "sample text";
  private String SAMPLE_ID = "sample id";
  private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

  @Before
  public void setup() throws IOException, XmlPullParserException {
    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(SAMPLE_TEXT, MEDIA_TYPE, 200);
    mockClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/revspeech/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockClient)
            .build();
    sut.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void getTranscriptTextTest() throws IOException {
    String mockResponse = sut.getTranscriptText(SAMPLE_ID);
    
    Assert.assertEquals(mockResponse, SAMPLE_TEXT);
  }

  @Test
  public void getTranscriptObjectTest() throws IOException {
    String sampleTranscript =
        "{\"monologues\":[{\"speaker\":0,\"elements\":[{\"ts\":\"0.58\",\"end_ts\":\"0.76\",\"type\":\"text\",\"value\":\"What\",\"confidence\":0.93}]}]}";
    JSONObject sampleTranscriptJSON = new JSONObject(sampleTranscript);
    this.mockInterceptor.setSampleResponse(sampleTranscript);

    RevAiTranscript mockTranscript = sut.getTranscriptObject(SAMPLE_ID);

    JSONObject mockTranscriptJSON = new JSONObject(gson.toJson(mockTranscript));
    Assert.assertTrue(sampleTranscriptJSON.similar(mockTranscriptJSON));
  }
}
