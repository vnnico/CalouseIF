package utils;

public class ValidatePassword {

	// Validate Password must be have unique characters.
	public static Boolean validate(String words) {
		char[] uniqueSymbols = {'!', '@', '#', '$', '%', '^', '&', '*'};
		for (char c : uniqueSymbols) {
			for(int i = 0; i < words.length(); i++) {
				if(words.charAt(i) == c) {
					return true;
				}
			}
		}
		return false;
	}
}
