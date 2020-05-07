package ai.rev.speechtotext;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import ai.rev.speechtotext.exceptions.AuthorizationException;
import ai.rev.speechtotext.exceptions.ForbiddenStateException;
import ai.rev.speechtotext.exceptions.InvalidHeaderException;
import ai.rev.speechtotext.exceptions.InvalidParameterException;
import ai.rev.speechtotext.exceptions.ResourceNotFoundException;
import ai.rev.speechtotext.exceptions.RevAiApiException;
import ai.rev.speechtotext.exceptions.ThrottlingLimitException;

import java.io.IOException;

/** The ErrorInterceptor class is used to intercept all responses with erroneous response codes. */
public class ErrorInterceptor implements Interceptor {

  public ErrorInterceptor() {}

  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();
    Response response = chain.proceed(request);
    int responseCode = response.code();
    if (responseCode > 399) {
      JSONObject errorResponse = new JSONObject(response.body().string());
      switch (responseCode) {
        case 401:
          throw new AuthorizationException(errorResponse);
        case 400:
          throw new InvalidParameterException(errorResponse);
        case 404:
          throw new ResourceNotFoundException(errorResponse);
        case 406:
          throw new InvalidHeaderException(errorResponse);
        case 409:
          throw new ForbiddenStateException(errorResponse);
        case 429:
          throw new ThrottlingLimitException(errorResponse);
        default:
          throw new RevAiApiException("Unexpected API Error", errorResponse, responseCode);
      }
    } else {
      return response;
    }
  }
}
