package utils;

public class Payloads {

	public static String login(String username, String password) {
		return "{\r\n" + 
				"  \"userName\": \""+ username + "\",\r\n" + 
				"  \"password\": \""+password+"\"\r\n" + 
				"}";
	}
	

	public static String logout(String refreshToken) {
		return "{\r\n" + 
				"  \"refreshToken\": \""+ refreshToken+"\",\r\n" + 
				"  \"logoutStatus\": true\r\n" + 
				"}";
	}


	
	public static String addTax(String code, String taxTypeCode, String taxType, boolean isActive, int countryId,
			String countryName) {
		return " {\r\n" + 
				"  \"taxTypeId\": " +  code + ",\r\n" + 
				"  \"taxTypeCode\": \"" + taxTypeCode + "\",\r\n" + 
				"  \"taxType\": \"" + taxType + "\",\r\n" + 
				"  \"isActive\": " + isActive + ",\r\n" + 
				"  \"countryId\": "+ countryId + ",\r\n" + 
				"  \"countryName\": \"" + countryName + "\"\r\n" + 
				"}";
	}

}
