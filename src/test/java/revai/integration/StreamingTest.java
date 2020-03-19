package revai.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okio.ByteString;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import revai.StreamContentType;
import revai.StreamingClient;
import revai.models.asynchronous.Element;
import revai.models.streaming.Hypothesis;

import javax.lang.model.util.Elements;
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

    StreamContentType streamContentType = new StreamContentType();
    streamContentType.setContentType("audio/x-raw");
    streamContentType.setLayout("interleaved");
    streamContentType.setFormat("S16LE");
    streamContentType.setRate(16000);
    streamContentType.setChannels(1);

    StreamingClient streamingClient = new StreamingClient(EnvHelper.getToken(), streamContentType);
    streamingClient.setFilterProfanity(true);
    streamingClient.setMetadata(testName.getMethodName());

    File file = new File("./src/test/java/revai/resources/english_test.raw");
    byte[] fileByteArray = readFileIntoByteArray(file);
    int chunk = 8000;
    ClientListener clientListener = new ClientListener();
    streamingClient.connect(clientListener);

    try {
      clientListener.getConnectedLatch().await(10, SECONDS);
      assertThat(clientListener.getConnectedMessage()).as("Connected message").isNotNull();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    streamAudioToServer(streamingClient, fileByteArray, chunk);

    try {
      clientListener.getFinalHypothesisLatch().await(30, SECONDS);
      assertThat(clientListener.getFinalHypotheses()).as("Final hypotheses").isNotEmpty();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    streamingClient.close();

    try {
      clientListener.getCloseLatch().await(30, SECONDS);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }

    assertPartialHypotheses(clientListener.getPartialHypotheses());
    assertFinalHypotheses(clientListener.getFinalHypotheses());
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
      streamingClient.sendAudioData(
          ByteString.of(
              ByteBuffer.wrap(
                  Arrays.copyOfRange(
                      fileByteArray, start, Math.min(fileByteArray.length, start + chunk)))));
    }
  }

  private void assertPartialHypotheses(List<Hypothesis> partialHypotheses) {
    if (partialHypotheses.isEmpty()) {
      throw new RuntimeException("No partial hypotheses are found");
    }
    partialHypotheses.forEach(
        partialHypothesis -> {
           List<Element> elements = Arrays.asList(partialHypothesis.getElements());
          for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            assertThat(element.getType())
                .as("Element type in partial")
                .isEqualTo("text");
            assertThat(element.getValue()).as("Element value").isNotNull();
          }
        });
  }

  private void assertFinalHypotheses(List<Hypothesis> finalHypotheses) {
    if (finalHypotheses.isEmpty()) {
      throw new RuntimeException("No final hypotheses are found");
    }
    finalHypotheses.forEach(
        finalHypothesis -> {
          assertThat(finalHypothesis.getType()).isEqualTo("final");
          List<Element> elements = Arrays.asList(finalHypothesis.getElements());
          for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (element.getType().equals("punct")) {
              assertThat(element.getValue()).as("Element value").isNotNull();
            } else {
              assertThat(element.getType()).as("Element type").isEqualTo("text");
              assertThat(element.getValue()).as("Element value").isNotNull();
              assertThat(element.getStartTimestamp()).as("Element time stamp").isNotNull();
              assertThat(element.getEndTimestamp())
                  .as("Element end time stamp")
                  .isNotNull();
              assertThat(element.getConfidence())
                  .as("Element confidence score")
                  .isNotNull();
            }
          }
        });
  }
}
