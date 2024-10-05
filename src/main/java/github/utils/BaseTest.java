package github.utils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.BeforeSuite;

public class BaseTest {
  
  @BeforeSuite
  public void beforeSuite() {
	  EnvironmentDetails.loadPropertiesFile();
  }
  protected String readFileReturnString(String filepath) throws IOException
  {
	  byte[]encoded=Files.readAllBytes(Paths.get(filepath));
	  return new String(encoded,StandardCharsets.UTF_8);
  }

}
