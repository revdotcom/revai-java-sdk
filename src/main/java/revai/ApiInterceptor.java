package revai;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import revai.exceptions.AuthorizationException;
import revai.exceptions.RevAiApiException;

import java.io.IOException;

public class ApiInterceptor implements Interceptor {

    private String accessToken;
    private String sdkVersion;

    public ApiInterceptor(String accessToken, String sdkVersion) {
        this.accessToken = accessToken;
        this.sdkVersion = sdkVersion;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", String.format("Bearer %s", accessToken))
                .addHeader("User-Agent", String.format("RevAi-JavaSDK/%s", sdkVersion))
                .build();
        Response response = chain.proceed(request);
        int responseCode = response.code();
        if (responseCode == 200) {
            return response;
        } else {
            JSONObject errorResponse = new JSONObject(response.body().string());
            switch (responseCode) {
                case 401:
                    throw new AuthorizationException(errorResponse, responseCode);
                default:
                    throw new RevAiApiException("Rev.AI API Exception", errorResponse, responseCode);
            }
        }
    }
}
