import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;


public class PasswordChecker {
	/**
	 * This section establishes all of the rules for pattern matching such as what is a vowel, what is a consonant, what
	 * letters cannot be doubled, and how many consecutive vowels/consonants or repeated letters are allowed. I separated 
	 * the creation of the regex strings from the regex patterns in order to make them easy to change. If any of the given rules 
	 * change in the future (numder of repeats, number of consecutive vowels/consonants, whether or not y is a vowel, etc), 
	 * you simply need to alter the strings. 
     */	
	
	//Strings used to create the regex patterns
	private static String vowels = "[aeiou]";
	private static String consonants = "[bcdfghjklmnpqrstvwxyz]";
	private static String nonRepeatableLetters = "([abcdfghijklmnpqrstuvwxyz])";
	private static String numConsecutive = "{3}";
	private static String numRepeats = "{1}";
	
	//Creates the regex patterns
	private static Pattern vowelPattern = Pattern.compile(vowels);
	private static Pattern consecutiveVowels = Pattern.compile(vowels + numConsecutive);
	private static Pattern consecutiveConsonants = Pattern.compile(consonants + numConsecutive);
	private static Pattern repeatLetters = Pattern.compile(nonRepeatableLetters + "(\\1"+ numRepeats + ")");
	
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String password = reader.readLine();
			while(!password.equals("end")) {
				if(validatePassword(password)) {
					System.out.println("<" + password + "> is acceptable.");
				}
				else {
					System.out.println("<" + password + "> is not acceptable.");
				}
				password = reader.readLine();
			}	
			reader.close();
			System.exit(0);
		} catch (FileNotFoundException e) {
			System.out.println("File name " + args[0] + " not found");
		} catch (IOException e) {
			System.out.println("Error reading lines of file.");
		}
	}
	
	private static boolean validatePassword(String password) {
		
		//Checks that the password has a vowel
		if(!vowelPattern.matcher(password).find()) {
			return false;
		}
		
		//Checks that the password does not have three or more repeating vowels
		if(consecutiveVowels.matcher(password).find()){
			return false;
		}
		
		//Checks that the password does not have three or more repeating consonants
		if(consecutiveConsonants.matcher(password).find()){
			return false;
		}		
		
		//Checks that the password does not have repeating letters that are not allowed
		if(repeatLetters.matcher(password).find()){
			return false;
		}
		
		return true;
	}

}
