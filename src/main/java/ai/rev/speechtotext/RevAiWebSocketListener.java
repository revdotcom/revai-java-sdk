package ai.rev.speechtotext;

import okhttp3.Response;
import ai.rev.speechtotext.models.streaming.ConnectedMessage;
import ai.rev.speechtotext.models.streaming.Hypothesis;

/** Listens for events over the WebSocket connection to Rev.ai */
public interface RevAiWebSocketListener {

  /**
   * Supplies the connection message received from Rev.ai.
   *
   * @param message a String in JSON format that contains the message type and job ID.
   * @see <a
   *     href="https://www.rev.ai/docs/streaming#section/Rev.ai-to-Client-Response">https://www.rev.ai/docs/streaming#section/Rev.ai-to-Client-Response</a
   */
  void onConnected(ConnectedMessage message);

  /**
   * Supplies the Hypothesis returned from Rev.ai.
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
