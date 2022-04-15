package ai.rev.speechtotext.models;

public class CustomerUrlData {

    private String url;
    private String authHeaders;
    public CustomerUrlData(String url, String authHeaders)
    {
        this.url = url;
        this.authHeaders = authHeaders;
    }

    @Override
    public String toString() {
        return "{"
                + "url='"
                + this.url
                + '\''
                + ", auth_headers='"
                + this.authHeaders
                + '}';
    }
}
