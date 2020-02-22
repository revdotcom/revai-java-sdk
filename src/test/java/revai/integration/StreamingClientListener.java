package revai.integration;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class StreamingClientListener extends WebSocketListener {

  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    System.out.println("On Open: " + response);
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    System.out.println("On Message text: " + text);
  }

  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    System.out.println("On Message bytes: " + bytes);
  }

  @Override
  public void onClosing(WebSocket webSocket, int code, String reason) {
    System.out.println("Closed code: {" + code + "} " + reason);
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    System.out.println("Closed code: {" + code + "} " + reason);
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    System.out.println(response + t.getMessage());
  }
}
