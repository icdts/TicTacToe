import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Game {
	static String[][] board;
	static String input;
	static boolean x_turn;
	static boolean finished;
	static String winner;
	
	static Random rand;
	
	static InputStreamReader convert;
    static BufferedReader stdin;
	
	public static void main(String[] args) throws IOException{
		for( int i = 0; i<10; i++){
			initialize();
			//printBoard();
			while(!finished){
				if(x_turn){
					getAIInput(1);
				}else{
					getAIInput(0);
				}
				x_turn = !x_turn;
				//printBoard();
				
				winner = checkForWinner(board);
				finished = (winner != "");
			}
			
			System.out.println(
				(winner == "XO" ? "Everybody" : winner) 
				+ " won!!!"
			);
		}
	}
	
	public static String checkForWinner(String[][] board) {
		for(int i=0; i<3; i++){
			if(board[0][i] == board[1][i] && board[1][i] == board[2][i]){
				if( board[0][i] != " "){
					return board[0][i];
				}
			}
			
			if(board[i][0] == board[i][1] && board[i][1] == board[i][2]){
				if( board[i][0] != " "){
					return board[i][0];
				}
			}
		}
		
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] ){
			if( board[0][0] != " "){
				return board[0][0];
			}
		}
		
		if(board[2][0] == board[1][1] && board[1][1] == board[0][2]){
			if( board[2][0] != " "){
				return board[2][0];
			}
		}
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(board[i][j] == " "){
					return "";
				}
			}
		}
		
		return "XO";
	}

	private static void getAIInput(int selection) {
		switch(selection){
		case 0:
			randomMove();
			
			break;
		case 1:
			minimaxMove();
			
			break;
		case 2:
			expectimaxMove();
			
			break;
		}
	}

	private static void randomMove() {
		ArrayList<Move> unfilled = new ArrayList<Move>();
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(board[i][j] == " "){
					unfilled.add(new Move(i,j));
				}
			}
		}
		
		Move move = unfilled.get(rand.nextInt(unfilled.size()));
		
		makeMove(move);
	}
	
	private static void minimaxMove() {
		Move move = Minimax.pickMove(board, (x_turn ? "X" : "O"));
		makeMove(move);
	}

	private static void expectimaxMove() {
		Move move = Expectimax.pickMove(board, (x_turn ? "X" : "O"));
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
		
		makeMove(
			new Move(
				Character.getNumericValue(input.charAt(0)),
				Character.getNumericValue(input.charAt(1))
			)
		);
	}

	private static void makeMove(Move move) {
		board[move.row][move.column] = (x_turn ? "X" : "O");
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
		System.out.print(board[0][0]);
		System.out.print("|");
		System.out.print(board[0][1]);
		System.out.print("|");
		System.out.print(board[0][2]);
		
		System.out.print("\n");
		System.out.print(" ");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("\n");
		
		System.out.print("1");
		System.out.print(board[1][0]);
		System.out.print("|");
		System.out.print(board[1][1]);
		System.out.print("|");
		System.out.print(board[1][2]);
		
		System.out.print("\n");
		System.out.print(" ");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("-");
		System.out.print("\n");
		
		System.out.print("2");
		System.out.print(board[2][0]);
		System.out.print("|");
		System.out.print(board[2][1]);
		System.out.print("|");
		System.out.print(board[2][2]);
		
		System.out.print("\n");
	}
	
	private static void initialize(){
		board = new String[3][3];
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				board[i][j] = " ";
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
				if( board[
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
