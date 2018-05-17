package chessica.board;

import chessica.board.square.*;

/**
@author Falguni Das Shuvo
*/

public class GameType {
	private static final int ENGINE = 1;
	private static final int NETWORK = 2;
	private static int type = ENGINE;
	private static boolean color = true;
	
	private GameType(){}
	
	public static void config(int t, boolean c) {
		type = t;
		color  = c;
	}
	
	public static ChessicaOpponentInterface getConn(Square [][] board){
		ChessicaOpponentInterface coi = null;
		
		switch(type){
			case ENGINE:
				coi = new UEConn(board,color);
				break;
			case NETWORK:
				coi = new NetConn(board,color);
				break;
			default:
				System.out.println("GameType error");
				System.exit(-1);
		
		}
		
		return coi;
	}
}