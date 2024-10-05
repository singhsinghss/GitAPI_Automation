package github.utils;

import org.testng.Assert;

import com.google.gson.JsonArray;

import java.io.File;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
  
  public static void validateSchema(String actualResponse,String schemaFile) {
	  JSONObject jsonSchemaExpected=new JSONObject(
			  new JSONTokener(Objects.requireNonNull(JsonUtils.class.getResourceAsStream("/ResponseSchema" + File.separator + schemaFile))));
	  JSONObject jsonSubjectActual=new JSONObject(new JSONTokener(actualResponse));
	  Schema schema=SchemaLoader.load(jsonSchemaExpected);
	  try
	  {
		  schema.validate(jsonSubjectActual);
	  }catch (Exception e) {
		// TODO: handle exception
		  log.error("schema validation fail due to "+e.getMessage());
		  Assert.fail("schema validation fail due to "+e.getMessage());
		  
	}
	  
  }
  
  public static void validateJsonSchema_arr(String actualResponse,String schemaFile)
  {
	  JSONObject jsonSchemaExpected=new JSONObject(
			  new JSONTokener(Objects.requireNonNull(JsonUtils.class.getResourceAsStream("/ResponseSchema" + File.separator + schemaFile))));
	 // JSONObject jsonSubjectActual=new JSONObject(new JSONTokener(actualResponse));
	  JSONArray json_arr=new JSONArray(actualResponse);
	  JSONObject jsonSubjectActual=(JSONObject) json_arr.get(0);
	  
	   Schema schema=SchemaLoader.load(jsonSchemaExpected);
	  try
	  {
		  schema.validate(jsonSubjectActual);
	  }catch (Exception e) {
		// TODO: handle exception
		  log.error("schema validation fail due to "+e.getMessage());
		  Assert.fail("schema validation fail due to "+e.getMessage());
		  
	}
  }
}
