package chessica.board;

//import chessica.board.square.*;
import java.awt.*;
import javax.swing.*;
import chessica.board.square.*;

/**
@author Falguni Das Shuvo
*/

public class ChessBoard extends JPanel{
	
	private Square square[][];
	
	
	public ChessBoard(boolean color) {
		square = new Square[8][8];	
		ButtonGroup chessBoardButtonGroup = new ButtonGroup();
		//this.setPreferredSize(new Dimension(900,900));  // commented out becaues it doesn't work
		
		this.setLayout(new GridLayout(8,8));
		
	
		
		SquareListener listener = new SquareListener(square,color); // there must be only one istance of this listener per board
		
		
		
		if(color){
			
			for(int j=7; j >= 0; j--)
				for(int i=0; i <= 7; i++ ){
					square[i][j] = new Square(i,j);
					square[i][j].addActionListener(listener);
					chessBoardButtonGroup.add(square[i][j]);
					add(square[i][j]);
				}
		}
		
		else {
			for(int j=0; j <= 7; j++)
				for(int i=7; i >= 0; i-- ){
					square[i][j] = new Square(i,j);
					square[i][j].addActionListener(listener);
					chessBoardButtonGroup.add(square[i][j]);
					add(square[i][j]);
				}
		}
				
			square[7][0].setPieceInfo(new ImageIcon("icons/WRW.PNG"), true,"WR",color);
			square[6][0].setPieceInfo(new ImageIcon("icons/WNB.PNG"), true,"WN", color);
			square[5][0].setPieceInfo(new ImageIcon("icons/WBW.PNG"), true,"WB", color);
			square[4][0].setPieceInfo(new ImageIcon("icons/WKB.PNG"), true,"WK", color);
			square[3][0].setPieceInfo(new ImageIcon("icons/WQW.PNG"), true,"WQ", color);
			square[2][0].setPieceInfo(new ImageIcon("icons/WBB.PNG"), true,"WB", color);
			square[1][0].setPieceInfo(new ImageIcon("icons/WNW.PNG"), true,"WN", color);
			square[0][0].setPieceInfo(new ImageIcon("icons/WRB.PNG"), true,"WR", color);
				
			
			for(int j=7; j >= 1; j-=2){
				square[j][1].setPieceInfo (new ImageIcon("icons/WPB.PNG"), true,"WP", color);
				square[j-1][1].setPieceInfo (new ImageIcon("icons/WPW.PNG"), true,"WP", color);
			}
			
			
	
			
			for(int j=7; j >= 1; j-=2){
				square[j][6].setPieceInfo (new ImageIcon("icons/BPW.PNG"), true,"BP", !color);
				square[j-1][6].setPieceInfo (new ImageIcon("icons/BPB.PNG"), true,"BP", !color);
			}
			
			square[7][7].setPieceInfo(new ImageIcon("icons/BRB.PNG"), true,"BR", !color);
			square[6][7].setPieceInfo(new ImageIcon("icons/BNW.PNG"), true,"BN", !color);
			square[5][7].setPieceInfo(new ImageIcon("icons/BBB.PNG"), true,"BB", !color);
			square[4][7].setPieceInfo(new ImageIcon("icons/BKW.PNG"), true,"BK", !color);
			square[3][7].setPieceInfo(new ImageIcon("icons/BQB.PNG"), true,"BQ", !color);
			square[2][7].setPieceInfo(new ImageIcon("icons/BBW.PNG"), true,"BB", !color);
			square[1][7].setPieceInfo(new ImageIcon("icons/BNB.PNG"), true,"BN", !color);
			square[0][7].setPieceInfo(new ImageIcon("icons/BRW.PNG"), true,"BR", !color);
			
	}
	
	
}