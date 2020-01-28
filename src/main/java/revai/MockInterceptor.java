package revai;

import okhttp3.*;

import java.io.IOException;

public class MockInterceptor implements Interceptor {

  public String sampleResponse;
  public Request request;

  public MockInterceptor(String sampleResponse) {
    this.sampleResponse = sampleResponse;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    request = chain.request();
    return chain
      .proceed(chain.request())
      .newBuilder()
      .code(200)
      .protocol(Protocol.HTTP_2)
      .message("mock interceptor")
      .body(ResponseBody.create(sampleResponse, MediaType.get("application/json; charset=utf-8")))
      .addHeader("content-type", "application/json")
      .build();
  }

  public void setResponse(String sampleResponse) {
    this.sampleResponse = sampleResponse;
  }
}
