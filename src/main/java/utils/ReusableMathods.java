package utils;

import io.restassured.path.json.JsonPath;

public class ReusableMathods {

	public static JsonPath rowToJson(String response) {
		JsonPath jsonPath = new JsonPath(response);
		
		return jsonPath;
	}
}
