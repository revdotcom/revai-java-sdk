package ai.rev.speechtotext;

import ai.rev.speechtotext.helpers.ClientHelper;
import ai.rev.speechtotext.models.CustomVocabulary;
import ai.rev.speechtotext.models.CustomVocabularyInformation;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

/**
 * The CustomVocabularyClient object provides methods to submit custom vocabularies and retrieve
 * related job information.
 */
public class CustomVocabulariesClient {

  private OkHttpClient client;

  /** Interface that CustomVocabularyClient methods use to make requests */
  public ApiInterface apiInterface;

  /**
   * Constructs the custom vocabulary client used to create custom vocabularies for streaming. The
   * user access token can be generated on the website at <a
   * href="https://www.rev.ai/access_token">https://www.rev.ai/access_token</a>.
   *
   * @param accessToken Rev.ai authorization token associate with the account.
   */
  public CustomVocabulariesClient(String accessToken) {
    if (accessToken == null) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.client = ClientHelper.createOkHttpClient(accessToken);
    Retrofit retrofit = ClientHelper.createRetrofitInstance(client);
    this.apiInterface = retrofit.create(ApiInterface.class);
  }

  /**
   * This methods makes a POST request to the /vocabularies endpoint, starts an asynchronous job to
   * process the vocabularies and returns a {@link CustomVocabularyInformation} object.
   *
   * @param metadata Optional information that can be provided.
   * @param customVocabularies List of {@link CustomVocabulary} objects.
   * @param callbackUrl The callback url that Rev.ai will send a POST to when the job has finished.
   * @return A {@link CustomVocabularyInformation} object.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the list of custom vocabularies is null.
   * @see CustomVocabularyInformation
   */
  public CustomVocabularyInformation submitCustomVocabularies(
      String metadata, List<CustomVocabulary> customVocabularies, String callbackUrl)
      throws IOException {
    if (customVocabularies == null) {
      throw new IllegalArgumentException("Custom vocabularies must be provided");
    }
    RevAiJobOptions options = new RevAiJobOptions();
    if (metadata != null) {
      options.setMetadata(metadata);
    }
    if (callbackUrl != null) {
      options.setCallbackUrl(callbackUrl);
    }
    options.setCustomVocabularies(customVocabularies);
    return apiInterface.submitCustomVocabularies(options).execute().body();
  }

  /**
   * Overload of {@link CustomVocabulariesClient#submitCustomVocabularies(String, List, String)}
   * without the optional callbackUrl property.
   *
   * @param metadata Optional information that can be provided.
   * @param customVocabularies List of {@link CustomVocabulary} objects.
   * @return A {@link CustomVocabularyInformation} object.
   * @throws IOException If the response has a status code > 399.
   * @see CustomVocabularyInformation
   */
  public CustomVocabularyInformation submitCustomVocabularies(
      String metadata, List<CustomVocabulary> customVocabularies) throws IOException {
    return submitCustomVocabularies(metadata, customVocabularies, null);
  }

  /**
   * Overload of {@link CustomVocabulariesClient#submitCustomVocabularies(String, List, String)} *
   * without the optional metadata property.
   *
   * @param customVocabularies List of {@link CustomVocabulary} objects.
   * @param callbackUrl The callback url that Rev.ai will send a POST to when the job has finished.
   * @return A {@link CustomVocabularyInformation} object.
   * @throws IOException If the response has a status code > 399.
   * @see CustomVocabularyInformation
   */
  public CustomVocabularyInformation submitCustomVocabularies(
      List<CustomVocabulary> customVocabularies, String callbackUrl) throws IOException {
    return submitCustomVocabularies(null, customVocabularies, callbackUrl);
  }

  /**
   * Overload of {@link CustomVocabulariesClient#submitCustomVocabularies(String, List, String)} *
   * without the optional metadata and callbackUrl properties.
   *
   * @param customVocabularies List of {@link CustomVocabulary} objects.
   * @return A {@link CustomVocabularyInformation} object.
   * @throws IOException If the response has a status code > 399.
   * @see CustomVocabularyInformation
   */
  public CustomVocabularyInformation submitCustomVocabularies(
      List<CustomVocabulary> customVocabularies) throws IOException {
    return submitCustomVocabularies(null, customVocabularies, null);
  }

  /**
   * This method sends a GET request to the /vocabularies endpoint and returns a list of {@link
   * CustomVocabularyInformation} objects.
   *
   * @return A list of {@link CustomVocabularyInformation} objects.
   * @throws IOException If the response has a status code > 399.
   * @see CustomVocabularyInformation
   */
  public List<CustomVocabularyInformation> getListOfCustomVocabularyInformation() throws IOException {
    return apiInterface.getListOfCustomVocabularyInformation().execute().body();
  }

  /**
   * This method sends a GET request to the /vocabularies/{id} endpoint and returns a {@link
   * CustomVocabularyInformation} object.
   *
   * @param id The Id of the custom vocabulary to return an object for.
   * @return A {@link CustomVocabularyInformation} object.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the custom vocabulary Id is null.
   * @see CustomVocabularyInformation
   */
  public CustomVocabularyInformation getCustomVocabularyInformation(String id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("Custom vocabulary Id must be provided");
    }
    return apiInterface.getCustomVocabularyInformation(id).execute().body();
  }
}
