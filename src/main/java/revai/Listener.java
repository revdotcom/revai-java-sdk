package revai;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import revai.models.streaming.Hypothesis;

public class Listener extends WebSocketListener {

    private Gson gson = new Gson();
    private RevAiWebsocketListener revAiWebsocketListener;

    public Listener(RevAiWebsocketListener revAiWebsocketListener) {
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
