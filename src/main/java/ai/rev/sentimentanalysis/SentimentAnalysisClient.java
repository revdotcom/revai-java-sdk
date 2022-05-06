package ai.rev.sentimentanalysis;

import ai.rev.helpers.ClientHelper;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.sentimentanalysis.models.Sentiment;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJob;
import ai.rev.sentimentanalysis.models.SentimentAnalysisJobOptions;
import ai.rev.sentimentanalysis.models.SentimentAnalysisResult;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The SentimentAnalysisClient object provides methods to send and retrieve information from all the
 * Rev AI Sentiment Analysis API endpoints using the Retrofit HTTP client.
 */
public class SentimentAnalysisClient {

  private OkHttpClient client;

  /** Interface that SentimentAnalysisClient methods use to make requests */
  public SentimentAnalysisInterface apiInterface;
  
  /**
   * Constructs the API client used to send HTTP requests to Rev AI. The user access token can be
   * generated on the website at <a
   * href="https://www.rev.ai/access_token">https://www.rev.ai/access_token</a>.
   *
   * @param accessToken Rev AI authorization token associate with the account.
   * @throws IllegalArgumentException If the access token is null or empty.
   */
  public SentimentAnalysisClient(String accessToken) {
    if (accessToken == null || accessToken.isEmpty()) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.client = ClientHelper.createOkHttpClient(accessToken);
    Retrofit retrofit = ClientHelper.createRetrofitInstance(client, "sentiment_analysis", "v1");
    this.apiInterface = retrofit.create(SentimentAnalysisInterface.class);
  }
  
  /** Manually closes the connection when the code is running in a JVM */
  public void closeConnection() {
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
  }

