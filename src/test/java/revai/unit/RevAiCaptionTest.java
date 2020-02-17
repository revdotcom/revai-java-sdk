package revai.unit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.ApiClient;
import revai.ApiInterface;
import revai.MockInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;
import static revai.models.asynchronous.RevAiCaptionType.SRT;
import static revai.models.asynchronous.RevAiCaptionType.VTT;

public class RevAiCaptionTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient mockApiClient;

  private final String SRT_CAPTION =
      "1\n" + "00:00:01,680 --> 00:00:05,760\n" + "Testing SRT\n";
  private final String VTT_CAPTION =
      "WEBVTT\n" + "\n" + "1\n" + "00:00:00.720 --> 00:00:02.250\n" + "Testing VTT";
  private final String JOB_ID = "testingID";
  private String CAPTIONS_URL = "https://api.rev.ai/revspeech/v1/jobs/" + JOB_ID + "/captions";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final Integer SPEAKER_CHANNEL = 1;

  @Before
  public void setup() {
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
    mockInterceptor.setSampleResponse(SRT_CAPTION);
    InputStream responseStream = mockApiClient.getCaptions(JOB_ID);

    String responseString = convertInputStreamToString(responseStream);
    Headers headers = mockInterceptor.request.headers();

    assertThat(headers.get("Accept")).isEqualTo(SRT.getContentType());
    assertThat(responseString).isEqualTo(SRT_CAPTION);
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(CAPTIONS_URL);
  }

  @Test
  public void getCaptionTypeTest() throws IOException {
    mockInterceptor.setSampleResponse(VTT_CAPTION);
    InputStream responseStream = mockApiClient.getCaptions(JOB_ID, VTT);

    String responseString = convertInputStreamToString(responseStream);
    Headers headers = mockInterceptor.request.headers();

    assertThat(headers.get("Accept")).isEqualTo(VTT.getContentType());
    assertThat(responseString).isEqualTo(VTT_CAPTION);
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(CAPTIONS_URL);
  }

  @Test
  public void getCaptionSpeakerChannelTest() throws IOException {
    mockInterceptor.setSampleResponse(SRT_CAPTION);
    InputStream responseStream = mockApiClient.getCaptions(JOB_ID, SPEAKER_CHANNEL);

    String responseString = convertInputStreamToString(responseStream);
    Headers headers = mockInterceptor.request.headers();
    String speakerChannel = mockInterceptor.request.url().queryParameter("speaker_channel");

    assertThat(speakerChannel).isEqualTo(SPEAKER_CHANNEL.toString());
    assertThat(headers.get("Accept")).isEqualTo(SRT.getContentType());
    assertThat(responseString).isEqualTo(SRT_CAPTION);

    String finalUrl = CAPTIONS_URL + "?speaker_channel=" + SPEAKER_CHANNEL;
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(finalUrl);
  }

  private String convertInputStreamToString(InputStream inputStream) throws IOException {
    StringBuilder builder = new StringBuilder();
    try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      int c;
      while ((c = reader.read()) != -1) {
        builder.append((char) c);
      }
    }
    return builder.toString();
  }
}
