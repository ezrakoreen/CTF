import java.util.ArrayList;
public class Piece implements Cloneable
{
	private int row, col;
	private double val = 0;
	private boolean isWhite;
	private String name;
	private boolean[][] possibleMoves = new boolean[8][8];

	//creates a Piece object
	public Piece(int r, int c, boolean is, String type){
		row = r;
		col = c;
		isWhite = is;
		if(isWhite) name = "w"+type;
		else name = "b"+type;
	}

	//returns whether or not a piece can be taken through en passsant
	//only here because each Piece on a board is created as a superclass Piece, not the subclass,
	//so you need the superclass method to call a subclass override
	public boolean getEP(){return false;}
	//sets whether or not a piece can be taken through en passant, same reasoning as above
	public void setEP(boolean eP){return;}

	//returns positional value of the piece
	public double getVal(){
		return val;
	}

	//sets the positional value of the piece
	public void setVal(Board theBoard){
		val = updateVal(theBoard,row,col);
	}

	//updates the positional value of the piece
	//this method only exists becase
	public double updateVal(Board theBoard, int row, int col){
		return 0;
	}

	public boolean[][] getPossibleMoves(){
		return possibleMoves;
	}

	public boolean getIsWhite(){
		return isWhite;
	}

	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
	}

	//sets row and col
	public void setPos(int r, int c){
		row = r;
		col = c;
	}

	public String getName(){
		return name;
	}

	//on each turn, the possible moves lists are updated for each piece, so moves are added to the list
	//the old moves must then be removed from the lists. This method does the removing.
	public void updateLists(Board board){
		if(isWhite){
			for(int i = 0; i<board.getWCaps().size(); i++){
				if(board.getWCaps().get(i)/100 == row*10+col){
					board.removeWCaps(i);
					i--;
				}
			}
			for(int i = 0; i<board.getWNonCaps().size(); i++){
				if(board.getWNonCaps().get(i)/100 == row*10+col){
					board.removeWNonCaps(i);
					board.removeWNonCapsVals(i);
					i--;
				}
			}
			for(int i = 0; i<board.getWChecks().size(); i++){
				if(board.getWChecks().get(i)/100 == row*10+col){
					board.removeWChecks(i);
					i--;
				}
			}
			if(name.substring(1,2).equals("p")){
				for(int i = 0; i<board.getWProms().size(); i++){
					if(board.getWProms().get(i)/100 == row*10+col){
						board.removeWProms(i);
						i--;
					}
				}
			}
		}
		else{
			for(int i = 0; i<board.getBCaps().size(); i++){
				if(board.getBCaps().get(i)/100 == row*10+col){
					board.removeBCaps(i);
					i--;
				}
			}
			for(int i = 0; i<board.getBNonCaps().size(); i++){
				if(board.getBNonCaps().get(i)/100 == row*10+col){
					board.removeBNonCaps(i);
					board.removeBNonCapsVals(i);
					i--;
				}
			}
			for(int i = 0; i<board.getBChecks().size(); i++){
				if(board.getBChecks().get(i)/100 == row*10+col){
					board.removeBChecks(i);
					i--;
				}
			}
			if(name.substring(1,2).equals("p")){
				for(int i = 0; i<board.getBProms().size(); i++){
					if(board.getBProms().get(i)/100 == row*10+col){
						board.removeBProms(i);
						i--;
					}
				}
			}
		}
	}

	//sets the possible moves for a piece and adds the moves to caps, noncaps, proms, checks, etc.
	public void setPossibleMoves(Board board) throws CloneNotSupportedException{
		Piece[][] thisBoard = board.getBoard();
		possibleMoves = checkPossibleMoves(thisBoard);
		setVal(board);
		int move;

		for(int r = 0; r<8; r++){
			for(int c=0; c<8; c++){
				//set blocks
				move = row*1000+col*100+r*10+c;
				if(thisBoard[r][c]!=null && thisBoard[r][c].getIsWhite() == isWhite && possibleMoves[r][c] == true){
					possibleMoves[r][c] = false;
					if(isWhite){
						board.addWBlocks(move);
					}
					else{
						board.addBBlocks(move);
					}
				}

				//set arraylists
				if(possibleMoves[r][c] && board.isLegal(move)){
					if(name.substring(1,2).equals("p")){
						if(isWhite && r==7 && r*10+c!=board.getBKingPos()){
							board.addWProms(move);
						}
						else if(!isWhite && r==0 && r*10+c!=board.getWKingPos()){
							board.addBProms(move);
						}
					}

					Piece test = this.clone();
					test.setPos(r,c);

					if(thisBoard[r][c]!=null && thisBoard[r][c].getIsWhite() != isWhite && isWhite==true){
						if(r*10+c != board.getBKingPos() && r!=7){
							board.addWCaps(move);
						}
					}
					else if(thisBoard[r][c]!=null && thisBoard[r][c].getIsWhite() != isWhite && isWhite==false){
						if(r*10+c != board.getWKingPos() && r!=0){
							board.addBCaps(move);
						}
					}
					else if(thisBoard[r][c]==null && isWhite && !(name.equals("wp") && row==6)){
						board.addWNonCaps(move);
						thisBoard[r][c] = test;
						Piece test1 = this.clone();
						thisBoard[row][col] = null;
						double newVal = test.updateVal(board,r,c);
						board.addWNonCapsVals(newVal-val);
						thisBoard[r][c] = null;
						thisBoard[row][col] = test1;
					}
					else if(thisBoard[r][c]==null && !isWhite && !(name.equals("bp") && row==1)){
						board.addBNonCaps(move);
						thisBoard[r][c] = test;
						Piece test1 = this.clone();
						thisBoard[row][col] = null;
						double newVal = test.updateVal(board,row,col);
						board.addBNonCapsVals(newVal-val);
						thisBoard[r][c] = null;
						thisBoard[row][col] = test1;
					}

					int kRow, kCol;
					if(isWhite){
						kRow = board.getBKingPos()/10;
						kCol = board.getBKingPos()%10;
					}
					else{
						kRow = board.getWKingPos()/10;
						kCol = board.getWKingPos()%10;
					}
					if(test.checkPossibleMoves(thisBoard)[kRow][kCol]==true){
						if(isWhite) board.addWChecks(move);
						else board.addBChecks(move);
					}
				}
			}
		}

		//set en passant

		//set castling
		if(name.equals("wk") && board.getWCasK() && thisBoard[0][5]==null && thisBoard[0][6]==null && !board.getWInCheck()){
			Piece test = clone();
			board.set(0,5,test);
			if(board.isLegal(405) && board.isLegal(506)){
				possibleMoves[0][6]=true;
				board.addWNonCaps(406);
				//board[0][5] = board[0][7].clone();
				board.addWNonCapsVals(1);
			}
			board.set(0,5,null);
		}
		if(name.equals("wk") && board.getWCasQ() && thisBoard[0][3]==null && thisBoard[0][2]==null && thisBoard[0][1]==null){
			Piece test = clone();
			board.set(0,3,test);
			if(board.isLegal(403) && board.isLegal(302)){
				possibleMoves[0][2]=true;
				board.addWNonCaps(402);
				board.addWNonCapsVals(.5);
			}
			board.set(0,3,null);
		}
		if(name.equals("bk") && board.getBCasK() && thisBoard[7][5]==null && thisBoard[7][6]==null){
			Piece test = clone();
			board.set(7,5,test);
			if(board.isLegal(7475) && board.isLegal(7576)){
				possibleMoves[7][6] = true;
				board.addBNonCaps(7476);
				board.addBNonCapsVals(1);
			}
			board.set(7,5,null);
		}
		if(name.equals("bk") && board.getBCasQ() && thisBoard[7][3]==null && thisBoard[7][2]==null && thisBoard[7][1]==null){
			Piece test = clone();
			board.set(7,3,test);
			if(board.isLegal(7473) && board.isLegal(7372)){
				possibleMoves[7][2]=true;
				board.addBNonCaps(7472);
				board.addBNonCapsVals(.5);
			}
			board.set(7,3,null);
		}
	}

	//returns empty 2D boolean array, only exists because you need the superclass to call subclass override
	public boolean[][] checkPossibleMoves(Piece[][] board){
		return new boolean[8][8];
	}

	//Creates a clone of a piece object
	@Override
	protected Piece clone() throws CloneNotSupportedException {
		return (Piece) super.clone();
	}
}