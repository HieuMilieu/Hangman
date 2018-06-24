

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class fileHandler implements Runnable{
	//Class variables
	private File task;
	private static ArrayList<String> allSelectedWords = new ArrayList<String>();
	private static ArrayList<String> loadFile = new ArrayList<String>();
	private static long sequentiallyTime = 0;
	private static long parallelTime = 0;
	//Constructor
	public fileHandler(File input){
		this.task = input;
	}
	
	public static ArrayList<String> getLoadFile(){
		return loadFile;
	}
	
	public static long getSequentiallyTime(){
		return sequentiallyTime;
	}
	
	public static long getParallelTime(){
		return parallelTime;
	}
	
	//Store words from a file into arrayList and selects 50 random words from the arrayList
	public static ArrayList<String> selectFifty(File f){
		ArrayList<String>stored = new ArrayList<String>();
		Random rand = new Random();
		ArrayList<String> randomW = new ArrayList<String>();
		String word = "";
		try(BufferedReader br = new BufferedReader(new FileReader(f))){
			while((word = br.readLine()) != null){
				stored.add(word);
			}
			for(int i=0; i<50; i++){
				int num = rand.nextInt(stored.size());
				randomW.add(stored.get(num));
					stored.remove(num);
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		return randomW;
	}
	
	//Outputs a binary file
	public static void serialise(ArrayList<String> input){
		File serial = new File("serialise.bin");
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serial, false))){		//FileOutputStream being false makes it so data will not append if the file exist
			 out.writeObject(input);
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	//runs automatically when thread is used
	public void run(){
		try{
			allSelectedWords.addAll(selectFifty(task));
			serialise(allSelectedWords);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Method for sequential 
	public static void sequential(){
		File[] files = new File[4];
		for (int i = 0; i<4; i++){	
			files[i] = new File("file" + (i+1) + ".txt");	//Set files to an object
		}
		long startTime = System.currentTimeMillis();	//Start timer for storing words in a binary file
		
		for(int i=0; i<4; i++){
			allSelectedWords.addAll(selectFifty(files[i]));		//Adds 50 words to an array
		}
		serialise(allSelectedWords);	//serialise allSelectedWords into a binary file
		allSelectedWords.clear();	//resets the allSelectedWords array
		long endTime = System.currentTimeMillis();	//End timer for storing words in a binary file
		sequentiallyTime = endTime - startTime;
	}
	
	//Method for parallel 
	public static void parallel(){
		File[] files = new File[4];
		for (int i = 0; i < 4; i++) {
			files[i] = new File("file" + (i + 1) + ".txt"); // Set files to an object
		}
		Thread[] threads = new Thread[4]; // make array of Thread objects

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 4; i++) {
			threads[i] = new Thread(new fileHandler(files[i]));
			threads[i].start();
		}
		// delay main thread
		try {
			for (int i = 0; i < 4; i++) {
				threads[i].join();	// delay main thread
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileHandler.allSelectedWords.clear(); // clears the allRand array in Hangman class once threads is complete
		}
		// Thread ends, display elapsed time
		long endTime = System.currentTimeMillis();
		parallelTime = endTime - startTime;
	}
	
	//Writes the current word and userInput as a string into a save_file
	public static void saveGame(String correctWord, String userInput, int life, String fileName){
		ArrayList<String> saveArray = new ArrayList<String>();
		saveArray.add(correctWord);
		saveArray.add(userInput);
		saveArray.add(String.valueOf(life));
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName+".data"))){
		    out.writeObject(saveArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//reads save_file.data and stores the content in an arrayList
	@SuppressWarnings("unchecked")
	public static void loadGame(String path){
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
			loadFile = (ArrayList<String>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}