package ai.rev.speechtotext.integration;

public class EnvHelper {

    public static String getToken() {
        return System.getenv("REAI_ACCESS_TOKEN");
    }
}
