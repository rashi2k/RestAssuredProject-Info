package base;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import config.Constants;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.Payloads;
import utils.ReusableMathods;

public class BaseTest {

	// This class will initialise authorisation data before all tests and clear authorisation data after complete the all tests 
	
	@BeforeSuite
	public static void setup() {
		RestAssured.baseURI = Constants.baseURL;
		RestAssured.useRelaxedHTTPSValidation();
		
		getAccessTokens();
	}

	 
	public static void getAccessTokens() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Accept", "application/json").body(Payloads.login(Constants.username, Constants.password))
				.when().post("/security-center/auth/login").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();

		JsonPath jsonPath = ReusableMathods.rowToJson(response);

		String token = jsonPath.get("result.access_token");
		String refreshToken = jsonPath.get("result.refresh_token");

		// Cache tokens in the system properties for future use
		System.setProperty("token", token);
		System.setProperty("refresh_token", refreshToken);

	}

	@AfterSuite
	public void removeAllAccessTokens() {
		given().log().all().header("Content-Type", "application/json").when()
				.body(Payloads.logout(System.getProperty("refresh_token"))).post("/security-center/auth/logout").then()
				.assertThat().log().all().statusCode(200).extract().response().asString();

		// clear all tokens from system properties
		System.clearProperty("token");
		System.clearProperty("refresh_token");

	}

}
