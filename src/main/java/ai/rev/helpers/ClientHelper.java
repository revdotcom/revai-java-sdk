package ai.rev.helpers;

import ai.rev.helpers.ApiInterceptor;
import ai.rev.helpers.ErrorInterceptor;
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

  public static Retrofit createRetrofitInstance(
    OkHttpClient client,
    String apiName,
    String apiVersion
    ) {
    return new Retrofit.Builder()
        .baseUrl(String.format("https://api-test.rev.ai/%s/%s/", apiName, apiVersion))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }
}
