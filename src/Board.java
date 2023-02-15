import java.util.Arrays;

public class Board {
	
	private final int NUM_OF_COLUMNS = 7;
	private final int NUM_OF_ROW = 6;
	private char board[][];
	
	/* 
	* The board object must contain the board state in some manner.
	* You must decide how you will do this.
	* 
	* You may add addition private/public methods to this class as you wish.
	* However, you should use best OO practices. That is, you should not expose
	* how the board is being implemented to other classes. Specifically, the
	* Player classes.
	* 
	* You may add private and public methods if you wish. In fact, to achieve
	* what the assignment is asking, you'll have to
	* 
	*/
	
	// constructor;
	public Board() {
		this.board = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
		reset(); // defined below, set all elements to ' '
	}
	
	public void reset() {
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				board[i][j] = ' ';
			}
		}
	}
	// variables & funcs for smartPos();
	private int countR = 0;
	private int countB = 0;
	// stores the postions where the AI can place its symbol to block the player's move.
	private int posR[] = {-1,0};
	// stores the postions where the AI can place its symbol to win the game.
	private int posB[] = {-1,0};
	// variables to store AI and human player symbols;
	private char sym1 = '-'; // default value
	private char sym2 = '-';
	
	// receive and store player symbols;
	public void setSym1(char c){
		if (sym1 == '-') sym1 = c;
		// if sym1 is occupied; (case when both players are AIs)
		else sym2 = c;
	}
	public void setSym2(char c){
		sym2 = c;
	}

	// will be used by AIplayer to know human player's symbol.
	public char getSym2(){
		return sym2;
	}
	public char getSym1(){
		return sym1;
	}

	// returns the array containing positions where the AI can place its symbol.
	public int[] getPos(char c){
		int[] ret = (c == sym2) ? posR : posB;
		return ret;
	}

	// helper function for testing purpose;
	public int getCount(char c1){
		int ret = (c1 == sym2) ? countR : countB;
		return ret;
	}
	
	
	public void printBoard() {
		System.out.println('\n'+Arrays.deepToString(board)
		.replace("], ", "]\n ")
		.replace(",", "|")+'\n');
	}
	
	public boolean containsWin() {

		// horizontal check;
		for(int i=0; i < board.length ;i++){
			for(int j=0; j < board[0].length-3 ; j++){
				if(board[i][j] != ' ' && board[i][j] == board[i][j+1]
				&& board[i][j+1] == board[i][j+2]
				&& board[i][j+2] == board[i][j+3]) return true;
			}
		}
		
		// negative slope check; (0,0) -> (5,6)
		for(int i=0; i < board.length-3; i++){
			for(int j=0; j < board[0].length-3 ; j++){
				if(board[i][j] != ' ' && i <= 2 && j <= 3
				&& board[i][j] == board[i+1][j+1]
				&& board[i+1][j+1] == board[i+2][j+2]
				&& board[i+2][j+2] == board[i+3][j+3]){
					return true;
				}
			}
		}
		
		// positve slope check (5,0) -> (0,6)
		for(int i=5; i >= 3 ; i--){
			for(int j=0 ; j < board[0].length-3 ; j++){
				if(board[i][j] != ' ' && board[i][j] == board[i-1][j+1]
				&& board[i-1][j+1] == board[i-2][j+2]
				&& board[i-2][j+2] == board[i-3][j+3]){
					// Collections.reverse(Arrays.asList(board));
					return true;
				}
			}
		}
		
		// vertical check
		char temp[] = new char[6]; // contains all ele of a column;
		for(int k=0; k < 7; k++){
			// adding lists into board2 thus making it 2d lists;
			for(int i=0; i < board.length; i++){
				temp[i] = board[i][k];
			}
			// System.out.println("column board : "+Arrays.toString(temp));
			for(int j=5 ; j >= 3; j--){
				if(temp[j] != ' ' && temp[j] == temp[j-1]
				&& temp[j-1] == temp[j-2]
				&& temp[j-2] == temp[j-3]){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isTie() {
		// no space should be empty;
		for (int i = 0; i < board[0].length; i++) {
			if(board[0][i] == ' ') return false;
		}
		return true;
	}
	

	/*used by the player classes to place a symbol, 
	allows the board implementation to remain hidden. */
	public boolean place(int pos,char c1){
		// valid inputs;
		try {
			if(pos <= 7 && pos >= 1){
				int bottom = 5;
				// traversing to the bottom (or lowest possible pos) of the board!
				while(bottom >= 0 && board[bottom][pos-1] != ' '){
					bottom--;
				}
				if(bottom == -1){
					System.out.println("Column full, Try Again;");
					return false;
				}
				board[bottom][pos-1] = c1;
				return true;
			}
			// other inputs;
			System.out.println("input out of range ; [1,7], Try Again;");
			return false;
		} catch (Exception e) {
			System.out.println("Invalid input, Try again!");
			return false;
		}
	}
				
	// func to get the positions where the AI can place its symbol to win/block a move.
	public void smartPos(char sym){
					
		// default vaule (not 0,0 since it can be an actual case)
		int[] arr1 = {-1,0};
		// vertical;
		char temp[] = new char[6]; // contains all ele of a column;
		for(int k=0; k < 7; k++){
			countR = 0; // reset values;
			countB = 0;
			posB[0] = -1;
			posB[1] = 0;
			posR[0] = -1;
			posR[1] = 0;
			// adding lists into board2 thus making it 2d lists;
			for(int i=0; i < board.length; i++){
				temp[i] = board[i][k];
			}
			// temp ready!
			for(int j=5 ; j >= 3; j--){
				if(temp[j] == sym && temp[j] == temp[j-1]
				&& temp[j-1] == temp[j-2] && temp[j-3] == ' '){
				    // store the empty coordinates;
					if(sym == sym1){
						countB = 3;
						posB[0] = j-3;
						posB[1] = k;
						return;
					}
					else if(sym == sym2){
						countR = 3;
						posR[0] = j-3;
						posR[1] = k;
						return;
					}
				}
			}
		}

		// horizontal;
		for(int i=0; i < board.length ;i++){
			for(int j=0; j < board[i].length-3 ; j++){
				countR = 0; // reset values;
				countB = 0;
				posB[0] = -1;
				posB[1] = 0;
				posR[0] = -1;
				posR[1] = 0;

				if(board[i][j] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				/* checking if spot is available && next in line is sym
				&& spot below spot is filled! */
				else if(board[i][j] == ' ' && board[i][j+1] == sym 
				&& (i==5 || board[i+1][j]!=' ')){
					// store the empty coordinates;
					if(sym == sym1){
						posB[0] = i;
						posB[1] = j;
					}
					else if(sym == sym2){
						posR[0] = i;
						posR[1] = j;
					}
				}
				if(board[i][j+1] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i][j+1] == ' ' && (i==5 || board[i+1][j+1] != ' ')){
					// checking for count to ensure the prev 1 are together
					if(sym == sym1 && countB == 1){
						posB[0] = i;
						posB[1] = j+1;
					}
					else if(sym == sym2 && countR == 1){
						posR[0] = i;
						posR[1] = j+1;
					}
				}
				if(board[i][j+2] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i][j+2] == ' '&& (i==5 || board[i+1][j+2] != ' ')){
					// checking for count to ensure the prev 2 are together
					if(sym == sym1 && countB == 2){
						posB[0] = i;
						posB[1] = j+2;
					}
					else if(sym == sym2&& countR == 2){
						posR[0] = i;
						posR[1] = j+2;
					}
				}
				if(board[i][j+3] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i][j+3] == ' ' && (i==5 || board[i+1][j+3] != ' ')){
					// checking for count to ensure the prev 3 are together
					if(sym == sym1 && countB == 3){
						System.out.println("i,j = "+i+','+j);
						posB[0] = i;
						posB[1] = j+3;
					}
					else if(sym == sym2 && countR == 3){
						posR[0] = i;
						posR[1] = j+3;
					}
				}
				if(sym == sym1 && countB >= 3 && !Arrays.equals(posB,arr1)) return;
				else if(sym == sym2 && countR >= 3 && !Arrays.equals(posR,arr1)) return;
			}	
		}

		// diagonal +ve slope;
		for(int i=5; i >= 3; i--){
			for(int j=0; j <= 3; j++){
				countR = 0; // reset values;
				countB = 0;
				posB[0] = -1;
				posB[1] = 0;
				posR[0] = -1;
				posR[1] = 0;
				if(board[i][j] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				/* checking if spot is available && next in diagonal is sym
				&& spot below spot is filled! */
				else if(board[i][j] == ' ' && board[i-1][j+1] == sym 
				&& (i==5 || board[i+1][j]!=' ')){
					// store the empty coordinates;
					if(sym == sym1){
						posB[0] = i;
						posB[1] = j;
					}
					else if(sym == sym2){
						posR[0] = i;
						posR[1] = j;
					}
				}
				if(board[i-1][j+1] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i-1][j+1] == ' ' && board[i][j+1]!=' '){
					// checking for count to ensure the prev 1 are together
					if(sym == sym1 && countB == 1){
						posB[0] = i-1;
						posB[1] = j+1;
					}
					else if(sym == sym2 && countR == 1){
						posR[0] = i-1;
						posR[1] = j+1;
					}
				}
				if(board[i-2][j+2] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i-2][j+2] == ' ' && board[i-1][j+2]!=' '){
					// checking for count to ensure the prev 2 are together
					if(sym == sym1 && countB == 2){
						posB[0] = i-2;
						posB[1] = j+2;
					}
					else if(sym == sym2&& countR == 2){
						posR[0] = i-2;
						posR[1] = j+2;
					}
				}
				if(board[i-3][j+3] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i-3][j+3] == ' ' && board[i-2][j+3]!=' '){
					// checking for count to ensure the prev 3 are together
					if(sym == sym1 && countB == 3){
						System.out.println("i,j = "+i+','+j);
						posB[0] = i-3;
						posB[1] = j+3;
					}
					else if(sym == sym2 && countR == 3){
						posR[0] = i-3;
						posR[1] = j+3;
					}
				}
				if(sym == sym1 && countB >= 3 && !Arrays.equals(posB,arr1)) return;
				else if(sym == sym2 && countR >= 3 && !Arrays.equals(posR,arr1)) return;
			}	
		}
		
		// diagonal -ve slope;
		for(int i=0; i <= 2; i++){
			for(int j=0; j <= 3; j++){
				countR = 0; // reset values;
				countB = 0;
				posB[0] = -1;
				posB[1] = 0;
				posR[0] = -1;
				posR[1] = 0;
				if(board[i][j] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				// check if spot is empty && next diagonal is sym && spot below spot is occupied
				else if(board[i][j] == ' ' && board[i+1][j+1] == sym && board[i+1][j]!=' '){
					// store the empty coordinates;
					if(sym == sym1){
						posB[0] = i;
						posB[1] = j;
					}
					else if(sym == sym2){
						posR[0] = i;
						posR[1] = j;
					}
				}
				if(board[i+1][j+1] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i+1][j+1] == ' ' && board[i+2][j+1]!=' '){
					if(sym == sym1 && countB == 1){
						posB[0] = i+1;
						posB[1] = j+1;
					}
					else if(sym == sym2 && countR == 1){
						posR[0] = i+1;
						posR[1] = j+1;
					}
				}
				if(board[i+2][j+2] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				else if(board[i+2][j+2] == ' ' && board[i+3][j+2]!=' '){
					if(sym == sym1 && countB == 2){
						posB[0] = i+2;
						posB[1] = j+2;
					}
					else if(sym == sym2&& countR == 2){
						posR[0] = i+2;
						posR[1] = j+2;
					}
				}
				if(board[i+3][j+3] == sym){
					if(sym == sym1) countB++;
					else if(sym == sym2) countR++;
				}
				// if i=2, then 2+4 = 6 > 5, thus to avoid that case;
				else if(board[i+3][j+3] == ' ' && (i==2 || board[i+4][j+3]!=' ')){
					if(sym == sym1 && countB == 3){
						System.out.println("i,j = "+i+','+j);
						posB[0] = i+3;
						posB[1] = j+3;
					}
					else if(sym == sym2 && countR == 3){
						posR[0] = i+3;
						posR[1] = j+3;
					}
				}
				if(sym == sym1 && countB >= 3 && !Arrays.equals(posB,arr1)) return;
				else if(sym == sym2 && countR >= 3 && !Arrays.equals(posR,arr1)) return;
			}
		}
		
	}
	public static void main(String[] args) {
		
		// Board b1 = new Board();
		// b1.board[4][3] = 'B';
		// b1.board[4][0] = 'B';
		// b1.board[3][0] = 'B';
		// b1.board[2][0] = 'B';
		// b1.board[5][4] = 'S';
		// b1.board[5][3] = 'B';
		// // b1.board[5][2] = 'W';
		// b1.board[5][1] = 'X';
		// b1.board[4][1] = 'B';
		// b1.board[5][0] = 'P';
		// b1.board[2][5] = 'B';
		// // b1.board[1][0] = 'R';
		// // b1.board[3][4] = 'B';
		// b1.board[4][4] = 'Q';
		// b1.board[3][1] = 'B';
		// b1.board[3][5] = 'Z';
		// b1.board[4][5] = 'X';
		// b1.board[5][5] = 'X';
		// // b1.smartPos('R'); // get blocking/winning position.
		// b1.printBoard();
		// System.out.println("win ? : "+b1.containsWin()
		// +", countB = "+b1.getCount('B')
		// +", countR = "+b1.getCount('R'));
		
		// AIPlayer robot1 = new AIPlayer('B',b1, "AI");
		// robot1.makeMove(b1);
		// b1.printBoard();
		// System.out.println("win ? : "+b1.containsWin()
		// +", countB = "+b1.getCount('B')
		// +", countR = "+b1.getCount('R')
		// +", posR = "+Arrays.toString(b1.getPos('R')));
	}
}