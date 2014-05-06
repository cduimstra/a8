package ClientServer;

import java.util.Random;
import java.util.concurrent.Callable;

public class task2 implements Callable<StringBuilder>{
	int character;										// Initialize
	StringBuilder text = new StringBuilder();
	Random rand = new Random();
	
	int length = rand.nextInt(10)+1;					// Generate length 1-10
	public StringBuilder call() {
		for(int i=0; i<length; i++){
			do{
				character = rand.nextInt(91-65)+65;		// Generate a character
			}while(!verify(character));					// Generate new char if invalid
			text.append((char)character);				// Append new char to string
		}// End for loop
		return text;
	}
	
	// Verify character is valid
	public static boolean verify(int r){
		if(r >= 'A' && r <= 'Z')
			return true;
		else
			return false;
	}
}
