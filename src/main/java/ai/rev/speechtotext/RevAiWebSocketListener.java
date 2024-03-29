package ai.rev.speechtotext;

import ai.rev.speechtotext.models.streaming.ConnectedMessage;
import ai.rev.speechtotext.models.streaming.Hypothesis;
import okhttp3.Response;

/** Listens for events over the WebSocket connection to Rev AI */
public interface RevAiWebSocketListener {

  /**
   * Supplies the connection message received from Rev AI.
   *
   * @param message a String in JSON format that contains the message type and job ID.
   * @see <a
   *     href="https://docs.rev.ai/api/streaming/responses/">https://docs.rev.ai/api/streaming/responses/</a
   */
  void onConnected(ConnectedMessage message);

  /**
   * Supplies the Hypothesis returned from Rev AI.
   *
   * @param hypothesis the partial or final hypothesis of the audio.
   * @see Hypothesis
   */
  void onHypothesis(Hypothesis hypothesis);

  /**
   * Supplies the error and response received during a WebSocket ErrorEvent.
   *
   * @param t the error thrown.
   * @param response the WebSocket response to the error.
   */
  void onError(Throwable t, Response response);

  /**
   * Supplies the close code and close reason received during a WebSocket CloseEvent.
   *
   * @param code the close code.
   * @param reason the close reason.
   */
  void onClose(int code, String reason);

  /**
   * Supplies the response received during the handshake.
   *
   * @param response the handshake response.
   */
  void onOpen(Response response);
}
