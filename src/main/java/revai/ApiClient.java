package revai;

import okhttp3.OkHttpClient;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import revai.exceptions.RevAiApiException;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;

import java.io.FileReader;
import java.io.IOException;



public class ApiClient {
    private static String accessToken;

    private Retrofit retrofit;
    private OkHttpClient client;
    public ApiInterface apiInterface;

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
    public void closeConnection() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }


    public ApiClient(String accessToken) throws IOException, XmlPullParserException {
        this.accessToken = accessToken;
        this.client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ApiInterceptor(accessToken, this.getSdkVersion()))
                .addNetworkInterceptor(new ErrorInterceptor(accessToken, this.getSdkVersion()))
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.apiInterface = retrofit.create(ApiInterface.class);
    }


    public RevAiAccount getAccount() throws RevAiApiException, IOException {
        return apiInterface.getAccount().execute().body();
    }

    public RevAiJob getJobDetails(String id) throws IOException {
        return apiInterface.getJobDetails(id).execute().body();
    }
}