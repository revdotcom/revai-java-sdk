package ai.rev.speechtotext.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.speechtotext.CustomVocabulariesClient;
import ai.rev.speechtotext.CustomVocabularyApiInterface;
import ai.rev.speechtotext.models.vocabulary.CustomVocabulary;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyInformation;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularySubmission;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyStatus;
import ai.rev.testutils.AssertHelper;
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

import static ai.rev.speechtotext.models.vocabulary.CustomVocabularyStatus.COMPLETE;
import static ai.rev.speechtotext.models.vocabulary.CustomVocabularyStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;

public class RevAiCustomVocabularyTest {

  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private CustomVocabulariesClient sut;

  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final String VOCABULARY_URL = "https://api.rev.ai/revspeech/v1/vocabularies";
  private final String CALLBACK_URL = "https://www.example.com";
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
    sut.customVocabularyApiInterface = mockRetrofit.create(CustomVocabularyApiInterface.class);
  }

  @Test
  public void
      submitCustomVocabulary_VocabularyCallbackMetadataAreSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo =
        createCustomVocabularyInfo(DATE, ID, IN_PROGRESS, testName.getMethodName(), CALLBACK_URL);
    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));
    CustomVocabulary customVocabulary = createCustomVocabulary();
    CustomVocabularySubmission options = new CustomVocabularySubmission();
    options.setCustomVocabularies(Collections.singletonList(customVocabulary));
    options.setMetadata(testName.getMethodName());
    options.setNotificationConfig(CALLBACK_URL);

    CustomVocabularyInformation customVocabularyInformation = sut.submitCustomVocabularies(options);

    assertRequest(VOCABULARY_URL, "POST", options);
    assertCustomVocabularyInformation(
        customVocabularyInformation, IN_PROGRESS, DATE, CALLBACK_URL, testName.getMethodName(), ID);
  }

  @Test
  public void
      submitCustomVocabulary_VocabularyAndMetadataAreSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo =
        createCustomVocabularyInfo(DATE, ID, IN_PROGRESS, testName.getMethodName(), null);
    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));
    CustomVocabulary customVocabulary = createCustomVocabulary();
    CustomVocabularySubmission options = new CustomVocabularySubmission();
    options.setCustomVocabularies(Collections.singletonList(customVocabulary));
    options.setMetadata(testName.getMethodName());

    CustomVocabularyInformation customVocabularyInformation = sut.submitCustomVocabularies(options);

    assertRequest(VOCABULARY_URL, "POST", options);
    assertCustomVocabularyInformation(
        customVocabularyInformation, IN_PROGRESS, DATE, null, testName.getMethodName(), ID);
  }

  @Test
  public void
      submitCustomVocabulary_VocabularyAndCallbackAreSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo =
        createCustomVocabularyInfo(DATE, ID, IN_PROGRESS, null, CALLBACK_URL);
    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));
    CustomVocabulary customVocabulary = createCustomVocabulary();
    CustomVocabularySubmission options = new CustomVocabularySubmission();
    options.setCustomVocabularies(Collections.singletonList(customVocabulary));
    options.setNotificationConfig(CALLBACK_URL);

    CustomVocabularyInformation customVocabularyInformation = sut.submitCustomVocabularies(options);

    assertRequest(VOCABULARY_URL, "POST", options);
    assertCustomVocabularyInformation(
        customVocabularyInformation, IN_PROGRESS, DATE, CALLBACK_URL, null, ID);
  }

  @Test
  public void submitCustomVocabulary_VocabularyIsSpecified_ReturnsACustomVocabularyInformation()
      throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo =
        createCustomVocabularyInfo(DATE, ID, IN_PROGRESS, null, null);
    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));
    CustomVocabulary customVocabulary = createCustomVocabulary();
    CustomVocabularySubmission options = new CustomVocabularySubmission();
    options.setCustomVocabularies(Collections.singletonList(customVocabulary));

    CustomVocabularyInformation customVocabularyInformation = sut.submitCustomVocabularies(options);

    assertRequest(VOCABULARY_URL, "POST", options);
    assertCustomVocabularyInformation(
        customVocabularyInformation, IN_PROGRESS, DATE, null, null, ID);
  }

  @Test
  public void
      getCustomVocabularyInformation_VocabularyIsSpecified_ReturnsACustomVocabularyInformation()
          throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo =
        createCustomVocabularyInfo(DATE, ID, COMPLETE, null, null);
    mockInterceptor.setSampleResponse(gson.toJson(mockCustomVocabularyInfo));

    CustomVocabularyInformation customVocabularyInformation =
        sut.getCustomVocabularyInformation(ID);

    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", VOCABULARY_URL + "/" + ID);
    assertCustomVocabularyInformation(customVocabularyInformation, COMPLETE, DATE, null, null, ID);
  }

  @Test
  public void getCustomVocabularies_Called_ReturnsAListOfCustomVocabularyInformationObjects()
      throws IOException {
    CustomVocabularyInformation mockCustomVocabularyInfo1 =
        createCustomVocabularyInfo(DATE, ID + 1, COMPLETE, null, null);
    CustomVocabularyInformation mockCustomVocabularyInfo2 =
        createCustomVocabularyInfo(DATE, ID + 2, IN_PROGRESS, testName.getMethodName(), null);
    List<CustomVocabularyInformation> suppliedVocabularyInformation =
        Arrays.asList(mockCustomVocabularyInfo1, mockCustomVocabularyInfo2);
    mockInterceptor.setSampleResponse(gson.toJson(suppliedVocabularyInformation));

    List<CustomVocabularyInformation> customVocabularyInformation =
        sut.getListOfCustomVocabularyInformation();

    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", VOCABULARY_URL);
    int numberOfExpectedVocabularies = 2;
    assertThat(customVocabularyInformation.size())
        .as("Number of vocabularies")
        .isEqualTo(numberOfExpectedVocabularies);
    assertCustomVocabularyInformation(
        customVocabularyInformation.get(0), COMPLETE, DATE, null, null, ID + 1);
    assertCustomVocabularyInformation(
        customVocabularyInformation.get(1),
        IN_PROGRESS,
        DATE,
        null,
        testName.getMethodName(),
        ID + 2);
  }

  @Test
  public void DeleteCustomVocabulary_IdIsValid_DoesNotTriggerExceptions() throws IOException {
    mockInterceptor.setResponseCode(204);
    mockInterceptor.setSampleResponse("");

    sut.deleteCustomVocabulary(ID);

    String expectedUrl = VOCABULARY_URL + "/" + ID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "DELETE", expectedUrl);
  }

  private void assertCustomVocabularyInformation(
      CustomVocabularyInformation customVocabularyInformation,
      CustomVocabularyStatus status,
      String createdOn,
      String callbackUrl,
      String metadata,
      String id) {
    assertThat(customVocabularyInformation.getStatus()).as("Status").isEqualTo(status);
    assertThat(customVocabularyInformation.getCreatedOn()).as("Created on").isEqualTo(createdOn);
    assertThat(customVocabularyInformation.getId()).as("Custom vocabulary Id").isEqualTo(id);
    assertThat(customVocabularyInformation.getMetadata()).as("Metadata").isEqualTo(metadata);
    assertThat(customVocabularyInformation.getCallbackUrl())
            .as("Callback url")
            .isEqualTo(callbackUrl);
  }

  private void assertRequest(
      String url, String requestMethod, CustomVocabularySubmission expectedOptions) {
    AssertHelper.assertRequestBody(
        mockInterceptor, expectedOptions, CustomVocabularySubmission.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, requestMethod, url);
  }

  private CustomVocabularyInformation createCustomVocabularyInfo(
      String date, String id, CustomVocabularyStatus status, String metadata, String callbackUrl) {
    CustomVocabularyInformation customVocabularyInformation = new CustomVocabularyInformation();
    customVocabularyInformation.setCreatedOn(date);
    customVocabularyInformation.setId(id);
    customVocabularyInformation.setStatus(status);
    customVocabularyInformation.setMetadata(metadata);
    customVocabularyInformation.setCallbackUrl(callbackUrl);
    return customVocabularyInformation;
  }

  private CustomVocabulary createCustomVocabulary() {
    List<String> phrases = Arrays.asList("test", "custom", "vocabulary");
    return new CustomVocabulary(phrases);
  }
}
