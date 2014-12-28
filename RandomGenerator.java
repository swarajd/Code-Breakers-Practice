import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class RandomGenerator {
	File file;
	private String sentencelist = "sentences2.txt";
	private String wordlist     = "words.txt";
	private Scanner sentenceFileScanner;
	private Scanner wordFileScanner;
	private InputStream sentencewordFileStream;
	private InputStream wordFileStream;
	private ArrayList<String> sentences = new ArrayList<String>();
	private ArrayList<String> words = new ArrayList<String>();
	
	RandomGenerator() {
		//instantiate sentence file scanner
		try {
			sentencewordFileStream = getClass().getResourceAsStream(sentencelist);
			if (sentencewordFileStream == null) {
				System.out.println("yep it's null");
			}
			sentenceFileScanner = new Scanner(sentencewordFileStream);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println(" file not found :(");
			e.printStackTrace();
		}
		
		//load lines into arraylist
		if (sentenceFileScanner != null) {
			while (sentenceFileScanner.hasNextLine()) {
				sentences.add(sentenceFileScanner.nextLine());
			}
			sentenceFileScanner.close();
		}
		
		//instantiate word file scanner 
		try {
			wordFileStream = getClass().getResourceAsStream(wordlist);
			if (wordFileStream == null) {
				System.out.println("yep it's null");
			}
			wordFileScanner = new Scanner(wordFileStream);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println(" file not found :(");
			e.printStackTrace();
		}
		
		//load lines into arraylist
		if (wordFileScanner != null) {
			while (wordFileScanner.hasNextLine()) {
				words.add(wordFileScanner.nextLine());
			}
			wordFileScanner.close();
		}
	}
	String getRandomSentence() {
		//only return a line if scanner exists
		if (sentenceFileScanner == null) {
			return "";
		}
		int size  = sentences.size();
		int index = (int)(Math.random() * size);
		return sentences.get(index);
	}
	
	String getRandomWord() {
		//only return a line if scanner exists
		if (wordFileScanner == null) {
			return "";
		}
		int size  = words.size();
		int index = (int)(Math.random() * size);
		return words.get(index);
	}
}
