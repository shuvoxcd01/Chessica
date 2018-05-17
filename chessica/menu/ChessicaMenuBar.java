package chessica.menu;

import javax.swing.*;
import java.awt.event.*;
import chessica.board.*;
import chessica.*;
import javax.swing.event.*;
import java.awt.*;
import chessica.arduino.*;

/**
@author Falguni Das Shuvo
*/

public class ChessicaMenuBar extends JMenuBar {
	private Chessica frame;
	
	public ChessicaMenuBar(Chessica frame) {
		this.frame = frame;
		JMenu playMenu = new JMenu("Play"); // Main menu
		JMenu againstComputer = new JMenu("Against Computer");  // sub-menu of playMenu
		JMenu againstFriend = new JMenu("Against a Friend");  // sub-menu of playMenu
		JMenuItem playComputerAsWhite = new JMenuItem("as white");
		JMenuItem playComputerAsBlack = new JMenuItem("as black");
		JMenuItem playFriendAsWhite = new JMenuItem("as white");
		JMenuItem playFriendAsBlack = new JMenuItem("as black");
		
		//
		againstComputer.add(playComputerAsWhite);
		againstComputer.add(playComputerAsBlack);
		againstFriend.add(playFriendAsWhite);
		againstFriend.add(playFriendAsBlack);
		
		playMenu.add(againstComputer);
		playMenu.add(againstFriend);
		
		this.add(playMenu);
		
		playComputerAsWhite.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GameType.config(1,true);
				frame.remove(frame.getBoard());
				ChessBoard cb = new ChessBoard(true);
				frame.setBoard(cb);
				frame.add(cb);
				frame.repaint();
				frame.setVisible(true);
			}
		});
		
		playComputerAsBlack.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GameType.config(1,false);
				
				frame.remove(frame.getBoard());
				ChessBoard cb = new ChessBoard(false);
				frame.setBoard(cb);
				frame.add(cb);
				frame.repaint();
				frame.setVisible(true);
			}
		});
		
		playFriendAsWhite.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GameType.config(2,true);
				
				frame.remove(frame.getBoard());
				ChessBoard cb = new ChessBoard(true);
				frame.setBoard(cb);
				frame.add(cb);
				frame.repaint();
				frame.setVisible(true);
			}
		});
		
		playFriendAsBlack.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GameType.config(2,false);
				
				frame.remove(frame.getBoard());
				ChessBoard cb = new ChessBoard(false);
				frame.setBoard(cb);
				frame.add(cb);
				frame.repaint();
				frame.setVisible(true);
			}
		});
		
		
		JMenu netConfig = new JMenu("Network Configuration"); // main menu
		JMenuItem setOpponentIp = new JMenuItem("Set Opponent's IP");
		netConfig.add(setOpponentIp);
		this.add(netConfig);
		setOpponentIp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JDialog jd = new JDialog(frame,"Network Config", true);
				jd.setLayout(new FlowLayout());
				JLabel label = new JLabel("Opponent's IP");
				jd.add(label);
					
				JTextField tf = new JTextField(20);
				tf.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){
						NetConn.config(tf.getText());
						jd.dispose();
					
					}
				});
				jd.add(tf);
				jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				jd.pack();
				jd.setVisible(true);
				System.out.println("done");
				
			}
			
		});
		
		
		JMenu engineConfig = new JMenu("Engine Configuration");
		JRadioButtonMenuItem stockfish = new JRadioButtonMenuItem("Stockfish",new ImageIcon("engines/stockfish.jpg"), true);
		JRadioButtonMenuItem  rybka = new JRadioButtonMenuItem("Rybka",new ImageIcon("engines/rybka.gif"),false);
		ButtonGroup engineButtonGroup = new ButtonGroup();
		
		engineConfig.add(stockfish);
		engineConfig.add(rybka);
		engineButtonGroup.add(stockfish);
		engineButtonGroup.add(rybka);
		
		this.add(engineConfig);
		
		stockfish.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				EngineCommunication.config("engines/stockfish 7 32bit.exe");
			}
		});
		
		rybka.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				EngineCommunication.config("engines/Rybkav2.3.2a.mp.w32.exe");
			}
		});
		
		JMenu arduino = new JMenu("Arduino");
		JCheckBoxMenuItem checkArduino = new JCheckBoxMenuItem("Enable Arduino", new ImageIcon("chessica/arduino/arduino.png"), false);
		arduino.add(checkArduino);
		this.add(arduino);
		
		checkArduino.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(checkArduino.isSelected()){
					ArduinoNotifier.arduinoConfig(false);
				}
				else{
					ArduinoNotifier.arduinoConfig(true);
				}
			}
		});
		

	}
}