package utils;

public class ValidatePhoneNumber {

	/*
	 * Validate phone number must contain +62
	 */
	public static Boolean validate(String words) {
		 
	    if (!words.startsWith("+62")) {
	        return false;
	    }
	    
	    return true;
	
	}
}
