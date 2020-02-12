package revai;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.helpers.SDKHelper;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiTranscript;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    RequestBody fileRequest =
        FileStreamRequestBody.create(
            new FileInputStream(file.getAbsoluteFile()), MediaType.parse("audio/*"));
    MultipartBody.Part filePart =
        MultipartBody.Part.createFormData("media", file.getName(), fileRequest);
    return apiInterface.submitJobLocalFile(filePart, options).execute().body();
  }

  public RevAiJob submitJobLocalFile(String filePath) throws IOException {
    return submitJobLocalFile(filePath, null);
  }
}
