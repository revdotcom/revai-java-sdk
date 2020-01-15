package revai;

import okhttp3.OkHttpClient;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import revai.models.asynchronous.RevAiAccount;

import java.io.IOException;
import java.util.logging.LogManager;

import org.json.JSONObject;

public class ApiClient {
    private static String accessToken;
    private static String accessParam;
    public Retrofit retrofit;
    public ApiService apiService;
    public OkHttpClient client;


    public interface ApiService {
        @GET("account")
        Call<RevAiAccount> getAccount();
    }

    public ApiClient(String accessToken) throws IOException, XmlPullParserException {
        this.accessToken = accessToken;
        this.client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ApiInterceptor(accessToken))
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.apiService = retrofit.create(ApiService.class);
    }


    public RevAiAccount getAccount() throws IOException {
        Call<RevAiAccount> call = apiService.getAccount();
        Response<RevAiAccount> response = call.execute();
        RevAiAccount account = response.body();
        response.raw().body().close();
        System.out.println("here");
        return account;
    }




}