package revai;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.helpers.SDKHelper;
import revai.models.asynchronous.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An ApiClient object contains all the endpoints that send and retrieve information from the Rev.AI
 * API using the Retrofit HTTP client.
 */
public class ApiClient {

  private Retrofit retrofit;
  private OkHttpClient client;
  public ApiInterface apiInterface;

  /*
  Helper function: manually closes the connection when the code is running in a JVM
   */
  public void closeConnection() {
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
  }

  public ApiClient(String accessToken) {
    if (accessToken == null) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.client =
        new OkHttpClient.Builder()
            .addNetworkInterceptor(new ApiInterceptor(accessToken, SDKHelper.getSdkVersion()))
            .addNetworkInterceptor(new ErrorInterceptor())
            .build();
    this.retrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/revspeech/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    this.apiInterface = retrofit.create(ApiInterface.class);
  }

  public RevAiAccount getAccount() throws IOException {
    return apiInterface.getAccount().execute().body();
  }

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

  public List<RevAiJob> getListOfJobs(Integer limit) throws IOException {
    return getListOfJobs(limit, null);
  }

  public List<RevAiJob> getListOfJobs(String startingAfter) throws IOException {
    return getListOfJobs(null, startingAfter);
  }

  public List<RevAiJob> getListOfJobs() throws IOException {
    return getListOfJobs(null, null);
  }

  public RevAiJob getJobDetails(String id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("ID must be provided");
    }
    return apiInterface.getJobDetails(id).execute().body();
  }

  public void deleteJob(String id) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("ID must be provided");
    }
    apiInterface.deleteJob(id).execute();
  }

  public RevAiTranscript getTranscriptObject(String id) throws IOException {
    return apiInterface.getTranscriptObject(id).execute().body();
  }

  public String getTranscriptText(String id) throws IOException {
    return apiInterface.getTranscriptText(id).execute().body();
  }

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

  public RevAiJob submitJobUrl(String mediaUrl) throws IOException {
    return submitJobUrl(mediaUrl, null);
  }

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

  public RevAiJob submitJobLocalFile(String filePath) throws IOException {
    return submitJobLocalFile(filePath, null);
  }

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

  public RevAiJob submitJobLocalFile(InputStream inputStream) throws IOException {
    return submitJobLocalFile(inputStream, null, null);
  }

  public RevAiJob submitJobLocalFile(InputStream inputStream, String fileName) throws IOException {
    return submitJobLocalFile(inputStream, fileName, null);
  }

  public RevAiJob submitJobLocalFile(InputStream inputStream, RevAiJobOptions options)
      throws IOException {
    return submitJobLocalFile(inputStream, null, options);
  }

  private RevAiJob submitMultipartRequest(
      InputStream inputStream, String fileName, RevAiJobOptions options) throws IOException {
    RequestBody fileRequest = FileStreamRequestBody.create(inputStream, MediaType.parse("audio/*"));
    MultipartBody.Part filePart = MultipartBody.Part.createFormData("media", fileName, fileRequest);
    return apiInterface.submitJobLocalFile(filePart, options).execute().body();
  }

  public InputStream getCaptions(String id, RevAiCaptionType captionType, Integer channelId)
      throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("ID must be provided");
    }
    if (captionType == null) {
      captionType = RevAiCaptionType.SRT;
    }
    Map<String, String> query = new HashMap<>();
    if (channelId != null) {
      query.put("speaker_channel", channelId.toString());
    }
    Map<String, String> contentHeader = new HashMap<>();
    contentHeader.put("Accept", captionType.getContentType());
    return apiInterface.getCaptionText(id, query, contentHeader).execute().body().byteStream();
  }

  public InputStream getCaptions(String id, RevAiCaptionType captionType) throws IOException {
    return getCaptions(id, captionType, null);
  }

  public InputStream getCaptions(String id, Integer channelId) throws IOException {
    return getCaptions(id, null, channelId);
  }

  public InputStream getCaptions(String id) throws IOException {
    return getCaptions(id, null, null);
  }
}
