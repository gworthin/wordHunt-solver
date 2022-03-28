package WordHunt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.*;

public class WordHunt {
	
	/* 
	 * Function handles user input to create the board of 16 letters and calls other
	 * functions to compute the list of words that can be played in the came, listing
	 * them out at the end.
	 * 
	 * @return void
	 */
	public static void main(String[] args) throws IOException {
	
		// Get's user input for the 16 letters on the board
		String letters = "";
		Scanner reader = new Scanner(System.in);
		while(letters.length() != 16) {
			System.out.println("Enter the 16 letters:");
			letters = reader.next();
		}
		letters = letters.toLowerCase();

		// Creates an array out of the string of letters
		ArrayList<String> lettersArr = new ArrayList<String>();
		for(int i = 0; i < 16; i++) {
			lettersArr.add(letters.substring(i, i+1));
		}
		
		// First narrow down the list of words to only those with the characters that appear on the board
		narrowDownWords(lettersArr);
		
		// Next find all words that can be used in the game
		ArrayList<String> words = findWords(lettersArr);
		
		// Sort the words based on length
		Collections.sort(words, Comparator.comparing(String::length));
		
		// Print all words
		System.out.println("All " + words.size() + " words:");
		for(int i = 0; i < words.size(); i++) {
			System.out.println(words.get(i));
		}
		
		
		// Close the file
		reader.close();
	}
	
	/*
	 * Function takes a file of all the words in the English Dictionary and creates 
	 * a new file called validwordsWH.txt containing only words that are between 3 and
	 * 7 characters whose letters can all be found on the board
	 * 
	 * @return void
	 */
	private static void narrowDownWords(ArrayList<String> letters) throws IOException {
	
		// Open the file of all words in the English dictionary
		File allWordsFile = new File("collinsLowerCase.txt");
		Scanner reader = new Scanner(allWordsFile);
		
		// Create a new file to put the valid words
		FileWriter validWordsFile = new FileWriter("validwordsWH.txt");
				
		// Iterate through all words in the dictionary
		while (reader.hasNextLine()) {
			String curWord = reader.nextLine();
			
			// If the word is the correct size and it's letters all appear on the board, put it in the file
			if(curWord.length() >= 3 && curWord.length() <= 8 && lettersMatch(letters, curWord)) {
				validWordsFile.write(curWord + "\n");
			}
		}
		
		// Close files
		reader.close();
		validWordsFile.close();
		
	}
	
	/*
	 * Function checks whether or not all the characters of the word parameter are
	 * in the ArrayList of letters without repeats.
	 * 
	 * @return boolean
	 */
	private static boolean lettersMatch(ArrayList<String> letters, String word) {
		// Create a duplicate of the letters to remove from so letters aren't double counted
		ArrayList<String> lettersDup = new ArrayList<String>();
		for(int k = 0; k < 16; k++) {
			lettersDup.add(letters.get(k));
		}
		
		// Iterate through every character in the word
		for(int i = 0; i < word.length(); i++) {
			String curChar = word.substring(i,i+1); // Current character in the word
			boolean curCharFoundInLetters = false; 
			
			// Iterate through every letter on the board
			for(int j = 0; j < lettersDup.size(); j++) {
				// If the characters equal each other, the character was found and break
				if(curChar.equals(lettersDup.get(j))) {
					lettersDup.remove(j);
					curCharFoundInLetters = true;
					break;
				}
			}
			// If at the end of the loop, the character was not found, return false
			if(curCharFoundInLetters == false) {
				return false;
			}
		}
		// If every character was found, return true
		return true;
	}

	
	private static ArrayList<String> findWords(ArrayList<String> letters) throws FileNotFoundException {
		
		// Create array of words to return to the main function for printing
		ArrayList<String> toReturn = new ArrayList<String>();
		
		// Create a new board object and fill the tiles with the letters from the input
		Board wordHuntBoard = new Board();
		for(int i = 0; i < 16; i++) {
			wordHuntBoard.addTile(i, letters.get(i));
		}
		
		// Open the valid words text file
		File validWordsFile = new File("validwordsWH.txt");
		Scanner reader = new Scanner(validWordsFile);
		
		// Iterate through all the valid words
		while(reader.hasNextLine()) {
			String curWord = reader.nextLine();
			//curWord = "gadget";
			
			// Convert the current word into an ArrayList of strings for each character
			ArrayList<String> curWordArr = new ArrayList<String>();
			for(int i = 0; i < curWord.length(); i++) {
				curWordArr.add(curWord.substring(i, i+1));
			}
			
			// Check to see if the word is valid
			if(wordHuntBoard.isWordValid(curWordArr)) {
				// If it's valid, add it to the ArrayList
				toReturn.add(curWord);
			}
		}
		
		// Close the file and return
		reader.close();
		return toReturn;
		
	}

}
