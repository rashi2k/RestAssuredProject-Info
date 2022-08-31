package tax;

import utils.Payloads;
import utils.ReusableMathods;
import static io.restassured.RestAssured.given;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.path.json.JsonPath;

public class PostTax extends BaseTest {

	
	private int countryId;
	private String countryName;

	//test tax data for create  the tax pay load 
	String taxTypeId = null; //String.valueOf((char) (new Random().nextInt(26) + 'A'));
	String taxTypeCode =  "" +((int) (Math.random() * 9999));
	String taxType = "AUT_TAX_" + ((int) (Math.random() * 99999));
	boolean isActive = true;

	@BeforeMethod
	public void initiate() {
		// Get country details for exact country name and country id
		String response = given().header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + System.getProperty("token")).log().all().when()
				.get("/control-center/location/country").then().log().all().extract().response().asString();
		JsonPath jsonPath = ReusableMathods.rowToJson(response);

		countryName = jsonPath.get("result[0].countryName");
		countryId = jsonPath.get("result[0].countryId");
	}

	@Test(priority = 1)
	public void createTax() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Accept", "application/json").header("Authorization", "Bearer " + System.getProperty("token"))
				.body(Payloads.addTax(taxTypeId , taxTypeCode, taxType, isActive, countryId, countryName)).when().post("/control-center/tax").then().log().all()
				.assertThat().statusCode(201).extract().response().asString();

		JsonPath jsonpath = ReusableMathods.rowToJson(response);
		
		Assert.assertNotEquals(jsonpath.getString("result.taxTypeId"), taxTypeId);
		Assert.assertEquals(jsonpath.get("result.taxTypeCode"), taxTypeCode);
		Assert.assertEquals(jsonpath.get("result.taxType"), taxType);
		Assert.assertEquals(jsonpath.get("result.isActive"), isActive);
		Assert.assertEquals(jsonpath.get("result.countryId"), countryId);
		Assert.assertEquals(jsonpath.get("result.countryName"), countryName);

	}

}
