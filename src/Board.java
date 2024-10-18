import java.util.Scanner;
import java.util.ArrayList;
public class Board implements Cloneable
{
	private Scanner input = new Scanner(System.in);

	private Piece[][] board = new Piece[8][8];
	private int wKingPos = 4;
	private int bKingPos = 74;
	private ArrayList<Integer> checkingPos = new ArrayList<Integer>();

	private int turn = 0;
	private boolean wasInCheck = false;
	private boolean wInCheck = false;
	private boolean wInMate = false;
	private boolean bInCheck = false;
	private boolean bInMate = false;
	private boolean stalemate = false;
	private boolean wIsK = false;
	private boolean wIsQ = false;
	private boolean bIsK = false;
	private boolean bIsQ = false;
	private boolean wCasK = true;
	private boolean wCasQ = true;
	private boolean bCasK = true;
	private boolean bCasQ = true;
	private ArrayList<Integer> wCaps = new ArrayList<Integer>();
	private ArrayList<Integer> bCaps = new ArrayList<Integer>();
	private ArrayList<Integer> wProms = new ArrayList<Integer>();
	private ArrayList<Integer> bProms = new ArrayList<Integer>();
	private ArrayList<Integer> wBlocks = new ArrayList<Integer>();
	private ArrayList<Integer> bBlocks = new ArrayList<Integer>();
	private ArrayList<Integer> wNonCaps = new ArrayList<Integer>();
	private ArrayList<Double> wNonCapsVals = new ArrayList<Double>();
	private ArrayList<Integer> bNonCaps = new ArrayList<Integer>();
	private ArrayList<Double> bNonCapsVals = new ArrayList<Double>();
	private ArrayList<Integer> wChecks = new ArrayList<Integer>();
	private ArrayList<Integer> bChecks = new ArrayList<Integer>();

	//getter method: returns the variable (used for every variable listed)
	public Piece[][] getBoard(){
		return board;
	}

	//sets the indicated square on the board to the inputted Piece parameter
	public void set(int r, int c, Piece x){
		board[r][c] = x;
	}

	public int getTurn(){
		return turn;
	}
	public boolean getWMate(){
		return wInMate;
	}
	public boolean getBMate(){
		return bInMate;
	}
	public boolean getStalemate(){
		return stalemate;
	}

	//adds a value to wNonCaps (applies for all other arraylists)
	public void addWNonCapsVals(double val){
		wNonCapsVals.add(val);
	}
	//removes a specific index in wNonCaps (applies for all other arraylists)
	public void removeWNonCapsVals(int index){
		wNonCapsVals.remove(index);
	}
	public ArrayList<Double> getWNonCapsVals(){
		return wNonCapsVals;
	}
	public void addBNonCapsVals(double val){
		bNonCapsVals.add(val);
	}
	public void removeBNonCapsVals(int index){
		bNonCapsVals.remove(index);
	}
	public ArrayList<Double> getBNonCapsVals(){
		return bNonCapsVals;
	}

	public boolean getWIsK(){
		return wIsK;
	}
	public boolean getWIsQ(){
		return wIsQ;
	}
	public boolean getBIsK(){
		return bIsK;
	}
	public boolean getBIsQ(){
		return bIsQ;
	}


	public boolean getWCasK(){
		return wCasK;
	}
	public boolean getWCasQ(){
		return wCasQ;
	}
	public boolean getBCasK(){
		return bCasK;
	}
	public boolean getBCasQ(){
		return bCasQ;
	}

	public void addWProms(int move){
		wProms.add(move);
	}
	public void addBProms(int move){
		bProms.add(move);
	}

	public void removeWProms(int index){
		wProms.remove(index);
	}
	public void removeBProms(int index){
		bProms.remove(index);
	}

	public ArrayList<Integer> getWProms(){
		return wProms;
	}
	public ArrayList<Integer> getBProms(){
		return bProms;
	}

	public ArrayList<Integer> getCheckingPos(){
		return checkingPos;
	}
	public void addCheckingPos(int pos){
		checkingPos.add(pos);
	}

	public void addWChecks(int check){
		wChecks.add(check);
	}
	public void addBChecks(int check){
		bChecks.add(check);
	}

