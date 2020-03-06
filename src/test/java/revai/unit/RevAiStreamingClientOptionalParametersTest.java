package revai.unit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import revai.StreamContentType;
import revai.StreamingClient;
import revai.integration.EnvHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

@RunWith(Parameterized.class)
public class RevAiStreamingClientOptionalParametersTest {

  private MockWebServer mockWebServer;
  private StreamContentType defaultContentType;
  private static StreamingClient streamingClient;
  private static final String VOCAB_ID = "VocabId";
  private static final String METADATA = "Best Metadata";
  private String metadata;
  private String customVocabularyId;
  private Boolean filterProfanity;

  public RevAiStreamingClientOptionalParametersTest(
      String metadata, String customVocabularyId, Boolean filterProfanity) {
    this.metadata = metadata;
    this.customVocabularyId = customVocabularyId;
    this.filterProfanity = filterProfanity;
    this.defaultContentType = new StreamContentType();
    defaultContentType.setContentType("audio/x-raw");
    defaultContentType.setLayout("interleaved");
    defaultContentType.setFormat("S16LE");
    defaultContentType.setRate(16000);
    defaultContentType.setChannels(1);
  }

  @Parameterized.Parameters
  public static Collection input() {
    return Arrays.asList(
        new Object[][] {
          {METADATA, VOCAB_ID, true},
          {METADATA, VOCAB_ID, false},
          {METADATA, null, true},
          {METADATA, VOCAB_ID, null},
          {null, VOCAB_ID, true},
          {null, VOCAB_ID, false},
          {null, VOCAB_ID, null},
          {null, null, true},
          {null, null, false},
          {null, null, null}
        });
  }

  @Before
  public void setup() {
    mockWebServer = new MockWebServer();
    mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new MockListener()));
    try {
      mockWebServer.start();
    } catch (IOException e) {
      throw new RuntimeException("Unable to start Mock Web Server");
    }
    streamingClient = new StreamingClient(EnvHelper.getToken(), defaultContentType);
    streamingClient.setScheme("http");
    streamingClient.setHost(mockWebServer.getHostName() + ":" + mockWebServer.getPort());
  }

  @After
  public void tearDown() {
    try {
      mockWebServer.close();
    } catch (IOException e) {
      throw new RuntimeException("Unable to close Mock Web Server");
    }
  }

  @Test
  public void StreamingClient_WithParameterizedVariables_ContainsParametersInUrl()
      throws URISyntaxException, UnsupportedEncodingException {
    streamingClient.setMetadata(metadata);
    streamingClient.setFilterProfanity(filterProfanity);
    streamingClient.setCustomVocabularyId(customVocabularyId);

    streamingClient.connect(new MockListener());
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
    if (filterProfanity != null) {
      assertThat(request.getPath()).contains("filter_profanity=" + filterProfanity);
    }
    if (customVocabularyId != null) {
      assertThat(request.getPath()).contains("custom_vocabulary_id=" + customVocabularyId);
    }
    if (metadata != null) {
      assertThat(request.getPath()).contains("metadata=" + URLEncoder.encode(metadata, "UTF8"));
    }
    assertThat(request.getPath()).contains("access_token=" + EnvHelper.getToken());
  }
}
