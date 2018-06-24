
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class hangmanGame {

	private static ArrayList<String> allRandWord;
	private static ArrayList<String> missingChar;
	private static String guessWord = "acdeefe";
	private static String missingWord = "";
	private static int level = 5;
	private static boolean duplicates = false;
	private static boolean gameLoaded = false;
	
	public hangmanGame(int currentLevel, String word){
		allRandWord = new ArrayList<String>();
		missingChar = new ArrayList<String>();
		guessWord = word;
		missingWord = "";
		level = currentLevel;
	}	
	
	public String getGuessWord() {
		return guessWord;
	}
	
	public static void setGameLoaded(boolean b){
		gameLoaded = b;
	}
	public static boolean isGameLoaded(){
		return gameLoaded;
	}
	
	public static void setGuessWord(String cGuessWord) {
		guessWord = cGuessWord;
	}
	//converts the missingWord array into a string
	public String getMissingWord(){
		StringBuilder builder = new StringBuilder();
		for (String value : missingChar) {
		    builder.append(value);
		}
		missingWord = builder.toString();
		return missingWord;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int currentLevel) {
		level = currentLevel;
	}
	
	//deserialises a file containing random words and selects a random word depending on the level
	@SuppressWarnings("unchecked")
	public void selectWord(int length) {
		//Deserialise the serialise.bin file
		File serial = new File("serialise.bin");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serial))) {
			allRandWord = (ArrayList<String>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<String> temp = new ArrayList<String>();
		Random randomGenerator = new Random();
		//adds all the words of a certain length to a temp ArrayList
		for (String word : allRandWord) {
			if (word.length() == length) {
				temp.add(word);
			}
		}
		//from the temp ArrayList select a random word
		int randomInt = randomGenerator.nextInt(50);
		setGuessWord(temp.get(randomInt));
		//set the level of the game selected
		setLevel(length);
	}
	public boolean checkDuplicates(){
		return duplicates;
	}
	//takes a char input and check if it is in the correct word
	public void adjustMissingChar (char input){
		int occurs = 0;
		//when the arrayList is empty fill it will "_ " for the size of the guessWord
		if(missingChar.isEmpty()){
			for(int i=0; i<guessWord.length(); i++){
				missingChar.add("_ ");
			}
		}
		//Checks when the input is equal to a character from the guess word then stores it in corresponding position in the arrayList
		for(int i=0; i<guessWord.length(); i++){
			if(input == guessWord.charAt(i)){
				missingChar.remove(i);
				missingChar.add(i, input+" ");	
				occurs++;
				duplicates = false;
			}
		}if(occurs >= 2){
			duplicates = true;
		}
	}
	//Checks if the input occurs in the guessWord
	public boolean guessChar(char input){
		boolean to_return = false;
		for(int i=0; i<guessWord.length(); i++){
			char correctChar = guessWord.charAt(i);
			if(correctChar == input){
				adjustMissingChar(input);
				to_return = true;
			}
		}return to_return;
	}
	
	//used for checking if the characters in a String match
	public boolean checkMatch(String word1, String word2){
		//assigns the Strings to a char array
		char[] first = word1.toCharArray();
		char[] second = word2.toCharArray();
		//sorts the arrays alphabetically 
		Arrays.sort(first);
		Arrays.sort(second);
		
		return Arrays.equals(first, second);
	}
}
