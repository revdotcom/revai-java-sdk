package ai.rev.speechtotext.unit;

import ai.rev.speechtotext.ApiInterface;
import ai.rev.speechtotext.CustomVocabulariesClient;
import ai.rev.speechtotext.MockInterceptor;
import ai.rev.speechtotext.models.CustomVocabulary;
import ai.rev.speechtotext.models.CustomVocabularyInformation;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RevAiCustomVocabularyTest {

  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private CustomVocabulariesClient sut;

  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final String VOCABULARY_URL = "https://api.rev.ai/revspeech/v1/vocabularies";
  private final String CALLBACK_URL = "https://test.callback.url";
  private final String DATE = new Date().toString();
  private final String ID = "TEST";
  private Gson gson;

  @Rule public TestName testName = new TestName();

  @Before
  public void setup() {
    gson = new Gson();
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    sut = new CustomVocabulariesClient("validToken");
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
  public void
      submitCustomVocabulary_WhenVocabularyCallbackMetadataAreSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo = new CustomVocabularyInformation();
    mockCustomVocabularyInfo.setCallbackUrl(CALLBACK_URL);
    mockCustomVocabularyInfo.setCreatedOn(DATE);
    mockCustomVocabularyInfo.setId(ID);
    mockCustomVocabularyInfo.setStatus(RevAiJobStatus.IN_PROGRESS);
    mockCustomVocabularyInfo.setMetadata(testName.getMethodName());

    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));

    List<String> phrases = Arrays.asList("test", "custom", "vocabulary");
    CustomVocabulary customVocabulary = new CustomVocabulary(phrases);

    CustomVocabularyInformation customVocabularyInformation =
        sut.submitCustomVocabularies(
            testName.getMethodName(), Collections.singletonList(customVocabulary), CALLBACK_URL);

    assertRequest(VOCABULARY_URL, "POST");
    assertCustomVocabularyInformation(
        customVocabularyInformation,
        RevAiJobStatus.IN_PROGRESS,
        DATE,
        CALLBACK_URL,
        testName.getMethodName(),
        ID);
  }

  @Test
  public void
      submitCustomVocabulary_WhenVocabularyAndMetadataAreSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo = new CustomVocabularyInformation();
    mockCustomVocabularyInfo.setCreatedOn(DATE);
    mockCustomVocabularyInfo.setId(ID);
    mockCustomVocabularyInfo.setStatus(RevAiJobStatus.IN_PROGRESS);
    mockCustomVocabularyInfo.setMetadata(testName.getMethodName());

    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));

    List<String> phrases = Arrays.asList("test", "custom", "vocabulary");
    CustomVocabulary customVocabulary = new CustomVocabulary(phrases);

    CustomVocabularyInformation customVocabularyInformation =
        sut.submitCustomVocabularies(
            testName.getMethodName(), Collections.singletonList(customVocabulary));

    assertRequest(VOCABULARY_URL, "POST");
    assertCustomVocabularyInformation(
        customVocabularyInformation,
        RevAiJobStatus.IN_PROGRESS,
        DATE,
        null,
        testName.getMethodName(),
        ID);
  }

  @Test
  public void
      submitCustomVocabulary_WhenVocabularyAndCallbackAreSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo = new CustomVocabularyInformation();
    mockCustomVocabularyInfo.setCallbackUrl(CALLBACK_URL);
    mockCustomVocabularyInfo.setCreatedOn(DATE);
    mockCustomVocabularyInfo.setId(ID);
    mockCustomVocabularyInfo.setStatus(RevAiJobStatus.IN_PROGRESS);

    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));

    List<String> phrases = Arrays.asList("test", "custom", "vocabulary");
    CustomVocabulary customVocabulary = new CustomVocabulary(phrases);

    CustomVocabularyInformation customVocabularyInformation =
        sut.submitCustomVocabularies(Collections.singletonList(customVocabulary), CALLBACK_URL);

    assertRequest(VOCABULARY_URL, "POST");
    assertCustomVocabularyInformation(
        customVocabularyInformation, RevAiJobStatus.IN_PROGRESS, DATE, CALLBACK_URL, null, ID);
  }

  @Test
  public void submitCustomVocabulary_WhenVocabularyIsSpecified_ReturnsACustomVocabularyInformation()
      throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo = new CustomVocabularyInformation();
    mockCustomVocabularyInfo.setCreatedOn(DATE);
    mockCustomVocabularyInfo.setId(ID);
    mockCustomVocabularyInfo.setStatus(RevAiJobStatus.IN_PROGRESS);

    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));

    List<String> phrases = Arrays.asList("test", "custom", "vocabulary");
    CustomVocabulary customVocabulary = new CustomVocabulary(phrases);

    CustomVocabularyInformation customVocabularyInformation =
        sut.submitCustomVocabularies(Collections.singletonList(customVocabulary));

    assertRequest(VOCABULARY_URL, "POST");
    assertCustomVocabularyInformation(
        customVocabularyInformation, RevAiJobStatus.IN_PROGRESS, DATE, null, null, ID);
  }

  @Test
  public void
      getCustomVocabularyInformation_WhenVocabularyIsSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo = new CustomVocabularyInformation();
    mockCustomVocabularyInfo.setCreatedOn(DATE);
    mockCustomVocabularyInfo.setId(ID);
    mockCustomVocabularyInfo.setStatus(RevAiJobStatus.COMPLETE);

    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));

    CustomVocabularyInformation customVocabularyInformation =
        sut.getCustomVocabularyInformation(ID);

    assertRequest(VOCABULARY_URL + "/" + ID, "GET");
    assertCustomVocabularyInformation(
        customVocabularyInformation, RevAiJobStatus.COMPLETE, DATE, null, null, ID);
  }

  @Test
  public void getCustomVocabularies_WhenCalled_ReturnsAListOfCustomVocabularyInformationObjects()
      throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo1 = new CustomVocabularyInformation();
    mockCustomVocabularyInfo1.setCreatedOn(DATE);
    mockCustomVocabularyInfo1.setId(ID + 1);
    mockCustomVocabularyInfo1.setStatus(RevAiJobStatus.COMPLETE);

    CustomVocabularyInformation mockCustomVocabularyInfo2 = new CustomVocabularyInformation();
    mockCustomVocabularyInfo2.setCreatedOn(DATE);
    mockCustomVocabularyInfo2.setId(ID + 2);
    mockCustomVocabularyInfo2.setStatus(RevAiJobStatus.IN_PROGRESS);
    mockCustomVocabularyInfo2.setMetadata(testName.getMethodName());

    List<CustomVocabularyInformation> suppliedVocabularyInformation =
        Arrays.asList(mockCustomVocabularyInfo1, mockCustomVocabularyInfo2);

    mockInterceptor.setSampleResponse(gson.toJson(suppliedVocabularyInformation));

    List<CustomVocabularyInformation> customVocabularyInformation = sut.getListOfCustomVocabularyInformation();

    assertRequest(VOCABULARY_URL, "GET");
    int numberOfExpectedVocabularies = 2;
    assertThat(customVocabularyInformation.size())
        .as("Number of vocabularies")
        .isEqualTo(numberOfExpectedVocabularies);
    assertCustomVocabularyInformation(
        customVocabularyInformation.get(0), RevAiJobStatus.COMPLETE, DATE, null, null, ID + 1);
    assertCustomVocabularyInformation(
        customVocabularyInformation.get(1),
        RevAiJobStatus.IN_PROGRESS,
        DATE,
        null,
        testName.getMethodName(),
        ID + 2);
  }

  private void assertCustomVocabularyInformation(
      CustomVocabularyInformation customVocabularyInformation,
      RevAiJobStatus status,
      String createdOn,
      String callbackUrl,
      String metadata,
      String id) {
    assertThat(customVocabularyInformation.getStatus()).as("Status").isEqualTo(status);
    assertThat(customVocabularyInformation.getCreatedOn()).as("Created on").isEqualTo(createdOn);

    assertThat(customVocabularyInformation.getCallbackUrl())
        .as("Callback url")
        .isEqualTo(callbackUrl);

    assertThat(customVocabularyInformation.getId()).as("Custom vocabulary Id").isEqualTo(id);
    assertThat(customVocabularyInformation.getMetadata()).as("Metadata").isEqualTo(metadata);
  }

  private void assertRequest(String url, String requestMethod) {
    assertThat(mockInterceptor.request.method()).as("Request method").isEqualTo(requestMethod);
    assertThat(mockInterceptor.request.url().toString()).as("Request url").isEqualTo(url);
  }
}
