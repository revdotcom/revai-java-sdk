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
import revai.models.asynchronous.RevAiJobOptions;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .addNetworkInterceptor(new ErrorInterceptor())
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rev.ai/revspeech/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.apiInterface = retrofit.create(ApiInterface.class);
    }


    public RevAiAccount getAccount() throws IOException {
        return apiInterface.getAccount().execute().body();
    }

    public RevAiJob getJobDetails(String id) throws IOException {
        return apiInterface.getJobDetails(id).execute().body();
    }

    public List<RevAiJob> getListOfJobs(int limit, String startingAfter) throws IOException {
        Map<String, String> options = new HashMap<String, String>();
        if (startingAfter != null) {
            options.put("starting_after", startingAfter);
        }
        if (limit != Double.POSITIVE_INFINITY){
            options.put("limit", String.valueOf(limit));
        }
        return apiInterface.getListOfJobs(options).execute().body();
    }

    public RevAiJob submitJobUrl(String mediaUrl, RevAiJobOptions options) throws IOException {
        if (options != null){
            options = new RevAiJobOptions();
        }
        options.mediaUrl = mediaUrl;
        return apiInterface.sendJobUrl(options).execute().body();
    }
}
