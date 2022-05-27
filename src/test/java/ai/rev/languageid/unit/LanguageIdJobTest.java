package ai.rev.languageid.unit;

import ai.rev.helpers.MockInterceptor;
import ai.rev.languageid.LanguageIdClient;
import ai.rev.languageid.LanguageIdInterface;
import ai.rev.languageid.models.LanguageIdJob;
import ai.rev.languageid.models.LanguageIdJobOptions;
import ai.rev.languageid.models.LanguageIdJobStatus;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LanguageIdJobTest {
    private OkHttpClient mockOkHttpClient;
    private MockInterceptor mockInterceptor;
    private LanguageIdClient sut;

    private final String JOB_ID = "testingID";
    private final String CREATED_ON = "2020-01-22T11:10:22.29Z";
    private final String COMPLETED_ON = "2020-01-22T11:13:22.29Z";
    private final String SAMPLE_FILENAME = "sampleAudio.mp3";
    private final String FORM_CONTENT_TYPE = "form-data";
    private final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
    private final String JOBS_URL = "https://api.rev.ai/languageid/v1/jobs";
    private final String METADATA = "unit test";
    private final String SOURCE_URL = "sample-url.com";
    private final Map<String, String> SOURCE_AUTH = Collections.singletonMap("Authorization", "Bearer <source token>");
    private final String NOTIFICATION_URL = "https://example.com";
    private final Map<String, String> NOTIFICATION_AUTH = Collections.singletonMap("Authorization", "Bearer <source token>");
    private Gson gson;
    private LanguageIdJob mockInProgressJob;
    private LanguageIdJob mockCompletedJob;

    @Before
    public void setup() {
        gson = new Gson();

        mockInProgressJob = new LanguageIdJob();
        mockInProgressJob.setJobId(JOB_ID);
        mockInProgressJob.setCreatedOn(CREATED_ON);
        mockInProgressJob.setJobStatus(LanguageIdJobStatus.IN_PROGRESS);
        mockInProgressJob.setType(RevAiJobType.LANGUAGEID);
        mockInProgressJob.setMetadata(METADATA);

        mockCompletedJob = mockInProgressJob;
        mockCompletedJob.setJobStatus(LanguageIdJobStatus.COMPLETED);
        mockCompletedJob.setCompletedOn(COMPLETED_ON);

        sut = new LanguageIdClient("validToken");
        mockInterceptor = new MockInterceptor(MEDIA_TYPE, 200);
        mockOkHttpClient = new OkHttpClient.Builder().addInterceptor(mockInterceptor).build();
        Retrofit mockRetrofit =
                new Retrofit.Builder()
                        .baseUrl("https://api.rev.ai/languageid/v1/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(mockOkHttpClient)
                        .build();

        sut.apiInterface = mockRetrofit.create(LanguageIdInterface.class);
    }

    @Test
    public void GetJobDetails_JobIdIsValid_ReturnsLanguageIdJob() throws IOException {
        mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));

        LanguageIdJob job = sut.getJobDetails(JOB_ID);

        String expectedUrl = JOBS_URL + "/" + JOB_ID;
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
        assertLanguageIdJob(job, mockInProgressJob);
    }

    @Test
    public void GetListOfJobs_NoArguments_ReturnsAListOfLanguageIdJobs() throws IOException {
        List<LanguageIdJob> mockJobList = new ArrayList<>();
        mockJobList.add(mockInProgressJob);
        mockJobList.add(mockCompletedJob);
        mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

        List<LanguageIdJob> jobs = sut.getListOfJobs();

        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", JOBS_URL);
        assertThat(jobs.size()).isEqualTo(mockJobList.size());
        assertJobsList(jobs);
    }

    @Test
    public void GetListOfJobs_JobLimitIsOne_ReturnsALanguageIdJobListSizeOfOne() throws IOException {
        Integer SAMPLE_LIMIT = 1;
        List<LanguageIdJob> mockJobList = new ArrayList<>();
        mockJobList.add(mockCompletedJob);
        mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

        List<LanguageIdJob> jobs = sut.getListOfJobs(SAMPLE_LIMIT);

        String expectedUrl = JOBS_URL + "?limit=" + SAMPLE_LIMIT;
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
        assertThat(jobs.size()).isEqualTo(1);
        assertLanguageIdJob(jobs.get(0), mockCompletedJob);
    }

    @Test
    public void GetListOfJobs_StartAfterIsSpecified_ReturnsAListOfLanguageIdJobs() throws IOException {
        String sampleID = "sampleID";
        List<LanguageIdJob> mockJobList = new ArrayList<>();
        mockJobList.add(mockInProgressJob);
        mockJobList.add(mockCompletedJob);
        mockInterceptor.setSampleResponse(gson.toJson(mockJobList));

        List<LanguageIdJob> languageIdJobs = sut.getListOfJobs(sampleID);

        String expectedUrl = JOBS_URL + "?starting_after=" + sampleID;
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "GET", expectedUrl);
        assertJobsList(languageIdJobs);
    }
    
    @Test
    public void SubmitJob_SourceConfigAndOptionsAreSpecified_ReturnsALanguageIdJob() throws IOException {
        mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
        LanguageIdJobOptions options = new LanguageIdJobOptions();
        options.setSourceConfig(SOURCE_URL);
        options.setNotificationConfig(NOTIFICATION_URL);
        options.setMetadata(METADATA);
        options.setDeleteAfterSeconds(0);

        LanguageIdJob languageIdJob = sut.submitJob(options);

        AssertHelper.assertRequestBody(mockInterceptor, options, LanguageIdJobOptions.class);
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
        assertLanguageIdJob(languageIdJob, mockInProgressJob);
    }

    @Test
    public void SubmitJob_SourceConfigAndOptionsAreSpecified_WithAuthHeaders_ReturnsALanguageIdJob() throws IOException {
        mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
        LanguageIdJobOptions options = new LanguageIdJobOptions();
        options.setSourceConfig(SOURCE_URL, SOURCE_AUTH);
        options.setNotificationConfig(NOTIFICATION_URL, NOTIFICATION_AUTH);
        options.setMetadata(METADATA);

        LanguageIdJob languageIdJob = sut.submitJob(options);

        AssertHelper.assertRequestBody(mockInterceptor, options, LanguageIdJobOptions.class);
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
        assertLanguageIdJob(languageIdJob, mockInProgressJob);
    }

    @Test
    public void SubmitJob_NullOptions_ReturnsIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.submitJob((LanguageIdJobOptions) null));
    }

    @Test
    public void SubmitJob_NullSourceConfig_ReturnsIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.submitJob(new LanguageIdJobOptions()));
    }

    @Test
    public void SubmitJob_NullSourceConfigUrl_ReturnsIllegalArgumentException() {
        LanguageIdJobOptions options = new LanguageIdJobOptions();
        options.setSourceConfig(null, null);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.submitJob(options));
    }

    @Test
    public void SubmitJobLocalFile_OnlyFilePathIsSpecified_ReturnsALanguageIdJob() throws IOException {
        mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
        String filePath = "src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";

        LanguageIdJob languageIdJob = sut.submitJobLocalFile(filePath, null);

        MultipartBody body = (MultipartBody) mockInterceptor.request.body();
        String headers = body.part(0).headers().toString();
        assertThat(headers).contains(SAMPLE_FILENAME);
        assertThat(headers).contains(FORM_CONTENT_TYPE);
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
        assertLanguageIdJob(languageIdJob, mockInProgressJob);
    }

    @Test
    public void SubmitJobLocalFile_FilePathAndOptionsAreSpecified_ReturnsALanguageIdJob() throws IOException {
        mockInterceptor.setSampleResponse(gson.toJson(mockInProgressJob));
        String filePath = "src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";
        LanguageIdJobOptions options = new LanguageIdJobOptions();

        LanguageIdJob languageIdJob = sut.submitJobLocalFile(filePath, options);

        MultipartBody body = (MultipartBody) mockInterceptor.request.body();
        String headers = body.part(0).headers().toString();
        assertThat(headers).as("Headers include").contains(SAMPLE_FILENAME);
        assertThat(headers).as("Headers include").contains(FORM_CONTENT_TYPE);
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "POST", JOBS_URL);
        assertLanguageIdJob(languageIdJob, mockInProgressJob);
    }

    @Test
    public void SubmitJobLocalFile_InputStreamIsNotSpecified_ReturnsIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> sut.submitJobLocalFile(null, null, null));
    }

    @Test
    public void DeleteJob_JobIdIsValid_DoesNotCauseErrors() throws IOException {
        mockInterceptor.setResponseCode(204);
        mockInterceptor.setSampleResponse("");

        sut.deleteJob(JOB_ID);

        String expectedUrl = JOBS_URL + "/" + JOB_ID;
        AssertHelper.assertRequestMethodAndUrl(mockInterceptor, "DELETE", expectedUrl);
    }

    private void assertJobsList(List<LanguageIdJob> jobs) {
        jobs.forEach(
                job -> {
                    if (job.getJobStatus().equals(LanguageIdJobStatus.COMPLETED)) {
                        assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockCompletedJob));
                    } else {
                        assertThat(gson.toJson(job)).isEqualTo(gson.toJson(mockInProgressJob));
                    }
                });
    }

    private void assertLanguageIdJob(LanguageIdJob actualJob, LanguageIdJob expectedJob) {
        assertThat(gson.toJson(actualJob)).as("LanguageIdJob").isEqualTo(gson.toJson(expectedJob));
    }
}
