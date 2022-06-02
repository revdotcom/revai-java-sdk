package ai.rev.languageid.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.languageid.LanguageIdClient;
import ai.rev.languageid.LanguageIdInterface;
import ai.rev.languageid.models.LanguageIdResult;
import ai.rev.testutils.AssertHelper;
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

public class LanguageIdResultTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private LanguageIdClient sut;

  private Gson gson = new Gson();
  private LanguageIdResult SAMPLE_RESULT = new LanguageIdResult();
  private String JOB_ID = "testingID";
  private final String RESULT_URL =
          "https://api.rev.ai/languageid/v1/jobs/" + JOB_ID + "/result";
  private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

  @Before
  public void setup() {
    sut = new LanguageIdClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
            new Retrofit.Builder()
                    .baseUrl("https://api.rev.ai/languageid/v1/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mockOkHttpClient)
                    .build();
    sut.apiInterface = mockRetrofit.create(LanguageIdInterface.class);
  }

  @Test
  public void GetResultObject_JobIdIsValid_ReturnsResultObject() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(SAMPLE_RESULT));
    this.mockInterceptor.setSampleResponse(gson.toJson(SAMPLE_RESULT));

    LanguageIdResult languageIdResult = sut.getResultObject(JOB_ID);

    Headers headers = mockInterceptor.request.headers();
    assertThat(headers.get("Accept")).isEqualTo("application/vnd.rev.languageid.v1.0+json");
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", RESULT_URL);
    assertThat(gson.toJson(SAMPLE_RESULT))
            .as("LanguageIdResult")
            .isEqualTo(gson.toJson(languageIdResult));
  }
}
