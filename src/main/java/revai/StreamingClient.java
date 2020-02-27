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
  private WebSocket webSocket;
  private String host;
  private StreamContentType streamContentType;
  private String metadata;
  private String customVocabulary;
  private Boolean filterProfanity;

  public StreamingClient(
      String accessToken,
      StreamContentType streamContentType) {
    this.accessToken = accessToken;
    this.streamContentType = streamContentType;
    this.client = setClient();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public String getCustomVocabulary() {
    return customVocabulary;
  }

  public void setCustomVocabulary(String customVocabulary) {
    this.customVocabulary = customVocabulary;
  }

  public Boolean getFilterProfanity() {
    return filterProfanity;
  }

  public void setFilterProfanity(Boolean filterProfanity) {
    this.filterProfanity = filterProfanity;
  }

  public void connect(WebSocketListener listener) throws URISyntaxException {
    String completeUrl = buildURL() + buildContentString(streamContentType);
    this.request = new Request.Builder().url(completeUrl).build();
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
    if (host != null ) {
      uriBuilder.setHost(host);
    } else {
      uriBuilder.setHost("api.rev.ai");
    }
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

  private OkHttpClient setClient() {
    return new OkHttpClient.Builder()
            .addNetworkInterceptor(new ApiInterceptor(accessToken, SDKHelper.getSdkVersion()))
            .addNetworkInterceptor(new ErrorInterceptor())
            .build();
  }
}
