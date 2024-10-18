public class Bishop extends Piece
{
	//creates a bishop object
	public Bishop(int r, int c, boolean is){
		super(r,c,is,"b");
	}

	//creates a 2D boolean array of the squares that the bishop object can move to
	//doesn't include complications due to check
	public boolean[][] checkPossibleMoves(Piece[][] board){
		boolean[][] temp = new boolean[8][8];
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

	//updates positional value of a bishop object
	public double updateVal(Board theBoard, int row, int col){

		Piece[][] board = theBoard.getBoard();
		//flips the board if it is a black piece
		if(!super.getIsWhite()){
			row = 7 - row;
			col = 7- col;
		}

		double value = 2.5;

		int r = row;
		int c = col;
		double mult = 1;
		while(r<7 && c<7){
			r++;
			c++;
			value += mult * ValData.squareValGeneric[r][c];
			if(board[r][c] != null){
				mult *= .5;
			}
		}
		r = row;
		c = col;
		mult = 1;
		while(r<7 && c>0){
			r++;
			c--;
			value += mult * ValData.squareValGeneric[r][c];
			if(board[r][c] != null){
				mult *= .5;
			}
		}
		r = row;
		c = col;
		mult = 1;
		while(r>0 && c>0){
			r--;
			c--;
			value += mult * ValData.squareValGeneric[r][c];
			if(board[r][c] != null){
				mult *= .5;
			}
		}
		r = row;
		c = col;
		mult = 1;
		while(r>0 && c<7){
			r--;
			c++;
			value += mult * ValData.squareValGeneric[r][c];
			if(board[r][c] != null){
				mult *= .5;
			}
		}
		return value;
	}
}