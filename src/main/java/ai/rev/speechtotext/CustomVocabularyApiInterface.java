package ai.rev.speechtotext;

import ai.rev.speechtotext.models.vocabulary.CustomVocabularyInformation;
import ai.rev.speechtotext.models.vocabulary.CustomVocabularyOptions;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface CustomVocabularyApiInterface {

  @POST("vocabularies")
  Call<CustomVocabularyInformation> submitCustomVocabularies(@Body CustomVocabularyOptions options);

  @GET("vocabularies")
  Call<List<CustomVocabularyInformation>> getListOfCustomVocabularyInformation();

  @GET("vocabularies/{id}")
  Call<CustomVocabularyInformation> getCustomVocabularyInformation(@Path("id") String jobId);
}
