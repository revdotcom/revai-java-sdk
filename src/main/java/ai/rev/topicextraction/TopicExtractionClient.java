package ai.rev.topicextraction;

import ai.rev.helpers.ClientHelper;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.topicextraction.models.TopicExtractionJob;
import ai.rev.topicextraction.models.TopicExtractionJobOptions;
import ai.rev.topicextraction.models.TopicExtractionResult;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TopicExtractionClient object provides methods to send and retrieve information from all the
 * Rev AI Topic Extraction API endpoints using the Retrofit HTTP client.
 */
public class TopicExtractionClient {

  private OkHttpClient client;

  /** Interface that TopicExtractionClient methods use to make requests */
  public TopicExtractionInterface apiInterface;
  
  /**
   * Constructs the API client used to send HTTP requests to Rev AI. The user access token can be
   * generated on the website at <a
   * href="https://www.rev.ai/access_token">https://www.rev.ai/access_token</a>.
   *
   * @param accessToken Rev AI authorization token associate with the account.
   * @throws IllegalArgumentException If the access token is null or empty.
   */
  public TopicExtractionClient(String accessToken) {
    if (accessToken == null || accessToken.isEmpty()) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.client = ClientHelper.createOkHttpClient(accessToken);
    Retrofit retrofit = ClientHelper.createRetrofitInstance(client, "topic_extraction", "v1beta");
    this.apiInterface = retrofit.create(TopicExtractionInterface.class);
  }
  
  /** Manually closes the connection when the code is running in a JVM */
  public void closeConnection() {
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
  }

  /**
   * This method sends a GET request to the /jobs endpoint and returns a list of {@link TopicExtractionJob}
   * objects.
   *
   * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
   * @param startingAfter The job ID at which the list begins.
   * @return A list of {@link TopicExtractionJob} objects.
   * @throws IOException If the response has a status code > 399.
   * @see TopicExtractionJob
   * @see <a
   *     href="https://docs.rev.ai/api/topic-extraction/reference/#operation/GetListOfTopicExtractionJobs">https://docs.rev.ai/api/topic-extraction/reference/#operation/GetListOfTopicExtractionJobs</a>
   */
  public List<TopicExtractionJob> getListOfJobs(Integer limit, String startingAfter) throws IOException {
    Map<String, String> options = new HashMap<>();
    if (startingAfter != null) {
      options.put("starting_after", startingAfter);
    }
    if (limit != null) {
      options.put("limit", String.valueOf(limit));
    }
    return apiInterface.getListOfJobs(options).execute().body();
  }

