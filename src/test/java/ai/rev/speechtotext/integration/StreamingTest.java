package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.StreamingClient;
import ai.rev.speechtotext.models.asynchronous.Element;
import ai.rev.speechtotext.models.streaming.Hypothesis;
import ai.rev.speechtotext.models.streaming.MessageType;
import ai.rev.speechtotext.models.streaming.SessionConfig;
import ai.rev.speechtotext.models.streaming.StreamContentType;
import okio.ByteString;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamingTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void StreamingClient_ContentIsValid_StreamsAudioAndReceivesHypotheses() {
        StreamContentType streamContentType = createStreamContentType();
        SessionConfig sessionConfig = createSessionsConfig();
        StreamingClient streamingClient = new StreamingClient(EnvHelper.getToken());
        ClientListener clientListener = new ClientListener();

        streamingClient.connect(clientListener, streamContentType, sessionConfig);
        awaitConnectedMessage(clientListener);
        File file = new File("./src/test/java/ai/rev/speechtotext/resources/english_test.raw");
        byte[] fileByteArray = readFileIntoByteArray(file);
        int chunk = 8000;
        streamAudioToServer(streamingClient, fileByteArray, chunk);
        awaitFinalHypothesis(clientListener);
        streamingClient.close();
        awaitCloseMessage(clientListener);

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
                    Element[] elements = partialHypothesis.getElements();
                    for (Element element : elements) {
                        assertThat(element.getType()).as("Element type in partial").isEqualTo("text");
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
                    assertThat(finalHypothesis.getType()).isEqualTo(MessageType.FINAL);
                    Element[] elements = finalHypothesis.getElements();
                    for (Element element : elements) {
                        if (element.getType().equals("punct")) {
                            assertThat(element.getValue()).as("Element value").isNotNull();
                        } else {
                            assertThat(element.getType()).as("Element type").isEqualTo("text");
                            assertThat(element.getValue()).as("Element value").isNotNull();
                            assertThat(element.getStartTimestamp()).as("Element time stamp").isNotNull();
                            assertThat(element.getEndTimestamp()).as("Element end time stamp").isNotNull();
                            assertThat(element.getConfidence()).as("Element confidence score").isNotNull();
                        }
                    }
                });
    }

    private StreamContentType createStreamContentType() {
        StreamContentType streamContentType = new StreamContentType();
        streamContentType.setContentType("audio/x-raw");
        streamContentType.setLayout("interleaved");
        streamContentType.setFormat("S16LE");
        streamContentType.setRate(16000);
        streamContentType.setChannels(1);
        return streamContentType;
    }

    private SessionConfig createSessionsConfig() {
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setMetaData(testName.getMethodName());
        sessionConfig.setFilterProfanity(true);
        sessionConfig.setRemoveDisfluencies(true);
        return sessionConfig;
    }

    private void awaitConnectedMessage(ClientListener clientListener) {
        try {
            clientListener.getConnectedLatch().await(10, SECONDS);
            assertThat(clientListener.getConnectedMessage()).as("Connected message").isNotNull();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void awaitFinalHypothesis(ClientListener clientListener) {
        try {
            clientListener.getFinalHypothesisLatch().await(30, SECONDS);
            assertThat(clientListener.getFinalHypotheses()).as("Final hypotheses").isNotEmpty();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void awaitCloseMessage(ClientListener clientListener) {
        try {
            clientListener.getCloseLatch().await(30, SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
