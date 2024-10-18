import java.util.Scanner;
import java.util.ArrayList;
public class Main
{

	public static int turn = 0;

	public static Board mainBoard = new Board();

	public static ArrayList <Integer> bestCaptures = new ArrayList <Integer>();

	public static double bestScore = -10000; //so that the first capture sets bestScore, or if there are no captures then it won't want to play a capture

	public static ArrayList <Integer> bestPosCaptures = new ArrayList <Integer>();

	public static double bestPosTactScore = -10000; //so that the first capture sets bestPosTactScore, or if there are no captures then it won't want to play a capture

	public static double bestPosScore = -10000;

	public static int bestPosMove = 0;

	public static boolean positional;


	//main method
	public static void main(String[] args) throws CloneNotSupportedException
	{
		mainBoard.makeBoard();
		int move=0;
		int r1=0, c1=0, r2=0, c2=0;
		Scanner input = new Scanner(System.in);
		boolean toMove = true;

		while(true){
			System.out.println(mainBoard.toString());

			//if(mainBoard.getTurn()==0 && toMove) mainBoard.move(1333);

			while(move == 0 || mainBoard.getBoard()[r1][c1]==null || mainBoard.getBoard()[r1][c1].getPossibleMoves()[r2][c2] == false){
				if(toMove && mainBoard.getTurn()==0) move = 1333;
				else if(mainBoard.getTurn()==1 && toMove){
					if(!(mainBoard.getBoard()[4][1] == null)){
						move= 1434;
					}
					else if(!(mainBoard.getBoard()[4][2] == null)){
						move = 3343;
					}
					else if(!(mainBoard.getBoard()[4][4] == null)){
						move = 3344;
					}
					else{
						move = 1232;
					}
				}

				else{
					System.out.print("Enter a move: ");
					try{
						move = Integer.parseInt(input.nextLine());
					} catch(NumberFormatException e){
						continue;
					}
				}

				System.out.println();
				r1 = move/1000;
				c1 = move%1000/100;
				r2 = move%100/10;
				c2 = move%10;

				if(r1>7 || c1>7 || r2>7 || c2>7 || r1<0){
					move = 0;
					continue;
				}
				if(mainBoard.getBoard()[r1][c1]==null){
					move = 0;
					continue;
				}


				if(turn%2 == 1 && mainBoard.getBoard()[r1][c1].getIsWhite()==true){
					move = 0;
					continue;
				}

				if (!mainBoard.isLegal(move)){
					move = 0;
					System.out.println("Illegal move");
					continue;
				}
			}


			r1 = r2;
			c1 = c2;
			String temp = mainBoard.toString();
			long t1 = System.currentTimeMillis();
			mainBoard.move(move);
			if(!temp.equals(mainBoard.toString())){
				turn++;
			}

			positional = false;

			if(mainBoard.getWCaps().size() > 0) {
				System.out.println(capCalc(mainBoard, 0, mainBoard.getWCaps(), mainBoard.getBCaps()));//first call to the method
			}

			mainBoard.posTacts();

			for(int i = 0; i<mainBoard.getWNonCaps().size(); i++){
				System.out.println(mainBoard.getWNonCaps().get(i)+" "+mainBoard.getWNonCapsVals().get(i));
			}

			for(int i = 0; i<mainBoard.getWNonCaps().size(); i++){
				if(mainBoard.getWNonCapsVals().get(i) > bestPosScore){
					bestPosScore = mainBoard.getWNonCapsVals().get(i);
					bestPosMove = mainBoard.getWNonCaps().get(i);
				}
			}

			System.out.print("bestScore:");
			System.out.println(bestScore);


			if(bestScore > 0){
				System.out.print("bestScore:");
				System.out.println(bestScore);
				System.out.print("best move:");
				System.out.println(bestCaptures.get(0));
			}
			else if(bestPosScore > 0){
				System.out.print("best move:");
				System.out.println(bestPosMove);
			}
			else if(bestPosScore > bestScore){
				System.out.print("best move:");
				System.out.println(bestPosMove);
			}
			else{
				System.out.print("bestScore:");
				System.out.println(bestScore);
				System.out.print("best move:");
				System.out.println(bestCaptures.get(0));
			}
			bestScore = -10000;
			bestPosTactScore = -10000;
			bestPosScore = -10000;
			bestPosMove = 0;
			//reset more stuff here pls

			if(mainBoard.getWMate()){
				System.out.println("Checkmate. Black wins.");
				break;
			}
			else if(mainBoard.getBMate()){
				System.out.println("Checkmate. White wins.");
				break;
			}
			else if(mainBoard.getStalemate()){
				System.out.println("Stalemate.");
				break;
			}

			long t2 = System.currentTimeMillis();
			//System.out.println(t2-t1);
			System.out.println("Black is king side: "+mainBoard.getBIsK());
			System.out.println("Black is queen side: "+mainBoard.getBIsQ());
			toMove = !toMove;

		}
	}

