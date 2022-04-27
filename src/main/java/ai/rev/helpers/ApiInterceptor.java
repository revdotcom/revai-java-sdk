package ai.rev.helpers;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * An ApiInterceptor object appends authorization information to all API calls and is used to check
 * the status of the request for debugging purposes.
 */
public class ApiInterceptor implements Interceptor {

  private String accessToken;
  private String sdkVersion;

  public ApiInterceptor(String accessToken, String sdkVersion) {
    this.accessToken = accessToken;
    this.sdkVersion = sdkVersion;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request =
        chain
            .request()
            .newBuilder()
            .addHeader("Authorization", String.format("Bearer %s", accessToken))
            .addHeader("User-Agent", String.format("RevAi-JavaSDK/%s", sdkVersion))
            .build();
    return chain.proceed(request);
  }
}
