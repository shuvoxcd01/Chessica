------------------------------------------------------------------------------------------------------------
*How to compile:
  1.Open command prompt 
  2.Make sure the current directory is "......\Java Project 3.0"
  3. On the command prompt write: javac chessica/Chessica.java

[NB: The source codes are already compiled so you may not do this.]
------------------------------------------------------------------------------------------------------------
*How to run: 
  1.Open command prompt 
  2.Make sure the current directory is "......\Java Project 3.0"
  3. On the command prompt write: java chessica.Chessica

		OR
	---------------------
*** You might just double click on "Chessica.jar"
------------------------------------------------------------------------------------------------------------
*How to play/Functionalities:
  Since this is a Graphical User Interface project, this should not be hard for you to find how to start playing (just look at the menus 
  on the menubar). The only thing you need to know is how to play chess.

  ## For the "enable arduino" JCheckBoxMenuItem you have to have a arduino connected to the computer via usb cable. (It is assumed and 
     normally is so that the arduino would connect on "COM Port 3".) 
     The code for the arduino is in ".....\Java Project 3.0\chessica\arduino\code for Arduino\SideToMoveIndicator" .

  ## To play against a friend over the network (as white / black) Opponent's IP has to be set first. Otherwise, the program
     will terminate. And of course, this tedious work has to be performed on both of the computers manually (where one computer will 
     play as white and other will play as black).

[***For the examiner: If you want to test the network functionality on the same computer, please do the followings:
	1. Copy the whole project somewhere else.
	2. Go to the source code "NetConn.java" (in chessica.board package) and you should find a comment line "// Network Information".
	3. Below that line you will find the lines:
			private static int opponentPort = 998;
			private static int mySendingPort = 999;
			private static int myReceivingPort = 998;
	4. In both the sources (the original one and the copied one) you should configure these ports properly.
	5. Recompile both the sources. Remember, you must delete necessary .class files otherwise the program will actually not recompile.
	   To be sure delete all .class files from all the packages and subpackages starting from "chessica" package.
	6. Launch two instances of Chessica of the two (original and copied) sources. Remember, do not just double click on "Chessica.jar".
	   Recompiling the sources has nothing to do with the .jar file(s).
	7. In the "Opponent's IP" text box of both the instances type your ip and press enter . Remember, use your local IPv4.
	8. In one instance select "Play->Against a friend->as white" and in another instance select "Play->Against a friend->as black".
	   And now you should be able to test the network functionality on the same computer.
	]
------------------------------------------------------------------------------------------------------------
*Bugs:
  Known bugs are:
	1. User moves are not checked if it is correct or not. (Of course, engines will always move according to the rules.).
	2. The "Pawn Promotion Rule" is not added. [See: https://en.wikipedia.org/wiki/Promotion_(chess)]
	3. The "en passant rule" is not added. [See: https://en.wikipedia.org/wiki/En_passant]

[NB: There are possibilities for uncaught bugs]
------------------------------------------------------------------------------------------------------------
**Project documentation:
	See: docs folder

------------------------------------------------------------------------------------------------------------
************************************************************************************************************

***********************************************************************************************************
------------------------------------------------------------------------------------------------------------


