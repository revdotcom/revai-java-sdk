package ai.rev.speechtotext.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CustomerUrlData {
    /** Customer provided url */
    @SerializedName("url")
    private String url;

    /** Authentication headers to access the url
     * Only authorization is currently supported
     * Example:
     *  Map<String, String> authHeaders = new HashMap<>();
     *  authHeaders.put("Authorization", "Bearer <token>");
     *  "*/
    @SerializedName("auth_headers")
    private Map<String, String> authHeaders;

    public CustomerUrlData(String url, Map<String, String> authHeaders)
    {
        this.url = url;
        this.authHeaders = authHeaders;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthHeaders(Map<String, String> authHeaders) {
        this.authHeaders = authHeaders;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getAuthHeaders() {
        return authHeaders;
    }
}
