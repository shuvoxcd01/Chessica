package chessica;

import chessica.board.*;
import javax.swing.*;
import chessica.menu.*;
import java.awt.event.*;
import animation.*;

/**
{@literal CHESSICA 3.0 BETA}
		
<pre>
	 <B>GROUP 12
		Members:</B> 
		<B><I>1. SHUVO, FALGUNI DAS  15-29789-2
		2. AHMED, SABBIR  14-28199-3
		3. TAHER, AZIZA	  14-27372-2
		4. RAHMAN, K. M. TABIBUR  15-29651-2
		</I></B>
		
<font color="#ff0066"> Special Thanks to <B><I> Tamosree Sikder </I></B> for providing us with the chess pieces and squares </font>
</pre> 

@author Falguni Das Shuvo
@version 3.0 Beta
*/

public class Chessica extends JFrame {
	private ChessBoard chessBoard;
	private static int edge = 900; // size will be edge x edge
	
	
	
	public Chessica(){
		super("CHESSICA 3.0 BETA");
		
		ImageIcon chessicaIcon = new ImageIcon("icons/chessica.jpg");
		
		setIconImage(chessicaIcon.getImage());
		
		chessBoard = new ChessBoard(true); 
		setJMenuBar(new ChessicaMenuBar(this));
		
		this.setSize(edge,edge);
		
		this.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mwe){
				int rotation = mwe.getWheelRotation();
				if(rotation < 0){  // mouse wheel moved clockwise
					edge += 10;
					setSize(edge,edge);
					setVisible(true);
				}
				else if(rotation > 0){
					edge -= 10;
					setSize(edge,edge);
					setVisible(true);
				}
			}
		});
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(chessBoard);
		
		this.setVisible(true);
	}
	
	public void setBoard(ChessBoard cb) {
		this.chessBoard = cb;
	}
	
	public ChessBoard getBoard() {
		return this.chessBoard;
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Chessica();
			}
		});
		
	
		new animation.Animation();
			
	}
}