package ai.rev.speechtotext;

import ai.rev.speechtotext.helpers.ClientHelper;
import ai.rev.speechtotext.models.asynchronous.RevAiAccount;
import ai.rev.speechtotext.models.asynchronous.RevAiCaptionType;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ApiClient object provides methods to send and retrieve information from all the Rev.AI API
 * endpoints using the Retrofit HTTP client.
 */
public class ApiClient {

  private OkHttpClient client;

  /** Interface that ApiClient methods use to make requests */
  public ApiInterface apiInterface;

  /**
   * Constructs the API client used to send HTTP requests to Rev.ai. The user access token can be
   * generated on the website at <a
   * href="https://www.rev.ai/access_token">https://www.rev.ai/access_token</a>.
   *
   * @param accessToken Rev.ai authorization token associate with the account.
   * @throws IllegalArgumentException If the access token is null or empty.
   */
  public ApiClient(String accessToken) {
    if (accessToken == null || accessToken.isEmpty()) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.client = ClientHelper.createOkHttpClient(accessToken);
    Retrofit retrofit = ClientHelper.createRetrofitInstance(client);
    this.apiInterface = retrofit.create(ApiInterface.class);
  }

  /** Manually closes the connection when the code is running in a JVM */
  public void closeConnection() {
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
  }

  /**
   * This method sends a GET request to the /account endpoint and returns an {@link RevAiAccount}
   * object.
   *
   * @return RevAiAccount The object containing basic Rev.ai account information
   * @throws IOException If the response has a status code > 399.
   * @see RevAiAccount
   */
  public RevAiAccount getAccount() throws IOException {
    return apiInterface.getAccount().execute().body();
  }

