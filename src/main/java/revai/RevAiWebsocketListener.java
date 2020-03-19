package revai;

import okhttp3.Response;
import revai.models.streaming.Hypothesis;

public interface RevAiWebsocketListener {

    public void onConnected(String message);

    public void onHypothesis(Hypothesis hypothesis);

    public void onError(Throwable t, Response response);

    public void onClose(int code, String reason);

    public void onOpen(Response response);
}
