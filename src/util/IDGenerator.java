package util;

import java.util.Random;

public class IDGenerator {

	/**
	 * Klasa koja generise random ID za xml dokumente
	 * 
	 * @author marko
	 */
	
	public String GenerateID()
	{
		Random randomGenerator = new Random();
		return Integer.toString(randomGenerator.nextInt(Integer.MAX_VALUE));
	}

}
