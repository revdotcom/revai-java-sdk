package revai.integration;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import revai.ApiClient;
import revai.exceptions.AuthorizationException;
import revai.helpers.EnvHelper;
import revai.models.asynchronous.RevAiAccount;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class GetAccount {

  @Test
  public void canGetAccount() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiAccount revAiAccount = apiClient.getAccount();
    assertThat(revAiAccount.getBalanceSeconds()).isNotNull();
    assertThat(revAiAccount.getEmail()).isNull();
  }

  @Test
  public void cannotGetAccountWithInvalidToken() {
    ApiClient apiClient = new ApiClient(RandomStringUtils.randomAlphabetic(25));
    try {
      apiClient.getAccount();
    } catch (Exception e) {
      if (!(e instanceof AuthorizationException)) {
        Assert.fail();
      }
    }
  }
}
