import java.util.ArrayList;


public class Minimax {
	static int depth;
	public static Move pickMove(String[][] board, String my_char){
		ArrayList<Move> possible_moves = new ArrayList<Move>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		possible_moves = getPossibleMoves(board);
		
		for(int i=0; i<possible_moves.size(); i++){
			depth = 0;
			values.add(evalMyMove(possible_moves.get(i),board, my_char));
		}
		
		int index = getIndexOfMax(values);
		
		return possible_moves.get(index);
	}
	
	public static int getIndexOfMax(ArrayList<Integer> values){
		int curr_max = -200;
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
		
		depth++;
		String result = Game.checkForWinner(board);
		
		if(result != ""){
			value = evalResult(result, my_char);
		}else{
			if( depth < 2 ){
				ArrayList<Move> possible_moves = getPossibleMoves(board);
				ArrayList<Integer> values = new ArrayList<Integer>();
				
				for(int i=0; i<possible_moves.size(); i++){
					values.add(evalOtherMove(possible_moves.get(i), board, my_char));
				}
				
				//Get min, as my opponent goes next and will seek to minimize my score
				value = getMin(values);
			}else{
				value = hueristic(board, my_char);
			}
		}
		
		//Unset move and return val
		board[move.row][move.column] = " ";
		return value;
	}

	private static Integer evalOtherMove(Move move, String[][] board, String my_char) {
		//Set move
		board[move.row][move.column] = (my_char=="X" ? "O" : "X");
		int value = 0;
		
		depth++;
		String result = Game.checkForWinner(board);
		
		
		if(result != ""){
			value = evalResult(result, my_char);
		}else{
			if( depth < 2 ){
				ArrayList<Move> possible_moves = getPossibleMoves(board);
				ArrayList<Integer> values = new ArrayList<Integer>();
				
				for(int i=0; i<possible_moves.size(); i++){
					values.add(evalMyMove(possible_moves.get(i), board, my_char));
				}
				
				//Get max, since I got next, and I would maximize my score
				value = getMax(values);
			}else{
				value = hueristic(board, my_char);
			}
		}
		
		//Unset move and return val
		board[move.row][move.column] = " ";
		return value;
	}
	
	private static int getMin(ArrayList<Integer> values) {
		int curr_min = 200;
		for(int i = 0; i<values.size(); i++){
			if(values.get(i) < curr_min){
				curr_min = values.get(i);
			}
		}
		return curr_min;
	}
	
	private static int getMax(ArrayList<Integer> values) {
		int curr_max = -200;
		for(int i = 0; i<values.size(); i++){
			if(values.get(i) > curr_max){
				curr_max = values.get(i);
			}
		}
		return curr_max;
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
		if(result == my_char){ //We won
			return 100;
		}else if(result == "XO"){ //Tie
			return 0;
		}else if(result.length() == 1){ //Other player won
			return -100;
		}
		
		return 0;
	}
	
	private static int hueristic(String[][] board, String my_char) {
		int sum = 0;
		
		int x_count = 0;
		int o_count = 0;
		
		for(int r=0; r<3; r++){
			for(int c=0; c<3; c++){
				if(board[r][c] == "X"){
					x_count++;
				}else if(board[r][c] == "O"){
					o_count++;
				}
			}
			
			sum += getSubValue(x_count,o_count,my_char);
			
			x_count = 0;
			o_count = 0;
		}
		
		for(int c=0; c<3; c++){
			for(int r=0; r<3; r++){
				if(board[r][c] == "X"){
					x_count++;
				}else if(board[r][c] == "O"){
					o_count++;
				}
			}
			
			sum += getSubValue(x_count,o_count,my_char);
			
			x_count = 0;
			o_count = 0;
		}
		
		//first diagonal
		if(board[0][0] == "X"){
			x_count += 1;
		}else if(board[0][0] == "O"){
			o_count += 1;
		}
		if(board[1][1] == "X"){
			x_count += 1;
		}else if(board[1][1] == "O"){
			o_count += 1;
		}
		if(board[2][2] == "X"){
			x_count += 1;
		}else if(board[2][2] == "O"){
			o_count += 1;
		}
		
		sum += getSubValue(x_count,o_count,my_char);
		
		x_count = 0;
		o_count = 0;
		
		//second diagonal
		if(board[2][0] == "X"){
			x_count += 1;
		}else if(board[2][0] == "O"){
			o_count += 1;
		}
		if(board[1][1] == "X"){
			x_count += 1;
		}else if(board[1][1] == "O"){
			o_count += 1;
		}
		if(board[0][2] == "X"){
			x_count += 1;
		}else if(board[0][2] == "O"){
			o_count += 1;
		}
		
		sum += getSubValue(x_count,o_count,my_char);
		
		return sum;
	}
	
	private static Integer getSubValue(int x_count, int o_count, String my_char){
		int sum = 0;
		int tmp;
		
		if(x_count > 0 && o_count == 0){
			if(x_count == 3){
				tmp = 100;
			}else{
				tmp = x_count;
			}
			if(my_char == "X"){
				sum += tmp;
			}else{
				sum -= tmp;
			}
		}else if(x_count == 0 && o_count > 0){
			if(o_count == 3){
				tmp = 100;
			}else{
				tmp = o_count;
			}
			if(my_char == "O"){
				sum += tmp;
			}else{
				sum -= tmp;
			}
		}
		return sum;
	}
}
