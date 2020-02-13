package revai.unit;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okio.Buffer;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.ApiClient;
import revai.ApiInterface;
import revai.MockInterceptor;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RevAiJobTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient mockApiClient;

  private final String JOB_ID = "testingID";
  private final String CREATED_ON = "2020-01-22T11:10:22.29Z";
  private final String COMPLETED_ON = "2020-01-22T11:13:22.29Z";
  private final String SAMPLE_FILENAME = "sampleAudio.mp3";
  private final String FORM_CONTENT_TYPE = "form-data";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final String JOBS_URL = "https://api.rev.ai/revspeech/v1/jobs";
  private Gson gson;
  private RevAiJob mockInProgressJob;
  private RevAiJob mockCompletedJob;

  @Before
  public void setup() {
    gson = new Gson();

    mockInProgressJob = new RevAiJob();
    mockInProgressJob.setJobID(JOB_ID);
    mockInProgressJob.setCreatedOn(CREATED_ON);
    mockInProgressJob.setName(SAMPLE_FILENAME);
    mockInProgressJob.setJobStatus(RevAiJobStatus.in_progress);
    mockInProgressJob.setDurationSeconds(107.04);
    mockInProgressJob.setType("async");

    mockCompletedJob = mockInProgressJob;
    mockCompletedJob.setJobStatus(RevAiJobStatus.transcribed);
    mockCompletedJob.setCompletedOn(COMPLETED_ON);

    mockApiClient = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/revspeech/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();

    mockApiClient.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void getJobDetailsTest() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    RevAiJob revAiJob = mockApiClient.getJobDetails(JOB_ID);

    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL + "/" + JOB_ID);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void getJobListTest() throws IOException {
    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<RevAiJob> revAiJobs = mockApiClient.getListOfJobs(null, null);

    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).contains(JOBS_URL);
    assertThat(revAiJobs.size()).isEqualTo(mockJobList.size());
    assertJobsList(revAiJobs);
  }

  @Test
  public void getJobListLimitTest() throws IOException {
    Integer SAMPLE_LIMIT = 1;

    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<RevAiJob> revAiJobs = mockApiClient.getListOfJobs(SAMPLE_LIMIT, null);

    HttpUrl url = mockInterceptor.request.url();
    Assert.assertEquals(url.queryParameter("limit"), SAMPLE_LIMIT.toString());
    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).contains(JOBS_URL);
    assertThat(revAiJobs.size()).isEqualTo(1);
    assertThat(gson.toJson(revAiJobs.get(0))).isEqualTo(gson.toJson(mockCompletedJob));
  }

  @Test
  public void getJobStartAfterTest() throws IOException {
    String sampleID = "sampleID";

    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<RevAiJob> revAiJobs = mockApiClient.getListOfJobs(null, sampleID);

    HttpUrl url = mockInterceptor.request.url();
    Assert.assertEquals(url.queryParameter("starting_after"), sampleID);
    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).contains(JOBS_URL);
    assertJobsList(revAiJobs);
  }

  @Test
  public void submitJobUrlTest() throws IOException {
    String SAMPLE_MEDIA_URL = "sample-url.com";
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    RevAiJob revAiJob = mockApiClient.submitJobUrl(SAMPLE_MEDIA_URL, null);

    Buffer buffer = new Buffer();
    mockInterceptor.request.body().writeTo(buffer);
    JSONObject requestBody = new JSONObject(buffer.readUtf8());
    Assert.assertEquals(requestBody.get("media_url"), SAMPLE_MEDIA_URL);
    assertThat(mockInterceptor.request.method()).isEqualTo("POST");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void submitJobLocalFileTest() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    String filePath = "src/test/java/revai/resources/sampleAudio.mp3";
    RevAiJob revAiJob = mockApiClient.submitJobLocalFile(filePath, null);

    MultipartBody body = (MultipartBody) mockInterceptor.request.body();
    String headers = body.part(0).headers().toString();
    assertThat(headers).contains(SAMPLE_FILENAME);
    assertThat(headers).contains(FORM_CONTENT_TYPE);
    assertThat(mockInterceptor.request.method()).isEqualTo("POST");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void deleteJobTest() throws IOException {
    mockInterceptor.setResponseCode(204);
    mockInterceptor.setSampleResponse("");
    mockApiClient.deleteJob(JOB_ID);

    Assert.assertEquals(mockInterceptor.request.method(), "DELETE");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL + "/" + JOB_ID);
  }

  public void assertJobsList(List<RevAiJob> revAiJobs) {
    revAiJobs.forEach(
        job -> {
          if (job.getJobStatus().equals(RevAiJobStatus.transcribed)) {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockCompletedJob));
          } else {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockInProgressJob));
          }
        });
  }
}
