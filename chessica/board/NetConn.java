package chessica.board;

import chessica.board.square.*;
import javax.swing.*;
import chessica.arduino.*;
import java.net.*;

/**
@author Falguni Das Shuvo
*/

public class NetConn implements Runnable, ChessicaOpponentInterface {
	private Thread t;
	private Square square[][];
	private boolean color; // color of user
	
	// Network Information
	private static InetAddress opponentIP = null;
	private static int opponentPort = 998;
	private static int mySendingPort = 999;
	private static int myReceivingPort = 998;
	private static DatagramSocket dsSend;
	private static DatagramSocket dsReceive;
	
	private boolean userMoved;
	private boolean opponentMoved;
	
	private boolean castled = false;
	
	private ArduinoNotifier arduino;  // Arduino
	
	//
	String myMove;
	String opponentMove;
	byte []moveToSend;
	byte []moveReceived = new byte[1024];
	
	

	
	private boolean capture;
	
	public void move(String str, boolean capture) {
		myMove = str;
		moveToSend = str.getBytes();
		this.capture = capture;	
		send();
		
	}
	
	synchronized public void send(){
		try{
			while(!opponentMoved){
				wait();
			}
			
		}catch(Exception e){}
		try{
			dsSend.send(new DatagramPacket(moveToSend,myMove.length(),opponentIP,opponentPort));
		}catch(Exception e){System.out.println(e);}
		
		System.out.println("In send(): moveToSend = " + new String(moveToSend,0,moveToSend.length));
		opponentMoved = false;
		userMoved = true;
		notify();
		arduino.notifyUserMoved();  // user moved
	}
	
	synchronized public void receive() {
		try{
			while(!userMoved){
				wait();
			}			
		}catch(Exception e){}
		
		DatagramPacket p = new DatagramPacket(moveReceived, moveReceived.length);
		try{
			dsReceive.receive(p);
		}catch(Exception e) {System.out.println(e);}
		
		opponentMove = new String(p.getData(),0,p.getLength());
		System.out.println("In receive(): opponentMove = " + opponentMove);
		
		opponentMoved = true;
		userMoved = false;
		notify();
		arduino.notifyEngineMoved(); // here, engineMoved represents Opponent moved.
	}
	
	
	
	public NetConn(Square square[][], boolean color) {
		t = new Thread(this, "NetConn");
		this.square = square;
		//this.oldBoard = board;
		
		if(opponentIP == null){
			System.out.println("Information missing");
			System.exit(1);
		}
		
		try{
			dsSend = new DatagramSocket(mySendingPort);
			dsReceive = new DatagramSocket(myReceivingPort);
		}catch(Exception e){System.out.println(e);}
		
		
	
		this.color = color;
		
		if(color){
			userMoved = false;
			opponentMoved = true;
		}
		else {
			userMoved = true;
			opponentMoved = false;
		}

		
		arduino = ArduinoNotifier.getArduinoConn();
		//System.out.println("Arduino");
		
		t.start();
		
	}
	
	public static void config(String ip){
		try{
			opponentIP = InetAddress.getByName(ip);
		}catch(Exception e){System.out.println(e);}
		
		
	}
	
	
	public void run() {
		while(true)
			makeMove();
	}
	
	
	public void makeMove() {
		//String move = ec.getEngineMove();
		receive();
		char pc = opponentMove.charAt(1);
		char pr = opponentMove.charAt(2);
		char cc = opponentMove.charAt(3);
		char cr = opponentMove.charAt(4);
		
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
		
		if(!castled){
					if(prev.getPieceName().equals("WK") && prev.getSquareName().equals("e1")){
						if(cur.getSquareName().equals("g1")){
							square[5][0].setIcon(square[7][0].getIcon());
							square[5][0].setPieceName(square[7][0].getPieceName());
							square[5][0].setIsPiece(true);
							square[5][0].setIsMyPiece(true);
							square[7][0].setIcon(new ImageIcon("icons/whiteSquare.gif"));
							square[7][0].setPieceName(null);
							square[7][0].setIsPiece(false);
							square[7][0].setIsMyPiece(false);
							castled = true;
							
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
							castled = true;
						}
					}
					else if(prev.getPieceName().equals("BK") && prev.getSquareName().equals("e8")){
						if(cur.getSquareName().equals("g8")){
							square[5][7].setIcon(square[7][7].getIcon());
							square[5][7].setPieceName(square[7][7].getPieceName());
							square[5][7].setIsPiece(true);
							square[5][7].setIsMyPiece(true);
							square[7][7].setIcon(new ImageIcon("icons/blackSquare.gif"));
							square[7][7].setPieceName(null);
							square[7][7].setIsPiece(false);
							square[7][7].setIsMyPiece(false);
							castled = true;
							
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
							castled = true;
						}
					}
				}
		
		System.out.println("here" + cur.getPieceName());
		
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
				
}