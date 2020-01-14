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
            System.out.println("here!");
            return false;
        } else {
            final RevAiAccount other = (RevAiAccount) obj;
            return email.equals(other.email) && balanceSeconds == other.balanceSeconds;
        }
    }

    public void from_json(JSONObject jsonResponse) throws Exception {
        email = jsonResponse.get("email").toString();
        balanceSeconds = Double.parseDouble(jsonResponse.get("balance_seconds").toString());
    }

    /*
    Helper function for testing purposes
     */
    public String displayInfo() {
        return "email: " + email + ", balance_seconds: " + balanceSeconds;
    }
}


