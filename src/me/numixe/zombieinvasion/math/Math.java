package me.numixe.zombieinvasion.math;

import java.util.Random;

public class Math {

	private static Random random = new Random();
	
	public static int randomInt(int start, int range) {
    	
    	return random.nextInt(range) + start;
    }
}
