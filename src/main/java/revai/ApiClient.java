package revai;

import okhttp3.OkHttpClient;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import revai.exceptions.RevAiApiException;
import revai.models.asynchronous.RevAiAccount;

import java.io.FileReader;
import java.io.IOException;



public class ApiClient {
    private static String accessToken;
    public Retrofit retrofit;
    public ApiService apiService;
    public OkHttpClient client;


    public interface ApiService {
        @GET("account")
        Call<RevAiAccount> getAccount();
    }

    /*
   Helper function: reads the current sdk version from pom.xml
    */
    private static String getSdkVersion() throws IOException, XmlPullParserException {
        //reads the current sdk version from pom.xml
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return model.getVersion();
    }

    /*
    Helper function: manually closes the connection when the code is running in a JVM
     */
    private void closeConnection() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }


    public ApiClient(String accessToken) throws IOException, XmlPullParserException {
        this.accessToken = accessToken;
        this.client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ApiInterceptor(accessToken, this.getSdkVersion()))
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.apiService = retrofit.create(ApiService.class);
    }


    public RevAiAccount getAccount() throws RevAiApiException, IOException {
        try {
            Call<RevAiAccount> call = apiService.getAccount();
            Response<RevAiAccount> response = call.execute();
            RevAiAccount account = response.body();
            return account;
        } finally {
            this.closeConnection();
        }
    }
}