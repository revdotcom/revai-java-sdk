package revai;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class MockInterceptor implements Interceptor {

    public JSONObject sampleResponse;

    public MockInterceptor(JSONObject sampleResponse) {
        this.sampleResponse = sampleResponse;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message("ok")
                .body(ResponseBody.create(
                        sampleResponse.toString(),
                        MediaType.get("application/json; charset=utf-8")))
                .addHeader("content-type", "application/json")
                .build();
    }
}
