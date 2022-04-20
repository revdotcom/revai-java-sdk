package ai.rev.speechtotext.integration;

import ai.rev.exceptions.AuthorizationException;
import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.asynchronous.RevAiAccount;
import ai.rev.testutils.EnvHelper;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GetAccountTest {

  @Test
  public void GetAccount_TokenIsValid_ReturnsRevAiAccount() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());

    RevAiAccount revAiAccount = apiClient.getAccount();

    assertThat(revAiAccount.getBalanceSeconds()).as("Account balance").isNotNull();
    assertThat(revAiAccount.getEmail()).as("Account email").isNotNull();
  }

  @Test
  public void GetAccount_TokenIsInvalid_ReturnsAuthorizationException() {
    ApiClient apiClient = new ApiClient("FAKE_TOKEN");

    assertThatExceptionOfType(AuthorizationException.class)
        .as("Exception type")
        .isThrownBy(() -> apiClient.getAccount());
  }
}
