package chessica.board.square;

import javax.swing.*;
import chessica.board.*;

/**
@author Falguni Das Shuvo
*/

public class Square extends JToggleButton{
	private  String squareName;
	private  boolean color;
	private final int indexX;
	private final int indexY;
	private boolean isChessPiece;
	private ImageIcon icon;
	private String pieceName;
	private boolean isOwn;
	
	public Square(int x, int y) {
		this.icon = null;
		this.indexX = x;
		this.indexY = y;
		this.pieceName = null;
		this.isChessPiece = false;
		this.isOwn = false;
		setSquareName();
		setColor();
		
		if((indexX + indexY) % 2 == 0){
			icon = new ImageIcon("icons/blackSquare.gif");
			setIcon(icon);
		}
			
		else{
			icon = new ImageIcon("icons/whiteSquare.gif");
			setIcon(icon);
		}
		
	}
	
	public void setPieceInfo( ImageIcon ii, boolean isChessPiece, String pieceName, boolean own){
		
		this.icon = ii;
		this.setIcon(icon);
		this.isChessPiece = true;
		this.pieceName = pieceName;
		this.isOwn = own;
		
	}
	
	public void setIcon(ImageIcon ii){
		this.icon = ii;
		super.setIcon(icon);
	}
	
	public ImageIcon getIcon(){
		return this.icon;
	}
	
	private void setSquareName(){
		char cname = 'a';
		cname = (char) (cname + indexX);
		this.squareName = String.valueOf(cname) + String.valueOf(indexY+1);
	}
	
	private void setColor() {
		if(((indexX+indexY) % 2) == 0)
			this.color = false;
		else
			this.color = true;
	}
	
	public boolean getColor() {
		return this.color;
	}
	
	public String getPieceName() {
		return this.pieceName;
	}
	
	public void setPieceName(String name){
		this.pieceName = name;
	}
	
	

	public String getSquareName() {
		return this.squareName;
	}
	
	public boolean isPiece() {
		return isChessPiece;
	}
	
	public boolean isMyPiece() {
		return isOwn;
	}
	
	public void setIsMyPiece(boolean value){
		this.isOwn = value;
	}
	
	public void setIsPiece(boolean value) {
		this.isChessPiece = value;
	}
	
}