  /**
   * This method sends a GET request to the /jobs endpoint and returns a list of {@link RevAiJob}
   * objects.
   *
   * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
   * @param startingAfter The job ID at which the list begins.
   * @return A list of {@link RevAiJob} objects.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/GetListOfJobs">https://www.rev.ai/docs#operation/GetListOfJobs</a>
   */
  public List<RevAiJob> getListOfJobs(Integer limit, String startingAfter) throws IOException {
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
   * Overload of {@link ApiClient#getListOfJobs(Integer, String)} without the optional startingAfter
   * parameter.
   *
   * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
   * @return A list of {@link RevAiJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<RevAiJob> getListOfJobs(Integer limit) throws IOException {
    return getListOfJobs(limit, null);
  }

  /**
   * Overload of {@link ApiClient#getListOfJobs(Integer, String)} without the optional limit
   * parameter.
   *
   * @param startingAfter The job ID at which the list begins.
   * @return A list of {@link RevAiJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<RevAiJob> getListOfJobs(String startingAfter) throws IOException {
    return getListOfJobs(null, startingAfter);
  }

  /**
   * Overload of {@link ApiClient#getListOfJobs(Integer, String)} without the optional limit and
   * startingAfter parameter.
   *
   * @return A list of {@link RevAiJob} objects.
   * @throws IOException If the response has a status code > 399.
   */
  public List<RevAiJob> getListOfJobs() throws IOException {
    return getListOfJobs(null, null);
  }

  /**
   * This method sends a GET request to the /jobs/{id} endpoint and returns a {@link RevAiJob}
   * object.
   *
   * @param id The ID of the job to return an object for.
   * @return A {@link RevAiJob} object.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID is null.
   */
  public RevAiJob getJobDetails(String id) throws IOException {
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
   *     href="https://www.rev.ai/docs#operation/DeleteJobById">https://www.rev.ai/docs#operation/DeleteJobById</a>
   */
  public void deleteJob(String id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("Job ID must be provided");
    }
    apiInterface.deleteJob(id).execute();
  }

  /**
   * The method sends a GET request to the /jobs/{id}/transcript endpoint and returns a {@link
   * RevAiTranscript} object.
   *
   * @param id The ID of the job to return a transcript for.
   * @return RevAiTranscript The transcript object.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiTranscript
   */
  public RevAiTranscript getTranscriptObject(String id) throws IOException {
    return apiInterface.getTranscriptObject(id).execute().body();
  }

  /**
   * The method sends a GET request to the /jobs/{id}/transcript endpoint and returns the transcript
   * as a String.
   *
   * @param id The ID of the job to return a transcript for.
   * @return The transcript as a String in text format.
   * @throws IOException If the response has a status code > 399.
   */
  public String getTranscriptText(String id) throws IOException {
    return apiInterface.getTranscriptText(id).execute().body();
  }

  /**
   * The method sends a POST request to the /jobs endpoint, starts an asynchronous job to transcribe
   * the media file located at the url provided and returns a {@link RevAiJob} object.
   *
   * @param mediaUrl A direct download link to the media.
   * @param options The transcription options associated with this job.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException if the media url is null.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
   */
  public RevAiJob submitJobUrl(String mediaUrl, RevAiJobOptions options) throws IOException {
    if (mediaUrl == null) {
      throw new IllegalArgumentException("Media url must be provided");
    }
    if (options == null) {
      options = new RevAiJobOptions();
    }
    options.setMediaUrl(mediaUrl);
    return apiInterface.submitJobUrl(options).execute().body();
  }

  /**
   * An overload of {@link ApiClient#submitJobUrl(String, RevAiJobOptions)} without the additional
   * transcription options.
   *
   * @param mediaUrl A direct download link to the media.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiJob
   * @see ApiClient#submitJobUrl(String, RevAiJobOptions)
   */
  public RevAiJob submitJobUrl(String mediaUrl) throws IOException {
    return submitJobUrl(mediaUrl, null);
  }

  /**
   * The method sends multipart/form request to the /jobs endpoint, starts an asynchronous job to
   * transcribe the local media file provided and returns a {@link RevAiJob} object.
   *
   * @param filePath A local path to the file on the computer.
   * @param options The transcription options associated with this job.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the file path is null.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
   */
  public RevAiJob submitJobLocalFile(String filePath, RevAiJobOptions options) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("File path must be provided");
    }
    if (options == null) {
      options = new RevAiJobOptions();
    }
    File file = new File(filePath);
    return submitMultipartRequest(
        new FileInputStream(file.getAbsoluteFile()), file.getName(), options);
  }

  /**
   * An overload of {@link ApiClient#submitJobLocalFile(String, RevAiJobOptions)} without the
   * additional transcription options.
   *
   * @param filePath A local path to the file on the computer.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiJob
   * @see ApiClient#submitJobLocalFile(String, RevAiJobOptions)
   */
  public RevAiJob submitJobLocalFile(String filePath) throws IOException {
    return submitJobLocalFile(filePath, null);
  }

  /**
   * The method sends a POST request to the /jobs endpoint, starts an asynchronous job to transcribe
   * the media file provided by InputStream and returns a {@link RevAiJob} object.
   *
   * @param inputStream An InputStream of the media file.
   * @param fileName The name of the file being streamed.
   * @param options The transcription options associated with this job.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the InputStream provided is null.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
   */
  public RevAiJob submitJobLocalFile(
      InputStream inputStream, String fileName, RevAiJobOptions options) throws IOException {
    if (inputStream == null) {
      throw new IllegalArgumentException("File stream must be provided");
    }
    if (options == null) {
      options = new RevAiJobOptions();
    }
    if (fileName == null) {
      fileName = "audio_file";
    }
    return submitMultipartRequest(inputStream, fileName, options);
  }

  /**
   * An overload of {@link ApiClient#submitJobLocalFile(InputStream, String, RevAiJobOptions)}
   * without the optional filename and transcription options.
   *
   * @param inputStream An InputStream of the media file.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
   */
  public RevAiJob submitJobLocalFile(InputStream inputStream) throws IOException {
    return submitJobLocalFile(inputStream, null, null);
  }

  /**
   * An overload of {@link ApiClient#submitJobLocalFile(InputStream, String, RevAiJobOptions)}
   * without the additional transcription options.
   *
   * @param inputStream An InputStream of the media file.
   * @param fileName The name of the file being streamed.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
   */
  public RevAiJob submitJobLocalFile(InputStream inputStream, String fileName) throws IOException {
    return submitJobLocalFile(inputStream, fileName, null);
  }

  /**
   * An overload of {@link ApiClient#submitJobLocalFile(InputStream, String, RevAiJobOptions)}
   * without the optional filename.
   *
   * @param inputStream An InputStream of the media file.
   * @param options The transcription options associated with this job.
   * @return RevAiJob A representation of the transcription job.
   * @throws IOException If the response has a status code > 399.
   * @see RevAiJob
   * @see <a
   *     href="https://www.rev.ai/docs#operation/SubmitTranscriptionJob">https://www.rev.ai/docs#operation/SubmitTranscriptionJob</a>
   */
  public RevAiJob submitJobLocalFile(InputStream inputStream, RevAiJobOptions options)
      throws IOException {
    return submitJobLocalFile(inputStream, null, options);
  }

  /**
   * The method sends a GET request to the /jobs/{id}/captions endpoint and returns captions for the
   * provided job ID in the form of an InputStream.
   *
   * @param id The ID of the job to return captions for.
   * @param captionType An enumeration of the desired caption type. Default is SRT.
   * @param channelId Identifies the audio channel of the file to output captions for. Default is
   *     null.
   * @return InputStream A stream of bytes that represents the caption output.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID provided is null.
   * @see <a
   *     href="https://www.rev.ai/docs#operation/GetCaptions">https://www.rev.ai/docs#operation/GetCaptions</a>
   */
  public InputStream getCaptions(String id, RevAiCaptionType captionType, Integer channelId)
      throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("Job ID must be provided");
    }
    Map<String, String> query = new HashMap<>();
    if (channelId != null) {
      query.put("speaker_channel", channelId.toString());
    }
    if (captionType == null) {
      captionType = RevAiCaptionType.SRT;
    }
    Map<String, String> contentHeader = new HashMap<>();
    contentHeader.put("Accept", captionType.getContentType());
    return apiInterface.getCaptionText(id, query, contentHeader).execute().body().byteStream();
  }

  /**
   * An overload of {@link ApiClient#getCaptions(String, RevAiCaptionType, Integer)} without the
   * optional channel ID.
   *
   * @param id The ID of the job to return captions for.
   * @param captionType An enumeration of the desired caption type. Default is SRT
   * @return InputStream A stream of bytes that represents the caption output.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID provided is null.
   * @see <a
   *     href="https://www.rev.ai/docs#operation/GetCaptions">https://www.rev.ai/docs#operation/GetCaptions</a>
   */
  public InputStream getCaptions(String id, RevAiCaptionType captionType) throws IOException {
    return getCaptions(id, captionType, null);
  }

  /**
   * An overload of {@link ApiClient#getCaptions(String, RevAiCaptionType, Integer)} without the
   * optional caption type.
   *
   * @param id The ID of the job to return captions for.
   * @param channelId Identifies the audio channel of the file to output captions for.
   * @return InputStream A stream of bytes that represents the caption output.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID provided is null.
   * @see <a
   *     href="https://www.rev.ai/docs#operation/GetCaptions">https://www.rev.ai/docs#operation/GetCaptions</a>
   */
  public InputStream getCaptions(String id, Integer channelId) throws IOException {
    return getCaptions(id, null, channelId);
  }

  /**
   * An overload of {@link ApiClient#getCaptions(String, RevAiCaptionType, Integer)} without the
   * optional caption type and channel ID.
   *
   * @param id The ID of the job to return captions for.
   * @return InputStream A stream of bytes that represents the caption output.
   * @throws IOException If the response has a status code > 399.
   * @throws IllegalArgumentException If the job ID provided is null.
   * @see <a
   *     href="https://www.rev.ai/docs#operation/GetCaptions">https://www.rev.ai/docs#operation/GetCaptions</a>
   */
  public InputStream getCaptions(String id) throws IOException {
    return getCaptions(id, null, null);
  }

  private RevAiJob submitMultipartRequest(
      InputStream inputStream, String fileName, RevAiJobOptions options) throws IOException {
    RequestBody fileRequest = FileStreamRequestBody.create(inputStream, MediaType.parse("audio/*"));
    MultipartBody.Part filePart = MultipartBody.Part.createFormData("media", fileName, fileRequest);
    return apiInterface.submitJobLocalFile(filePart, options).execute().body();
  }
}
