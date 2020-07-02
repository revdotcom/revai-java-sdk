package ai.rev.speechtotext.unit;

import ai.rev.speechtotext.RevAiWebSocketListener;
import ai.rev.speechtotext.StreamingClient;
import ai.rev.speechtotext.models.streaming.SessionConfig;
import ai.rev.speechtotext.models.streaming.StreamContentType;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Theories.class)
public class RevAiStreamingClientOptionalParametersTest {

  private static final String VOCAB_ID = "VocabId";
  private static final String METADATA = "Best Metadata";
  private static final String FAKE_ACCESS_TOKEN = "foo";

  private MockWebServer mockWebServer;
  private StreamContentType defaultContentType;
  private static StreamingClient streamingClient;
  private SessionConfig sessionConfig = new SessionConfig();

  @DataPoints("metadataAndNull")
  public static String[] metadataAndNull() {
    return new String[] {METADATA, null};
  }

  @DataPoints("customVocabularyIdAndNull")
  public static String[] vocabIdAndNull() {
    return new String[] {VOCAB_ID, null};
  }

  @DataPoints("booleanValuesAndNull")
  public static Boolean[] booleanValuesAndNull() {
    return new Boolean[] {true, false, null};
  }

  public RevAiStreamingClientOptionalParametersTest() {
    this.defaultContentType = new StreamContentType();
    defaultContentType.setContentType("audio/x-raw");
    defaultContentType.setLayout("interleaved");
    defaultContentType.setFormat("S16LE");
    defaultContentType.setRate(16000);
    defaultContentType.setChannels(1);
  }

  @Before
  public void setup() {
    mockWebServer = new MockWebServer();
    mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {}));
    try {
      mockWebServer.start();
    } catch (IOException e) {
      throw new RuntimeException("Unable to start Mock Web Server");
    }
    streamingClient = new StreamingClient(FAKE_ACCESS_TOKEN);
    streamingClient.setScheme("http");
    streamingClient.setHost(mockWebServer.getHostName());
    streamingClient.setPort(mockWebServer.getPort());
  }

  @After
  public void tearDown() {
    try {
      mockWebServer.close();
    } catch (IOException e) {
      throw new RuntimeException("Unable to close Mock Web Server");
    }
  }

  @Theory
  public void StreamingClient_CustomVocabularyIdIsNull_ContainsParametersInUrl(
      @FromDataPoints("metadataAndNull") String metadata,
      @FromDataPoints("customVocabularyIdAndNull") String customVocabularyId,
      @FromDataPoints("booleanValuesAndNull") Boolean filterProfanity,
      @FromDataPoints("booleanValuesAndNull") Boolean removeDisfluencies)
      throws UnsupportedEncodingException {
    sessionConfig.setMetaData(metadata);
    sessionConfig.setFilterProfanity(filterProfanity);
    sessionConfig.setCustomVocabularyId(customVocabularyId);
    sessionConfig.setRemoveDisfluencies(removeDisfluencies);

    streamingClient.connect(
        Mockito.mock(RevAiWebSocketListener.class), defaultContentType, sessionConfig);
    RecordedRequest request;

    try {
      request = mockWebServer.takeRequest(1000, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    if (request == null) {
      throw new RuntimeException("No request was found");
    }
    assertStreamOptions(request);
  }

  private void assertStreamOptions(RecordedRequest request) throws UnsupportedEncodingException {
    if (sessionConfig.getRemoveDisfluencies() != null) {
      assertThat(request.getPath())
          .contains("remove_disfluencies=" + sessionConfig.getRemoveDisfluencies());
    }
    if (sessionConfig.getFilterProfanity() != null) {
      assertThat(request.getPath())
          .contains("filter_profanity=" + sessionConfig.getFilterProfanity());
    }
    if (sessionConfig.getCustomVocabularyId() != null) {
      assertThat(request.getPath())
          .contains("custom_vocabulary_id=" + sessionConfig.getCustomVocabularyId());
    }
    if (sessionConfig.getMetaData() != null) {
      assertThat(
          URLDecoder.decode(request.getPath(), "UTF8")
              .contains("metadata=" + sessionConfig.getMetaData()));
    }
    assertThat(request.getPath()).contains("access_token=" + FAKE_ACCESS_TOKEN);
  }
}
