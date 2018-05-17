package chessica.arduino;

import com.fazecast.jSerialComm.*;
import java.io.*;

/**
@author Falguni Das Shuvo
*/

public final class ArduinoNotifier {
	private static PrintStream out;
	private static SerialPort arduino;
	private static boolean haltConnection = true;
	private static ArduinoNotifier singleton = null;
	
	private ArduinoNotifier(){
		
	}
	
	private static void initiate() {
		arduino = SerialPort.getCommPort("COM3");
		System.out.println("Initiated1");
		if(!arduino.isOpen())
			arduino.openPort();
		System.out.println("Initiated2");
		arduino.setBaudRate(9600);
		System.out.println("Initiated3");
		out = new PrintStream(arduino.getOutputStream(),true);
		System.out.println("Initiated4");
	}
	
	public static ArduinoNotifier getArduinoConn() {
		if(singleton == null)
			singleton = new ArduinoNotifier();
		
		return singleton;
	}
	
	public static void arduinoConfig(boolean h){
		haltConnection = h;
		try{
			if(!haltConnection)
				initiate();
		}catch(Exception e) {System.out.println(e);}
	}
	
	public void notifyEngineMoved() {
		try{
			if(haltConnection)
			return;
			//out.print((int)0);
			out.write(0);
			System.out.println("EngineMoved");
		}catch(Exception e) {System.out.println(e);}
		

	}
	
	public void notifyUserMoved() {
		try{
			if(haltConnection)
				return;
			//out.print((int)1);
			out.write(1);
			System.out.println("UserMoved");
		}catch(Exception e) {System.out.println(e);}
	}
	
	public void finalize() {
		out.close();
		arduino.closePort();
	}
}
