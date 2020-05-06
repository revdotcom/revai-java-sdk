package ai.rev.speechtotext.helpers;

/** A helper class that provides methods for getting SDK information. */
public class SDKHelper {

  /**
   * Helper function that reads the current sdk version from pom.xml.
   *
   * @return The current SDK version.
   */
  public static String getSdkVersion() {
    return SDKHelper.class.getPackage().getImplementationVersion();
  }
}