	//calculates the value of captures
	public static double capCalc(Board board, int movesCalced, ArrayList<Integer> wCaps, ArrayList <Integer> bCaps) throws CloneNotSupportedException{

		//checks for if there are no captures to be made
		/*System.out.print("movesCalced: ");
		System.out.println(movesCalced);
		System.out.println(board.toString());*/
		if((movesCalced % 2 == 0 && wCaps.size() == 0) || (movesCalced % 2 == 1 && bCaps.size() == 0)){

			return 0;
		}
//__________________________________________________________________________________________________________________________________
		else if(movesCalced == 5){
			double moveScore = 0;

			for(int x = 0;  x < bCaps.size(); x++){
				if(moveScore < (board.getBoard()[(bCaps.get(x) / 10) % 10][bCaps.get(x) % 10]).getVal()){
					moveScore = (board.getBoard()[(bCaps.get(x) / 10) % 10][bCaps.get(x) % 10]).getVal();
				}

			}

			return moveScore * -1;
		}

//__________________________________________________________________________________________________________________________________

		else if(movesCalced % 2 == 0){

			double moveScore = 0;
			for(int x = 0;  x < wCaps.size(); x++){

				Board newBoard = new Board();

				newBoard = board.clone();

				newBoard.move(wCaps.get(x));

				double thisMoveScore = capCalc(newBoard, movesCalced + 1, newBoard.getWCaps(), newBoard.getBCaps()) + (board.getBoard()[(wCaps.get(x) / 10) % 10][wCaps.get(x) % 10]).getVal();
				//System.out.println("this move score" + thisMoveScore);
				if(moveScore <= thisMoveScore){
					moveScore = thisMoveScore;
				}
				if(movesCalced == 0 && moveScore >= bestScore){ //changes currentMove to the current first move in the calculation sequence if it is the first move
					if(thisMoveScore > bestScore){
						bestScore = thisMoveScore;
						bestCaptures.clear();
						bestCaptures.add(wCaps.get(x));
					}

					else if(bestCaptures.size() > 0 || bestCaptures.get(bestCaptures.size() - 1) != wCaps.get(x)){
						bestCaptures.add(wCaps.get(x));
					}
				}
			}

			return moveScore;
		}
//____________________________________________________________________________________________________________________________________
		else if(movesCalced % 2 == 1){
			double moveScore = 0;
			for(int x = 0;  x < bCaps.size(); x++){

				Board newBoard = new Board();

				newBoard = board.clone();

				newBoard.move(bCaps.get(x));

				if(moveScore >= (capCalc(newBoard, movesCalced + 1, newBoard.getWCaps(), newBoard.getBCaps()) - (board.getBoard()[(bCaps.get(x) / 10) % 10][bCaps.get(x) % 10]).getVal())){
					moveScore = (capCalc(newBoard, movesCalced + 1, newBoard.getWCaps(), newBoard.getBCaps()) - (board.getBoard()[(bCaps.get(x) / 10) % 10][bCaps.get(x) % 10]).getVal());
				}
			}

			return moveScore;
		}
		return 69; //so it doesn't throw an error because there is no return statement at the end
	}

//__________________________________________________________________________________________________________________________________
//__________________________________________________________________________________________________________________________________
//__________________________________________________________________________________________________________________________________

	//calculates the positional gain of captures
	public static double posCapCalc(Board board, int movesCalced, ArrayList<Integer> wCaps, ArrayList <Integer> bCaps) throws CloneNotSupportedException{

		//checks for if there are no captures to be made
		/*System.out.print("movesCalced: ");
		System.out.println(movesCalced);
		System.out.println(board.toString());*/
		if((movesCalced % 2 == 0 && bCaps.size() == 0) || (movesCalced % 2 == 1 && wCaps.size() == 0)){

			return 0;
		}
//__________________________________________________________________________________________________________________________________
		else if(movesCalced == 3){
			double moveScore = 0;

			for(int x = 0;  x < wCaps.size(); x++){
				if(moveScore < (board.getBoard()[(wCaps.get(x) / 10) % 10][wCaps.get(x) % 10]).getVal()){
					moveScore = (board.getBoard()[(wCaps.get(x) / 10) % 10][wCaps.get(x) % 10]).getVal();
				}

			}

			return moveScore * -1;
		}

//__________________________________________________________________________________________________________________________________

		else if(movesCalced % 2 == 0){

			double moveScore = 0;
			for(int x = 0;  x < bCaps.size(); x++){

				Board newBoard = new Board();

				newBoard = board.clone();

				newBoard.move(bCaps.get(x));

				double thisMoveScore = posCapCalc(newBoard, movesCalced + 1, newBoard.getWCaps(), newBoard.getBCaps()) + (board.getBoard()[(bCaps.get(x) / 10) % 10][bCaps.get(x) % 10]).getVal();
				//System.out.println("this move score" + thisMoveScore);
				if(moveScore <= thisMoveScore){
					moveScore = thisMoveScore;
				}
				if(movesCalced == 0 && moveScore >= bestPosTactScore){ //changes currentMove to the current first move in the calculation sequence if it is the first move
					if(thisMoveScore > bestPosTactScore){
						bestPosTactScore += thisMoveScore;
						bestPosCaptures.clear();
						bestPosCaptures.add(bCaps.get(x));
					}
					else if(bestPosCaptures.size() > 0 || bestPosCaptures.get(bestPosCaptures.size() - 1) != bCaps.get(x)){
						bestPosCaptures.add(bCaps.get(x));
					}
				}
			}

			return moveScore;
		}
//____________________________________________________________________________________________________________________________________
		else if(movesCalced % 2 == 1){
			double moveScore = 0;
			for(int x = 0;  x < wCaps.size(); x++){

				Board newBoard = new Board();

				newBoard = board.clone();

				newBoard.move(wCaps.get(x));

				if(moveScore >= (posCapCalc(newBoard, movesCalced + 1, newBoard.getWCaps(), newBoard.getBCaps()) - (board.getBoard()[(wCaps.get(x) / 10) % 10][wCaps.get(x) % 10]).getVal())){
					moveScore = (posCapCalc(newBoard, movesCalced + 1, newBoard.getWCaps(), newBoard.getBCaps()) - (board.getBoard()[(wCaps.get(x) / 10) % 10][wCaps.get(x) % 10]).getVal());
				}
			}

			return moveScore;
		}
		return 69; //so it doesn't throw an error because there is no return statement at the end
	}
}