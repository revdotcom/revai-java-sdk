package ai.rev.topicextraction.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.ApiInterface;
import ai.rev.speechtotext.models.asynchronous.Element;
import ai.rev.speechtotext.models.asynchronous.Monologue;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import ai.rev.testutils.AssertHelper;
import ai.rev.topicextraction.TopicExtractionClient;
import ai.rev.topicextraction.TopicExtractionInterface;
import ai.rev.topicextraction.models.TopicExtractionResult;
import com.google.gson.Gson;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicExtractionResultTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private TopicExtractionClient sut;

  private Gson gson = new Gson();
  private TopicExtractionResult SAMPLE_RESULT = new TopicExtractionResult();
  private String JOB_ID = "testingID";
  private final String RESULT_URL =
      "https://api.rev.ai/topic_extraction/v1beta/jobs/" + JOB_ID + "/result";
  private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

  @Before
  public void setup() {
    sut = new TopicExtractionClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/topic_extraction/v1beta/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();
    sut.apiInterface = mockRetrofit.create(TopicExtractionInterface.class);
  }

  @Test
  public void GetTranscriptObject_JobIdIsValid_ReturnsResultObject() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(SAMPLE_RESULT));
    this.mockInterceptor.setSampleResponse(gson.toJson(SAMPLE_RESULT));

    TopicExtractionResult topicResult = sut.getResultObject(JOB_ID);

    Headers headers = mockInterceptor.request.headers();
    assertThat(headers.get("Accept")).isEqualTo("application/vnd.rev.topic.v1.0+json");
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", RESULT_URL);
    assertThat(gson.toJson(SAMPLE_RESULT))
        .as("TopicExtractionResult")
        .isEqualTo(gson.toJson(topicResult));
  }
}
