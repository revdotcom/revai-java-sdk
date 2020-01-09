package src.models.asynchronous;
// import org.json.JSONException;
import org.json.JSONObject;



public class RevAiAccount { 
    
    private String email;
    private double balanceSeconds;
    
    public RevAiAccount(String Email, double BalanceSeconds)
    {
        email = Email;
        balanceSeconds = BalanceSeconds;
    }

    public void from_json(JSONObject jsonResponse) throws Exception{
        email = jsonResponse.get("email").toString();
        balanceSeconds = Double.parseDouble(jsonResponse.get("balance_seconds").toString());
    }

    public String displayInfo(){
        return "email: " + email + ", balance_seconds: " + balanceSeconds;
    }
}
    
     
