package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.NlpModel;
import ai.rev.speechtotext.models.asynchronous.*;
import ai.rev.testutils.EnvHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitJobTest {

  private final String LOCAL_FILE = "./src/test/java/ai/rev/speechtotext/resources/sampleAudio.mp3";
  private final String SOURCE_URL = "https://www.rev.ai/FTC_Sample_1.mp3";
  private final String CALLBACK_URL = "https://example.com";
  private static ApiClient apiClient;

  @Rule public TestName testName = new TestName();

  @Before
  public void setup() {
    apiClient = new ApiClient(EnvHelper.getToken());
  }

  @Test
  public void SubmitJobLocalFile_FilePathAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();

    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_OnlyFilePathIsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, null);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void
      SubmitJobLocalFile_InputStreamAndFileNameAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
          throws IOException {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob =
        apiClient.submitJobLocalFile(fileInputStream, file.getName(), revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamAndFileNameAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, file.getName(), null);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_InputStreamAndOptionsAreSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, null, revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobLocalFile_OnlyInputStreamIsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    File file = new File(LOCAL_FILE);
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not find file [" + file.getName() + "]");
    }

    RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, null, null);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobUrl_OptionsOnly_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();
    revAiJobOptions.setSourceConfig(SOURCE_URL);
    revAiJobOptions.setNotificationConfig(CALLBACK_URL);

    RevAiJob revAiJob = apiClient.submitJobUrl(revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobUrl_OptionsOnlyWithCallback_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();
    revAiJobOptions.setSourceConfig(SOURCE_URL);
    revAiJobOptions.setCallbackUrl(CALLBACK_URL);

    RevAiJob revAiJob = apiClient.submitJobUrl(revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobUrl_UrlAndOptionsSpecified_ReturnsRevAiJobInProgress()
      throws IOException {
    RevAiJobOptions revAiJobOptions = getJobOptions();

    RevAiJob revAiJob = apiClient.submitJobUrl(SOURCE_URL, revAiJobOptions);

    assertRevAiJob(revAiJob);
  }

  @Test
  public void SubmitJobUrl_OnlyUrlIsSpecified_ReturnsRevAiJobInProgress() throws IOException {
    RevAiJob revAiJob = apiClient.submitJobUrl(SOURCE_URL);

    assertRevAiJob(revAiJob);
  }
  @Test
  public void SubmitJobLocalFile_SummarizationOptionsSpecified_ReturnsRevAiJobInProgress()
          throws IOException, InterruptedException {

    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    revAiJobOptions.setDeleteAfterSeconds(50000);
    revAiJobOptions.setLanguage("en");

    revAiJobOptions.setSummarizationOptions(new SummarizationOptions()
            .setType(SummarizationFormattingOptions.BULLETS)
            .setModel(NlpModel.PREMIUM)
            .setPrompt("Try to summarize this transcript as good as you possibly can")
    );

    revAiJobOptions.setTranslationOptions(new TranslationOptions(Arrays.asList(
                    new TranslationLanguageOptions("es")
                            .setModel(NlpModel.PREMIUM),
                    new TranslationLanguageOptions("ru")
    )));


    RevAiJob revAiJob = apiClient.submitJobLocalFile(LOCAL_FILE, revAiJobOptions);

    assertRevAiJob(revAiJob);
    assertThat(revAiJob.getSummarization()).isNotNull();
    assertThat(revAiJob.getSummarization().getModel()).isEqualTo(NlpModel.PREMIUM);
    assertThat(revAiJob.getSummarization().getType()).isEqualTo(SummarizationFormattingOptions.BULLETS);
    assertThat(revAiJob.getSummarization().getPrompt()).isEqualTo("Try to summarize this transcript as good as you possibly can");

    assertThat(revAiJob.getTranslation()).isNotNull();
    assertThat(revAiJob.getTranslation().getTargetLanguages()).isNotNull();
    assertThat(revAiJob.getTranslation().getTargetLanguages().size()).isEqualTo(2);

    while(revAiJob != null && (
            revAiJob.getSummarization().getJobStatus() == SummarizationJobStatus.IN_PROGRESS ||
            revAiJob.getTranslation().getTargetLanguages().get(0).getJobStatus() == TranslationJobStatus.IN_PROGRESS ||
            revAiJob.getTranslation().getTargetLanguages().get(1).getJobStatus() == TranslationJobStatus.IN_PROGRESS
        )
    )
    {
      Thread.sleep(5000);
      revAiJob = apiClient.getJobDetails(revAiJob.getJobId());
    }
    assertThat(revAiJob.getJobStatus()).isEqualTo(RevAiJobStatus.TRANSCRIBED);
    assertThat(revAiJob.getSummarization().getJobStatus()).isEqualTo(SummarizationJobStatus.COMPLETED);

    assertThat(revAiJob.getTranslation().getCompletedOn()).isNotNull();
    assertThat(revAiJob.getTranslation().getTargetLanguages().get(0).getJobStatus()).isEqualTo(TranslationJobStatus.COMPLETED);
    assertThat(revAiJob.getTranslation().getTargetLanguages().get(0).getLanguage()).isEqualTo("es");
    assertThat(revAiJob.getTranslation().getTargetLanguages().get(0).getModel()).isEqualTo(NlpModel.PREMIUM);

    assertThat(revAiJob.getTranslation().getTargetLanguages().get(1).getJobStatus()).isEqualTo(TranslationJobStatus.COMPLETED);

    String summary = apiClient.getTranscriptSummaryText(revAiJob.getJobId());
    assertThat(summary).isNotNull();

    Summary summaryObject = apiClient.getTranscriptSummaryObject(revAiJob.getJobId());
    assertThat(summaryObject).isNotNull();

    String translationString1 = apiClient.getTranslatedTranscriptText(revAiJob.getJobId(),"es");
    assertThat(translationString1).isNotNull();

    String translationString2 = apiClient.getTranslatedTranscriptText(revAiJob.getJobId(),"ru");
    assertThat(translationString2).isNotNull();


    RevAiTranscript translationObject1 = apiClient.getTranslatedTranscriptObject(revAiJob.getJobId(),"es");
    assertThat(translationObject1).isNotNull();

    RevAiTranscript translationObject2 = apiClient.getTranslatedTranscriptObject(revAiJob.getJobId(),"ru");
    assertThat(translationObject2).isNotNull();

    byte[] buf = new byte[1024];

    InputStream translatedCaptionsStream1 = apiClient.getTranslatedCaptions(revAiJob.getJobId(),"es",RevAiCaptionType.SRT,0);
    assertThat(translatedCaptionsStream1).isNotNull();
    int nRead = translatedCaptionsStream1.read(buf);
    String s = new String(buf);
    InputStream translatedCaptionsStream2 = apiClient.getTranslatedCaptions(revAiJob.getJobId(),"ru",RevAiCaptionType.SRT,0);
    nRead = translatedCaptionsStream2.read(buf);
    s = new String(buf);
    assertThat(translatedCaptionsStream2).isNotNull();



  }
  public void assertRevAiJob(RevAiJob revAiJob) {
    assertThat(revAiJob.getJobId()).as("Job Id").isNotNull();
    assertThat(revAiJob.getJobStatus()).as("Job status").isEqualTo(RevAiJobStatus.IN_PROGRESS);
  }

  private RevAiJobOptions getJobOptions() {
    RevAiJobOptions revAiJobOptions = new RevAiJobOptions();
    revAiJobOptions.setMetadata(testName.getMethodName());
    revAiJobOptions.setFilterProfanity(true);
    revAiJobOptions.setRemoveDisfluencies(true);
    revAiJobOptions.setSkipPunctuation(true);
    revAiJobOptions.setSkipDiarization(true);
    revAiJobOptions.setSkipPostprocessing(true);
    revAiJobOptions.setSpeakerChannelsCount(null);
    revAiJobOptions.setDeleteAfterSeconds(0);
    revAiJobOptions.setLanguage("en");
    revAiJobOptions.setTranscriber("machine_v2");
    return revAiJobOptions;
  }
}
