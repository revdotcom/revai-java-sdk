package revai;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;

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
    Call<RevAiJob> sendJobUrl(@QueryMap Map<String, String> options);
}

