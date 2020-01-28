package revai;

import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

public class RevAiTranscriptTest {
  @InjectMocks
  private OkHttpClient mockClient;
  private MockInterceptor mockInterceptor;

  // class to be tested
  private ApiClient sut;

  private String SAMPLE_TEXT = "sample text";
  private String SAMPLE_ID = "sample id";

  @Before
  public void setup() throws IOException, XmlPullParserException {
    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(SAMPLE_TEXT);
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
}
