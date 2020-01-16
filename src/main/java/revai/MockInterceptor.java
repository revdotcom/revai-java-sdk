package revai;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class MockInterceptor implements Interceptor {

    public MockInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        JSONObject sampleResponse = new JSONObject("{balance_seconds:10, email:example.com}");
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