  /**
   * This method sends a GET request to the /jobs endpoint and returns a list of {@link SentimentAnalysisJob}
   * objects.
   *
   * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
   * @param startingAfter The job ID at which the list begins.
   * @return A list of {@link SentimentAnalysisJob} objects.
   * @throws IOException If the response has a status code > 399.
   * @see SentimentAnalysisJob
   * @see <a
   *     href="https://docs.rev.ai/api/sentiment-analysis/reference/#operation/GetListOfSentimentAnalysisJobs">https://docs.rev.ai/api/sentiment-analysis/reference/#operation/GetListOfSentimentAnalysisJobs</a>
   */
  public List<SentimentAnalysisJob> getListOfJobs(Integer limit, String startingAfter) throws IOException {
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
   * Overload of {@link SentimentAnalysisClient#getListOfJobs(Integer, String)} without the optional startingAfter
   * parameter.
   *
   * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
   * @return A list of {@link SentimentAnalysisJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<SentimentAnalysisJob> getListOfJobs(Integer limit) throws IOException {
    return getListOfJobs(limit, null);
  }

  /**
   * Overload of {@link SentimentAnalysisClient#getListOfJobs(Integer, String)} without the optional limit
   * parameter.
   *
   * @param startingAfter The job ID at which the list begins.
   * @return A list of {@link SentimentAnalysisJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<SentimentAnalysisJob> getListOfJobs(String startingAfter) throws IOException {
    return getListOfJobs(null, startingAfter);
  }

  /**
   * Overload of {@link SentimentAnalysisClient#getListOfJobs(Integer, String)} without the optional limit and
   * startingAfter parameter.
   *
   * @return A list of {@link SentimentAnalysisJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<SentimentAnalysisJob> getListOfJobs() throws IOException {
    return getListOfJobs(null, null);
  }

  /**
   * This method sends a GET request to the /jobs/{id} endpoint and returns a {@link SentimentAnalysisJob}
   * object.
   *
   * @param id The ID of the job to return an object for.
   * @return A {@link SentimentAnalysisJob} object.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID is null.
   */
  public SentimentAnalysisJob getJobDetails(String id) throws IOException {
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
   *     href="https://docs.rev.ai/api/sentiment-analysis/reference/#operation/DeleteSentimentAnalysisJobById">https://docs.rev.ai/api/sentiment-analysis/reference/#operation/DeleteSentimentAnalysisJobById</a>
   */
  public void deleteJob(String id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("Job ID must be provided");
    }
    apiInterface.deleteJob(id).execute();
  }

  /**
   * The method sends a GET request to the /jobs/{id}/result endpoint and returns a {@link
   * SentimentAnalysisResult} object.
   *
   * @param id The ID of the job to return a result for.
   * @return SentimentAnalysisResult The result object.
   * @throws IOException If the response has a status code > 399.
   * @see SentimentAnalysisResult
   */
  public SentimentAnalysisResult getResultObject(String id, Sentiment filterFor) throws IOException {
    Map<String, Object> options = new HashMap<>();
    options.put("filter_for", filterFor.getSentiment();
    return apiInterface.getResultObject(id, options).execute().body();
  }

  /**
   * The method sends a GET request to the /jobs/{id}/result endpoint and returns a {@link
   * SentimentAnalysisResult} object.
   *
   * @param id The ID of the job to return a result for.
   * @return SentimentAnalysisResult The result object.
   * @throws IOException If the response has a status code > 399.
   * @see SentimentAnalysisResult
   */
  public SentimentAnalysisResult getResultObject(String id) throws IOException {
    Map<String, Object> options = new HashMap<>();
    return apiInterface.getResultObject(id, options).execute().body();
  }

  /**
   * The method sends a POST request to the /jobs endpoint, starts a sentiment analysis job for the
   * provided text and returns a {@link SentimentAnalysisJob} object.
   *
   * @param text Text to have sentiments detected on it.
   * @param options The sentiment analysis options associated with this job.
   * @return SentimentAnalysisJob A representation of the sentiment analysis job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the text is null.
   * @see SentimentAnalysisJob
   * @see <a
   *     href="https://docs.rev.ai/api/sentiment-analysis/reference/#operation/SubmitSentimentAnalysisJob">https://docs.rev.ai/api/sentiment-analysis/reference/#operation/SubmitSentimentAnalysisJob</a>
   */
  public SentimentAnalysisJob submitJobText(String text, SentimentAnalysisJobOptions options) throws IOException {
    if (text == null) {
      throw new IllegalArgumentException("Text must be provided");
    }
    if (options == null) {
      options = new SentimentAnalysisJobOptions();
    }
    options.setText(text);
    return apiInterface.submitJob(options).execute().body();
  }

  /**
   * An overload of {@link SentimentAnalysisClient#submitJobText(String, SentimentAnalysisJobOptions)} without the additional
   * sentiment analysis options.
   *
   * @param text Text to have sentiments detected on it.
   * @return SentimentAnalysisJob A representation of the sentiment analysis job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the text is null.
   * @see SentimentAnalysisJob
   * @see SentimentAnalysisClient#submitJobText(String, SentimentAnalysisJobOptions)
   */
  public SentimentAnalysisJob submitJobText(String text) throws IOException {
    return submitJobText(text, null);
  }
  
  /**
   * The method sends a POST request to the /jobs endpoint, starts a sentiment analysis job for the
   * provided RevAiTranscript and returns a {@link SentimentAnalysisJob} object.
   *
   * @param json RevAiTranscript to submit for sentiment analysis
   * @param options The sentiment analysis options associated with this job.
   * @return SentimentAnalysisJob A representation of the sentiment analysis job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the json is null.
   * @see SentimentAnalysisJob
   * @see <a
   *     href="https://docs.rev.ai/api/sentiment-analysis/reference/#operation/SubmitSentimentAnalysisJob">https://docs.rev.ai/api/sentiment-analysis/reference/#operation/SubmitSentimentAnalysisJob</a>
   */
  public SentimentAnalysisJob submitJobJson(RevAiTranscript json, SentimentAnalysisJobOptions options) throws IOException {
    if (json == null) {
      throw new IllegalArgumentException("Json must be provided");
    }
    if (options == null) {
      options = new SentimentAnalysisJobOptions();
    }
    options.setJson(json);
    return apiInterface.submitJob(options).execute().body();
  }

  /**
   * An overload of {@link SentimentAnalysisClient#submitJobText(String, SentimentAnalysisJobOptions)} without the additional
   * sentiment analysis options.
   *
   * @param json RevAiTranscript to submit for sentiment analysis
   * @return SentimentAnalysisJob A representation of the sentiment analysis job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the json is null.
   * @see SentimentAnalysisJob
   * @see SentimentAnalysisClient#submitJobJson(RevAiTranscript, SentimentAnalysisJobOptions)
   */
  public SentimentAnalysisJob submitJobJson(RevAiTranscript json) throws IOException {
    return submitJobJson(json, null);
  }
}

