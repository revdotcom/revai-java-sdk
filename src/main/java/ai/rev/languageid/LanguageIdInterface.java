package ai.rev.languageid;

import ai.rev.languageid.models.LanguageIdJob;
import ai.rev.languageid.models.LanguageIdJobOptions;
import ai.rev.languageid.models.LanguageIdResult;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * The LanguageIdInterface is a type-safe Retrofit interface that presents all the endpoints that are made
 * to communicate with the Rev AI Language Identification API.
 */
public interface LanguageIdInterface {
  String REV_LANGUAGE_ID_CONTENT_TYPE = "application/vnd.rev.languageid.v1.0+json";

  @GET("jobs/{id}")
  Call<LanguageIdJob> getJobDetails(@Path("id") String jobID);

  @GET("jobs")
  Call<List<LanguageIdJob>> getListOfJobs(@QueryMap Map<String, String> options);

  @Headers("Accept: " + REV_LANGUAGE_ID_CONTENT_TYPE)
  @GET("jobs/{id}/result")
  Call<LanguageIdResult> getResultObject(@Path("id") String jobID);

  @POST("jobs")
  Call<LanguageIdJob> submitJob(@Body LanguageIdJobOptions options);

  @Multipart
  @POST("jobs")
  Call<LanguageIdJob> submitJobLocalFile(@Part MultipartBody.Part file, @Part("options") LanguageIdJobOptions options);

  @DELETE("jobs/{id}")
  Call<Void> deleteJob(@Path("id") String jobID);
}
