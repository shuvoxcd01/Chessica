package chessica.board;

import chessica.board.square.*;
import javax.swing.*;
import chessica.arduino.*;

/**
@author Falguni Das Shuvo
*/

public class UEConn implements Runnable, ChessicaOpponentInterface {
	private Thread t;
	private Square square[][];
	private boolean color; // color of user
		
	/*
	The following attributes are necessary for getFEN()
	*/
	private String enPassantTarget;
	private int fullmoveNumber;
	private int halfmoveClock;
	private boolean whiteKingMoved;
	private boolean whiteKingRookMoved;
	private boolean whiteQueenRookMoved;
	private boolean blackKingMoved;
	private boolean blackKingRookMoved;
	private boolean blackQueenRookMoved;
	private final String activeColor;  // the color moves next - which also means it is opponent's color.
	private boolean fullmoveNumberErrorAdjuster;
	private boolean whiteCanCastle;
	private boolean blackCanCastle;
	
	/*
	The following attributes are for the purpose of the engine.
	*/
	private static String fen;
	private static String move;
	private boolean userMoved = false;
	private EngineCommunication ec;	
	private ArduinoNotifier arduinoNotifier;
	private boolean engineMoveFirst = false;
	private boolean engineCastled = false;
	
	//new additions
	private String previousSquare;
	private String currentSquare;
	private String movedPiece;
	private boolean capture;
	
	public void move(String str, boolean capture) {
		movedPiece = String.valueOf(str.charAt(0));
		previousSquare = String.valueOf(str.charAt(1)) + String.valueOf(str.charAt(2));
		currentSquare = String.valueOf(str.charAt(3)) + String.valueOf(str.charAt(4));
		this.capture = capture;
		fenUpdate(capture);
		engineDataSetter();
	}
	
	
	
	public UEConn(Square square[][], boolean color) {
		t = new Thread(this, "UEConn");
		this.square = square;
		//this.oldBoard = board;
		this.enPassantTarget = "-";
		this.fullmoveNumber = 1;
		this.halfmoveClock = 0;
		this.whiteKingMoved = false;
		this.whiteKingRookMoved = false;
		this.whiteQueenRookMoved = false;
		this.blackKingMoved = false;
		this.blackKingRookMoved = false;
		this.blackQueenRookMoved = false;
		this.color = color;
		if(color) {
			this.activeColor = "b";
			this.fullmoveNumberErrorAdjuster = true;
		}
		else {
			this.activeColor = "w";
			this.fullmoveNumberErrorAdjuster = false;
			this.engineMoveFirst = true;
		}
		
		this.whiteCanCastle = true;
		this.blackCanCastle = true;
		
		//engine
		ec = new EngineCommunication(this);
		
		arduinoNotifier = ArduinoNotifier.getArduinoConn();
		//System.out.println("Arduino");
		
		t.start();
		
	}
	
