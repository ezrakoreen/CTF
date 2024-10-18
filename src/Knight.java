public class Knight extends Piece
{
	public Knight(int r, int c, boolean is){
		super(r,c,is,"n");
	}


	public boolean[][] checkPossibleMoves(Piece[][] board){
		boolean[][] temp = new boolean[8][8];
		int row = super.getRow();
		int col = super.getCol();

		for(int r = 0; r<8; r++){
			for(int c = 0; c<8; c++){
				if(Math.abs(row-r)==2 && Math.abs(col-c)==1) temp[r][c] = true;
				if(Math.abs(row-r)==1 && Math.abs(col-c)==2) temp[r][c] = true;
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

		double value = ValData.knightSquareVal[row][col];
		if(this.getIsWhite()){
			if(theBoard.getBIsK()){
				value += 10.0 * ValData.onKingside[row][col];
			}
			else if(theBoard.getBIsQ()){
				value += 10.0 * ValData.onQueenside[row][col];
			}
		}
		else{
			if(theBoard.getWIsK()){
				value += 10.0 * ValData.onKingside[row][col];
			}
			else if(theBoard.getWIsQ()){
				value += 10.0 * ValData.onQueenside[row][col];
			}
		}
		return value;
	}
}