package ai.rev.speechtotext.testutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ConversionUtil {

  public static String convertInputStreamToString(InputStream inputStream) throws IOException {
    StringBuilder builder = new StringBuilder();
    try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      int count;
      while ((count = reader.read()) != -1) {
        builder.append((char) count);
      }
    }
    return builder.toString();
  }
}
