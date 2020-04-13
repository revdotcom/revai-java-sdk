package revai.helpers;

public class SDKHelper {

  /*
  Helper function: reads the current sdk version from pom.xml
  */
  public static String getSdkVersion() {
    return SDKHelper.class.getPackage().getImplementationVersion();
  }
}
