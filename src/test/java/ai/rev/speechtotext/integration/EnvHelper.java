package ai.rev.speechtotext.integration;

public class EnvHelper {

  public static String getToken() {
    return System.getenv("REVAI_ACCESS_TOKEN");
  }
}
