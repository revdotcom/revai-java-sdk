package ai.rev.topicextraction.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.speechtotext.models.asynchronous.RevAiJobType;
import ai.rev.speechtotext.models.asynchronous.RevAiTranscript;
import ai.rev.testutils.AssertHelper;
import ai.rev.topicextraction.TopicExtractionClient;
import ai.rev.topicextraction.TopicExtractionInterface;
import ai.rev.topicextraction.models.TopicExtractionJob;
import ai.rev.topicextraction.models.TopicExtractionJobOptions;
import ai.rev.topicextraction.models.TopicExtractionJobStatus;
import com.google.gson.Gson;
import okhttp3.MediaType;
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

public class TopicExtractionJobTest {
  private OkHttpClient mockOkHttpClient;
  private MockInterceptor mockInterceptor;
  private TopicExtractionClient sut;

  private final String JOB_ID = "testingID";
  private final String CREATED_ON = "2020-01-22T11:10:22.29Z";
  private final String COMPLETED_ON = "2020-01-22T11:13:22.29Z";
  private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private final String JOBS_URL = "https://api.rev.ai/topic_extraction/v1beta/jobs";
  private final String METADATA = "unit test";
  private Gson gson;
  private TopicExtractionJob mockInProgressJob;
  private TopicExtractionJob mockCompletedJob;

  @Before
  public void setup() {
    gson = new Gson();

    mockInProgressJob = new TopicExtractionJob();
    mockInProgressJob.setJobId(JOB_ID);
    mockInProgressJob.setCreatedOn(CREATED_ON);
    mockInProgressJob.setJobStatus(TopicExtractionJobStatus.IN_PROGRESS);
    mockInProgressJob.setType(RevAiJobType.TOPICEXTRACTION);
    mockInProgressJob.setMetadata(METADATA);

    mockCompletedJob = mockInProgressJob;
    mockCompletedJob.setJobStatus(TopicExtractionJobStatus.COMPLETED);
    mockCompletedJob.setCompletedOn(COMPLETED_ON);

    sut = new TopicExtractionClient("validToken");
    mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
    mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
    Retrofit mockRetrofit =
        new Retrofit.Builder()
            .baseUrl("https://api.rev.ai/topic_extraction/v1beta/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mockOkHttpClient)
            .build();

    sut.apiInterface = mockRetrofit.create(TopicExtractionInterface.class);
  }

  @Test
  public void GetJobDetails_JobIdIsValid_ReturnsRevAiJob() throws IOException {
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    TopicExtractionJob job = sut.getJobDetails(JOB_ID);

    String expectedUrl = JOBS_URL + "/" + JOB_ID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
    assertRevAiJob(job, mockInProgressJob);
  }

  @Test
  public void GetListOfJobs_NoArguments_ReturnsAListOfRevAiJobs() throws IOException {
    List<TopicExtractionJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<TopicExtractionJob> jobs = sut.getListOfJobs();

    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", JOBS_URL);
    assertThat(jobs.size()).isEqualTo(mockJobList.size());
    assertJobsList(jobs);
  }

  @Test
  public void GetListOfJobs_JobLimitIsOne_ReturnsARevAiJobListSizeOfOne() throws IOException {
    Integer SAMPLE_LIMIT = 1;
    List<TopicExtractionJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<TopicExtractionJob> jobs = sut.getListOfJobs(SAMPLE_LIMIT);

    String expectedUrl = JOBS_URL + "?limit=" + SAMPLE_LIMIT;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
    assertThat(jobs.size()).isEqualTo(1);
    assertRevAiJob(jobs.get(0), mockCompletedJob);
  }

  @Test
  public void GetListOfJobs_StartAfterIsSpecified_ReturnsAListOfRevAiJobs() throws IOException {
    String sampleID = "sampleID";
    List<TopicExtractionJob> mockJobList = new ArrayList<>();
    mockJobList.add(mockInProgressJob);
    mockJobList.add(mockCompletedJob);
    mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

    List<TopicExtractionJob> revAiJobs = sut.getListOfJobs(sampleID);

    String expectedUrl = JOBS_URL + "?starting_after=" + sampleID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
    assertJobsList(revAiJobs);
  }

  @Test
  public void SubmitJobText_OnlyTextIsSpecified_ReturnsARevAiJob() throws IOException {
    String SAMPLE_TEXT = "sample";
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    TopicExtractionJob revAiJob = sut.submitJobText(SAMPLE_TEXT, null);

    TopicExtractionJobOptions options = new TopicExtractionJobOptions();
    options.setText(SAMPLE_TEXT);
    AssertHelper.assertRequestBody(mockInterceptor, options, TopicExtractionJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobText_TextAndOptionsAreSpecified_ReturnsARevAiJob() throws IOException {
    String SAMPLE_TEXT = "sample";
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    TopicExtractionJobOptions options = new TopicExtractionJobOptions();
    options.setCallbackUrl("https://example.com");
    options.setMetadata(METADATA);
    options.setDeleteAfterSeconds(0);

    TopicExtractionJob revAiJob = sut.submitJobText(SAMPLE_TEXT, options);

    AssertHelper.assertRequestBody(mockInterceptor, options, TopicExtractionJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobText_TextIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobText(null, null));
  }

  @Test
  public void SubmitJobJson_OnlyJsonIsSpecified_ReturnsARevAiJob() throws IOException {
    RevAiTranscript SAMPLE_JSON = new RevAiTranscript();
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

    TopicExtractionJob revAiJob = sut.submitJobJson(SAMPLE_JSON);

    TopicExtractionJobOptions options = new TopicExtractionJobOptions();
    options.setJson(SAMPLE_JSON);
    AssertHelper.assertRequestBody(mockInterceptor, options, TopicExtractionJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobJson_JsonAndOptionsAreSpecified_ReturnsARevAiJob()
      throws IOException {
    RevAiTranscript SAMPLE_JSON = new RevAiTranscript();
    mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
    TopicExtractionJobOptions options = new TopicExtractionJobOptions();

    TopicExtractionJob revAiJob = sut.submitJobJson(SAMPLE_JSON, options);

    AssertHelper.assertRequestBody(mockInterceptor, options, TopicExtractionJobOptions.class);
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
    assertRevAiJob(revAiJob, mockInProgressJob);
  }

  @Test
  public void SubmitJobJson_JsonIsNotSpecified_ReturnsIllegalArgumentException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> sut.submitJobJson(null, null));
  }

  @Test
  public void DeleteJob_JobIdIsValid_DoesNotCauseErrors() throws IOException {
    mockInterceptor.setResponseCode(204);
    mockInterceptor.setSampleResponse("");

    sut.deleteJob(JOB_ID);

    String expectedUrl = JOBS_URL + "/" + JOB_ID;
    AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "DELETE", expectedUrl);
  }

  private void assertJobsList(List<TopicExtractionJob> jobs) {
    jobs.forEach(
        job -> {
          if (job.getJobStatus().equals(TopicExtractionJobStatus.COMPLETED)) {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockCompletedJob));
          } else {
            assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockInProgressJob));
          }
        });
  }

  private void assertRevAiJob(TopicExtractionJob actualJob, TopicExtractionJob expectedJob) {
    assertThat(gson.toJson(actualJob)).as("TopicExtractionJob").isEqualTo(gson.toJson(expectedJob));
  }
}
