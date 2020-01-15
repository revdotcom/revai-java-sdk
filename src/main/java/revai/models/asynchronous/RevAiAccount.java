package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

public class RevAiAccount {

    public String email;

    @SerializedName("balance_seconds")
    public int balanceSeconds;

    public RevAiAccount(String email, int balanceSeconds) {
        this.email = email;
        this.balanceSeconds = balanceSeconds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RevAiAccount)) {
            return false;
        } else {
            final RevAiAccount other = (RevAiAccount) obj;
            return email.equals(other.email) && balanceSeconds == other.balanceSeconds;
        }
    }
/*
    public void from_json(JSONObject jsonResponse) throws Exception {
        email = jsonResponse.get("email").toString();
        balanceSeconds = Double.parseDouble(jsonResponse.get("balance_seconds").toString());
    }
*/
    /*
    Helper function for testing purposes
     */
    public String displayInfo() {
        return "email: " + email + ", balance_seconds: " + balanceSeconds;
    }
}


