package revai.integration;

import okio.ByteString;
import org.junit.Test;
import revai.StreamContentType;
import revai.StreamingClient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class StreamingTest {

  @Test
  public void testWebsocketConnection()
      throws IOException, URISyntaxException, InterruptedException {
    StreamContentType streamContentType =
        new StreamContentType.Builder()
            .contentType("audio/x-raw")
            .layout("interleaved")
            .format("S16LE")
            .rate(16000)
            .channels(1)
            .build();

    StreamingClient streamingClient =
        new StreamingClient.Builder()
            .accessToken(EnvHelper.getToken())
            .streamContentType(streamContentType)
            .host("api-test.rev.ai")
            .metadata("java-sdk")
            .filterProfanity(true)
            .build();

    File file = new File("./src/test/java/revai/sources/english_test.raw");
    byte[] fileByteArray = readFileIntoByteArray(file);
    int chunk = 8000;
    streamingClient.connect(new StreamingClientListener());
    streamAudioToServer(streamingClient, fileByteArray, chunk);
    Thread.sleep(5000);
    streamingClient.close();
    Thread.sleep(5000);
  }

  private byte[] readFileIntoByteArray(File file) {
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
    return fileByteArray;
  }

  public void streamAudioToServer(
      StreamingClient streamingClient, byte[] fileByteArray, int chunk) {
    for (int start = 0; start < fileByteArray.length; start += chunk) {
      streamingClient.sendBytes(
          ByteString.of(
              ByteBuffer.wrap(
                  Arrays.copyOfRange(
                      fileByteArray, start, Math.min(fileByteArray.length, start + chunk)))));
    }
  }
}
