package ai.rev.speechtotext;

import ai.rev.speechtotext.helpers.ClientHelper;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyInformation;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularySubmission;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

/**
 * The CustomVocabularyClient object provides methods to interact with the Custom Vocabulary Api.
 */
public class CustomVocabulariesClient {

  private OkHttpClient client;

  /** Interface that CustomVocabularyClient methods use to make requests */
  public CustomVocabularyApiInterface customVocabularyApiInterface;

  /**
   * Constructs the custom vocabulary client used to create custom vocabularies for streaming. The
   * user access token can be generated on the website at <a
   * href="https://www.rev.ai/access_token">https://www.rev.ai/access_token</a>.
   *
   * @param accessToken Rev AI authorization token associate with the account.
   * @throws IllegalArgumentException If the access token is null or empty.
   */
  public CustomVocabulariesClient(String accessToken) {
    if (accessToken == null || accessToken.isEmpty()) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.client = ClientHelper.createOkHttpClient(accessToken);
    Retrofit retrofit = ClientHelper.createRetrofitInstance(client);
    this.customVocabularyApiInterface = retrofit.create(CustomVocabularyApiInterface.class);
  }

  /**
   * This method makes a POST request to the /vocabularies endpoint and returns a {@link
   * CustomVocabularyInformation} object that provides details about the custom vocabulary
   * submission and its progress.
   *
   * @param submission An object that contains the custom vocabularies as well as optional metadata
   *     and callback url
   * @return A {@link CustomVocabularyInformation} object.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the list of custom vocabularies is null or empty.
   * @see CustomVocabularyInformation
   */
  public CustomVocabularyInformation submitCustomVocabularies(CustomVocabularySubmission submission)
      throws IOException {
    if (submission.getCustomVocabularies() == null
        || submission.getCustomVocabularies().isEmpty()) {
      throw new IllegalArgumentException("Custom vocabularies must be provided");
    }

    return customVocabularyApiInterface.submitCustomVocabularies(submission).execute().body();
  }

  /**
   * This method sends a GET request to the /vocabularies endpoint and returns a list of {@link
   * CustomVocabularyInformation} objects.
   *
   * @return A list of {@link CustomVocabularyInformation} objects.
   * @throws IOException If the response has a status code > 399.
   * @see CustomVocabularyInformation
   */
  public List<CustomVocabularyInformation> getListOfCustomVocabularyInformation()
      throws IOException {
    return customVocabularyApiInterface.getListOfCustomVocabularyInformation().execute().body();
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
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Custom vocabulary Id must be provided");
    }
    return customVocabularyApiInterface.getCustomVocabularyInformation(id).execute().body();
  }

  /**
   * This method sends a DELETE request to the /vocabularies/{id} endpoint.
   *
   * @param id The Id of the custom vocabulary to be deleted.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job Id is null.
   * @see <a
   *     href="https://docs.rev.ai/api/custom-vocabulary/reference/#operation/DeleteCustomVocabulary">https://docs.rev.ai/api/custom-vocabulary/reference/#operation/DeleteCustomVocabulary</a>
   */
  public void deleteCustomVocabulary(String id) throws IOException {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Custom vocabulary Id must be provided");
    }
    customVocabularyApiInterface.deleteCustomVocabulary(id).execute().body();
  }
}
