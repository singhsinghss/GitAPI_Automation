package ValidateAllReqRes_functionality;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.fge.jsonschema.keyword.validator.draftv4.AllOfValidator;
import com.jayway.jsonpath.*;
import github.utils.APIHelper;
import github.utils.BaseTest;
import github.utils.Constants;
import github.utils.JsonUtils;
import github.utils.TestDataUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import responsePOJO.StatusRepository;

import static org.hamcrest.Matchers.equalTo;


public class ValidateAddandDelData_functionality extends BaseTest {
	APIHelper apiHelper;
	
	@BeforeClass
	public void beforeClass()
	{
		apiHelper=new APIHelper();
		TestDataUtils.loadPropertiesFile();
	}
	
  @Test (priority = 0, description = "Validate single repository")
  public void validateGetData() throws IOException {
	  Response response = apiHelper.getSingleRepo();
      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Get single Repo functionality is not working as expected.");
      
      String actualResponse = response.jsonPath().prettyPrint();
      String actualHeader=response.header("Content-Type");
      String expectedHeader="application/json; charset=utf-8";
      Assert.assertEquals(actualHeader,expectedHeader, "Headers do not match");
      
      JsonUtils.validateSchema(actualResponse, "GetSingleRepo_Schema.json");
      String fileReadyToRead=readFileReturnString(Constants.EXPECTED_GETSINGLEREPODATA);
      
      String expected_FullName=JsonPath.read(fileReadyToRead,"$.full_name");
      String expected_defaultBranch=JsonPath.read(fileReadyToRead, "$.default_branch");
            
      response.then().body("full_name",equalTo(expected_FullName));
      response.then().body("default_branch",equalTo(expected_defaultBranch));
      
  }
  
  @Test (priority = 1, description = "validate non existing repository")
  public void validateNonExistingRepo() {
	  Response response=apiHelper.getNonExistingSingleRepo();
	  Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND," Mentioned repo not found");
	  System.out.println("response time: "+response.time());
	  System.out.println("Response time in milisecond: "+response.timeIn(TimeUnit.MILLISECONDS));
	  StatusRepository statusResponse=response.as(StatusRepository.class);
	  Assert.assertEquals(statusResponse.getMessage(),TestDataUtils.getProperty("InvalidEndPoint"), "Not Found");
  }
   @Test (priority = 2, description = "validate get all repositories")
   public void validateGetAllRepository() throws IOException {
	   Response response=apiHelper.getAllRepos();
	   Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK,"All repositories are not displayed");
	   String actualResponse=response.jsonPath().prettyPrint();
	   String actualHeader=response.header("Content-Type");
	   String expectedHeader="application/json; charset=utf-8";
	   Assert.assertEquals(actualHeader, expectedHeader,"Headers do not match");
	   JsonUtils.validateJsonSchema_arr(actualResponse, "GetAllRepoSchema.json");
	   String fileReadyToRead=readFileReturnString(Constants.EXPECTED_GETALLREPOATA);
		   
	    net.minidev.json.JSONArray total_Repo_Count=JsonPath.read(fileReadyToRead, "$.[*].name");
	    int count=total_Repo_Count.size();
	   System.out.println("Total count of repositories are : "+count);
	   System.out.println("Name of repositories are: ");
	   for(int i=0;i<count;i++)
	   {
		   System.out.print(JsonPath.read(fileReadyToRead, "$.["+i+"].name")+" , ");
	   }
	   System.out.println();
	   
   }
   
   
}
