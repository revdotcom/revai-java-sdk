package revai.unit;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okio.Buffer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.ApiClient;
import revai.ApiInterface;
import revai.MockInterceptor;
import revai.models.asynchronous.RevAiJob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevAiJobTest {
  @InjectMocks
  private OkHttpClient mockClient;
  private MockInterceptor mockInterceptor;

  // class to be tested
  private ApiClient sut;

  private final String JOB_ID = "testingID";
  private final String STATUS = "transcribed";
  private final String CREATED_ON = "2020-01-22T11:10:22.29Z";
  private final String SAMPLE_FILENAME = "sampleAudio.mp3";
  private final String SAMPLE_CONTENT_TYPE = "form-data";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private JSONObject sampleResponse;
  private JSONArray sampleJobList;
  private Gson gson;

  @Before
  public void setup() throws IOException, XmlPullParserException {
    gson = new Gson();

    Map<String, String> sampleJobA = new HashMap<String, String>();
    sampleJobA.put("id", JOB_ID);
    sampleJobA.put("status", STATUS);
    sampleJobA.put("created_on", CREATED_ON);

    Map<String, String> sampleJobB = new HashMap<String, String>();
    sampleJobB.put("id", "sampleJobB");
    sampleJobB.put("status", STATUS);
    sampleJobB.put("created_on", CREATED_ON);

    sampleResponse = new JSONObject(sampleJobA);
    sampleJobList = new JSONArray();
    sampleJobList.put(sampleJobA);
    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(sampleResponse.toString(), MEDIA_TYPE, 200);
    mockClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
      new Retrofit.Builder()
        .baseUrl("https://api.rev.ai/revspeech/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(mockClient)
        .build();
    sampleJobList.put(sampleJobA);

    sut.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void getJobDetailsTest() throws IOException {
    mockInterceptor.setSampleResponse(sampleResponse.toString());

    JSONObject mockResponse = new JSONObject(gson.toJson(sut.getJobDetails(JOB_ID)));

    Assert.assertTrue(sampleResponse.similar(mockResponse));
  }

  @Test
  public void getJobListTest() throws IOException {
    mockInterceptor.setSampleResponse(sampleJobList.toString());

    List<RevAiJob> mockJobList = sut.getListOfJobs(null, null);

    Assert.assertEquals(mockJobList.size(), sampleJobList.length());
    for (int i = 0; i < mockJobList.size(); i++) {
      Assert.assertTrue(
        new JSONObject(gson.toJson(mockJobList.get(i))).similar(sampleJobList.get(i)));
    }
  }

  @Test
  public void getJobListLimitTest() throws IOException {
    Integer SAMPLE_LIMIT = 1;
    mockInterceptor.setSampleResponse(sampleJobList.toString());

    sut.getListOfJobs(SAMPLE_LIMIT, null);

    HttpUrl url = mockInterceptor.request.url();
    Assert.assertEquals(url.queryParameter("limit"), SAMPLE_LIMIT.toString());
  }

  @Test
  public void getJobStartAfterTest() throws IOException {
    String SAMPLE_STARTING_JOB = "sampleID";
    mockInterceptor.setSampleResponse(sampleJobList.toString());

    sut.getListOfJobs(null, SAMPLE_STARTING_JOB);

    HttpUrl url = mockInterceptor.request.url();
    Assert.assertEquals(url.queryParameter("starting_after"), SAMPLE_STARTING_JOB);
  }

  @Test
  public void submitJobUrlTest() throws IOException {
    String SAMPLE_MEDIA_URL = "sample-url.com";
    mockInterceptor.setSampleResponse(sampleResponse.toString());

    sut.submitJobUrl(SAMPLE_MEDIA_URL, null);

    Buffer buffer = new Buffer();
    mockInterceptor.request.body().writeTo(buffer);
    JSONObject requestBody = new JSONObject(buffer.readUtf8());
    Assert.assertEquals(requestBody.get("media_url"), SAMPLE_MEDIA_URL);
  }

  @Test
  public void submitJobLocalFileTest() throws IOException {
    String filePath = "src/test/java/revai/resources/sampleAudio.mp3";
    mockInterceptor.setSampleResponse(sampleResponse.toString());

    sut.submitJobLocalFile(filePath, null);

    MultipartBody body = (MultipartBody) mockInterceptor.request.body();
    String headers = body.part(0).headers().toString();
    Assert.assertTrue(headers.contains(SAMPLE_FILENAME));
    Assert.assertTrue(headers.contains(SAMPLE_CONTENT_TYPE));
  }

  @Test
  public void deleteJobTest() throws IOException {
    sut.deleteJob(JOB_ID);

    Assert.assertEquals(mockInterceptor.request.method(), "DELETE");
  }
}
