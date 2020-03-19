package revai.unit;

import okhttp3.Response;
import revai.RevAiWebsocketListener;
import revai.models.streaming.Hypothesis;

public class MockClientListener implements RevAiWebsocketListener {
    @Override
    public void onConnected(String message) {

    }

    @Override
    public void onHypothesis(Hypothesis hypothesis) {

    }

    @Override
    public void onError(Throwable t, Response response) {

    }

    @Override
    public void onClose(int code, String reason) {

    }

    @Override
    public void onOpen(Response response) {

    }
}
