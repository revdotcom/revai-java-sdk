package ai.rev.sentimentanalysis;

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
 * The SentimentAnalysisInterface is a type-safe Retrofit interface that presents all the endpoints that are made
 * to communicate with the Rev AI Sentiment Analysis API.
 */
public interface SentimentAnalysisInterface {
    String REV_SENTIMENT_CONTENT_TYPE = "application/vnd.rev.sentiment.v1.0+json";

    @GET("jobs/{id}")
    Call<TopicExtractionJob> getJobDetails(@Path("id") String jobID);

    @GET("jobs")
    Call<List<TopicExtractionJob>> getListOfJobs(@QueryMap Map<String, String> options);

    @Headers("Accept: " + REV_SENTIMENT_CONTENT_TYPE)
    @GET("jobs/{id}/result")
    Call<TopicExtractionResult> getResultObject(@Path("id") String jobID, @QueryMap Map<String, Object> options);

    @POST("jobs")
    Call<TopicExtractionJob> submitJob(@Body TopicExtractionJobOptions options);

    @DELETE("jobs/{id}")
    Call<Void> deleteJob(@Path("id") String jobID);
}
