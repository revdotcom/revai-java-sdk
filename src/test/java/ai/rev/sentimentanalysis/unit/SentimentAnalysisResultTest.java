package ai.rev.sentimentanalysis.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.ApiInterface;
import ai.rev.speechtotext.models.asynchronous.Element;
import ai.rev.speechtotext.models.asynchronous.Monologue;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import ai.rev.testutils.AssertHelper;
import ai.rev.sentimentanalysis.SentimentAnalysisClient;
import ai.rev.sentimentanalysis.SentimentAnalysisInterface;
import ai.rev.sentimentanalysis.models.SentimentAnalysisResult;
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

public class SentimentAnalysisResultTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private SentimentAnalysisClient sut;

  private Gson gson = new Gson();
  private SentimentAnalysisResult SAMPLE_RESULT = new SentimentAnalysisResult();
  private String JOB_ID = "testingID";
  private final String RESULT_URL =
      "https://api.rev.ai/sentiment_analysis/v1/jobs/" + JOB_ID + "/result";
  private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

  @Before
  public void setup() {
    sut = new SentimentAnalysisClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/sentiment_analysis/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();
    sut.apiInterface = mockRetrofit.create(SentimentAnalysisInterface.class);
  }

  @Test
  public void GetTranscriptObject_JobIdIsValid_ReturnsResultObject() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(SAMPLE_RESULT));
    this.mockInterceptor.setSampleResponse(gson.toJson(SAMPLE_RESULT));

    SentimentAnalysisResult topicResult = sut.getResultObject(JOB_ID);

    Headers headers = mockInterceptor.request.headers();
    assertThat(headers.get("Accept")).isEqualTo("application/vnd.rev.sentiment.v1.0+json");
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", RESULT_URL);
    assertThat(gson.toJson(SAMPLE_RESULT))
        .as("SentimentAnalysisResult")
        .isEqualTo(gson.toJson(topicResult));
  }
}
