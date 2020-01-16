package revai;

import retrofit2.Call;
import retrofit2.http.GET;
import revai.models.asynchronous.RevAiAccount;

public interface ApiService {
    @GET("account")
    Call<RevAiAccount> getAccount();
}