	public ArrayList<Integer> getWChecks(){
		return wChecks;
	}
	public ArrayList<Integer> getBChecks(){
		return bChecks;
	}

	public void removeWChecks(int index){
		wChecks.remove(index);
	}
	public void removeBChecks(int index){
		bChecks.remove(index);
	}

	//sets wInCheck equal to the inputted parameter
	public void setWInCheck(boolean check){
		wInCheck = check;
	}
	//sets bInCheck equal to the inputted parameter
	public void setBInCheck(boolean check){
		bInCheck = check;
	}

	public boolean getWInCheck(){
		return wInCheck;
	}

	public boolean getBInCheck(){
		return bInCheck;
	}

	public int getWKingPos(){
		return wKingPos;
	}

	public int getBKingPos(){
		return bKingPos;
	}

	public ArrayList<Integer> getWBlocks(){
		return wBlocks;
	}

	public void addWBlocks(int move){
		wBlocks.add(move);
	}

	public ArrayList<Integer> getBBlocks(){
		return bBlocks;
	}

	public void addBBlocks(int move){
		bBlocks.add(move);
	}

	public ArrayList<Integer> getWCaps(){
		return wCaps;
	}

	public void addWCaps(int move){
		wCaps.add(move);
	}

	public void removeWCaps(int index){
		wCaps.remove(index);
	}

	public ArrayList<Integer> getBCaps(){
		return bCaps;
	}

	public void addBCaps(int move){
		bCaps.add(move);
	}

	public void removeBCaps(int index){
		bCaps.remove(index);
	}

	public ArrayList<Integer> getWNonCaps(){
		return wNonCaps;
	}

	public void addWNonCaps(int move){
		wNonCaps.add(move);
	}

	public void removeWNonCaps(int index){
		wNonCaps.remove(index);
	}

	public ArrayList<Integer> getBNonCaps(){
		return bNonCaps;
	}

	public void addBNonCaps(int move){
		bNonCaps.add(move);
	}

	public void removeBNonCaps(int index){
		bNonCaps.remove(index);
	}

	//checks if a move is legal by making sure you're not moving into check and you're not exposing check
	public boolean isLegal (int move) throws CloneNotSupportedException{

		int r1 = move/1000;
		int c1 = move%1000/100;
		int r2 = move%100/10;
		int c2 = move%10;

		//avoids moving king into check
		if(board[r1][c1].getName().equals("wk")){
			for(int r = 0; r<8; r++){
				for(int c = 0; c<8; c++){
					if(board[r][c]!=null && board[r][c].getName().equals("bp") && r==r2+1 && Math.abs(c-c2)==1){
						return false;
					}
					Piece test = board[r1][c1].clone();
					board[r1][c1] = null;
					if(board[r][c]!=null && !board[r][c].getIsWhite() && board[r][c].checkPossibleMoves(board)[r2][c2] == true){
						if(board[r][c].getName().substring(1,2).equals("p")){
							board[r1][c1] = test;
							continue;
						}
						board[r1][c1] = test;
						return false;
					}
					board[r1][c1] = test;
				}
			}
		}
		else if(board[r1][c1].getName().equals("bk")){
			for(int r = 0; r<8; r++){
				for(int c = 0; c<8; c++){
					if(board[r][c]!=null && board[r][c].getName().substring(0,2).equals("wp") && r==r2-1 && Math.abs(c-c2)==1) return false;
					Piece test = board[r1][c1].clone();
					if(board[r][c]!=null && board[r][c].getIsWhite() && board[r][c].checkPossibleMoves(board)[r2][c2] == true){
						if(board[r][c].getName().substring(1,2).equals("p")) continue;
						board[r1][c1] = test;
						return false;
					}
					board[r1][c1] = test;
				}
			}
		}

		if(!board[r1][c1].getName().substring(1,2).equals("k")){
			Piece test1 = board[r1][c1].clone();
			Piece test2 = null;
			board[r1][c1] = null;
			if(board[r2][c2]!=null){
				test2 = board[r2][c2].clone();
			}
			board[r2][c2] = test1;
			if(test1.getName().substring(0,2).equals("wk")) wKingPos = r2*10+c2;
			else if(test1.getName().substring(0,2).equals("bk")) bKingPos = r2*10+c2;

			if(test1.getIsWhite()){
				for(int r = 0; r<8; r++){
					for(int c = 0; c<8; c++){
						if(board[r][c]!=null && !board[r][c].getIsWhite() && board[r][c].checkPossibleMoves(board)[wKingPos/10][wKingPos%10]==true){
							board[r1][c1] = test1;
							board[r2][c2] = test2;
							return false;
						}
					}
				}
			}
			else{
				for(int r = 0; r<8; r++){
					for(int c = 0; c<8; c++){
						if(board[r][c]!=null && board[r][c].getIsWhite() && board[r][c].checkPossibleMoves(board)[bKingPos/10][bKingPos%10]==true){
							board[r1][c1] = test1;
							board[r2][c2] = test2;
							return false;
						}
					}
				}
			}
			board[r1][c1] = test1;
			board[r2][c2] = test2;

			if(test1.getName().substring(0,2).equals("wk")) wKingPos = r1*10+c1;
			else if(test1.getName().substring(0,2).equals("bk")) bKingPos = r1*10+c1;
		}
		return true;
	}

