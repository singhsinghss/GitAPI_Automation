package github.utils;

import java.io.File;

public class Constants {
	
	public final static String PROJECT_HOME=System.getProperty("user.dir");
	public static final String MAIN_RESOURCE=PROJECT_HOME+File.separator+"src"+File.separator+"main"+File.separator+"resources";
	public static final String ENVIRONMENT_DETAILS=MAIN_RESOURCE+File.separator+"EnvironmentDetails"+File.separator+"EnvironmentDetails.properties";
	
	public static final String TESTDATA_PROPERTIES_FILE=MAIN_RESOURCE+File.separator+"TestData"+File.separator+"TestData.properties";
	
	public static final String TEST_RESOURCE=PROJECT_HOME+File.separator+"src"+File.separator+"test"+File.separator+"resources";
	public static final String EXPECTED_GETSINGLEREPODATA=TEST_RESOURCE+File.separator+"ExpectedResponse"+File.separator+"GetSingleRepoData.json";
	public static final String GETREPO_SCHEMA=TEST_RESOURCE+File.separator+"ResponseSchema"+File.separator+"GetSingleRepo_Schema.json";
			
	public static final String EXPECTED_GETALLREPOATA=TEST_RESOURCE+File.separator+"ExpectedResponse"+File.separator+"GetAllRepoData.json";
	public static final String CREATE_REPO_RESPONSE=TEST_RESOURCE+File.separator+"ExpectedResponse"+File.separator+"CreateRepoData.json";
	public static final String CREATE_EXISTING_REPO_RESPONSE=TEST_RESOURCE+File.separator+"ExpectedResponse"+File.separator+"CreateExistingRepoData.json";
	public static final String UPDATE_REPO_RESPONSE=TEST_RESOURCE+File.separator+"ExpectedResponse"+File.separator+"UpdateRepoResponse.json";
}
