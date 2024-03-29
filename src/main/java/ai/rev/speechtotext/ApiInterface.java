package ai.rev.speechtotext;

import ai.rev.speechtotext.models.asynchronous.*;
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
 * to communicate with the Rev AI async API.
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

  @Headers("Accept: " + REV_JSON_CONTENT_TYPE)
  @GET("jobs/{id}/transcript/translation/{language}")
  Call<RevAiTranscript> getTranslatedTranscriptObject(@Path("id") String jobID, @Path("language") String language);

  @Headers("Accept: " + REV_TEXT_CONTENT_TYPE)
  @GET("jobs/{id}/transcript/translation/{language}")
  Call<String> getTranslatedTranscriptText(@Path("id") String jobID, @Path("language") String language);

  @Headers("Accept: " + REV_TEXT_CONTENT_TYPE)
  @GET("jobs/{id}/transcript/summary")
  Call<String> getTranscriptSummaryText(@Path("id") String jobID);

  @Headers("Accept: application/json")
  @GET("jobs/{id}/transcript/summary")
  Call<Summary> getTranscriptSummaryObject(@Path("id") String jobID);

  @POST("jobs")
  Call<RevAiJob> submitJobUrl(@Body RevAiJobOptions options);

  @DELETE("jobs/{id}")
  Call<Void> deleteJob(@Path("id") String jobID);

  @Multipart
  @POST("jobs")
  Call<RevAiJob> submitJobLocalFile(
      @Part MultipartBody.Part file, @Part("options") RevAiJobOptions options);

  @GET("jobs/{id}/captions")
  Call<ResponseBody> getCaptionText(
      @Path("id") String jobID,
      @QueryMap Map<String, String> query,
      @HeaderMap Map<String, String> contentType);

  @GET("jobs/{id}/captions/translation/{language}")
  Call<ResponseBody> getTranslatedCaptionText(
          @Path("id") String jobID,
          @Path("language") String language,
          @QueryMap Map<String, String> query,
          @HeaderMap Map<String, String> contentType);
}
