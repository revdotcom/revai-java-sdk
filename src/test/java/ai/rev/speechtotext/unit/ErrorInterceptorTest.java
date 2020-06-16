package ai.rev.speechtotext.unit;

import ai.rev.speechtotext.exceptions.RevAiApiException;
import ai.rev.speechtotext.ErrorInterceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ErrorInterceptorTest {
  @InjectMocks private Chain mockChain;

  private ErrorInterceptor sut;
  private Request sampleRequest;
  private Response sampleResponse;

  private int statusCode;

  public ErrorInterceptorTest(int statusCode) {
    super();
    this.statusCode = statusCode;
  }

  @Before
  public void setup() {
    sut = new ErrorInterceptor();
    mockChain = mock(Chain.class);
    sampleRequest = new Request.Builder().url("https://api.rev.ai/revspeech/v1/").build();
    when(mockChain.request()).thenReturn(sampleRequest);
    sampleResponse =
        new Response.Builder()
            .code(200)
            .request(sampleRequest)
            .protocol(Protocol.HTTP_2)
            .message("mock interceptor")
            .body(
                ResponseBody.create(
                    "{error: sample response}", MediaType.get("application/json; charset=utf-8")))
            .addHeader("content-type", "application/json")
            .build();
  }

  @Parameterized.Parameters
  public static Collection input() {
    return Arrays.asList(new Object[][] {{400}, {401}, {404}, {406}, {409}, {429}, {500}});
  }

  @Test
  public void ErrorInterceptor_ResponseCodeIsGreaterThan399_ReturnsRevAiApiException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(statusCode).build());

    assertThatExceptionOfType(RevAiApiException.class)
        .as("Expected status code [" + statusCode + "] to throw RevAiApiException")
        .isThrownBy(() -> sut.intercept(mockChain));
  }
}
