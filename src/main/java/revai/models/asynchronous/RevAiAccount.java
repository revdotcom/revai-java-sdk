package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;

/**
 * The RevAiAccount object provides basic information about a Rev.ai account associated with a valid
 * access token.
 *
 * @see <a href="https://www.rev.ai/docs#tag/Account">https://www.rev.ai/docs#tag/Account</a>
 */
public class RevAiAccount {

  /**
   * The email associated with the account.
   */
  @SerializedName("email")
  private String email;

  /**
   * The number of seconds remaining on the account.
   */
  @SerializedName("balance_seconds")
  private Integer balanceSeconds;

  /**
   * Returns a String containing the account email.
   * @return A String containing the account email.
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the email value
   * @param email The String value to set as the {@link RevAiAccount#email}.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the remaining number of seconds that can be used on the account.
   * @return The number of seconds remaining on the account
   */
  public Integer getBalanceSeconds() {
    return this.balanceSeconds;
  }

  /**
   * Sets the balanceSeconds value. This cannot be used to affect the actual number of seconds remaining.
   * @param balanceSeconds The Integer value to set as the {@link RevAiAccount#balanceSeconds}.
   */
  public void setBalanceSeconds(Integer balanceSeconds) {
    this.balanceSeconds = balanceSeconds;
  }
}
