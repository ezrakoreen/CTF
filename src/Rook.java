public class Rook extends Piece
{
	public Rook(int r, int c, boolean is){
		super(r,c,is,"r");
	}


	public boolean[][] checkPossibleMoves(Piece[][] board){
		boolean[][] temp = new boolean[8][8];
		int cRow = super.getRow();
		int cCol = super.getCol();


		for(int r = cRow+1; r<8; r++){
			if(board[r][cCol] == null) temp[r][cCol] = true;
			else{
				temp[r][cCol]=true;
				break;
			}
		}
		for(int r = cRow-1; r>=0; r--){
			if(board[r][cCol] == null) temp[r][cCol] = true;
			else{
				temp[r][cCol]=true;
				break;
			}
		}
		for(int c = cCol+1; c<8; c++){
			if(board[cRow][c] == null) temp[cRow][c] = true;
			else{
				temp[cRow][c]=true;
				break;
			}
		}
		for(int c = cCol-1; c>=0; c--){
			if(board[cRow][c] == null) temp[cRow][c] = true;
			else{
				temp[cRow][c]=true;
				break;
			}
		}
		return temp;
	}

	//for rook val
	public double updateVal(Board theBoard, int row, int col){
		//flips the board if it is a black piece
		Piece[][] board = theBoard.getBoard();
		if(!super.getIsWhite()){
			row = 7 - row;
			col = 7- col;
		}

		double value = 4.5;
		double mult = 1;

		for(int r = row + 1; r<8; r++){
			value += mult * ValData.squareValGeneric[r][col];
			if(board[r][col] != null){
				mult *= .5;
			}
		}

		mult = 1;

		for(int r = row - 1; r>=0; r--){
			value += mult * ValData.squareValGeneric[r][col];
			if(board[r][col] != null){
				mult *= .5;
			}
		}

		mult = 1;

		for(int c = col + 1; c<8; c++){
			value += mult * ValData.squareValGeneric[row][c];
			if(board[row][c] != null){
				mult *= .5;
			}
		}

		mult = 1;

		for(int c = col - 1; c>=0; c--){
			value += mult * ValData.squareValGeneric[row][c];
			if(board[row][c] != null){
				mult *= .5;
			}
		}
		//System.out.println(value);
		return value;
	}
}