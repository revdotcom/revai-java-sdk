package revai;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import revai.helpers.SDKHelper;
import revai.models.streaming.Hypothesis;

public class StreamingClient {

  private String accessToken;
  private OkHttpClient client;
  private WebSocket webSocket;
  private String scheme;
  private String host;
  private Integer port;

  /**
   * Constructs the streaming client that is used to establish a WebSocket connection with the
   * Rev.ai server and stream audio data. The user access token can be generated on the website at
   * https://www.rev.ai/access_token.
   *
   * @param accessToken Rev.ai authorization token.
   * @see StreamContentType
   */
  public StreamingClient(String accessToken) {
    this.accessToken = accessToken;
    this.client = setClient();
  }

  /**
   * This method sets the URL scheme to be used in the WebSocket connect request
   *
   * @param scheme URL scheme.
   */
  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  /**
   * This methods sets the URL host name. By default the host name is api.rev.ai.
   *
   * @param host URL host name.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * This method sets a port number to be used in the WebSocket connect request.
   *
   * @param port the port used to connect to
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Sends an HTTP request and upon authorization is upgraded to a WebSocket connection. Use
   * {@link RevAiWebSocketListener} to handle web socket events. To establish a successful connection a
   * valid StreamContentType must be provided.
   *
   * <p>Providing metadata is optional but helps to identify the stream.
   *
   * <p>Providing a custom vocabulary is optional. This method assigns a custom vocabulary to be
   * used during the stream. Custom vocabularies are submitted prior to usage in the stream and
   * assigned an Id.
   *
   * <p>Setting the profanity filter is optional. More information regarding the profanity filter
   * can be found here: <a
   * href="https://www.rev.ai/docs/streaming#section/WebSocket-Endpoint/Filter-Profanity">Filter
   * Profanity</a
   *
   * @param revAiWebSocketListener the listener used to capture WebSocket events.
   * @param metadata used to identify the stream.
   * @param streamContentType content-type query parameter.
   * @param customVocabularyId customVocabularyId the ID of the custom vocabulary.
   * @param filterProfanity this is false by default.
   * @see RevAiWebSocketListener
   * @see StreamContentType
   */
  public void connect(
      RevAiWebSocketListener revAiWebSocketListener,
      String metadata,
      StreamContentType streamContentType,
      String customVocabularyId,
      Boolean filterProfanity) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(metadata, streamContentType, customVocabularyId, filterProfanity);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String)} without the optional metadata, customVocabularyId and filterProfanity.
   *
   * @see StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType, String)
   */
  public void connect(
          RevAiWebSocketListener revAiWebSocketListener, StreamContentType streamContentType) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(null, streamContentType, null, null);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String)} without the optional customVocabularyId.
   *
   * @see StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType, String)
   */
  public void connect(
      RevAiWebSocketListener revAiWebSocketListener,
      String metadata,
      StreamContentType streamContentType) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(metadata, streamContentType, null, null);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String, Boolean)} without the optional metadata.
   */
  public void connect(
      RevAiWebSocketListener revAiWebSocketListener,
      StreamContentType streamContentType,
      String customVocabularyId) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(null, streamContentType, customVocabularyId, null);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String, Boolean)} without the optional filterProfanity.
   */
  public void connect(
      RevAiWebSocketListener revAiWebSocketListener,
      String metadata,
      StreamContentType streamContentType,
      String customVocabularyId) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(metadata, streamContentType, customVocabularyId, null);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String, Boolean)} without the optional metadata.
   */
  public void connect(
          RevAiWebSocketListener revAiWebSocketListener,
          StreamContentType streamContentType,
          String customVocabularyId,
          Boolean filterProfanity) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(null, streamContentType, customVocabularyId, filterProfanity);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String, Boolean)} without the optional customVocabularyId.
   */
  public void connect(
          RevAiWebSocketListener revAiWebSocketListener,
          String metadata,
          StreamContentType streamContentType,
          Boolean filterProfanity) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(metadata, streamContentType, null, filterProfanity);
    createWebSocketConnection(url, listener);
  }

  /**
   * Overload of {@link StreamingClient#connect(RevAiWebSocketListener, String, StreamContentType,
   * String, Boolean)} without the optional metadata and customVocabularyId.
   */
  public void connect(
          RevAiWebSocketListener revAiWebSocketListener,
          StreamContentType streamContentType,
          Boolean filterProfanity) {
    Listener listener = new Listener(revAiWebSocketListener);
    String url = buildURL(null, streamContentType, null, filterProfanity);
    createWebSocketConnection(url, listener);
  }

  /**
   * Sends data over WebSocket in the form of a ByteString
   *
   * @param byteString Audio data in the form of a ByteString
   */
  public void sendAudioData(ByteString byteString) {
    webSocket.send(byteString);
  }

  /** Will close the WebSocket connection by informing the server that it's the End of Stream */
  public void close() {
    webSocket.send("EOS");
  }

  private void createWebSocketConnection(String url, Listener listener) {
    Request request = new Request.Builder().url(url).build();
    webSocket = client.newWebSocket(request, listener);
  }

  private String buildURL(
      String metadata,
      StreamContentType streamContentType,
      String customVocabularyId,
      Boolean filterProfanity) {

    HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
    if (scheme != null) {
      urlBuilder.scheme(scheme);
    } else {
      urlBuilder.scheme("https");
    }
    if (host != null) {
      urlBuilder.host(host);
    } else {
      urlBuilder.host("api.rev.ai");
    }
    if (port != null) {
      urlBuilder.port(port);
    }
    urlBuilder.addPathSegment("speechtotext");
    urlBuilder.addPathSegment("v1");
    urlBuilder.addPathSegment("stream");

    urlBuilder.addQueryParameter("access_token", accessToken);
    if (metadata != null) {
      urlBuilder.addQueryParameter("metadata", metadata);
    }
    if (customVocabularyId != null) {
      urlBuilder.addQueryParameter("custom_vocabulary_id", customVocabularyId);
    }
    if (filterProfanity != null) {
      urlBuilder.addQueryParameter("filter_profanity", String.valueOf(filterProfanity));
    }
    return urlBuilder.build().toString()
        + "&content_type="
        + streamContentType.buildContentString();
  }

  private OkHttpClient setClient() {
    return new OkHttpClient.Builder()
        .addNetworkInterceptor(new ApiInterceptor(accessToken, SDKHelper.getSdkVersion()))
        .addNetworkInterceptor(new ErrorInterceptor())
        .build();
  }

  private static class Listener extends WebSocketListener {

    private Gson gson = new Gson();
    private RevAiWebSocketListener revAiWebsocketListener;

    public Listener(RevAiWebSocketListener revAiWebsocketListener) {
      this.revAiWebsocketListener = revAiWebsocketListener;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
      revAiWebsocketListener.onOpen(response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
      JsonObject jsonObject = gson.fromJson(text, JsonObject.class);
      String type = jsonObject.get("type").getAsString();
      if (type.equals("connected")) {
        revAiWebsocketListener.onConnected(gson.toJson(jsonObject));
      } else if (type.equals("partial") || type.equals("final")) {
        revAiWebsocketListener.onHypothesis(gson.fromJson(text, Hypothesis.class));
      }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
      revAiWebsocketListener.onClose(code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
      revAiWebsocketListener.onClose(code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
      revAiWebsocketListener.onError(t, response);
    }
  }
}
