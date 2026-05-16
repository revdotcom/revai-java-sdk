package ai.rev.speechtotext;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.IOException;
import java.io.InputStream;

/** Customized request body used for submitting local file jobs. */
public class FileStreamRequestBody {

  public static RequestBody create(final InputStream inputStream, final MediaType mediaType) {
    return new RequestBody() {
      @Override
      public MediaType contentType() {
        return mediaType;
      }

      @Override
      public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
          source = Okio.source(inputStream);
          sink.writeAll(source);
        } finally {
          try {
            source.close();
          } catch (AssertionError ae) {
            throw ae;
          } catch (RuntimeException re) {
            if ("bio == null".equals(re.getMessage())) {
              // Conscrypt in Android 10 and 11 may throw closing an SSLSocket. This is safe to ignore.
              // https://issuetracker.google.com/issues/177450597
              return;
            }
            throw re;
          } catch (Exception ignore) {}
        }
      }
    };
  }
}
