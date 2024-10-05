package github.utils;

import java.util.HashMap;


import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import requestPOJO.CreateRepo;
import requestPOJO.UpdateRepo;

@Slf4j
public class APIHelper {
  RequestSpecification reqSpec;
  String token="";
  public APIHelper() {
	 RestAssured.baseURI=EnvironmentDetails.getProperty("baseURL");
	 System.out.println(RestAssured.baseURI);
	 reqSpec=RestAssured.given();
  }
  
  public Response getSingleRepo()
  {
	  reqSpec=RestAssured.given();
	  reqSpec.headers(getHeader());
	  Response response=null;
	  try
	  {
		  response=reqSpec.get("/repos/singhsinghss/selenium_framework");
		  response.then().log().all();
	  }catch(Exception e)
	  {
		  Assert.fail("Get Data is failing due to :: "+e.getMessage());
	  }
	return response;
	  
  }
  
  
  public Response getNonExistingSingleRepo()
  {
	  reqSpec=RestAssured.given();
	  reqSpec.headers(getHeader());
	  Response response=null;
	  try
	  {
		response=reqSpec.get("/repos/singhsinghss/test1");
		response.then().log().all();
	  }
	  catch(Exception e)
	  {
		  Assert.fail("Get non existing single repo is failing due to "+e.getMessage());
	  }
	  return response;
	  
  }
  
  public Response getAllRepos() {
	    reqSpec=RestAssured.given();
	    reqSpec.headers(getHeader());
	    Response response=null;
	    try
	    {
	    	response=reqSpec.get("/user/repos");
	    	
	    	response.then().log().all();
	    }
	    catch(Exception e)
	    {
	    	Assert.fail("Get all repos failing due to :: "+e.getMessage());
	    }
	    return response;
  }
  
  public Response createRepo(CreateRepo addRepo)
  {
	  reqSpec=RestAssured.given();
	  Response response=null;
	  try
	  {
		  reqSpec.headers(getHeader());
		  reqSpec.body(new ObjectMapper().writeValueAsString(addRepo));
		  response=reqSpec.post("/user/repos");
		  response.then().log().all();
				  	  
	  }
	  catch(Exception ex)
	  {
		  Assert.fail("Add repo failing due to :: "+ex.getMessage());
	  }
	  return response;
  }
  
  public Response createExistingRepo(CreateRepo createExistingRepo) {
	  reqSpec=RestAssured.given();
	  Response response=null;
	  try
	  {
		  reqSpec.headers(getHeader());
		  reqSpec.body(new ObjectMapper().writeValueAsString(createExistingRepo));
		  response=reqSpec.post("/user/repos");
		  response.then().log().all();
	  }
	  catch(Exception e)
	  {
		  Assert.fail("create existing Repo failing due to :: "+e.getMessage());
	  }
	  return response;
  }
  
  public Response updateRepository(UpdateRepo update_Repo)
  {
	  reqSpec=RestAssured.given();
	  Response response=null;
	  try {
		  reqSpec.headers(getHeader());
		  reqSpec.body(new ObjectMapper().writeValueAsString(update_Repo));
		  response=reqSpec.patch("/repos/singhsinghss/Hello-World");
		  response.then().log().all();
	  }
	  catch(Exception e)
	  {
		Assert.fail("Update repository is failing due to :: "+e.getMessage());  
	  }
	  return response;
  }
  
  public Response deleteExistingRepo()
  {
	  reqSpec=RestAssured.given();
	  reqSpec.headers(getHeader());
	  Response response=null;
	  try
	  {		 
		  response=reqSpec.delete("/repos/singhsinghss/dummy_repo");
		  response.then().log().all();
	  }
	  catch(Exception e)
	  {
		  Assert.fail("delete existing repo is failing due to :: "+e.getMessage());
	  }
	  return response;
  }
  public Response deleteNonExistingRepo() {
	  reqSpec=RestAssured.given();
	  reqSpec.headers(getHeader());
	  Response response=null;
	  try
	  {
		  response=reqSpec.delete("/repos/singhsinghss/xyz");
		  response.then().log().all();
	  }
	  catch(Exception e)
	  {
		  Assert.fail("delete non existing repo is failing due to :: "+e.getMessage());
	  }
	  return response;
  }
  public HashMap<String,String>getHeader(){
	  HashMap<String, String>headers=new HashMap<>();
	  headers.put("Content_Type", "application/json");
	  token=EnvironmentDetails.getProperty("PA_Token");
	  System.out.println("token "+token);
	  headers.put("Authorization", "Bearer "+token);
	  return headers;
  }
}
