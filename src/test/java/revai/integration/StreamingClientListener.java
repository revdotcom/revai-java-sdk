package revai.integration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StreamingClientListener extends WebSocketListener {

  private CountDownLatch connectedLatch = new CountDownLatch(1);
  private CountDownLatch closeLatch = new CountDownLatch(1);
  private CountDownLatch finalHypothesisLatch = new CountDownLatch(1);
  private Gson gson = new Gson();
  private JsonObject connectedMessage;
  private List<JsonObject> partialHypotheses = new ArrayList<>();
  private List<JsonObject> finalHypotheses = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(StreamingClientListener.class);

  public StreamingClientListener() {}

  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    LOG.info(response.message());
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    JsonObject jsonObject = gson.fromJson(text, JsonObject.class);
    if (jsonObject.get("type").getAsString().equals("connected")) {
      connectedMessage = jsonObject;
      connectedLatch.countDown();
    } else if (jsonObject.get("type").getAsString().equals("partial")) {
      partialHypotheses.add(jsonObject);
    } else if (jsonObject.get("type").getAsString().equals("final")) {
      finalHypotheses.add(jsonObject);
      finalHypothesisLatch.countDown();
    }
  }

  @Override
  public void onClosing(WebSocket webSocket, int code, String reason) {
    LOG.info("Closed code: {" + code + "} " + reason);
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    LOG.info("Closed code: {" + code + "} " + reason);
    closeLatch.countDown();
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    LOG.info(response + t.getMessage());
  }

  public CountDownLatch getCloseLatch() {
    return closeLatch;
  }

  public CountDownLatch getConnectedLatch() {
    return connectedLatch;
  }

  public CountDownLatch getFinalHypothesisLatch() {
    return finalHypothesisLatch;
  }

  public List<JsonObject> getPartialHypotheses() {
    return partialHypotheses;
  }

  public List<JsonObject> getFinalHypotheses() {
    return finalHypotheses;
  }

  public JsonObject getConnectedMessage() {
    return connectedMessage;
  }
}