  /**
   * Overload of {@link TopicExtractionClient#getListOfJobs(Integer, String)} without the optional startingAfter
   * parameter.
   *
   * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
   * @return A list of {@link TopicExtractionJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<TopicExtractionJob> getListOfJobs(Integer limit) throws IOException {
    return getListOfJobs(limit, null);
  }

  /**
   * Overload of {@link TopicExtractionClient#getListOfJobs(Integer, String)} without the optional limit
   * parameter.
   *
   * @param startingAfter The job ID at which the list begins.
   * @return A list of {@link TopicExtractionJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<TopicExtractionJob> getListOfJobs(String startingAfter) throws IOException {
    return getListOfJobs(null, startingAfter);
  }

  /**
   * Overload of {@link TopicExtractionClient#getListOfJobs(Integer, String)} without the optional limit and
   * startingAfter parameter.
   *
   * @return A list of {@link TopicExtractionJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<TopicExtractionJob> getListOfJobs() throws IOException {
    return getListOfJobs(null, null);
  }

  /**
   * This method sends a GET request to the /jobs/{id} endpoint and returns a {@link TopicExtractionJob}
   * object.
   *
   * @param id The ID of the job to return an object for.
   * @return A {@link TopicExtractionJob} object.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID is null.
   */
  public TopicExtractionJob getJobDetails(String id) throws IOException {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Job ID must be provided");
    }
    return apiInterface.getJobDetails(id).execute().body();
  }

  /**
   * This method sends a DELETE request to the /jobs/{id} endpoint.
   *
   * @param id The Id of the job to be deleted.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID is null.
   * @see <a
   *     href="https://docs.rev.ai/api/topic-extraction/reference/#operation/DeleteTopicExtractionJobById">https://docs.rev.ai/api/topic-extraction/reference/#operation/DeleteTopicExtractionJobById</a>
   */
  public void deleteJob(String id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("Job ID must be provided");
    }
    apiInterface.deleteJob(id).execute();
  }

  /**
   * The method sends a GET request to the /jobs/{id}/transcript endpoint and returns a {@link
   * TopicExtractionResult} object.
   *
   * @param id The ID of the job to return a transcript for.
   * @return TopicExtractionResult The result object.
   * @throws IOException If the response has a status code > 399.
   * @see TopicExtractionResult
   */
  public TopicExtractionResult getResultObject(String id, Double threshold) throws IOException {
    Map<String, Object> options = new HashMap<>();
    options.put("threshold", threshold);
    return apiInterface.getResultObject(id, options).execute().body();
  }

  /**
   * The method sends a GET request to the /jobs/{id}/transcript endpoint and returns a {@link
   * TopicExtractionResult} object.
   *
   * @param id The ID of the job to return a transcript for.
   * @return TopicExtractionResult The result object.
   * @throws IOException If the response has a status code > 399.
   * @see TopicExtractionResult
   */
  public TopicExtractionResult getResultObject(String id) throws IOException {
    Map<String, Object> options = new HashMap<>();
    return apiInterface.getResultObject(id, options).execute().body();
  }

  /**
   * The method sends a POST request to the /jobs endpoint, starts an asynchronous job to transcribe
   * the media file located at the url provided and returns a {@link TopicExtractionJob} object.
   *
   * @param text A direct download link to the media.
   * @param options The topic extraction options associated with this job.
   * @return TopicExtractionJob A representation of the topic extraction job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the text is null.
   * @see TopicExtractionJob
   * @see <a
   *     href="https://docs.rev.ai/api/topic-extraction/reference/#operation/SubmitTopicExtractionJob">https://docs.rev.ai/api/topic-extraction/reference/#operation/SubmitTopicExtractionJob</a>
   */
  public TopicExtractionJob submitJobText(String text, TopicExtractionJobOptions options) throws IOException {
    if (text == null) {
      throw new IllegalArgumentException("Text must be provided");
    }
    if (options == null) {
      options = new TopicExtractionJobOptions();
    }
    options.setText(text);
    return apiInterface.submitJob(options).execute().body();
  }

  /**
   * An overload of {@link TopicExtractionClient#submitJobText(String, TopicExtractionJobOptions)} without the additional
   * topic extraction options.
   *
   * @param text A direct download link to the media.
   * @return TopicExtractionJob A representation of the topic extraction job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the text is null.
   * @see TopicExtractionJob
   * @see TopicExtractionClient#submitJobText(String, TopicExtractionJobOptions)
   */
  public TopicExtractionJob submitJobText(String text) throws IOException {
    return submitJobText(text, null);
  }
  
  /**
   * The method sends a POST request to the /jobs endpoint, starts an asynchronous job to transcribe
   * the media file located at the url provided and returns a {@link TopicExtractionJob} object.
   *
   * @param json RevAiTranscript to submit for topic extraction
   * @param options The topic extraction options associated with this job.
   * @return TopicExtractionJob A representation of the topic extraction job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the json is null.
   * @see TopicExtractionJob
   * @see <a
   *     href="https://docs.rev.ai/api/topic-extraction/reference/#operation/SubmitTopicExtractionJob">https://docs.rev.ai/api/topic-extraction/reference/#operation/SubmitTopicExtractionJob</a>
   */
  public TopicExtractionJob submitJobJson(RevAiTranscript json, TopicExtractionJobOptions options) throws IOException {
    if (json == null) {
      throw new IllegalArgumentException("Json must be provided");
    }
    if (options == null) {
      options = new TopicExtractionJobOptions();
    }
    options.setJson(json);
    return apiInterface.submitJob(options).execute().body();
  }

  /**
   * An overload of {@link TopicExtractionClient#submitJobText(String, TopicExtractionJobOptions)} without the additional
   * topic extraction options.
   *
   * @param json RevAiTranscript to submit for topic extraction
   * @return TopicExtractionJob A representation of the topic extraction job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the json is null.
   * @see TopicExtractionJob
   * @see TopicExtractionClient#submitJobJson(RevAiTranscript, TopicExtractionJobOptions)
   */
  public TopicExtractionJob submitJobJson(RevAiTranscript json) throws IOException {
    return submitJobJson(json, null);
  }
}

