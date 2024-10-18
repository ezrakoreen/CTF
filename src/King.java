public class King extends Piece
{
	public King(int r, int c, boolean is){
		super(r,c,is,"k");
	}

	//return the value of a king
	public double getVal(){
		return 15;
	}

	public boolean[][] checkPossibleMoves(Piece[][] board){
		boolean[][] temp = new boolean[8][8];

		int row = super.getRow();
		int col = super.getCol();

		for(int r = 0; r<8; r++){
			for(int c = 0; c<8; c++){
				if(Math.abs(row-r)<=1 && Math.abs(col-c)<=1 && !(row == r && col ==c)) temp[r][c] = true;
			}
		}
		return temp;
	}

	public double updateVal(Board theBoard, int row, int col){
		return 100;
	}
}