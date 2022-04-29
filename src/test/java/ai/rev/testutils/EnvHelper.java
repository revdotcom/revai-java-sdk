package ai.rev.testutils;

public class EnvHelper {

  public static String getToken() {
    return System.getenv("REVAI_ACCESS_TOKEN");
  }
}
