/*
@author Falguni Das Shuvo

 */

int userToMove = 7;
int engineToMove = 8;
int flag;
 
void setup() {
  //Initialize serial and wait for port to open:
  pinMode(userToMove,OUTPUT);
  pinMode(engineToMove, OUTPUT);
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

}


void loop() {
  if(Serial.available() > 0){
    flag = Serial.read();
    if(flag == 0){
      digitalWrite(engineToMove,LOW);
      digitalWrite(userToMove,HIGH);
    }
    else if(flag == 1){
      digitalWrite(userToMove,LOW);
      digitalWrite(engineToMove,HIGH);
    }
    else{
       digitalWrite(engineToMove,HIGH);
       digitalWrite(userToMove,HIGH);
       delay(1000);
       digitalWrite(engineToMove,LOW);
       digitalWrite(userToMove,LOW);

       Serial.println(flag); 
    }

  }

}
