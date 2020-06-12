package ai.rev.speechtotext.unit;

import ai.rev.speechtotext.ApiInterface;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.MockInterceptor;
import ai.rev.speechtotext.models.asynchronous.RevAiAccount;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class RevAiAccountTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient sut;

  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final String ACCOUNT_URL = "https://api.rev.ai/revspeech/v1/account";
  private Gson gson;

  @Before
  public void setup() {
    gson = new Gson();
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    sut = new ApiClient("validToken");
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/revspeech/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();
    sut.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void GetAccount_TokenIsValid_ReturnsRevAiAccount() throws Exception {
    RevAiAccount mockAccount = new RevAiAccount();
    mockAccount.setBalanceSeconds(10);
    mockAccount.setEmail("example.com");
    mockInterceptor.setSampleResponse(gson.toJson(mockAccount));

    RevAiAccount revAiAccount = sut.getAccount();

    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", ACCOUNT_URL);
    assertThat(revAiAccount.getBalanceSeconds()).isEqualTo(mockAccount.getBalanceSeconds());
    assertThat(revAiAccount.getBalanceSeconds()).isEqualTo(mockAccount.getBalanceSeconds());
  }
}
