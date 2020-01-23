package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

public class RevAiAccount {

  public String email;

  @SerializedName("balance_seconds")
  public int balanceSeconds;
}