	//performs an actual move on the board and all the associated updates for the rest of the pieces
	//sets possibleMoves for each piece, sets arraylists, checks for check, checkmate, stalemate, etc.
	public void move(int move) throws CloneNotSupportedException {

		/*System.out.println("val size: "+wNonCapsVals.size());
		System.out.println("move size: "+wNonCaps.size());*/

		if (wInCheck) {
			wInCheck = false;
			wasInCheck = true;
		} else if (bInCheck) {
			bInCheck = false;
			wasInCheck = true;
		}

		int r1 = move / 1000;
		int c1 = move % 1000 / 100;
		int r2 = move % 100 / 10;
		int c2 = move % 10;

		if (move / 100 == 7) wCasK = false;
		else if (move / 100 == 0) wCasQ = false;
		else if (move / 100 == 70) bCasQ = false;
		else if (move / 100 == 77) bCasK = false;

		if (move / 100 == 4) {
			wCasK = false;
			wCasQ = false;
		} else if (move / 100 == 74) {
			bCasK = false;
			bCasQ = false;
		}

		if (move / 100 == wKingPos) wKingPos = move % 100;
		else if (move / 100 == bKingPos) bKingPos = move % 100;

		//promotions
		if (board[r1][c1].getName().substring(0, 2).equals("wp") && r1 == 6) {
			board[r1][c1] = new Queen(r1, c1, true);
		} else if (board[r1][c1].getName().substring(0, 2).equals("bp") && r1 == 1) {
			System.out.print("Enter the piece you would like: ");
			String choice = input.nextLine();
			System.out.println();
			if (choice.equals("n")) board[r1][c1] = new Knight(r1, c1, false);
			else if (choice.equals("b")) board[r1][c1] = new Bishop(r1, c1, false);
			else if (choice.equals("q")) board[r1][c1] = new Queen(r1, c1, false);
			else if (choice.equals("r")) board[r1][c1] = new Rook(r1, c1, false);
			else {
				System.out.println("Invalid selection");
				return;
			}
		}

		if (board[r1][c1].getIsWhite()) {
			for (int i = 0; i < wCaps.size(); i++) {
				if (move == wCaps.get(i)) {
					board[r2][c2].updateLists(this);
					break;
				}
			}
		} else {
			for (int i = 0; i < bCaps.size(); i++) {
				if (move == bCaps.get(i)) {
					board[r2][c2].updateLists(this);
					break;
				}
			}
		}

		if (board[r1][c1].getName().equals("wk")) {
			if (move == 406) {
				board[0][7].updateLists(this);
				board[0][5] = board[0][7];
				board[0][7] = null;
				board[0][5].setPos(0, 5);
			} else if (move == 402) {
				board[0][0].updateLists(this);
				board[0][3] = board[0][0];
				board[0][0] = null;
				board[0][3].setPos(0, 3);
			}
		} else if (board[r1][c1].getName().equals("bk")) {
			if (move == 7476) {
				board[7][7].updateLists(this);
				board[7][5] = board[7][7];
				board[7][7] = null;
				board[7][5].setPos(7, 5);
			} else if (move == 7472) {
				board[7][0].updateLists(this);
				board[7][3] = board[7][0];
				board[7][0] = null;
				board[7][3].setPos(7, 3);
			}
		}

		board[r1][c1].updateLists(this);
		board[r2][c2] = board[r1][c1];
		board[r1][c1] = null;
		board[r2][c2].setPos(r2,c2);
		for(int r = 0; r<8; r++){
			for(int c = 0; c<8; c++){
				if(board[r][c]!=null){
					for(int row = 0; row<8; row++){
						for(int col = 0; col<8; col++){
							if(board[row][col]!=null){
								if(board[r][c].checkPossibleMoves(board)[row][col] && board[r][c].getIsWhite() && row*10+col == bKingPos){
									bInCheck = true;
									checkingPos.add(r*10+c);
								}
								else if(board[r][c].checkPossibleMoves(board)[row][col] && !board[r][c].getIsWhite() && row*10+col == wKingPos){
									wInCheck = true;
									checkingPos.add(r*10+c);
								}
							}
						}
					}
				}
			}
		}
		board[r2][c2].setPossibleMoves(this);
		if(move==406 && wKingPos==6) board[0][5].setPossibleMoves(this);
		else if(move==402 && wKingPos==2) board[0][3].setPossibleMoves(this);
		else if(move==7476 && wKingPos==76) board[7][5].setPossibleMoves(this);
		else if(move==7472 && wKingPos==72) board[7][3].setPossibleMoves(this);

		if(wasInCheck || wInCheck || bInCheck){
			for(int r = 0; r<8; r++){
				for(int c = 0; c<8; c++){
					if(r==r2 && c==c2) continue;
					if(board[r][c]!=null){
						board[r][c].updateLists(this);
						board[r][c].setPossibleMoves(this);
					}
				}
			}
		}

		else{
			for(int r = 0; r<8; r++){
				for(int c = 0; c<8; c++){
					if(r == r2 && c==c2) continue;
					if(board[r][c]!=null){
						//if you could move to the final square
						if(board[r][c].getPossibleMoves()[r2][c2] == true){
							board[r][c].updateLists(this);
							board[r][c].setPossibleMoves(this);
						}
						//if you could move to the initial square
						else if(board[r][c].getPossibleMoves()[r1][c1]==true){
							board[r][c].updateLists(this);
							board[r][c].setPossibleMoves(this);
						}
						//if pawn
						else if(board[r][c].getName().substring(1,2).equals("p")){
							/*if(board[r][c].getIsWhite()){
								if(r<7 && ((c<7 && board[r+1][c+1]!=null && !board[r+1][c+1].getIsWhite()) || (c>0 && board[r+1][c-1]!=null && !board[r+1][c-1].getIsWhite()))){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
								}
								else if(r==1 && ((c<7 && board[r+2][c+1]!=null && !board[r+2][c+1].getIsWhite()) || (c>0 && board[r+2][c-1]!=null && !board[r+2][c-1].getIsWhite()))){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
								}
								else if(Math.abs(c - bKingPos%10) == 1){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
								}
							}
							else{
								if(r>0 && ((c<7 && board[r-1][c+1]!=null && board[r-1][c+1].getIsWhite()) || (c>0 && board[r-1][c-1]!=null && board[r-1][c-1].getIsWhite()))){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
								}
								else if(r==6 && ((c<7 && board[r-2][c+1]!=null && board[r-2][c+1].getIsWhite()) || (c>0 && board[r-2][c-1]!=null && board[r-2][c-1].getIsWhite()))){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
								}
								else if(Math.abs(c - wKingPos%10) == 1){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
								}
							}*/
							board[r][c].updateLists(this);
							board[r][c].setPossibleMoves(this);
						}
						//if was blocked and white
						else if(board[r][c].getIsWhite()){
							int testMove=r*1000+c*100+r1*10+c1;
							int otherTestMove = r*1000+c*100+r2*10+c2;
							for(int block : wBlocks){
								if(testMove==block){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
									wBlocks.remove(wBlocks.indexOf(testMove));
									break;
								}
								else if(otherTestMove==block){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
									wBlocks.remove(wBlocks.indexOf(otherTestMove));
									break;
								}
							}
						}
						//if was blocked and black
						else if(!(board[r][c].getIsWhite())){
							int testMove=r*1000+c*100+r1*10+c1;
							int otherTestMove = r*1000+c*100+r2*10+c2;
							for(int block : bBlocks){
								if(testMove==block){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
									bBlocks.remove(bBlocks.indexOf(testMove));
									break;
								}
								else if(otherTestMove==block){
									board[r][c].updateLists(this);
									board[r][c].setPossibleMoves(this);
									bBlocks.remove(bBlocks.indexOf(otherTestMove));
									break;
								}
							}
						}
						//if it's a rook
						else if(board[r][c].getName().substring(1,2).equals("r") || board[r][c].getName().substring(1,2).equals("b")){
							board[r][c].setVal(this);
							int start = 0;
							if(board[r][c].getIsWhite()){
								for(int i = 0; i<wNonCaps.size(); i++){
									if(r*10+c==wNonCaps.get(i)/100){
										int row = wNonCaps.get(i)/1000;
										int col = wNonCaps.get(i)/100%10;
										Piece test = board[r][c].clone();
										board[row][col] = test;
										board[r][c] = null;
										wNonCapsVals.set(i,test.updateVal(this,row,col)-board[r][c].getVal());
										board[row][col] = null;
										board[r][c] = test;
									}
								}
							}
							else{
								for(int i = 0; i<bNonCaps.size(); i++){
									if(r*10+c==bNonCaps.get(i)/100){
										int row = bNonCaps.get(i)/1000;
										int col = bNonCaps.get(i)/100%10;
										Piece test = board[r][c].clone();
										board[row][col] = test;
										board[r][c] = null;
										bNonCapsVals.set(i,test.updateVal(this,row,col)-board[r][c].getVal());
										board[row][col] = null;
										board[r][c] = test;
									}
								}
							}
						}

						/*board[r][c].setVal(this);
						int start = 0;
						if(board[r][c].getIsWhite()){
							for(int i = 0; i<wNonCaps.size(); i++){
								if(r*10+c==wNonCaps.get(i)/100){
									int row = wNonCaps.get(i)/1000;
									int col = wNonCaps.get(i)/100%10;
									Piece test = board[r][c].clone();
									Piece original = board[r][c];
									board[row][col] = test;
									board[r][c] = null;
									System.out.println("Value difference for "+r*1000+c*100+row*10+c + ": " +
									wNonCapsVals.set(i,test.updateVal(this,row,col)-original.getVal());
									board[row][col] = null;
									board[r][c] = original;
								}
							}
						}
						else{
							for(int i = 0; i<bNonCaps.size(); i++){
								if(r*10+c==bNonCaps.get(i)/100){
									int row = bNonCaps.get(i)/1000;
									int col = bNonCaps.get(i)/100%10;
									Piece test = board[r][c].clone();
									Piece original = board[r][c];
									board[row][col] = test;
									board[r][c] = null;
									bNonCapsVals.set(i,test.updateVal(this,row,col)-original.getVal());
									board[row][col] = null;
									board[r][c] = original;
								}
							}
						}*/

					}
				}
			}
		}

		if(wProms.size()==0 && wCaps.size()==0 && wNonCaps.size()==0 && wInCheck) wInMate = true;
		else if(bProms.size()==0 && bCaps.size()==0 && bNonCaps.size()==0 && bInCheck) bInMate = true;
		else if(board[r2][c2].getIsWhite() && bProms.size()==0 && bCaps.size()==0 && bNonCaps.size()==0) stalemate = true;
		else if(!board[r2][c2].getIsWhite() && wProms.size()==0 && wCaps.size()==0 && wNonCaps.size()==0) stalemate = true;

		board[wKingPos/10][wKingPos%10].updateLists(this);
		board[wKingPos/10][wKingPos%10].setPossibleMoves(this);
		board[bKingPos/10][bKingPos%10].updateLists(this);
		board[bKingPos/10][bKingPos%10].setPossibleMoves(this);

		if(wKingPos/10<=2){
			if(wKingPos%10>=4 && !(wKingPos==4 && wCasK)){
				wIsK = true;
			}
			else if(wKingPos%10<4) wIsQ = true;
		}
		else{
			wIsK = false;
			wIsQ = false;
		}

		if(bKingPos/10>=5){
			if(bKingPos%10>=4 && !(bKingPos==74 && bCasK)){
				bIsK = true;
			}
			else if(bKingPos%10<4) bIsQ = true;
		}
		else{
			bIsK = false;
			bIsQ = false;
		}
		checkingPos.clear();
		wasInCheck = false;
		if(!board[r2][c2].getIsWhite()) turn++;
	}

