package revai.models.asynchronous;

import org.json.JSONObject;

public class RevAiAccount {

    private String email;
    private double balanceSeconds;

    public RevAiAccount(String Email, double BalanceSeconds) {
        email = Email;
        balanceSeconds = BalanceSeconds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RevAiAccount)) {
            return false;
        } else {
            final RevAiAccount other = (RevAiAccount) obj;
            return email == other.email && balanceSeconds == other.balanceSeconds;
        }
    }

    public void from_json(JSONObject jsonResponse) throws Exception {
        email = jsonResponse.get("email").toString();
        balanceSeconds = Double.parseDouble(jsonResponse.get("balance_seconds").toString());
    }

    public String displayInfo() {
        return "email: " + email + ", balance_seconds: " + balanceSeconds;
    }
}


