package WordHunt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {

	// Adjacency matrix keeps track of which nodes are adjacent to each other
	private int[][] adjacencyMatrix;
	
	// tiles variable maps each numbered tile to a string
	private HashMap<Integer, String> tiles;
	
	/*
	 * A board looks like this:
	 * 
	 *  0   1   2   3 
	 *  4   5   6   7
	 *  8   9  10  11
	 * 12  13  14  15 
	 * 
	 * Constructor fills the adjacency matrix to specify which cells are next to each other
	 * 
	 */
	public Board() {
		// Create the matrix and tiles map
		adjacencyMatrix = new int[16][16];
		tiles = new HashMap<Integer, String>();
		
		// Populate the adjacency matrix
		for(int i = 0; i < 16; i++) {
			adjacencyMatrix[i][i] = -1;
			
			// If tile is not on the left side, the tile numbered 1 less is to its left
			if(i % 4 != 0) { adjacencyMatrix[i][i-1] = 1; }
			// If tile is not on the right side, the tile numbered 1 greater is to its right
			if(i % 4 != 3) { adjacencyMatrix[i][i+1] = 1; } 
			// If tile is not on the top, the tile numbered 4 less is above it
			if(i >= 4) { adjacencyMatrix[i][i-4] = 1; } 
			// If tile is not on the bottom, the tile numbered 4 greater is below it
			if(i < 12) { adjacencyMatrix[i][i+4] = 1; } 
			// If the tile is not on the left or top, the tile numbered 5 less is to its top left
			if(i % 4 != 0 && i >= 4) { adjacencyMatrix[i][i-5] = 1; } 
			// If the tile is not on the left or the bottom, the tile numbered 3 greater is to its bottom left
			if(i % 4 != 0 && i < 12) { adjacencyMatrix[i][i+3] = 1; } 
			// If the tile is not on the right or the top, the tile numbered 3 less it to its top right
			if(i % 4 != 3 && i >= 4) { adjacencyMatrix[i][i-3] = 1; } 
			// If the tile is not on the right or the bottom, the tile numbered 5 more is to its bottom right
			if(i % 4 != 3 && i < 12) { adjacencyMatrix[i][i+5] = 1; }
		}
		
	}
	
	/* 
	 * Function adds a tile to the map of tiles with the position and string
	 */
	public void addTile(int place, String character) {
		if(place >= 0 && place < 16) {
			tiles.put(place, character);
		}
	}
	
	public HashSet<Integer> getAdjacentTiles(int place) {
		HashSet<Integer> adjacencies = new HashSet<Integer>();
		for(int i = 0; i < 16; i++) {
			if(adjacencyMatrix[place][i] == 1) {
				adjacencies.add(i);
			}
		}
		return adjacencies;
	}

	/*
	 * Function checks if a curWord parameter is playable on the current board
	 * 
	 * @return boolean
	 */
	public boolean isWordValid(ArrayList<String> curWord) {
		
		// Boolean is set to false initially
		boolean toReturn = false;
		
		for(int i = 0; i < 16; i++) {
			HashSet<Integer> visitedSet = new HashSet<Integer>();
			int index = 0;
			// If tile of index i contains the same letter as the first letter in the word
			if(tiles.get(i).equals(curWord.get(index))) {
				index++; // Increment the index
				visitedSet.add(i); // Add the tile to the visited set
				
				HashSet<Integer> adjacencies = getAdjacentTiles(i);
				for(int j : adjacencies) {
					if(tiles.get(j).equals(curWord.get(index)) && !visitedSet.contains(j)) {
						visitedSet.add(j); // Add the tile to the visited set
						// Call the recursive helper function on this tile
						toReturn = isWordValidHelper(curWord, j, index, visitedSet);
						// If it returned true, automatically return true
						if(toReturn == true) {
							return true;
						}
					}
				}
			}
		}
		// If we pursued every possible path of tiles and couldn't make a word, return false
		return false;
		
	}	
	
	/*
	 * Recursive helper function keeps searching for the word in the board of tiles
	 */
	public boolean isWordValidHelper(ArrayList<String> curWord, int position, int index, HashSet<Integer> visitedSet) {
		boolean toReturn = true;
		index++; // Increment the index after recursion
		// Base case: Return true if the index is equal to the size of the word, meaning we made the word
		if(index == curWord.size()) {
			return true;
		} else {
			HashSet<Integer> adjacencies = getAdjacentTiles(position);
			for(int j : adjacencies) {
				if(tiles.get(j).equals(curWord.get(index)) && !visitedSet.contains(j)) {
					visitedSet.add(j);
					 toReturn = isWordValidHelper(curWord, j, index, visitedSet);
					 if(toReturn == true) {
						 return true;
					 }
				}
			}
		}
		// If there is no where to go and the word hasn't been found, return false
		return false;
	}
	
}
