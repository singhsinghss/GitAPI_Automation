package ValidateAllReqRes_functionality;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import github.utils.APIHelper;
import github.utils.BaseTest;
import github.utils.Constants;

import io.restassured.response.Response;
import requestPOJO.CreateRepo;
import requestPOJO.UpdateRepo;


import static org.hamcrest.Matchers.equalTo;

public class Create_update_del_Repo extends BaseTest {
	APIHelper apiHelper;
	String name,description,homePage;
	boolean Private;
	@BeforeClass
	public void BeforeClass()
	{
		apiHelper=new APIHelper();
	}
  @Test
  public void createRepository() throws IOException {
	  name="Hello-World";
	  description="creating new repo";
	  homePage="https://github.com";
	  Private=false;
	  
	  CreateRepo createNewRepo=CreateRepo.builder().Repo_Name(name).description(description).homePage(homePage).repoType(Private).build();
	  Response response= apiHelper.createRepo(createNewRepo);
	  Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_CREATED," New repository could not be created");
	  
	  String readResponseFile=readFileReturnString(Constants.CREATE_REPO_RESPONSE);
	  String expected_Name=JsonPath.read(readResponseFile,"$.name");
	  String expected_ownerLogin=JsonPath.read(readResponseFile,"$.owner.login");
	  String expected_ownerType=JsonPath.read(readResponseFile, "$.owner.type");
	  
	  response.then().body("name", equalTo(expected_Name));
	  response.then().body("owner.login", equalTo(expected_ownerLogin));
	  response.then().body("owner.type", equalTo(expected_ownerType));
	  System.out.println("time taken for creating repository: "+response.then().using().extract().timeIn(TimeUnit.SECONDS));
  }
  
  @Test
  
  public void createExistingRepo() throws IOException 
  {
	  name="Hello-World";
	  description="creating new repo";
	  homePage="https://github.com";
	  Private=false;
	  
	  CreateRepo existingRepo=CreateRepo.builder().Repo_Name(name).description(description).homePage(homePage).repoType(Private).build();
	  Response response=apiHelper.createExistingRepo(existingRepo);
	  Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_UNPROCESSABLE_ENTITY, "new repository has been created OR some error occured");
	  
	  String readDataFile=readFileReturnString(Constants.CREATE_EXISTING_REPO_RESPONSE);
	  String expectedMsg=JsonPath.read(readDataFile, "$.errors[0].message");
	  
	  response.then().body("errors[0].message", equalTo(expectedMsg));
  }
  
  @Test
  public void UpdateRepository() throws IOException
  {
	  name="dummy_repo";
	  description="Updated repository";
	  Private=false;
	  
	  UpdateRepo updateRepo=UpdateRepo.builder().name(name).desc(description).Private(Private).build();
	  Response response=apiHelper.updateRepository(updateRepo);
	  Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK,"Repository not updated");
	 System.out.println("response time: "+response.then().using().extract().timeIn(TimeUnit.SECONDS)+"s");
	 
	 String readResponseFile=readFileReturnString(Constants.UPDATE_REPO_RESPONSE);
	  String expectedName=JsonPath.read(readResponseFile, "$.name");
	  response.then().body("name", equalTo(expectedName));
	 // System.out.println("Time taken for updating repo: "+response.then().using().extract().timeIn(TimeUnit.SECONDS));
  }
  
  @Test
  public void deleteExistingRepo() {
	  Response response=apiHelper.deleteExistingRepo();
	  Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT,"Repository can not be deleted");
	  Assert.assertEquals(response.body().asString(),"");
  }
  
  @Test
  public void deleteNonExistingRepo() {
	  Response response=apiHelper.deleteNonExistingRepo();
	  Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND,"deleted the repository");
	  String expectedMsg="Not Found";
	  String actualMsg=response.jsonPath().get("message");
	  Assert.assertEquals(actualMsg, expectedMsg,"Repository found");
  }
}
