package revai;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.http.client.utils.URIBuilder;
import revai.helpers.SDKHelper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamingClient {

  private String accessToken;
  private OkHttpClient client;
  private WebSocket webSocket;
  private String scheme;
  private String host;
  private StreamContentType streamContentType;
  private String metadata;
  private String customVocabularyId;
  private Boolean filterProfanity;

  /**
   * Constructs the streaming client that is used to establish a websocket connection with the
   * Rev.ai server and stream audio data. The user access token can be generated on the website at
   * https://www.rev.ai/access_token. To establish a successful connection a valid StreamContentType
   * must be provided. More info on the expected can be found at
   * https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Content-Type
   *
   * @param accessToken
   * @param streamContentType
   * @see StreamContentType
   */
  public StreamingClient(String accessToken, StreamContentType streamContentType) {
    this.accessToken = accessToken;
    this.streamContentType = streamContentType;
    this.client = setClient();
  }

  /**
   * This method is used for internal testing of the websocket connection against a mock webserver
   * and should not be used
   *
   * @param scheme
   */
  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHost() {
    return host;
  }

  /**
   * This method is used for internal testing and should not be used
   *
   * @param host
   */
  public void setHost(String host) {
    this.host = host;
  }

  public String getMetadata() {
    return metadata;
  }

  /**
   * Setting metadata is optional but helps to identify the stream.
   *
   * @param metadata String size restricted to <= 256 characters
   */
  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public String getCustomVocabularyId() {
    return customVocabularyId;
  }

  /**
   * Setting a custom vocabulary is optional. This method assigns a custom vocabulary to be used
   * during the stream. Custom vocabularies are submitted prior to usage in the stream and assigned
   * an Id.
   *
   * @param customVocabularyId
   */
  public void setCustomVocabularyId(String customVocabularyId) {
    this.customVocabularyId = customVocabularyId;
  }

  public Boolean getFilterProfanity() {
    return filterProfanity;
  }

  /**
   * Setting the profanity filter is optional. More info on the expected can be found at
   * https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Filter-Profanity
   *
   * @param filterProfanity This is false by default.
   */
  public void setFilterProfanity(Boolean filterProfanity) {
    this.filterProfanity = filterProfanity;
  }

  /**
   * Sends an HTTP request and upon authorization is upgraded to a websocket connection. Use
   * websocket listener to handle websocket events.
   *
   * @param revAiWebsocketListener
   * @see WebSocketListener
   * @throws URISyntaxException
   */
  public void connect(RevAiWebsocketListener revAiWebsocketListener) throws URISyntaxException {
    Listener listener = new Listener(revAiWebsocketListener);
    Request request = new Request.Builder().url(buildURL()).build();
    webSocket = client.newWebSocket(request, listener);
  }

  /**
   * Sends data over websocket in the form of a ByteString
   *
   * @param byteString
   */
  public void sendAudioData(ByteString byteString) {
    webSocket.send(byteString);
  }

  /** Will close the websocket connection by informing the server that it's the End of Stream */
  public void close() {
    webSocket.send("EOS");
  }

  private String buildURL() throws URISyntaxException {
    URIBuilder uriBuilder = new URIBuilder();
    if (scheme != null) {
      uriBuilder.setScheme(scheme);
    } else {
      uriBuilder.setScheme("wss");
    }
    if (host != null) {
      uriBuilder.setHost(host);
    } else {
      uriBuilder.setHost("api.rev.ai");
    }
    uriBuilder.setPath("/speechtotext/v1/stream");
    uriBuilder.setParameter("access_token", accessToken);
//    if (streamContentType != null) {
//      uriBuilder.setParameter("content_type", buildContentString());
//    }
    if (metadata != null) {
      uriBuilder.setParameter("metadata", metadata);
    }
    if (customVocabularyId != null) {
      uriBuilder.setParameter("custom_vocabulary_id", customVocabularyId);
    }
    if (filterProfanity != null) {
      uriBuilder.setParameter("filter_profanity", String.valueOf(filterProfanity));
    }
    return uriBuilder.build().toString() + buildContentString();
  }

  private String buildContentString() {
    List<String> content = getListFromContentType();
    String empty = "";
    if (content.size() == 0) {
      return empty;
    } else {
      return createContentTypeParameter(content);
    }
  }

  private List<String> getListFromContentType() {
    List<String> content = new ArrayList<>();
    if (streamContentType.getContentType() != null) {
      content.add("content_type" + "=" + streamContentType.getContentType());
    }
    if (streamContentType.getLayout() != null) {
      content.add("layout" + "=" + streamContentType.getLayout());
    }
    if (streamContentType.getRate() != null) {
      content.add("rate" + "=" + streamContentType.getRate());
    }
    if (streamContentType.getFormat() != null) {
      content.add("format" + "=" + streamContentType.getFormat());
    }
    if (streamContentType.getChannels() != null) {
      content.add("channels" + "=" + streamContentType.getChannels());
    }
    return content;
  }

  private String createContentTypeParameter(List<String> content) {
    String completeContentType = content.stream().collect(Collectors.joining(";"));
    return "&" + completeContentType;
  }

  private OkHttpClient setClient() {
    return new OkHttpClient.Builder()
        .addNetworkInterceptor(new ApiInterceptor(accessToken, SDKHelper.getSdkVersion()))
        .addNetworkInterceptor(new ErrorInterceptor())
        .build();
  }
}
