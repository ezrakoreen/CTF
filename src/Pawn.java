public class Pawn extends Piece
{
	private boolean enPassantable = false;

	//returns whether or not a pawn can be taken through en passsant
	public boolean getEP(){return enPassantable;}
	//sets whether or not a piece can be taken through en passant
	public void setEP(boolean eP){enPassantable = eP;}

	public Pawn(int r, int c, boolean is){
		super(r,c,is,"p");
	}


	public boolean[][] checkPossibleMoves(Piece[][] board){
		boolean[][] temp = new boolean[8][8];

		if(super.getName().equals("wp")){
			for(int r = 0; r<8; r++){
				for(int c = 0; c<8; c++){
					if(board[r][c]==null && c==super.getCol() && r==super.getRow()+1) temp[r][c] = true;
					else if(board[r][c]!=null && Math.abs(c-super.getCol()) == 1 && r==super.getRow()+1) temp[r][c] = true;
					else if(board[r][c]==null && c==super.getCol() && r==super.getRow()+2 && super.getRow() == 1 && board[r-1][c]==null) temp[r][c] = true;
				}
			}
		}

		else{
			for(int r = 0; r<8; r++){
				for(int c = 0; c<8; c++){
					if(board[r][c]==null && c==super.getCol() && r==super.getRow()-1) temp[r][c] = true;
					else if(board[r][c]!=null && Math.abs(c-super.getCol()) == 1 && r==super.getRow()-1) temp[r][c] = true;
					else if(board[r][c]==null && c==super.getCol() && r==super.getRow()-2 && super.getRow() == 6 && board[r+1][c]==null) temp[r][c] = true;
				}
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

		double value = ValData.pawnSquareVal[row][col];
		if(this.getIsWhite()){
			if(theBoard.getWIsK()){
				value+=ValData.pawnDefendK[row][col];
			}
			else if(theBoard.getWIsQ()){
				value+=ValData.pawnDefendQ[row][col];
			}

			if(theBoard.getBIsK()){
				value += 5.0 * ValData.onKingside[row][col];
			}
			else if(theBoard.getBIsQ()){
				value += 5.0 * ValData.onQueenside[row][col];
			}

			if((col<7 && board[row+1][col+1]!=null && board[row+1][col+1].getName().equals("wp")) ||
			 (col > 0 && board[row+1][col-1]!=null && board[row+1][col-1].getName().equals("wp"))){
				value+=.08;
				if(col<=3) value+=col*.01;
				else value+=(7-col)*.01;
			}
		}
		else{
			if(theBoard.getBIsK()){
				value+=ValData.pawnDefendK[row][col];
			}
			else if(theBoard.getBIsQ()){
				value+=ValData.pawnDefendQ[row][col];
			}

			if(theBoard.getWIsK()){
				value += 5.0 * ValData.onKingside[row][col];
			}
			else if(theBoard.getWIsQ()){
				value += 5.0 * ValData.onQueenside[row][col];
			}
			row = 7 - row;
			col = 7 - col;
			if((col < 7 && board[row-1][col+1]!=null && board[row-1][col+1].getName().equals("bp")) ||
			 (col > 0 && board[row-1][col-1]!=null && board[row-1][col-1].getName().equals("bp"))){
				value+=.08;
			}
		}

		return value;
	}
}