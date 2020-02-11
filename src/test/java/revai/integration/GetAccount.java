package revai.integration;

import org.junit.Test;
import revai.ApiClient;
import revai.helpers.EnvHelper;

import java.io.IOException;

public class RevAiAccount {

    @Test
    public void canGetAccount() throws IOException {
        ApiClient apiClient = new ApiClient(EnvHelper.getToken());
        revai.models.asynchronous.RevAiAccount revAiAccount = apiClient.getAccount();
        System.out.println(
                "Email: " + revAiAccount.getEmail() +
                        "Balance: " + revAiAccount.getBalanceSeconds());
    }
}
