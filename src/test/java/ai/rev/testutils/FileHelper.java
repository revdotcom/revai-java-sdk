package ai.rev.testutils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Helper for file related operations in tests
 */
public class FileHelper {

  /**
   * Read file contents into a string
   *
   * @param fileName Path representing the file location
   * @return String content of the file
   */
  public static String getFileContents(Path fileName) {
    StringBuilder contentBuilder = new StringBuilder();

    try (Stream<String> stream
                 = Files.lines(fileName, StandardCharsets.UTF_8))
    {
      stream.forEach(s -> contentBuilder.append(s).append("\n"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return contentBuilder.toString();
  }
}
