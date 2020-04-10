package revai.integration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.Response;
import revai.RevAiWebSocketListener;
import revai.models.streaming.Hypothesis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class ClientListener implements RevAiWebSocketListener {

  private CountDownLatch connectedLatch = new CountDownLatch(1);
  private CountDownLatch closeLatch = new CountDownLatch(1);
  private CountDownLatch finalHypothesisLatch = new CountDownLatch(1);
  private Gson gson = new Gson();
  private JsonObject connectedMessage;
  private List<Hypothesis> partialHypotheses = new ArrayList<>();
  private List<Hypothesis> finalHypotheses = new ArrayList<>();
  private static final Logger logger = Logger.getLogger(ClientListener.class.getName());

  @Override
  public void onConnected(String message) {
    logger.info("On Connected: " + message);
    connectedMessage = gson.fromJson(message, JsonObject.class);
    connectedLatch.countDown();
  }

  @Override
  public void onHypothesis(Hypothesis hypothesis) {
    if (hypothesis.getType().equals("partial")) {
      partialHypotheses.add(hypothesis);
    } else if (hypothesis.getType().equals("final")) {
      finalHypotheses.add(hypothesis);
      finalHypothesisLatch.countDown();
    }
  }

  @Override
  public void onError(Throwable t, Response response) {
    logger.info("On Error: " + response + t.getMessage());
  }

  @Override
  public void onClose(int code, String reason) {
    logger.info("Closed code: {" + code + "} " + reason);
    closeLatch.countDown();
  }

  @Override
  public void onOpen(Response response) {
    logger.info("On Open : " + response.message());
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

  public List<Hypothesis> getPartialHypotheses() {
    return partialHypotheses;
  }

  public List<Hypothesis> getFinalHypotheses() {
    return finalHypotheses;
  }

  public JsonObject getConnectedMessage() {
    return connectedMessage;
  }
}
