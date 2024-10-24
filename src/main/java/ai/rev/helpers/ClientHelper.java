package ai.rev.helpers;

import ai.rev.helpers.RevAiApiDeploymentConfiguration;
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
    String apiVersion,
    String baseUrl
    ) {
    return new Retrofit.Builder()
        .baseUrl(String.format(
          "%s/%s/%s/",
          baseUrl != null ? baseUrl : RevAiApiDeploymentConfiguration.getConfig(RevAiApiDeploymentConfiguration.RevAiApiDeployment.US).getBaseUrl(),
          apiName,
          apiVersion
        ))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }
}
