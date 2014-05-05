package a8;

import java.util.Random;

public class text {
	public static void main(String[] args) {
		Random rand = new Random();
		
		// Random length between 1 and 10
		int length = rand.nextInt(10)+1;
		// Create string
		StringBuilder text = new StringBuilder();
		
		for(int i=0; i<length; i++){
			int r;
			// Generate new character if invalid
			do{
				r = rand.nextInt(123-33)+33;
			}while (!verify(r));
			// Add character to string
			text.append((char)r);
		}
		// Output string
		System.out.println(text);
	}
	
	public static boolean verify(int r){
		if(r >= 'A' && r <= 'Z' || r >= 'a' && r <= 'z' || r == '!' || r == ')' || r == '&' || r == '@' || r == '%')
			return true;
		else
			return false;
	}
}