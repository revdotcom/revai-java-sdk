package ai.rev.speechtotext.unit;

import ai.rev.helpers.MockInterceptor;
import com.google.gson.Gson;
import okio.Buffer;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertHelper {

  public static void assertRequestMethodAndUrl(
      MockInterceptor mockInterceptor, String method, String url) {
    assertThat(mockInterceptor.request.method()).as("Request method").isEqualTo(method);
    assertThat(mockInterceptor.request.url().toString()).as("Request URL").isEqualTo(url);
  }

  public static <T> void assertRequestBody(
      MockInterceptor mockInterceptor, T expectedObject, Class<T> expectedClass) {
    Buffer buffer = new Buffer();
    Gson gson = new Gson();
    try {
      mockInterceptor.request.body().writeTo(buffer);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    T requestBodyObject = gson.fromJson(buffer.readUtf8(), expectedClass);

    String requestBodyString = gson.toJson(requestBodyObject);
    String expectedBodyString = gson.toJson(expectedObject);
    assertThat(requestBodyString).as("Request body").isEqualTo(expectedBodyString);
  }
}
