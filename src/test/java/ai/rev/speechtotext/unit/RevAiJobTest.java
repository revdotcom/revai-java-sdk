package ai.rev.speechtotext.unit;

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
import ai.rev.speechtotext.clients.ApiClient;
import ai.rev.speechtotext.AsyncApiInterface;
import ai.rev.speechtotext.interceptors.MockInterceptor;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import ai.rev.speechtotext.models.asynchronous.RevAiJobType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RevAiJobTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private ApiClient sut;

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
    mockInProgressJob.setJobId(JOB_ID);
    mockInProgressJob.setCreatedOn(CREATED_ON);
    mockInProgressJob.setName(SAMPLE_FILENAME);
    mockInProgressJob.setJobStatus(RevAiJobStatus.IN_PROGRESS);
    mockInProgressJob.setDurationSeconds(107.04);
    mockInProgressJob.setType(RevAiJobType.ASYNC);

    mockCompletedJob = mockInProgressJob;
    mockCompletedJob.setJobStatus(RevAiJobStatus.TRANSCRIBED);
    mockCompletedJob.setCompletedOn(COMPLETED_ON);

    sut = new ApiClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/revspeech/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();

    sut.asyncApiInterface = mockRetrofit.create(AsyncApiInterface.class);
  }

  @Test
  public void GetJobDetails_WhenJobIdIsValid_ReturnsRevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    RevAiJob revAiJob = sut.getJobDetails(JOB_ID);

    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL + "/" + JOB_ID);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void GetListOfJobs_WhenNoArguments_ReturnsAListOfRevAiJobs() throws IOException {
    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);

    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));
    List<RevAiJob> revAiJobs = sut.getListOfJobs();

    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).contains(JOBS_URL);
    assertThat(revAiJobs.size()).isEqualTo(mockJobList.size());
    assertJobsList(revAiJobs);
  }

  @Test
  public void GetListOfJobs_WhenJobLimitIsOne_ReturnsARevAiJobListSizeOfOne() throws IOException {
    Integer SAMPLE_LIMIT = 1;

    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockCompletedJob);

    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));
    List<RevAiJob> revAiJobs = sut.getListOfJobs(SAMPLE_LIMIT);
    HttpUrl url = mockInterceptor.request.url();

    assertThat(url.queryParameter("limit")).isEqualTo(SAMPLE_LIMIT.toString());
    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).contains(JOBS_URL);
    assertThat(revAiJobs.size()).isEqualTo(1);
    assertThat(gson.toJson(revAiJobs.get(0))).isEqualTo(gson.toJson(mockCompletedJob));
  }

  @Test
  public void GetListOfJobs_WhenStartAfterIsSpecified_ReturnsAListOfRevAiJobs() throws IOException {
    String sampleID = "sampleID";

    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);

    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));
    List<RevAiJob> revAiJobs = sut.getListOfJobs(sampleID);
    HttpUrl url = mockInterceptor.request.url();

    assertThat(url.queryParameter("starting_after")).isEqualTo(sampleID);
    assertThat(mockInterceptor.request.method()).isEqualTo("GET");
    assertThat(mockInterceptor.request.url().toString()).contains(JOBS_URL);
    assertJobsList(revAiJobs);
  }

  @Test
  public void SubmitJobUrl_WhenOnlyUrlIsSpecified_ReturnsARevAiJob() throws IOException {
    String SAMPLE_MEDIA_URL = "sample-url.com";
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    RevAiJob revAiJob = sut.submitJobUrl(SAMPLE_MEDIA_URL);

    Buffer buffer = new Buffer();
    mockInterceptor.request.body().writeTo(buffer);
    JSONObject requestBody = new JSONObject(buffer.readUtf8());

    assertThat(requestBody.get("media_url")).isEqualTo(SAMPLE_MEDIA_URL);
    assertThat(mockInterceptor.request.method()).isEqualTo("POST");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void SubmitJobUrl_WhenUrlAndOptionsAreSpecified_ReturnsARevAiJob() throws IOException {
    String SAMPLE_MEDIA_URL = "sample-url.com";
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    RevAiJobOptions options = new RevAiJobOptions();
    options.setSkipPunctuation(true);

    RevAiJob revAiJob = sut.submitJobUrl(SAMPLE_MEDIA_URL, options);

    Buffer buffer = new Buffer();
    mockInterceptor.request.body().writeTo(buffer);
    JSONObject requestBody = new JSONObject(buffer.readUtf8());

    assertThat(requestBody.get("media_url")).isEqualTo(SAMPLE_MEDIA_URL);
    assertThat(mockInterceptor.request.method()).isEqualTo("POST");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void SubmitJobUrl_WhenJobUrlIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobUrl(null, null));
  }

  @Test
  public void SubmitJobLocalFile_WhenOnlyFilePathIsSpecified_ReturnsARevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    String filePath = "src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";

    RevAiJob revAiJob = sut.submitJobLocalFile(filePath, null);
    MultipartBody body = (MultipartBody) mockInterceptor.request.body();
    String headers = body.part(0).headers().toString();

    assertThat(headers).contains(SAMPLE_FILENAME);
    assertThat(headers).contains(FORM_CONTENT_TYPE);
    assertThat(mockInterceptor.request.method()).isEqualTo("POST");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void SubmitJobLocalFile_WhenFilePathAndOptionsAreSpecified_ReturnsARevAiJob()
      throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    String filePath = "src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";
    RevAiJobOptions options = new RevAiJobOptions();
    options.setSkipDiarization(true);

    RevAiJob revAiJob = sut.submitJobLocalFile(filePath, options);
    MultipartBody body = (MultipartBody) mockInterceptor.request.body();
    String headers = body.part(0).headers().toString();

    assertThat(headers).contains(SAMPLE_FILENAME);
    assertThat(headers).contains(FORM_CONTENT_TYPE);
    assertThat(mockInterceptor.request.method()).isEqualTo("POST");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL);
    assertThat(gson.toJson(revAiJob)).isEqualTo(gson.toJson(mockInProgressJob));
  }

  @Test
  public void SubmitJobLocalFile_WhenInputStreamIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobLocalFile(null, null, null));
  }

  @Test
  public void SubmitJobLocalFile_WhenFilePathIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobLocalFile((String) null, null));
  }

  @Test
  public void DeleteJob_WhenJobIdIsValid_DoesNotCauseErrors() throws IOException {
    mockInterceptor.setResponseCode(204);
    mockInterceptor.setSampleResponse("");
    sut.deleteJob(JOB_ID);

    Assert.assertEquals(mockInterceptor.request.method(), "DELETE");
    assertThat(mockInterceptor.request.url().toString()).isEqualTo(JOBS_URL + "/" + JOB_ID);
  }

  public void assertJobsList(List<RevAiJob> revAiJobs) {
    revAiJobs.forEach(
        job -> {
          if (job.getJobStatus().equals(RevAiJobStatus.TRANSCRIBED)) {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockCompletedJob));
          } else {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockInProgressJob));
          }
        });
  }
}
