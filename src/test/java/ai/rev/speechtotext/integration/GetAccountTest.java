package ai.rev.speechtotext.integration;

import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.exceptions.AuthorizationException;
import ai.rev.speechtotext.models.asynchronous.RevAiAccount;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GetAccountTest {

  @Test
  public void GetAccount_WhenTokenIsValid_ReturnsRevAiAccount() throws IOException {
    ApiClient apiClient = new ApiClient(EnvHelper.getToken());
    RevAiAccount revAiAccount = apiClient.getAccount();
    assertThat(revAiAccount.getBalanceSeconds()).isNotNull();
    assertThat(revAiAccount.getEmail()).isNotNull();
  }

  @Test
  public void GetAccount_WhenTokenIsInvalid_ReturnsAuthorizationException() {
    ApiClient apiClient = new ApiClient("FAKE_TOKEN");
    assertThatExceptionOfType(AuthorizationException.class)
        .isThrownBy(() -> apiClient.getAccount());
  }
}
