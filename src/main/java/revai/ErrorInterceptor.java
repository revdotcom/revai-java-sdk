package revai;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import revai.exceptions.AuthorizationException;
import revai.exceptions.RevAiApiException;

import java.io.IOException;

public class ErrorInterceptor implements Interceptor {

    public ErrorInterceptor() {
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int responseCode = response.code();
        if (responseCode != 200) {
            JSONObject errorResponse = new JSONObject(response.body().string());
            switch (responseCode) {
                case 401:
                    throw new AuthorizationException(errorResponse);
                default:
                    throw new RevAiApiException("Unexpected API Error", errorResponse, responseCode);
            }
        } else {
            return response;
        }
    }
}