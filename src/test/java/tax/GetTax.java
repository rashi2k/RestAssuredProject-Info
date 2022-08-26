package tax;

import utils.ReusableMathods;
import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.path.json.JsonPath;

public class GetTax extends BaseTest {

	@Test(priority = 1)
	public void getAllTax() {
		String responseAllTaxes = given().log().all().header("Accept", "application/json")
				.header("content_type", "application/json").header("Authorization", "Bearer " + System.getProperty("token")).when()
				.get("/control-center/tax").then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath jsonpath = ReusableMathods.rowToJson(responseAllTaxes);

		Object pagination = jsonpath.get("pagination");
		Assert.assertNotNull(pagination);
	}
}