	//calculates positional values of non-capture moves
	public void posTacts() throws CloneNotSupportedException{
		for(int x = 0; x < wNonCaps.size(); x++){

			Board newBoard = this.clone();

			newBoard.move(wNonCaps.get(x));

			wNonCapsVals.set(x, wNonCapsVals.get(x) - Main.posCapCalc(newBoard, 0, newBoard.getWCaps(), newBoard.getBCaps()));
		}
	}

	//creates a printable form of the board for the player to use
	public String toString(){
		String toReturn = "";
		for(int r = 7; r>=0; r--){
			toReturn += (r+"   ");
			for(int c = 0; c<8; c++){
				if(board[r][c] == null) toReturn += "-- ";
				else toReturn += board[r][c].getName()+" ";
			}
			toReturn += "\n";
		}
		toReturn += "\n"+"    ";
		for(int i = 0; i<8; i++){
			toReturn += i+"  ";
		}
		toReturn += "\n";
		return toReturn;
	}

	//creates the board at the start of the game
	public void makeBoard() throws CloneNotSupportedException{
		board[0][0] = new Rook(0,0,true);
		board[0][1] = new Knight(0,1,true);
		board[0][2] = new Bishop(0,2,true);
		board[0][3] = new Queen(0,3,true);
		board[0][4] = new King(0,4,true);
		board[0][5] = new Bishop(0,5,true);
		board[0][6] = new Knight(0,6,true);
		board[0][7] = new Rook(0,7,true);


		for(int i = 0; i<8; i++){
			board[1][i] = new Pawn(1,i,true);
		}
		for(int i = 0; i<8; i++){
			board[6][i] = new Pawn(6,i,false);
		}

		board[7][0] = new Rook(7,0,false);
		board[7][1] = new Knight(7,1,false);
		board[7][2] = new Bishop(7,2,false);
		board[7][3] = new Queen(7,3,false);
		board[7][4] = new King(7,4,false);
		board[7][5] = new Bishop(7,5,false);
		board[7][6] = new Knight(7,6,false);
		board[7][7] = new Rook(7,7,false);

		for(int r = 0; r<8; r++){
			for(int c = 0; c<8; c++){
				if(board[r][c]!=null){
					board[r][c].setPossibleMoves(this);
				}
			}
		}
	}

	//creates a deep clone of a specific board object
	public Board clone() throws CloneNotSupportedException {
		Board cloned = (Board) super.clone();

		// Deep clone the board array
		cloned.board = new Piece[8][8];
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null) {
					cloned.board[r][c] = (Piece) board[r][c].clone();
				}
			}
		}

		// Deep clone the ArrayList fields
		cloned.wProms = new ArrayList<>(wProms);
		cloned.bProms = new ArrayList<>(bProms);
		cloned.wNonCapsVals = new ArrayList<>(wNonCapsVals);
		cloned.bNonCapsVals = new ArrayList<>(bNonCapsVals);
		cloned.checkingPos = new ArrayList<>(checkingPos);
		cloned.wCaps = new ArrayList<>(wCaps);
		cloned.bCaps = new ArrayList<>(bCaps);
		cloned.wBlocks = new ArrayList<>(wBlocks);
		cloned.bBlocks = new ArrayList<>(bBlocks);
		cloned.wNonCaps = new ArrayList<>(wNonCaps);
		cloned.bNonCaps = new ArrayList<>(bNonCaps);
		cloned.wChecks = new ArrayList<>(wChecks);
		cloned.bChecks = new ArrayList<>(bChecks);

		return cloned;
	}
}