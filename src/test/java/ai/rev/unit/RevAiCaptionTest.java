package ai.rev.unit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ai.rev.ApiClient;
import ai.rev.ApiInterface;
import ai.rev.MockInterceptor;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ai.rev.models.asynchronous.RevAiCaptionType.SRT;
import static ai.rev.models.asynchronous.RevAiCaptionType.VTT;
import static ai.rev.testutils.ConversionUtil.*;

public class RevAiCaptionTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient sut;

  private final String SRT_CAPTION = "1\n" + "00:00:01,680 --> 00:00:05,760\n" + "Testing SRT\n";
  private final String VTT_CAPTION =
      "WEBVTT\n" + "\n" + "1\n" + "00:00:00.720 --> 00:00:02.250\n" + "Testing VTT";
  private final String JOB_ID = "testingID";
  private String CAPTIONS_URL = "https://api.rev.ai/revspeech/v1/jobs/" + JOB_ID + "/captions";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final Integer SPEAKER_CHANNEL = 1;

  @Before
  public void setup() {
    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();

    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/revspeech/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();
    sut.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void GetCaptions_WhenOnlyJobId_ReturnsSrtFormat() throws IOException {
    mockInterceptor.setSampleResponse(SRT_CAPTION);
    InputStream responseStream = sut.getCaptions(JOB_ID,null, null);

    String responseString = convertInputStreamToString(responseStream);
    Headers headers = mockInterceptor.request.headers();

    assertThat(headers.get("Accept")).isEqualTo(SRT.getContentType());
    assertThat(responseString).isEqualTo(SRT_CAPTION);
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(CAPTIONS_URL);
  }

  @Test
  public void GetCaptions_WhenVttIsSpecified_ReturnsVttFormat() throws IOException {
    mockInterceptor.setSampleResponse(VTT_CAPTION);
    InputStream responseStream = sut.getCaptions(JOB_ID, VTT, null);

    String responseString = convertInputStreamToString(responseStream);
    Headers headers = mockInterceptor.request.headers();

    assertThat(headers.get("Accept")).isEqualTo(VTT.getContentType());
    assertThat(responseString).isEqualTo(VTT_CAPTION);
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(CAPTIONS_URL);
  }

  @Test
  public void GetCaptions_WhenSpeakerChannelIsSpecified_ReturnsCaptionStream() throws IOException {
    mockInterceptor.setSampleResponse(SRT_CAPTION);
    InputStream responseStream = sut.getCaptions(JOB_ID, null, SPEAKER_CHANNEL);

    String responseString = convertInputStreamToString(responseStream);
    Headers headers = mockInterceptor.request.headers();
    String speakerChannel = mockInterceptor.request.url().queryParameter("speaker_channel");

    assertThat(speakerChannel).isEqualTo(SPEAKER_CHANNEL.toString());
    assertThat(headers.get("Accept")).isEqualTo(SRT.getContentType());
    assertThat(responseString).isEqualTo(SRT_CAPTION);

    String finalUrl = CAPTIONS_URL + "?speaker_channel=" + SPEAKER_CHANNEL;
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(finalUrl);
  }

  @Test
  public void GetCaptions_WhenJobIdIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.getCaptions(null));
  }
}