	public void run() {
		while(true)
			makeMove();
	}
	
	
	// following fenUpdate is only for user moves. Need to implement the engine move version.
	public void fenUpdate(boolean capture){
		if(capture){
			/*
					code for FEN related attributes
					*/
					fullmoveNumber++;
					halfmoveClock = 0;
					enPassantTarget = "-";  // inefficient (I guess)
					
					/* code for FEN castling.
						Another copy is in move.
					*/
					if(color) {
						if(whiteCanCastle){
							String piece = movedPiece;
							if(piece.equals("K")){
								whiteKingMoved = true;
								whiteCanCastle = false;
							}
							else if(piece.equals("R")) {
								String from = String.valueOf(previousSquare.charAt(0));
								if(from.equals("h"))
									whiteKingRookMoved = true;
								else if(from.equals("a"))
									whiteQueenRookMoved = true;
								if(whiteKingRookMoved && whiteQueenRookMoved)
									whiteCanCastle = false;
							}
						}
					}
					
					else if(!color) {
						if(blackCanCastle){
							String piece = movedPiece;
							if(piece.equals("K")){
								blackKingMoved = true;
								blackCanCastle = false;
							}
							else if(piece.equals("R")) {
								String from = String.valueOf(previousSquare.charAt(0));
								if(from.equals("h"))
									blackKingRookMoved = true;
								else if(from.equals("a"))
									blackQueenRookMoved = true;
								if(blackKingRookMoved && blackQueenRookMoved)
									blackCanCastle = false;
							}
						}
					}
					
					
					
					// end of code for FEN related attributes
					
		}
		else {
			/*
				code for FEN related attributes
				*/
				//if(curSelected)
				if(fullmoveNumberErrorAdjuster){   // since a capture in first move is never possible this conditionla check is omitted in the capture part
					fullmoveNumberErrorAdjuster = false;
					fullmoveNumber = 0;
				}
				
				fullmoveNumber++;
				
				if(movedPiece.equals("P")){
					halfmoveClock = 0;
					
				/* code for enPassantTarget
				since it is only possible in a move (not in a capture)
				the following copy of code is not made to the capture part.
				
				However, since after a capture any enPassantTarget must be "-" (common sense),
				one line of code (enPassantTarget = "-";) is made in the capture part.
				*/
					
					String pawnPosStart = String.valueOf(previousSquare.charAt(1));
					String pawnPosEnd = String.valueOf(currentSquare.charAt(1));
					if(pawnPosStart.equals("2") && pawnPosEnd.equals("4"))
						enPassantTarget = String.valueOf(previousSquare.charAt(0)) + "3";
					else if(pawnPosStart.equals("7") && pawnPosEnd.equals("5"))
						enPassantTarget = String.valueOf(previousSquare.charAt(0)) + "6";
				}
				else{
					halfmoveClock++;
					enPassantTarget = "-";
				}
				
				/* code for FEN castling.
						Another copy is in capture.
					*/
					if(color) {
						if(whiteCanCastle){
							String piece = movedPiece;
							if(piece.equals("K")){
								whiteKingMoved = true;
								whiteCanCastle = false;
							}
							else if(piece.equals("R")) {
								String from = String.valueOf(previousSquare.charAt(0));
								if(from.equals("h"))
									whiteKingRookMoved = true;
								else if(from.equals("a"))
									whiteQueenRookMoved = true;
								if(whiteKingRookMoved && whiteQueenRookMoved)
									whiteCanCastle = false;
							}
						}
					}
					
					else if(!color) {
						if(blackCanCastle){
							String piece = movedPiece;
							if(piece.equals("K")){
								blackKingMoved = true;
								blackCanCastle = false;
							}
							else if(piece.equals("R")) {
								String from = String.valueOf(previousSquare.charAt(0));
								if(from.equals("h"))
									blackKingRookMoved = true;
								else if(from.equals("a"))
									blackQueenRookMoved = true;
								if(blackKingRookMoved && blackQueenRookMoved)
									blackCanCastle = false;
							}
						}
					}
				
				
				
				
				// code for FEN related attributes ended
				
		}
	}
	
	//FEN related code
	
	private String getFEN() {
		int counter;
		
		String fen = "";
		
		for(int j=7; j >= 0; j--){
			counter = 0;
			for(int i=0; i <= 7; i++){
				if(square[i][j].isPiece()){
					if(counter != 0){
						fen += String.valueOf(counter);
						counter = 0;
					}
					String fName="";
					String orgName = square[i][j].getPieceName();
					if(orgName.charAt(0) == 'W'){
						fName = orgName.substring(1).toUpperCase();
					}
					else{
						fName = orgName.substring(1).toLowerCase();
					}
					fen += fName;
				}
				
				else {
					counter++;
				}
			}
			
			if(counter != 0){
					fen += String.valueOf(counter);
					//counter = 0;
				}
				
				if(j == 0){
					fen += " ";
				}
				else{
					fen += "/";
				}
		}
		
		fen += activeColor + " ";
		
		String castle = "";
		boolean np = true;
		
		if(!whiteKingMoved){
			if(!whiteKingRookMoved){
				np = false;
				castle += "K";
			}
			
			if(!whiteQueenRookMoved){
				if(np)
					np = false;
				castle += "Q";
			}
		}
		if(!blackKingMoved){
			if(!blackKingRookMoved){
				if(np)
					np = false;
				castle += "k";
			}
			if(!blackQueenRookMoved){
				if(np)
					np = false;
				castle += "q";
			}
		}
		
		if(np){
			fen += "- ";
		}
		else{
			fen += castle + " ";
		}
		
		fen += enPassantTarget + " ";
		fen += String.valueOf(halfmoveClock) + " ";
		fen += String.valueOf(fullmoveNumber);
		
		
		return fen;
			
	}

		/*
	the following method is for engine.
	*/
	
