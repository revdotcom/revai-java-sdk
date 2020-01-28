package revai;

import retrofit2.Call;
import retrofit2.http.*;
import revai.models.asynchronous.RevAiAccount;
import revai.models.asynchronous.RevAiJob;
import revai.models.asynchronous.RevAiJobOptions;
import revai.models.asynchronous.RevAiTranscript;

import java.util.List;
import java.util.Map;

/**
 * The ApiInterface is a type-safe Retrofit interface that presents all the endpoints that are made to
 * communicate with the Rev.AI API.
 */
public interface ApiInterface {
  String REV_JSON_CONTENT_TYPE = "application/vnd.rev.transcript.v1.0+json";
  String REV_TEXT_CONTENT_TYPE = "text/plain";

  @GET("account")
  Call<RevAiAccount> getAccount();

  @GET("jobs/{id}")
  Call<RevAiJob> getJobDetails(@Path("id") String jobID);

  @GET("jobs")
  Call<List<RevAiJob>> getListOfJobs(@QueryMap Map<String, String> options);

  @Headers("Accept: " + REV_JSON_CONTENT_TYPE)
  @GET("jobs/{id}/transcript")
  Call<RevAiTranscript> getTranscriptObject(@Path("id") String jobID);

  @Headers("Accept: " + REV_TEXT_CONTENT_TYPE)
  @GET("jobs/{id}/transcript")
  Call<String> getTranscriptText(@Path("id") String jobID);

  @POST("jobs")
  Call<RevAiJob> submitJobUrl(@Body RevAiJobOptions options);

  @DELETE("jobs/{id}")
  Call<Void> deleteJob(@Path("id") String jobID);
}
