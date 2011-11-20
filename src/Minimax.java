import java.util.ArrayList;


public class Minimax {
	public static Move pickMove(String[][] board, String my_char){
		ArrayList<Move> possible_moves = new ArrayList<Move>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		possible_moves = getPossibleMoves(board);
		
		for(int i=0; i<possible_moves.size(); i++){
			values.add(evalMyMove(possible_moves.get(i),board, my_char));
		}
		
		int index = getIndexOfMax(values);
		
		return possible_moves.get(index);
	}
	
	public static int getIndexOfMax(ArrayList<Integer> values){
		int curr_max = -2;
		int curr_max_index = -1;
		for(int i = 0; i<values.size(); i++){
			if(values.get(i) > curr_max){
				curr_max = values.get(i);
				curr_max_index = i;
			}
		}
		return curr_max_index;
	}

	private static Integer evalMyMove(Move move, String[][] board, String my_char) {
		//Set move
		board[move.row][move.column] = my_char;
		int value = 0;
		
		String result = Game.checkForWinner(board);
		
		if(result != ""){
			value = evalResult(result, my_char);
		}else{
			ArrayList<Move> possible_moves = getPossibleMoves(board);
			ArrayList<Integer> values = new ArrayList<Integer>();
			
			for(int i=0; i<possible_moves.size(); i++){
				values.add(evalOtherMove(possible_moves.get(i), board, my_char));
			}
			
			//Get max
			value = getMax(values);
		}
		
		//Unset move and return val
		board[move.row][move.column] = " ";
		return value;
	}

	private static int getMax(ArrayList<Integer> values) {
		int curr_max = -2;
		for(int i = 0; i<values.size(); i++){
			if(values.get(i) > curr_max){
				curr_max = values.get(i);
			}
		}
		return curr_max;
	}

	private static Integer evalOtherMove(Move move, String[][] board, String my_char) {
		//Set move
		board[move.row][move.column] = my_char;
		int value = 0;
		
		String result = Game.checkForWinner(board);
		
		if(result != ""){
			value = evalResult(result, my_char);
		}else{
			ArrayList<Move> possible_moves = getPossibleMoves(board);
			ArrayList<Integer> values = new ArrayList<Integer>();
			
			for(int i=0; i<possible_moves.size(); i++){
				values.add(evalOtherMove(possible_moves.get(i), board, my_char));
			}
			
			//Get max
			value = getMin(values);
		}
		
		//Unset move and return val
		board[move.row][move.column] = " ";
		return value;
	}
	
	private static int getMin(ArrayList<Integer> values) {
		int curr_min = 2;
		for(int i = 0; i<values.size(); i++){
			if(values.get(i) < curr_min){
				curr_min = values.get(i);
			}
		}
		return curr_min;
	}

	private static ArrayList<Move> getPossibleMoves(String[][] board){
		ArrayList<Move> possible_moves = new ArrayList<Move>();
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(board[i][j] == " "){
					possible_moves.add(new Move(i,j));
				}
			}
		}
		
		return possible_moves;
	}
	
	private static int evalResult(String result, String my_char){
		if(result == my_char){
			return 1;
		}else if(result == "XO"){
			return 0;
		}else if(result.length() == 1){
			return -1;
		}
		
		return 0;
	}
}
