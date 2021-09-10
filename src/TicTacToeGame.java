import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author mikael
 * Main class of the Tic-Tac-Toe Game.
 */
public class TicTacToeGame {

	// Global variables.
	public static Scanner scan = new Scanner(System.in);
	public static boolean restart = false;
	public static int[] score = {0, 0};
	public static ArrayList<Integer> xPositions = new ArrayList<Integer>();
	public static ArrayList<Integer> oPositions = new ArrayList<Integer>();
	public static final ArrayList<ArrayList<Integer>> LINES = 
			new ArrayList<ArrayList<Integer>>(List.of(
					new ArrayList<Integer>(List.of(0, 1, 2)), 
					new ArrayList<Integer>(List.of(3, 4, 5)), 
					new ArrayList<Integer>(List.of(6, 7, 8)), 
					new ArrayList<Integer>(List.of(0, 3, 6)), 
					new ArrayList<Integer>(List.of(1, 4, 7)), 
					new ArrayList<Integer>(List.of(2, 5, 8)), 
					new ArrayList<Integer>(List.of(0, 4, 8)), 
					new ArrayList<Integer>(List.of(2, 4, 6))));
	
	/*
	 * Main method that starts the program
	 */
	public static void main(String[] args) {
		// Generate gameBoard.
		char[][] gameBoard = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		
		// Start game loop.
		while(true) {
			// Welcome message.
			System.out.println("Welcome to this game of Tic-Tac-Toe");
			System.out.println("type r or restart at any time to restart and q or quit at any time to quit");
			System.out.println("------------------");
			
			// Reset the game board.
			gameBoard = resetBoard(gameBoard);
			
			// Run game.
			gameOn(gameBoard);
			
			// Check if players want to restart, quits the game if not.
			if(!restart) {
				System.out.println("Want to play again?, type r to restart");
				String input = scan.next();
				if (input.equalsIgnoreCase("r") || input.equalsIgnoreCase("restart")) {
					restart = true;
				} else {
					quit();	
				}
			}
			
			// Restarts the game.
			if(restart) {
				System.out.println("------------------");
				System.out.println("-----New Game-----");
				System.out.println("------------------");
				restart = false;
				continue;
			}
		}
	}
	
	/*
	 * Method for resetting the game board.
	 */
	public static char[][] resetBoard(char[][] gameBoard){
		for (int i = 0; i < 9; i++) {
			gameBoard[(int) Math.floor(i / 3)][i % 3] = String.valueOf((i + 1)).charAt(0);
		}
		return gameBoard;
	}
	
	/*
	 * Method that runs a game.
	 */
	public static void gameOn(char[][] gameBoard){
		
		// Declare game variables.
		int activePlayer = (int) Math.floor(Math.random()*2);
		char[] players = {'X','O'};
		int squarePlayed;
		int rounds = 0;
		boolean isRunning = true;
		boolean winner = false;

		// Clear position lists.
		xPositions.clear();
		oPositions.clear();
		
		// Loop while game is running.
		while(isRunning) {
			
			// Print board.
			printBoard(gameBoard);
			
			// Get player input.
			squarePlayed = playerInput(players[activePlayer]);
			
			// Check input response.
			if(squarePlayed == -1){
				// Restarts the game. (forces a draw)
				restart = true;
				rounds = 9;
			} else if(squarePlayed < 0 || squarePlayed > 8) {
				// Response does not exist on the game board.
				System.out.println("Invalid input, try again!");
			} else if(!String.valueOf(players).contains(String.valueOf(gameBoard[(int) Math.floor(squarePlayed / 3)][squarePlayed % 3]))){				
				// Add token to game board.
				gameBoard[(int) Math.floor(squarePlayed / 3)][squarePlayed % 3] = players[activePlayer];
				
				// Add played square to positions list.
				if(players[activePlayer] == 'X') {
					xPositions.add(squarePlayed);
				} else {
					oPositions.add(squarePlayed);
				}
				
				// Check if there is a winner.
				winner = checkResult();
				
				// Change active player.
				if (activePlayer < players.length - 1) {
					activePlayer++;
				} else {
					activePlayer = 0;
				}
				
				rounds++;
			} else {
				// Response is valid but occupied.
				System.out.println("That square is occupied, try again!");
			}
			
			// Check if the game is over.
			if(rounds == 9 || winner) {
				isRunning = false;
			}
		}
		
		// Checks if there is a draw.
		if(!winner) {
			System.out.println("It is a draw!");
		}
		
		// Print end game board and total scores.
		printBoard(gameBoard);
		System.out.println("Current score is X's:" + score[0] +" | O's:" + score[1]);
	}
	
	/*
	 * Method for getting the player input.
	 */
	public static int playerInput(char player) {
		// Declare local variables.
		String input; 
		int number = 0;
				
		while(true){
			// Player message.
			System.out.println("Player " + player + ", select square to play! (1-9)");
			
			// Get input.
			input = scan.next();
			
			// Check if restart or quit.
			if (input.equalsIgnoreCase("r") || input.equalsIgnoreCase("restart")) {
				return -1;	
			} else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
				quit();
			}
			
			// Try to convert into a number.
			try {
				number = Integer.valueOf(input);
				return  number - 1;
			} catch (Exception e) {
				// Not a number, continues loop for a new input.
				System.out.println("Not a number!");
			}
		}
	}

	/*
	 * Method to check if there is a winner.
	 */
	public static boolean checkResult() {
		
		// Loop through possible lines and see if the is a winner.
		for(ArrayList<Integer> line : LINES ) {
			if(xPositions.containsAll(line)) {
				System.out.println("Congratulations X's wins!");
				score[0]++;
				return true;
			} else if(oPositions.containsAll(line)) {
					System.out.println("Congratulations O's wins!");
					score[1]++;
					return true;
			}
		}
		
		// Returns false if no winner is found.
		return false;
	}

	/*
	 * Method for printing the game board.
	 */
	private static void printBoard(char[][] gameBoard) {
		System.out.println("   |   |   ");
		for (int i = 0; i < 9; i++) {
			System.out.print(" ");
			System.out.print(gameBoard[(int) Math.floor((6 - Math.floor(i/3) * 6 + i) / 3)][i % 3]);
			
			if(i % 3 == 2) {
				System.out.println(" ");
				System.out.println("   |   |   ");
				if (i != 8) {
					System.out.println("---+---+---");
					System.out.println("   |   |   ");						
				}
			} else {
				System.out.print(" |");
			}
		}
	}

	/*
	 * Method that ends the program.
	 */
	private static void quit() {
		System.out.println("Program shut down, thanks for playing!");
		scan.close();
		System.exit(0);
	}
}
