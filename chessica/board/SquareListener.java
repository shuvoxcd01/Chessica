package chessica.board;

import java.awt.event.*;
import javax.swing.*;
import chessica.board.square.*;

/**
@author Falguni Das Shuvo
*/

public class SquareListener implements ActionListener {
	private Square prevSelected = null;
	private Square square[][];
	private ChessicaOpponentInterface coi;
	private boolean castled = false;

	private boolean color;
		
	public SquareListener(Square square[][], boolean myColor) {
		this.square = square;
		this.color = myColor;
		coi = GameType.getConn(square);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		try{
		Square curSelected = (Square) e.getSource();
		if(prevSelected == null) {
			if(curSelected.isPiece()){
				if(!curSelected.isMyPiece()){
					this.prevSelected = null;
				}
				else {
					this.prevSelected = curSelected;
				}
			}
			else{
				this.prevSelected = null;
			}
		}
		else{
			if(curSelected.isPiece()) {
				if(!curSelected.isMyPiece()) {
					//capture
				
					String prevIconName;
					boolean prevColor = prevSelected.getColor();
					boolean curColor = curSelected.getColor();
					if(prevColor){
						prevIconName = "icons/whiteSquare.gif";
					}
					else {
						prevIconName = "icons/blackSquare.gif";
					}
					prevSelected.setIcon(new ImageIcon(prevIconName));
					prevSelected.setIsPiece(false);
					
					String curIconName = "icons/" + prevSelected.getPieceName();
					if(curColor) {
						curIconName += "W.PNG";
					}
					else{
						curIconName += "B.PNG";
					}
					
					curSelected.setIcon(new ImageIcon(curIconName));
					curSelected.setIsPiece(true);
					curSelected.setPieceName(prevSelected.getPieceName());
					
					moveGen(curSelected, prevSelected, true);
					
					
					/*
					Important:
					set all the attributes of the previously selected Square object and currently 
					selected Square object accordingly and correctly.
					*/
					prevSelected.setPieceName(null);
					prevSelected.setIsMyPiece(false);
					curSelected.setIsMyPiece(true);
					this.prevSelected = null;
				
				}
				else{
					this.prevSelected = null;
				}
			}
			else{
				//move
					
				String prevIconName;
				boolean prevColor = prevSelected.getColor();
				boolean curColor = curSelected.getColor();
				if(prevColor){
					prevIconName = "icons/whiteSquare.gif";
				}
				else {
					prevIconName = "icons/blackSquare.gif";
				}
				prevSelected.setIcon(new ImageIcon(prevIconName));
				prevSelected.setIsPiece(false);
				
				String curIconName = "icons/" + prevSelected.getPieceName();
				if(curColor) {
					curIconName += "W.PNG";
				}
				else{
					curIconName += "B.PNG";
				}
				
				curSelected.setIcon(new ImageIcon(curIconName));
				curSelected.setIsPiece(true);
				curSelected.setPieceName(prevSelected.getPieceName());
				
				if(!castled){
					if(prevSelected.getPieceName().equals("WK") && prevSelected.getSquareName().equals("e1")){
						if(curSelected.getSquareName().equals("g1")){
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
						else if(curSelected.getSquareName().equals("c1")){
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
					else if(prevSelected.getPieceName().equals("BK") && prevSelected.getSquareName().equals("e8")){
						if(curSelected.getSquareName().equals("g8")){
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
						else if(curSelected.getSquareName().equals("c8")){
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
				
				moveGen(curSelected, prevSelected, false);
				
				/*
				Important:
				set all the attributes of the previously selected Square object and currently 
				selected Square object accordingly and correctly.
				*/
				prevSelected.setPieceName(null);
				prevSelected.setIsMyPiece(false);
				curSelected.setIsMyPiece(true);
				
				this.prevSelected = null;

			}
			
		}
		
		} catch (Exception ex) {
			System.out.println("Exception caught: " + ex);
		}
		
	}
	
	private void moveGen(Square cur, Square prev, boolean capture) {
		String mv = String.valueOf(cur.getPieceName().charAt(1))  + prev.getSquareName() + cur.getSquareName();
		coi.move(mv, capture);
	}
	
	
	
	
}
