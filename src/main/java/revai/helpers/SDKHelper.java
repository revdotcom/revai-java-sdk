package revai.helpers;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

public class SDKHelper {

  /*
  Helper function: reads the current sdk version from pom.xml
  */
  public static String getSdkVersion() {
    // reads the current sdk version from pom.xml
    MavenXpp3Reader reader = new MavenXpp3Reader();
    Model model = null;
    try {
      model = reader.read(new FileReader("pom.xml"));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    }
    return model.getVersion();
  }
}
