package github.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

public class TestDataUtils {
	static Properties prop1=new Properties();
	public static void loadPropertiesFile()
	{
		try
		{
			System.out.println(Constants.TESTDATA_PROPERTIES_FILE);
			File propertyFile=new File(Constants.TESTDATA_PROPERTIES_FILE);
			prop1.load(new FileInputStream(propertyFile));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String propertyName)
	{
		return prop1.getProperty(propertyName);
	}
}
