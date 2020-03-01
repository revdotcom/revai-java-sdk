package revai.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okio.ByteString;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import revai.StreamContentType;
import revai.StreamingClient;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.*;

public class StreamingTest {

  @Rule public TestName testName = new TestName();

  @Test
  public void canStreamRawAudioAndRecieveHypothesis() throws URISyntaxException {
    StreamContentType streamContentType =
        new StreamContentType.Builder()
            .contentType("audio/x-raw")
            .layout("interleaved")
            .format("S16LE")
            .rate(16000)
            .channels(1)
            .build();

    StreamingClient streamingClient = new StreamingClient(EnvHelper.getToken(), streamContentType);
    streamingClient.setFilterProfanity(true);
    streamingClient.setMetadata(testName.getMethodName());

    File file = new File("./src/test/java/revai/resources/english_test.raw");
    byte[] fileByteArray = readFileIntoByteArray(file);
    int chunk = 8000;
    StreamingClientListener streamingClientListener = new StreamingClientListener();
    streamingClient.connect(streamingClientListener);

    try {
      streamingClientListener.getConnectedLatch().await(10, SECONDS);
      assertThat(streamingClientListener.getConnectedMessage()).as("Connected message").isNotNull();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    streamAudioToServer(streamingClient, fileByteArray, chunk);

    try {
      streamingClientListener.getFinalHypothesisLatch().await(30, SECONDS);
      assertThat(streamingClientListener.getFinalHypotheses()).as("Final hypotheses").isNotEmpty();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    streamingClient.close();

    try {
      streamingClientListener.getCloseLatch().await(30, SECONDS);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    assertPartialHypotheses(streamingClientListener.getPartialHypotheses());
    assertFinalHypotheses(streamingClientListener.getFinalHypotheses());
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

  private void streamAudioToServer(
      StreamingClient streamingClient, byte[] fileByteArray, int chunk) {
    for (int start = 0; start < fileByteArray.length; start += chunk) {
      streamingClient.sendBytes(
          ByteString.of(
              ByteBuffer.wrap(
                  Arrays.copyOfRange(
                      fileByteArray, start, Math.min(fileByteArray.length, start + chunk)))));
    }
  }

  private void assertPartialHypotheses(List<JsonObject> partialHypotheses) {
    partialHypotheses.forEach(
        partialHypothesis -> {
          JsonArray elements = partialHypothesis.getAsJsonArray("elements");
          for (int i = 0; i < elements.size(); i++) {
            JsonObject element = elements.get(i).getAsJsonObject();
            assertThat(element.get("type").getAsString())
                .as("Element type in partial")
                .isEqualTo("text");
            assertThat(element.get("value").getAsString()).as("Element value").isNotNull();
          }
        });
  }

  private void assertFinalHypotheses(List<JsonObject> finalHypotheses) {
    finalHypotheses.forEach(
        finalHypothesis -> {
          assertThat(finalHypothesis.get("type").getAsString()).isEqualTo("final");
          JsonArray elements = finalHypothesis.getAsJsonArray("elements");
          for (int i = 0; i < elements.size(); i++) {
            JsonObject element = elements.get(i).getAsJsonObject();
            if (element.get("type").getAsString().equals("punct")) {
              assertThat(element.get("value")).as("Element value").isNotNull();
            } else {
              assertThat(element.get("type").getAsString()).as("Element type").isEqualTo("text");
              assertThat(element.get("value").getAsString()).as("Element value").isNotNull();
              assertThat(element.get("ts").getAsString()).as("Element time stamp").isNotNull();
              assertThat(element.get("end_ts").getAsString())
                  .as("Element end time stamp")
                  .isNotNull();
              assertThat(element.get("confidence").getAsString())
                  .as("Element confidence score")
                  .isNotNull();
            }
          }
        });
  }
}