	synchronized public String getUserMove() {
		if(engineMoveFirst){
			engineMoveFirst = false;
			userMoved = false;
			return null;
		}
		while(!userMoved){
			try{
				wait();
			}catch(Exception e) {}
		}
		String str = "position fen " + fen + " \n"; //" moves " + move + " \n";
		userMoved = false;
		System.out.println("FEN = " + fen);
		arduinoNotifier.notifyUserMoved();
		return str;
	}
	
	
	public void makeMove() {
		String move = ec.getEngineMove();
		arduinoNotifier.notifyEngineMoved();
		move = move.trim();
		char pc = move.charAt(0);
		char pr = move.charAt(1);
		char cc = move.charAt(2);
		char cr = move.charAt(3);
		
		int pci = Integer.parseInt(String.valueOf(pc - 'a'));
		int pri = Integer.parseInt(String.valueOf(pr)) - 1;
		int cci = Integer.parseInt(String.valueOf(cc - 'a'));
		int cri = Integer.parseInt(String.valueOf(cr)) - 1;
		
		Square prev = square[pci][pri];
		Square cur = square[cci][cri];
		
		String prevIconName;
		boolean prevColor = prev.getColor();
		boolean curColor = cur.getColor();
		if(prevColor){
			prevIconName = "icons/whiteSquare.gif";
		}
		else {
			prevIconName = "icons/blackSquare.gif";
		}
		prev.setIcon(new ImageIcon(prevIconName));
		prev.setIsPiece(false);
		
		String curIconName = "icons/" + prev.getPieceName();
		if(curColor) {
			curIconName += "W.PNG";
		}
		else{
			curIconName += "B.PNG";
		}
		
		cur.setIcon(new ImageIcon(curIconName));
		cur.setIsPiece(true);
		cur.setPieceName(prev.getPieceName());
		
		System.out.println(cur.getPieceName());
		
		if(!engineCastled){
			if(prev.getPieceName().equals("WK") && prev.getSquareName().equals("e1")){
				engineCastled = true;
				whiteKingMoved = true;
				if(cur.getSquareName().equals("g1")){
					square[5][0].setIcon(square[7][0].getIcon());
					square[5][0].setPieceName(square[7][0].getPieceName());
					square[5][0].setIsPiece(true);
					square[5][0].setIsMyPiece(true);
					square[7][0].setIcon(new ImageIcon("icons/whiteSquare.gif"));
					square[7][0].setPieceName(null);
					square[7][0].setIsPiece(false);
					square[7][0].setIsMyPiece(false);

					whiteKingRookMoved = true;
					
				}
				else if(cur.getSquareName().equals("c1")){
					square[3][0].setIcon(square[7][0].getIcon());
					square[3][0].setPieceName(square[0][0].getPieceName());
					square[3][0].setIsPiece(true);
					square[3][0].setIsMyPiece(true);
					square[0][0].setIcon(new ImageIcon("icons/blackSquare.gif"));
					square[0][0].setPieceName(null);
					square[0][0].setIsPiece(false);
					square[0][0].setIsMyPiece(false);
			
					whiteQueenRookMoved = true;
				}
			}
			else if(prev.getPieceName().equals("BK") && prev.getSquareName().equals("e8")){
				blackKingMoved = true;
				engineCastled = true;
				if(cur.getSquareName().equals("g8")){
					square[5][7].setIcon(square[7][7].getIcon());
					square[5][7].setPieceName(square[7][7].getPieceName());
					square[5][7].setIsPiece(true);
					square[5][7].setIsMyPiece(true);
					square[7][7].setIcon(new ImageIcon("icons/blackSquare.gif"));
					square[7][7].setPieceName(null);
					square[7][7].setIsPiece(false);
					square[7][7].setIsMyPiece(false);
					
					blackKingRookMoved = true;
					
				}
				else if(cur.getSquareName().equals("c8")){
					square[3][7].setIcon(square[7][7].getIcon());
					square[3][7].setPieceName(square[0][7].getPieceName());
					square[3][7].setIsPiece(true);
					square[3][7].setIsMyPiece(true);
					square[0][7].setIcon(new ImageIcon("icons/whiteSquare.gif"));
					square[0][7].setPieceName(null);
					square[0][7].setIsPiece(false);
					square[0][7].setIsMyPiece(false);
					
					blackQueenRookMoved = true;
				}
			}
				}
		
		/*
		Important:
		set all the attributes of the previously selected Square object and currently 
		selected Square object accordingly and correctly.
		*/
		prev.setPieceName(null);
		prev.setIsMyPiece(false);
		cur.setIsMyPiece(false);
	
	
		prev = null;
		cur = null;

	}
	
	
	synchronized public void engineDataSetter() {
		/*
		code for Engine.
		Must be done previous FEN related codes
		Also see capture part.
		*/
		
		fen = getFEN();
		move = previousSquare + currentSquare;
		
		
		userMoved = true;
		notify();	
		
		// Engine-code ended.
	}

	
	
}