package revai.unit;

import com.google.gson.Gson;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.ApiClient;
import revai.ApiInterface;
import revai.MockInterceptor;
import revai.models.asynchronous.Element;
import revai.models.asynchronous.Monologue;
import revai.models.asynchronous.RevAiTranscript;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class RevAiTranscriptTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient mockApiClient;

  private Gson gson = new Gson();
  private String SAMPLE_TEXT = "sample text";
  private String JOB_ID = "testingID";
  private final String TRANSCRIPT_URL =
      "https://api.rev.ai/revspeech/v1/jobs/" + JOB_ID + "/transcript";
  private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

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
  public void getTranscriptTextTest() throws IOException {
    mockInterceptor.setSampleResponse(SAMPLE_TEXT);
    String mockResponse = mockApiClient.getTranscriptText(JOB_ID);

    Headers headers = mockInterceptor.request.headers();
    assertThat(headers.get("Accept")).isEqualTo("text/plain");
    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(TRANSCRIPT_URL);
    assertThat(mockResponse).isEqualTo(SAMPLE_TEXT);
  }

  @Test
  public void getTranscriptObjectTest() throws IOException {
    mockInterceptor.setSampleResponse(SAMPLE_TEXT);
    Element element = new Element();
    element.setType("text");
    element.setValue("What");
    element.setStartTimestamp(0.58);
    element.setEndTimestamp(0.76);
    element.setConfidence(0.93);

    Monologue monologue = new Monologue();
    monologue.setElements(Arrays.asList(element));
    monologue.setSpeaker(0);

    RevAiTranscript mockTranscript = new RevAiTranscript();
    mockTranscript.setMonologues(Arrays.asList(monologue));

    this.mockInterceptor.setSampleResponse(gson.toJson(mockTranscript));

    RevAiTranscript revAiTranscript = mockApiClient.getTranscriptObject(JOB_ID);

    Headers headers = mockInterceptor.request.headers();
    assertThat(headers.get("Accept")).isEqualTo("application/vnd.rev.transcript.v1.0+json");
    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(TRANSCRIPT_URL);
    assertThat(gson.toJson(mockTranscript)).isEqualTo(gson.toJson(revAiTranscript));
  }
}
