package utils;

public class GenerateID {

	public static String generateNewId(String oldId, String desiredId) {
		String unprocessedId = oldId;
		String slicedId = unprocessedId.substring(2);
		int id = Integer.parseInt(slicedId);
		return String.format("%s%010d", desiredId, (id + 1)); 
	}
}
