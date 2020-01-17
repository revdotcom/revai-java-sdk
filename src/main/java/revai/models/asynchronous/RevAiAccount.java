package revai.models.asynchronous;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

public class RevAiAccount {

    public String email;

    @SerializedName("balance_seconds")
    public int balanceSeconds;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RevAiAccount)) {
            return false;
        } else {
            final RevAiAccount other = (RevAiAccount) obj;
            return email.equals(other.email) && balanceSeconds == other.balanceSeconds;
        }
    }
}


