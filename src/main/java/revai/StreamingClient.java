package revai;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.http.client.utils.URIBuilder;
import revai.helpers.SDKHelper;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamingClient {

  private String accessToken;
  private OkHttpClient client;
  private Request request;
  private String host;
  private String metadata;
  private String customVocabulary;
  private StreamContentType streamContentType;
  private WebSocket webSocket;
  private Boolean filterProfanity;

  public StreamingClient(
      String accessToken,
      StreamContentType streamContentType,
      String metadata,
      String customVocabulary,
      Boolean filterProfanity,
      OkHttpClient client,
      String host)
      throws URISyntaxException {
    this.accessToken = accessToken;
    this.streamContentType = streamContentType;
    this.metadata = metadata;
    this.customVocabulary = customVocabulary;
    this.client = client;
    this.host = host;
    this.filterProfanity = filterProfanity;

    String content = buildContentString(streamContentType);
    String url = buildURL() + content;
    this.request = new Request.Builder().url(url).build();
  }

  public void connect(WebSocketListener listener) {
    webSocket = client.newWebSocket(request, listener);
    client.dispatcher().executorService().shutdown();
  }

  public void sendBytes(ByteString byteString) {
    webSocket.send(byteString);
  }

  public void close() {
    webSocket.send("EOS");
  }

  private String createContentTypeString(List<String> content) {
    String completeContentType = content.stream().collect(Collectors.joining(";"));
    return "&" + completeContentType;
  }

  private String buildContentString(StreamContentType streamContentType) {
    List<String> content = getFieldNamesAndValues(streamContentType);
    String empty = "";
    if (content.size() == 0) {
      return empty;
    } else {
      return createContentTypeString(content);
    }
  }

  private String buildURL() throws URISyntaxException {
    URIBuilder uriBuilder = new URIBuilder();
    uriBuilder.setScheme("wss");
    uriBuilder.setHost(host);
    uriBuilder.setPath("/speechtotext/v1/stream");
    uriBuilder.setParameter("access_token", accessToken);
    if (metadata != null) {
      uriBuilder.setParameter("metadata", metadata);
    }
    if (customVocabulary != null) {
      uriBuilder.setParameter("custom_vocabulary_id", customVocabulary);
    }
    if (filterProfanity != null) {
      uriBuilder.setParameter("filter_profanity", String.valueOf(filterProfanity));
    }
    return uriBuilder.build().toString();
  }

  private List<String> getFieldNamesAndValues(StreamContentType streamContentType) {
    List<String> content = new ArrayList<>();
    Class<?> contentClass = streamContentType.getClass();
    Field[] fields = contentClass.getDeclaredFields();
    for (Field field : fields) {
      try {
        if (field.get(streamContentType) != null) {
          String fieldContent = field.getName() + "=" + field.get(streamContentType).toString();
          content.add(fieldContent);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return content;
  }

  public static class Builder {
    private String accessToken;
    private OkHttpClient client = setClient();
    private String host = "api.rev.ai";
    private String metadata;
    private String customVocabulary;
    private Boolean filterProfanity;
    private StreamContentType streamContentType;

    public Builder() {}

    public Builder accessToken(String accessToken) {
      this.accessToken = accessToken;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder metadata(String metadata) {
      this.metadata = metadata;
      return this;
    }

    public Builder filterProfanity(Boolean filterProfanity) {
      this.filterProfanity = filterProfanity;
      return this;
    }

    public Builder customVocabulary(String customVocabulary) {
      this.customVocabulary = customVocabulary;
      return this;
    }

    public Builder streamContentType(StreamContentType streamContentType) {
      this.streamContentType = streamContentType;
      return this;
    }

    public StreamingClient build() throws URISyntaxException {
      return new StreamingClient(
          accessToken,
          streamContentType,
          metadata,
          customVocabulary,
          filterProfanity,
          client,
          host);
    }

    private OkHttpClient setClient() {
      return new OkHttpClient.Builder()
          .addNetworkInterceptor(new ApiInterceptor(accessToken, SDKHelper.getSdkVersion()))
          .addNetworkInterceptor(new ErrorInterceptor())
          .build();
    }
  }
}
