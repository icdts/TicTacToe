import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Game {
	static String[][] spaces;
	static String input;
	static boolean x_turn;
	static boolean finished;
	static String winner;
	
	static Random rand;
	
	static InputStreamReader convert;
    static BufferedReader stdin;
	
	public static void main(String[] args) throws IOException{
		initialize();
		printBoard();
		while(!finished){
			if(x_turn){
				getUserInput();
			}else{
				getAIInput(0);
			}
			x_turn = !x_turn;
			printBoard();
			
			finished = checkForWinner();
		}
		
		System.out.println(winner + " won!!!");
	}
	
	private static boolean checkForWinner() {
		for(int i=0; i<3; i++){
			if(spaces[0][i] == spaces[1][i] && spaces[1][i] == spaces[2][i]){
				if( spaces[0][i] != " "){
					winner = spaces[0][i];
					return true;
				}
			}
			
			if(spaces[i][0] == spaces[i][1] && spaces[i][1] == spaces[i][2]){
				if( spaces[i][0] != " "){
					winner = spaces[i][0];
					return true;
				}
			}
		}
		
		if(spaces[0][0] == spaces[1][1] && spaces[1][1] == spaces[2][2] ){
			if( spaces[0][0] != " "){
				winner = spaces[0][0];
				return true;
			}
		}
		
		if(spaces[2][0] == spaces[1][1] && spaces[1][1] == spaces[0][2]){
			if( spaces[2][0] != " "){
				winner = spaces[2][0];
				return true;
			}
		}
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(spaces[i][j] == " "){
					return false;
				}
			}
		}
		
		winner = "Everybody";
		return true;
	}

	private static void getAIInput(int selection) {
		switch(selection){
		case 0:
			randomMove();
			
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}

	private static void randomMove() {
		ArrayList<String> unfilled = new ArrayList<String>();
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(spaces[i][j] == " "){
					unfilled.add(String.valueOf(i) + String.valueOf(j));
				}
			}
		}
		
		String move = unfilled.get(rand.nextInt(unfilled.size()));
		
		makeMove(move);
	}

	private static void getUserInput() throws IOException {
		String input = null;
		boolean valid = false;
		
		while(!valid){
			System.out.print("What is your move: ");
			
			input = stdin.readLine();
		
			valid = isValidInput(input);
		}
		
		makeMove(input);
	}

	private static void makeMove(String move) {
		int row = Character.getNumericValue(move.charAt(0));
		int col = Character.getNumericValue(move.charAt(1));
		
		spaces[row][col] = (x_turn ? "X" : "O");
	}

	private static void printBoard(){
		System.out.print(" ");
		System.out.print("0");
		System.out.print(" ");
		System.out.print("1");
		System.out.print(" ");
		System.out.print("2");
		System.out.print("\n");
		
		System.out.print("0");
		System.out.print(spaces[0][0]);
		System.out.print("|");
		System.out.print(spaces[0][1]);
		System.out.print("|");
		System.out.print(spaces[0][2]);
		
		System.out.print("\n");
		System.out.print(" ");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("\n");
		
		System.out.print("1");
		System.out.print(spaces[1][0]);
		System.out.print("|");
		System.out.print(spaces[1][1]);
		System.out.print("|");
		System.out.print(spaces[1][2]);
		
		System.out.print("\n");
		System.out.print(" ");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("\n");
		
		System.out.print("2");
		System.out.print(spaces[2][0]);
		System.out.print("|");
		System.out.print(spaces[2][1]);
		System.out.print("|");
		System.out.print(spaces[2][2]);
		
		System.out.print("\n");
	}
	
	private static void initialize(){
		spaces = new String[3][3];
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				spaces[i][j] = " ";
			}
		}
		x_turn = true;
		finished = false;
		
		convert = new InputStreamReader(System.in);
        stdin = new BufferedReader(convert);
        
        rand = new Random();
	}
	
	private static boolean isValidInput(String in){
		if(in.length() == 2){
			if(
				isValidDigit(in.charAt(0)) && 
				isValidDigit(in.charAt(1))
			){
				if( spaces[
				      Character.getNumericValue(in.charAt(0))
				    ][
				      Character.getNumericValue(in.charAt(1))
				    ] == " "
				){
					return true;
				}
			}
		}
		System.out.println("INvalid input: " + in);
		return false;
	}
	private static boolean isValidDigit(char c){
		char[] valid_digits = {'0', '1', '2'};
		
		for(char d : valid_digits){
			if(d == c){
				return true;
			}
		}
		System.out.println("INvalid digit: " + c);
		return false;
	}
}
