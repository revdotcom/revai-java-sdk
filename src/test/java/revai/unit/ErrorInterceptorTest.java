package revai.unit;

import okhttp3.Interceptor.Chain;
import okhttp3.*;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import revai.ErrorInterceptor;
import revai.exceptions.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ErrorInterceptorTest {
  @InjectMocks private Chain mockChain;

  // class to be tested
  private ErrorInterceptor sut;

  private Request sampleRequest;
  private Response sampleResponse;

  @Before
  public void setup() throws IOException {
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

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs400_ReturnsInvalidParameterException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(400).build());
    assertThatExceptionOfType(InvalidParameterException.class)
        .isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs401_ReturnsAuthorizationException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(401).build());
    assertThatExceptionOfType(AuthorizationException.class)
        .isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs404_ReturnsResourceNotFoundException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(404).build());
    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs406_ReturnsInvalidHeaderException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(406).build());
    assertThatExceptionOfType(InvalidHeaderException.class)
        .isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs409_ReturnsForbiddenStateException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(409).build());
    assertThatExceptionOfType(ForbiddenStateException.class)
        .isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs429_ReturnsThrottlingLimitException()
      throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(429).build());
    assertThatExceptionOfType(ThrottlingLimitException.class)
        .isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseCodeIs500_ReturnsRevAiApiException() throws IOException {
    int badResponseCode = 500;
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(badResponseCode).build());
    assertThatExceptionOfType(RevAiApiException.class).isThrownBy(() -> sut.intercept(mockChain));
  }

  @Test
  public void ErrorInterceptor_WhenResponseIs200_ReturnsOkHttpResponse() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(200).build());
    Response response = sut.intercept(mockChain);
    assertThat(response.code()).isEqualTo(200);
  }
}
