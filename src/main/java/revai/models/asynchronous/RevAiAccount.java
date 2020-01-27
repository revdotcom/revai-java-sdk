package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public class RevAiAccount {

  @SerializedName("email")
  public String email;

  @SerializedName("balance_seconds")
  public Integer balanceSeconds;
}
