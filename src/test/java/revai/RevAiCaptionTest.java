package revai;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.models.asynchronous.RevAiCaptionType;

import java.io.IOException;

public class RevAiCaptionTest {
  @InjectMocks
  private OkHttpClient mockClient;
  private MockInterceptor mockInterceptor;

  // class to be tested
  private ApiClient sut;

  private final String SAMPLE_CAPTION = "sample caption";
  private final String SAMPLE_ID = "sample id";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final RevAiCaptionType SAMPLE_CAPTION_TYPE = RevAiCaptionType.VTT;
  private final Integer SAMPLE_SPEAKER_CHANNEL = 1;

  @Before
  public void setup() throws IOException, XmlPullParserException {
    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(SAMPLE_CAPTION, MEDIA_TYPE, 200);
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
  public void getDefaultCaptionTest() throws IOException {
    String mockResponse = sut.getCaptionText(SAMPLE_ID, null, null);

    Assert.assertEquals(mockResponse, SAMPLE_CAPTION);
  }

  @Test
  public void getCaptionTypeTest() throws IOException {
    String mockResponse = sut.getCaptionText(SAMPLE_ID, SAMPLE_CAPTION_TYPE, null);

    Headers headers = mockInterceptor.request.headers();
    Assert.assertEquals(headers.get("Accept"), SAMPLE_CAPTION_TYPE.getContentType());
    Assert.assertEquals(mockResponse, SAMPLE_CAPTION);
  }

  @Test
  public void getCaptionSpeakerChannelTest() throws IOException {
    sut.getCaptionText(SAMPLE_ID, null, SAMPLE_SPEAKER_CHANNEL);

    String speakerChannel = mockInterceptor.request.url().queryParameter("speaker_channel");
    Assert.assertEquals(speakerChannel, SAMPLE_SPEAKER_CHANNEL.toString());
  }
}
