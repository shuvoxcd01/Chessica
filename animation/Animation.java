/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

/**
 *
 * @author Gaddafi
 */

 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;


public class Animation extends JFrame {

    JLabel lblText;
    JLabel lblText2;
    JLabel lblText3;
    JLabel lblText4;
    JLabel lblText5;
    JLabel lblText6;
    JButton PLAY;
    
   public  Animation(){
      setTitle("Chessica");
      setLayout(null);
     lblText=new JLabel("<html><p style='color: #bf00ff; font-size:100pt'>Chessica</p></html>");
     lblText.setSize(500,100);
     add(lblText);
     
     
      lblText2=new JLabel("<html><p style='color: #cc3300; font-size:30pt'>BY</p></html>");
      lblText2.setSize(250,50);
      lblText2.setLocation(700,40);
      add(lblText2);
      
      lblText3=new JLabel("<html><p style='color: #31e628; font-size:25pt'>1.SHUVO,FALGUNI DAS</p></html>");
      lblText3.setSize(300,100);
      lblText3.setLocation(570,70);
      add(lblText3);
      
      lblText4=new JLabel("<html><p style='color: #31e628; font-size:25pt'>2.AHMED,SABBIR</p></html>");
      lblText4.setSize(300,200);
      lblText4.setLocation(570,75);
      add(lblText4);
      
      lblText5=new JLabel("<html><p style='color: #31e628; font-size:25pt'>3.TAHER,AZIZA</p></html>");
      lblText5.setSize(300,300);
      lblText5.setLocation(570,80);
      add(lblText5);
      
      lblText6=new JLabel("<html><p style='color: #31e628; font-size:25pt'>4.RAHMAN,K.M.TABIBUR</p></html>");
      lblText6.setSize(300,400);
      lblText6.setLocation(570,85);
      add(lblText6);
      
      PLAY = new JButton("PLAY");
      PLAY.setBounds(680, 330, 100, 50);
      //add(PLAY);
      
      
     getContentPane().setBackground(Color.DARK_GRAY);
     setSize(900,500);
     setResizable(false);
     setDefaultCloseOperation(DISPOSE_ON_CLOSE);
     setVisible(true);
     
    doAnimation();
 }

  public void doAnimation(){
          int x=50;
          int y=50;
          try{
              while(x == 50){
                     lblText.setLocation(x,y);
                    Thread.sleep(20);
                    x+=1;
                    y+=2;
                    if(x>50) x=50;
                    if(y>500) y=50;
 
                    } 
   
           }
          
             catch(InterruptedException ie){System.out.println("Interrupted...");
           
           }
    }

}


