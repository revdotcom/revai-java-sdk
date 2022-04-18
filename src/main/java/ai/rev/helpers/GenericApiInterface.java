package ai.rev.helpers;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * The ApiInterface is a type-safe Retrofit interface that presents all the endpoints that are made
 * to communicate with any newer Rev AI API.
 */
public interface ApiInterface<TSubmission, TJob, TResult> {
  @GET("jobs/{id}")
  Call<TJob> getJobDetails(@Path("id") String jobID);

  @GET("jobs")
  Call<List<TJob>> getListOfJobs(@QueryMap Map<String, String> options);

  @GET("jobs/{id}/result")
  Call<TResult> getResultObject(@Path("id") String jobID, @QueryMap Map<String, String> options);

  @POST("jobs")
  Call<TJob> submitJob(@Body TSubmission options);

  @DELETE("jobs/{id}")
  Call<Void> deleteJob(@Path("id") String jobID);
}
