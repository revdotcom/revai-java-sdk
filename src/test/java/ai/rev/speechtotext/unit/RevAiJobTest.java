package ai.rev.speechtotext.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.ApiInterface;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import ai.rev.speechtotext.models.asynchronous.RevAiJobOptions;
import ai.rev.speechtotext.models.asynchronous.RevAiJobStatus;
import ai.rev.speechtotext.models.asynchronous.RevAiJobType;
import ai.rev.testutils.AssertHelper;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
  private final String METADATA = "unit test";
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
    mockInProgressJob.setMetadata(METADATA);

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

    sut.apiInterface = mockRetrofit.create(ApiInterface.class);
  }

  @Test
  public void GetJobDetails_JobIdIsValid_ReturnsRevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    RevAiJob revAiJob = sut.getJobDetails(JOB_ID);

    String expectedUrl = JOBS_URL + "/" + JOB_ID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void GetListOfJobs_NoArguments_ReturnsAListOfRevAiJobs() throws IOException {
    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<RevAiJob> revAiJobs = sut.getListOfJobs();

    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", JOBS_URL);
    assertThat(revAiJobs.size()).isEqualTo(mockJobList.size());
    assertJobsList(revAiJobs);
  }

  @Test
  public void GetListOfJobs_JobLimitIsOne_ReturnsARevAiJobListSizeOfOne() throws IOException {
    Integer SAMPLE_LIMIT = 1;
    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<RevAiJob> revAiJobs = sut.getListOfJobs(SAMPLE_LIMIT);

    String expectedUrl = JOBS_URL + "?limit=" + SAMPLE_LIMIT;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
    assertThat(revAiJobs.size()).isEqualTo(1);
    assertRevAiJob(revAiJobs.get(0), mockCompletedJob);
  }

  @Test
  public void GetListOfJobs_StartAfterIsSpecified_ReturnsAListOfRevAiJobs() throws IOException {
    String sampleID = "sampleID";
    List<RevAiJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<RevAiJob> revAiJobs = sut.getListOfJobs(sampleID);

    String expectedUrl = JOBS_URL + "?starting_after=" + sampleID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
    assertJobsList(revAiJobs);
  }

  @Test
  public void SubmitJobUrl_OnlyUrlIsSpecified_ReturnsARevAiJob() throws IOException {
    String SAMPLE_MEDIA_URL = "sample-url.com";
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    RevAiJob revAiJob = sut.submitJobUrl(SAMPLE_MEDIA_URL);

    RevAiJobOptions options = new RevAiJobOptions();
    options.setSourceConfig(SAMPLE_MEDIA_URL, null);
    AssertHelper.assertRequestBody(mockInterceptor, options, RevAiJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobUrl_UrlAndOptionsAreSpecified_ReturnsARevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    RevAiJobOptions options = new RevAiJobOptions();
    options.setSourceConfig("sample-url.com");
    options.setFilterProfanity(true);
    options.setRemoveDisfluencies(true);
    options.setSkipPunctuation(true);
    options.setSkipDiarization(true);
    options.setNotificationConfig("https://example.com");
    options.setMetadata(METADATA);
    options.setSpeakerChannelsCount(2);
    options.setDeleteAfterSeconds(0);
    options.setLanguage("en");
    options.setTranscriber("machine_v2");

    RevAiJob revAiJob = sut.submitJobUrl(options);

    AssertHelper.assertRequestBody(mockInterceptor, options, RevAiJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobUrl_UrlAndOptionsAreSpecified_WithAuthHeaders_ReturnsARevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    RevAiJobOptions options = new RevAiJobOptions();
    options.setSourceConfig("sample-url.com", "Authentication: Bearer <token>");
    options.setFilterProfanity(true);
    options.setRemoveDisfluencies(true);
    options.setSkipPunctuation(true);
    options.setSkipDiarization(true);
    options.setNotificationConfig("https://example.com", "Authentication: Bearer <token>");
    options.setMetadata(METADATA);
    options.setSpeakerChannelsCount(2);
    options.setDeleteAfterSeconds(0);
    options.setLanguage("en");
    options.setTranscriber("machine_v2");

    RevAiJob revAiJob = sut.submitJobUrl(options);

    AssertHelper.assertRequestBody(mockInterceptor, options, RevAiJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobUrl_NullOptions_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sut.submitJobUrl((RevAiJobOptions) null));
  }

  @Test
  public void SubmitJobUrl_NullSourceConfig_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sut.submitJobUrl(new RevAiJobOptions()));
  }

  @Test
  public void SubmitJobUrl_NullSourceConfigUrl_ReturnsIllegalArgumentException() {
    RevAiJobOptions options = new RevAiJobOptions();
    options.setSourceConfig(null, null);
    assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sut.submitJobUrl(options));
  }

  @Test
  public void SubmitJobLocalFile_OnlyFilePathIsSpecified_ReturnsARevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    String filePath = "src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";

    RevAiJob revAiJob = sut.submitJobLocalFile(filePath, null);

    MultipartBody body = (MultipartBody) mockInterceptor.request.body();
    String headers = body.part(0).headers().toString();
    assertThat(headers).contains(SAMPLE_FILENAME);
    assertThat(headers).contains(FORM_CONTENT_TYPE);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobLocalFile_FilePathAndOptionsAreSpecified_ReturnsARevAiJob()
      throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    String filePath = "src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";
    RevAiJobOptions options = new RevAiJobOptions();

    RevAiJob revAiJob = sut.submitJobLocalFile(filePath, options);

    MultipartBody body = (MultipartBody) mockInterceptor.request.body();
    String headers = body.part(0).headers().toString();
    assertThat(headers).as("Headers include").contains(SAMPLE_FILENAME);
    assertThat(headers).as("Headers include").contains(FORM_CONTENT_TYPE);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobLocalFile(null, null, null));
  }

  @Test
  public void SubmitJobLocalFile_FilePathIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobLocalFile((String) null, null));
  }

  @Test
  public void DeleteJob_JobIdIsValid_DoesNotCauseErrors() throws IOException {
    mockInterceptor.setResponseCode(204);
    mockInterceptor.setSampleResponse("");

    sut.deleteJob(JOB_ID);

    String expectedUrl = JOBS_URL + "/" + JOB_ID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "DELETE", expectedUrl);
  }

  private void assertJobsList(List<RevAiJob> revAiJobs) {
    revAiJobs.forEach(
        job -> {
          if (job.getJobStatus().equals(RevAiJobStatus.TRANSCRIBED)) {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockCompletedJob));
          } else {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockInProgressJob));
          }
        });
  }

  private void assertRevAiJob(RevAiJob actualJob, RevAiJob expectedJob) {
    assertThat(gson.toJson(actualJob)).as("RevAiJob").isEqualTo(gson.toJson(expectedJob));
  }
}
