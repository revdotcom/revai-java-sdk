package ai.rev;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/** A MockInterceptor object is used to mock the responses for testing purposes. */
public class MockInterceptor implements Interceptor {

  private String sampleResponse;
  private MediaType mediaType;
  private Integer responseCode;
  public Request request;

  public MockInterceptor(MediaType mediaType, Integer responseCode) {
    this.mediaType = mediaType;
    this.responseCode = responseCode;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    request = chain.request();
    return chain
        .proceed(chain.request())
        .newBuilder()
        .code(responseCode)
        .protocol(Protocol.HTTP_2)
        .message("mock interceptor")
        .body(ResponseBody.create(sampleResponse, mediaType))
        .addHeader("content-type", "application/json")
        .build();
  }

  public String getSampleResponse() {
    return sampleResponse;
  }

  public void setSampleResponse(String sampleResponse) {
    this.sampleResponse = sampleResponse;
  }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public Integer getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(Integer responseCode) {
    this.responseCode = responseCode;
  }
}
