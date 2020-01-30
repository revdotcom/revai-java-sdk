package revai;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiTranscript;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An ApiClient object contains all the endpoints that send and retrieve information from the Rev.AI
 * API using the Retrofit HTTP client.
 */
public class ApiClient {
  private static String accessToken;

  private Retrofit retrofit;
  private OkHttpClient client;
  public ApiInterface apiInterface;

  /*
  Helper function: reads the current sdk version from pom.xml
   */
  private static String getSdkVersion() throws IOException, XmlPullParserException {
    // reads the current sdk version from pom.xml
    MavenXpp3Reader reader = new MavenXpp3Reader();
    Model model = reader.read(new FileReader("pom.xml"));
    return model.getVersion();
  }

  /*
  Helper function: manually closes the connection when the code is running in a JVM
   */
  public void closeConnection() {
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
  }

  public ApiClient(String accessToken) throws IOException, XmlPullParserException {
    if (accessToken == null) {
      throw new IllegalArgumentException("Access token must be provided");
    }
    this.accessToken = accessToken;
    this.client =
        new OkHttpClient.Builder()
          .addNetworkInterceptor(new ApiInterceptor(accessToken, this.getSdkVersion()))
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
    Map<String, String> options = new HashMap<String, String>();
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

  public RevAiJob submitJobLocalFile(String filename, InputStream fileStream, RevAiJobOptions options) throws IOException {
    RequestBody fileRequest =
      FileStreamRequestBody.create(
        fileStream,
        MediaType.parse("audio/*")
      );

    MultipartBody.Part filePart = MultipartBody.Part.createFormData("media", filename, fileRequest);
    return apiInterface.sendJobLocalFile(filePart, options).execute().body();
  }

  public RevAiJob submitJobLocalFile(InputStream fileStream, RevAiJobOptions options) throws IOException {
    RequestBody fileRequest =
      FileStreamRequestBody.create(
        fileStream,
        MediaType.parse("audio/*")
      );

    MultipartBody.Part filePart = MultipartBody.Part.createFormData("media", "INPUT MEDIA", fileRequest);
    return apiInterface.sendJobLocalFile(filePart, options).execute().body();
  }

  public String getCaptionText(String id, String contentType, Integer channelID) throws IOException {
    if (id == null) {
      throw new IllegalArgumentException("ID must be provided");
    }
    if (contentType == null){
      contentType = "application/x-subrip";
    }
    String query = "";
    if (channelID != null){
      query = "?speaker_channel=" + channelID;
    }
    Map<String, String> contentHeader = new HashMap<String, String>();
    contentHeader.put("Accept", contentType);
    return apiInterface.getCaptionText(id, query, contentHeader).execute().body();
  }
}
