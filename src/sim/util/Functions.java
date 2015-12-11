package sim.util;

import java.util.Random;

public class Functions {

	public static int randInt(int min, int max) {
		
		Random rand = new Random();
		
		int randomNum = rand.nextInt((max-min) + 1) + min;
		
		return randomNum;
	}
	
}
