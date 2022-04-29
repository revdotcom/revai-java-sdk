package ai.rev.speechtotext.models;

import com.google.gson.annotations.SerializedName;

public class CustomerUrlData {
    /** Customer provided url */
    @SerializedName("url")
    private String url;

    /** Authentication headers to access the url
     * Only authorization is currently supported
     * Example: "Authorization: Bearer <token>"*/
    @SerializedName("auth_headers")
    private String authHeaders;

    public CustomerUrlData(String url, String authHeaders)
    {
        this.url = url;
        this.authHeaders = authHeaders;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthHeaders(String authHeaders) {
        this.authHeaders = authHeaders;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthHeaders() {
        return authHeaders;
    }
}
