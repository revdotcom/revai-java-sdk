package revai;

import okhttp3.Interceptor.Chain;
import okhttp3.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import revai.exceptions.*;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ErrorInterceptorTest {
  @InjectMocks
  private Chain mockChain;

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
          .body(ResponseBody.create("{error: sample response}", MediaType.get("application/json; charset=utf-8")))
          .addHeader("content-type", "application/json")
          .build();
  }

  @Test
  public void InvalidParameterExceptionTest() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(400).build());
    try {
      sut.intercept(mockChain);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof InvalidParameterException);
    }
  }

  @Test
  public void AuthorizationExceptionTest() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(401).build());
    try {
      sut.intercept(mockChain);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof AuthorizationException);
    }
  }

  @Test
  public void ResourceNotFoundExceptionTest() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(404).build());
    try {
      sut.intercept(mockChain);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof ResourceNotFoundException);
    }
  }

  @Test
  public void InvalidHeaderExceptionTest() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(406).build());
    try {
      sut.intercept(mockChain);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof InvalidHeaderException);
    }
  }

  @Test
  public void ForbiddenStateExceptionTest() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(409).build());
    try {
      sut.intercept(mockChain);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof ForbiddenStateException);
    }
  }

  @Test
  public void ThrottlingLimitExceptionTest() throws IOException {
    when(mockChain.proceed(any(Request.class)))
        .thenReturn(sampleResponse.newBuilder().code(429).build());
    try {
      sut.intercept(mockChain);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertTrue(e instanceof ThrottlingLimitException);
    }
  }
}
