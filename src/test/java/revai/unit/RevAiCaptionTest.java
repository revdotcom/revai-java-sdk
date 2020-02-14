package revai.unit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.ApiClient;
import revai.ApiInterface;
import revai.MockInterceptor;

import java.io.IOException;
import java.io.InputStream;

import static revai.models.asynchronous.RevAiCaptionType.*;

public class RevAiCaptionTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient mockApiClient;

  private final String SAMPLE_CAPTION = "sample caption";
  private final String SAMPLE_ID = "sample id";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final Integer SAMPLE_SPEAKER_CHANNEL = 1;

  @Before
  public void setup() throws IOException, XmlPullParserException {

    mockApiClient = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
          .baseUrl("https://api.rev.ai/revspeech/v1/")
          .addConverterFactory(ScalarsConverterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())

          .client(mockOkHttpClient)
          .build();
    mockApiClient.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void getDefaultCaptionTest() throws IOException {

    mockInterceptor.setSampleResponse(SAMPLE_CAPTION);
    InputStream mockResponse = mockApiClient.getCaptions(SAMPLE_ID, null, null);

    Assert.assertEquals(mockResponse, SAMPLE_CAPTION);
  }

  @Test
  public void getCaptionTypeTest() throws IOException {
    InputStream mockResponse = mockApiClient.getCaptions(SAMPLE_ID, VTT, null);

    Headers headers = mockInterceptor.request.headers();
    Assert.assertEquals(headers.get("Accept"), VTT.getContentType());
    Assert.assertEquals(mockResponse, SAMPLE_CAPTION);
  }

  @Test
  public void getCaptionSpeakerChannelTest() throws IOException {
    mockApiClient.getCaptions(SAMPLE_ID, null, SAMPLE_SPEAKER_CHANNEL);

    String speakerChannel = mockInterceptor.request.url().queryParameter("speaker_channel");
    Assert.assertEquals(speakerChannel, SAMPLE_SPEAKER_CHANNEL.toString());
  }
}
