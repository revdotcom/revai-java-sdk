package ai.rev.speechtotext;

import ai.rev.speechtotext.models.streaming.ConnectedMessage;
import ai.rev.speechtotext.models.streaming.Hypothesis;
import ai.rev.speechtotext.models.streaming.SessionConfig;
import ai.rev.speechtotext.models.streaming.StreamContentType;
import okhttp3.Response;
import okio.ByteString;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class StreamingFromLocalFileExample {

  public static void main(String[] args) throws InterruptedException {
    // Assign your access token to a String
    String accessToken = "your_access_token";

    // Configure the streaming content type
    StreamContentType streamContentType = new StreamContentType();
    streamContentType.setContentType("audio/x-raw"); // audio content type
    streamContentType.setLayout("interleaved"); // layout
    streamContentType.setFormat("S16LE"); // format
    streamContentType.setRate(16000); // sample rate
    streamContentType.setChannels(1); // channels

    // Setup the SessionConfig with any optional parameters
    SessionConfig sessionConfig = new SessionConfig();
    sessionConfig.setMetaData("Streaming from the Java SDK");
    sessionConfig.setFilterProfanity(true);

    // Initialize your client with your access token
    StreamingClient streamingClient = new StreamingClient(accessToken);

    // Initialize your WebSocket listener
    WebSocketListener webSocketListener = new WebSocketListener();

    // Begin the streaming session
    streamingClient.connect(webSocketListener, streamContentType, sessionConfig);

    // Read file from disk
    File file = new File("path/to/file/foo.raw");

    // Convert file into byte array
    byte[] fileByteArray = new byte[(int) file.length()];
    try (final FileInputStream fileInputStream = new FileInputStream(file)) {
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
      try (final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream)) {
        dataInputStream.readFully(fileByteArray, 0, fileByteArray.length);
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    // Set the number of bytes to send in each message
    int chunk = 8000;

    // Stream the file in the configured chunk size
    for (int start = 0; start < fileByteArray.length; start += chunk) {
      streamingClient.sendAudioData(
          ByteString.of(
              ByteBuffer.wrap(
                  Arrays.copyOfRange(
                      fileByteArray, start, Math.min(fileByteArray.length, start + chunk)))));
    }

    // Wait to make sure all responses are received
    Thread.sleep(5000);

    // Close the WebSocket
    streamingClient.close();
  }

  // Your WebSocket listener for all streaming responses
  private static class WebSocketListener implements RevAiWebSocketListener {

    @Override
    public void onConnected(ConnectedMessage message) {
      System.out.println(message);
    }

    @Override
    public void onHypothesis(Hypothesis hypothesis) {
      System.out.println(hypothesis.toString());
    }

    @Override
    public void onError(Throwable t, Response response) {
      System.out.println(response);
    }

    @Override
    public void onClose(int code, String reason) {
      System.out.println(reason);
    }

    @Override
    public void onOpen(Response response) {
      System.out.println(response.toString());
    }
  }
}
