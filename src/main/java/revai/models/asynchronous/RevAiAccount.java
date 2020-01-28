package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * A RevAiAccount object provides basic information about a Rev.AI account associated with a valid access token.
 */
public class RevAiAccount {

  @SerializedName("email")
  private String email;

  @SerializedName("balance_seconds")
  private Integer balanceSeconds;

  public String getEmail(){
    return this.email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public Integer getBalanceSeconds(){
    return this.balanceSeconds;
  }

  public void setBalanceSeconds(Integer balanceSeconds){
    this.balanceSeconds = balanceSeconds;
  }

}
