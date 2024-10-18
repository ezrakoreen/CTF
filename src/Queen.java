public class Queen extends Piece
{
	public Queen(int r, int c, boolean is){
		super(r,c,is,"q");
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

		int r = super.getRow();
		int c = super.getCol();
		while(r<7 && c<7){
			r++;
			c++;
			if(board[r][c] == null) temp[r][c] = true;
			else{
				temp[r][c]=true;
				break;
			}
		}
		r = super.getRow();
		c = super.getCol();
		while(r<7 && c>0){
			r++;
			c--;
			if(board[r][c] == null) temp[r][c] = true;
			else{
				temp[r][c]=true;
				break;
			}
		}
		r = super.getRow();
		c = super.getCol();
		while(r>0 && c>0){
			r--;
			c--;
			if(board[r][c] == null) temp[r][c] = true;
			else{
				temp[r][c]=true;
				break;
			}
		}
		r = super.getRow();
		c = super.getCol();
		while(r>0 && c<7){
			r--;
			c++;
			if(board[r][c] == null) temp[r][c] = true;
			else{
				temp[r][c]=true;
				break;
			}
		}
		return temp;
	}

	public double updateVal(Board theBoard, int row, int col){
		Piece[][] board = theBoard.getBoard();
		//flips the board if it is a black piece
		if(!super.getIsWhite()){
			row = 7 - row;
			col = 7- col;
		}

		double value = ValData.queenSquareVal[row][col];
		if(this.getIsWhite()){
			if(theBoard.getBIsK()){
				value += 20.0 * ValData.onKingside[row][col];
			}
			else if(theBoard.getBIsQ()){
				value += 20.0 * ValData.onQueenside[row][col];
			}
		}
		else{
			if(theBoard.getWIsK()){
				value += 20.0 * ValData.onKingside[row][col];
			}
			else if(theBoard.getWIsQ()){
				value += 20.0 * ValData.onQueenside[row][col];
			}
		}
		return value;
	}
}