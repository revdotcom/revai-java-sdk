package ai.rev.speechtotext.helpers;

import ai.rev.speechtotext.interceptors.ApiInterceptor;
import ai.rev.speechtotext.interceptors.ErrorInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ClientHelper {

  public static OkHttpClient createOkHttpClient(String accessToken) {
    return new OkHttpClient.Builder()
        .retryOnConnectionFailure(false)
        .addNetworkInterceptor(new ApiInterceptor(accessToken, SDKHelper.getSdkVersion()))
        .addNetworkInterceptor(new ErrorInterceptor())
        .retryOnConnectionFailure(false)
        .build();
  }

  public static Retrofit createRetrofitInstance(OkHttpClient client) {
    return new Retrofit.Builder()
        .baseUrl("https://api.rev.ai/speechtotext/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }
}
