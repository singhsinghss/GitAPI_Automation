package github.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class EnvironmentDetails {
 
	static Properties prop=new Properties();
	public static void loadPropertiesFile()
	{
		try
		{
			File propertyFile=new File(Constants.ENVIRONMENT_DETAILS);
			prop.load(new FileInputStream(propertyFile));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String propertyName)
	{
		return prop.getProperty(propertyName);
	}
}
