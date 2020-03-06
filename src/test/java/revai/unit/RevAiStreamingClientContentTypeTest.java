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
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class RevAiStreamingClientContentTypeTest {

  private StreamingClient streamingClient;
  private MockWebServer mockWebServer;
  private static final String CONTENT_TYPE = "audio/x-raw";
  private static final String LAYOUT = "interleaved";
  private static final Integer RATE = 16000;
  private static final String FORMAT = "S16LE";
  private static final Integer CHANNELS = 1;
  private String contentType;
  private String layout;
  private Integer rate;
  private String format;
  private Integer channels;

  public RevAiStreamingClientContentTypeTest(
      String contentType, String layout, Integer rate, String format, Integer channels) {
    this.contentType = contentType;
    this.layout = layout;
    this.rate = rate;
    this.format = format;
    this.channels = channels;
  }

  @Parameterized.Parameters
  public static Collection input() {
    return Arrays.asList(
        new Object[][] {
          {CONTENT_TYPE, LAYOUT, RATE, FORMAT, CHANNELS},
          {CONTENT_TYPE, null, null, null, null},
          {null, LAYOUT, null, null, null},
          {null, null, RATE, null, null},
          {null, null, null, FORMAT, null},
          {null, null, null, null, CHANNELS},
          {null, null, null, null, null}
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

    StreamContentType streamContentType = new StreamContentType();
    streamContentType.setContentType(contentType);
    streamContentType.setLayout(layout);
    streamContentType.setRate(rate);
    streamContentType.setFormat(format);
    streamContentType.setChannels(channels);

    streamingClient = new StreamingClient(EnvHelper.getToken(), streamContentType);
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
  public void StreamingClient_WithParameterizedContent_ContainsContentInUrl()
      throws URISyntaxException {

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
    assertContentType(request);
  }

  private void assertContentType(RecordedRequest request) {
    if (contentType != null) {
      assertThat(request.getPath()).contains("content_type=" + contentType);
    }
    if (layout != null) {
      assertThat(request.getPath()).contains("layout=" + layout);
    }
    if (rate != null) {
      assertThat(request.getPath()).contains("rate=" + rate);
    }
    if (format != null) {
      assertThat(request.getPath()).contains("format=" + format);
    }
    if (channels != null) {
      assertThat(request.getPath()).contains("channels=" + channels);
    }
    assertThat(request.getPath()).contains("access_token=" + EnvHelper.getToken());
  }
}
