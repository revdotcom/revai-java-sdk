package ai.rev.topicextraction;

import ai.rev.topicextraction.models.TopicExtractionJob;
import ai.rev.topicextraction.models.TopicExtractionJobOptions;
import ai.rev.topicextraction.models.TopicExtractionResult;
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
 * The TopicExtractionInterface is a type-safe Retrofit interface that presents all the endpoints that are made
 * to communicate with the Rev AI Topic Extraction API.
 */
public interface TopicExtractionInterface {
    @GET("jobs/{id}")
    Call<TopicExtractionJob> getJobDetails(@Path("id") String jobID);

    @GET("jobs")
    Call<List<TopicExtractionJob>> getListOfJobs(@QueryMap Map<String, String> options);

    @GET("jobs/{id}/result")
    Call<TopicExtractionResult> getResultObject(@Path("id") String jobID, @QueryMap Map<String, Object> options);

    @POST("jobs")
    Call<TopicExtractionJob> submitJob(@Body TopicExtractionJobOptions options);

    @DELETE("jobs/{id}")
    Call<Void> deleteJob(@Path("id") String jobID);
}
