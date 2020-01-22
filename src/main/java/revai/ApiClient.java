package revai;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.internal.concurrent.Task;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import revai.exceptions.RevAiApiException;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiTranscript;

import java.io.File;
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
                .addConverterFactory(ScalarsConverterFactory.create())
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

    public RevAiTranscript getTranscriptObject(String id) throws IOException {
        return apiInterface.getTranscriptObject(id).execute().body();
    }

    public String getTranscriptText(String id) throws IOException {
        return apiInterface.getTranscriptText(id).execute().body();
    }

    public List<RevAiJob> getListOfJobs(Integer limit, String startingAfter) throws IOException {
        Map<String, String> options = new HashMap<String, String>();
        if (startingAfter != null) {
            options.put("starting_after", startingAfter);
        }
        if (limit != null){
            options.put("limit", String.valueOf(limit));
        }
        return apiInterface.getListOfJobs(options).execute().body();
    }

    public RevAiJob submitJobUrl(String mediaUrl, RevAiJobOptions options) throws IOException {
        if (options == null){
            options = new RevAiJobOptions();
        }
        options.mediaUrl = mediaUrl;
        return apiInterface.sendJobUrl(options).execute().body();
    }

    public RevAiJob submitJobLocalFile(String filepath, RevAiJobOptions options) throws IOException {
        File file = new File(filepath);
        RequestBody fileRequest =
                RequestBody.create(
                        file,
                        MediaType.parse("audio/*")
                );
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("media", file.getName(), fileRequest);
        return apiInterface.sendJobLocalFile(filePart, options).execute().body();
    }


}
