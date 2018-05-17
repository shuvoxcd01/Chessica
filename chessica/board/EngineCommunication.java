package chessica.board;

import java.io.*;
import java.util.*;

/**
@author Falguni Das Shuvo
*/

public class EngineCommunication implements Runnable {
	UEConn sl;
	private Process p;
	private Runtime r;
	private BufferedReader eIn;
	private PrintStream eOut;
	private Thread t;
	private static boolean engineMoved;
	private static String engineMove;
	private String str;
	private static String dir = null;
	
	
	public EngineCommunication(UEConn sl) {
		this.sl = sl;
		t = new Thread(this, "EngineCommunication");
		engineMoved = false;
		if(dir == null)
			dir = System.getProperty("user.dir") + "\\engines\\" + "stockfish 7 32bit.exe";
		t.start();
	}
	
	public static void config(String engineDirectory){
		dir = engineDirectory;
	}
	
	public void run() {
		r = Runtime.getRuntime();
		
		try{	
			p = r.exec(dir);
			eOut = new PrintStream(p.getOutputStream(),true);
			eIn = new BufferedReader(new InputStreamReader(p.getInputStream()));
			eOut.print("isready\n");
			try{
				Thread.sleep(500);
			}catch(Exception e) {}
			
			while(eIn.ready()) {
					str = eIn.readLine();
					System.out.println(str);
				}
				
			str = "";	
			
			
			while(true){
				com();
				try{
					Thread.sleep(500);
				}catch(Exception e) {}

				
				System.gc();
			}
			
			//p.waitFor();
	
			
		} catch(Exception e){
			System.out.println(e);
		}
	}
	
	synchronized void com() throws Exception {		
	
		str = sl.getUserMove();
		if(str != null)
			eOut.print(str);
		eOut.print("go\n");
		
		try {
			Thread.sleep(500);
		}catch (Exception e){
			System.out.println(e);
		}
		eOut.print("stop\n");
		str = "";
		String sc;
		
		try {
			Thread.sleep(500);
		}catch (Exception e){
			System.out.println(e);
		}
		
		while(eIn.ready()) {
			sc = eIn.readLine();
			if(sc == null)
				break;
			str += sc;
		}
		
		if(str.isEmpty()){
			System.out.println("str is empty");
			System.exit(0);
		}
		
		//System.out.println(str);
		
		
		String temp[] = str.split("bestmove");
		
		str = temp[1].trim();
		String temp2[] = str.split(" ");
	
		this.engineMove = temp2[0].trim();
		engineMoved = true;
		notify();
		System.out.println("Engine Move: " + engineMove);
		
	}
	

	synchronized public String getEngineMove() {
		try{
			while(!engineMoved)
				wait();
		}catch(Exception e) {}
		
		engineMoved = false;
		return engineMove;
	}
}