package revai;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;

import java.util.List;
import java.util.Map;

public interface ApiInterface {
    @GET("account")
    Call<RevAiAccount> getAccount();

    @GET("jobs/{id}")
    Call<RevAiJob> getJobDetails(@Path("id") String jobID);

    @GET("jobs")
    Call<List<RevAiJob>> getListOfJobs(@QueryMap Map<String, String> options);

    @POST("jobs")
    Call<RevAiJob> sendJobUrl(@Body RevAiJobOptions options);

    @Multipart
    @POST("jobs")
    Call<RevAiJob> sendJobLocalFile(@Part MultipartBody.Part file,
                                    @Part("options") RevAiJobOptions options);
}

