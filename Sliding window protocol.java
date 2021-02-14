/*


Note:

The sender sends the packets and waits for acknowledgement.

The receiver uses a random function to send / withhold acknowledgements when a packet is received.

When sender receives an acknowledgement, it calculates the number of free slots in the sending window and transmits the next frames.

The packet data is set to alphabets from ‘a’ to ‘e’ and program terminates after 15 frames are sent and acknowledged.

*/



// Sliding window protocol
// Sender.java

import java.io.*;
import java.net.*;
import java.util.*;

public class Slide_Sender
{
     Socket sender;
   
     ObjectOutputStream out;
     ObjectInputStream in;
   
     String pkt;
     char data='a';
    
     int SeqNum = 1, SWS = 5;
     int LAR = 0, LFS = 0;
     int NF;
   
     Slide_Sender()
     {
     }
   
     public void SendFrames()
     {
          if((SeqNum<=15)&&(SWS > (LFS - LAR)) )
          {
              try
              {
                   NF = SWS - (LFS - LAR);
                   for(int i=0;i<NF;i++)
                   {
                        pkt = String.valueOf(SeqNum);
                        pkt = pkt.concat(" ");
                        pkt = pkt.concat(String.valueOf(data));
                        out.writeObject(pkt);
                        LFS = SeqNum;
                        System.out.println("Sent  " + SeqNum + "  " + data);
                            
                        data++;
                        if(data=='f')
                             data='a';
                            
                        SeqNum++;
                        out.flush();
                   }
              }  
              catch(Exception e)
              {}
          }
     }
    
     public void run() throws IOException
     {
          sender = new Socket("localhost",1500);

          out = new ObjectOutputStream(sender.getOutputStream());
          in = new ObjectInputStream(sender.getInputStream());
       
          while(LAR<15)
          {       
              try
              {  
                   SendFrames();      
                  
                   String Ack = (String)in.readObject();
                   LAR = Integer.parseInt(Ack);
                   System.out.println("ack received : " + LAR);
              }       
              catch(Exception e)
              {
              }
          }
         
          in.close();
          out.close();
          sender.close();
          System.out.println("\nConnection Terminated.");  
     }
    
     public static void main(String as[]) throws IOException
     {
          Slide_Sender s = new Slide_Sender();
          s.run();
     }
}


// Sliding window protocol
// Receiver.java

import java.io.*;
import java.net.*;
import java.util.*;

public class Slide_Receiver
{
     ServerSocket reciever;
     Socket conc = null;
   
     ObjectOutputStream out;
     ObjectInputStream in;
   
     String ack, pkt, data="";
     int delay ;
    
     int SeqNum = 0, RWS = 5;
     int LFR = 0;
     int LAF = LFR+RWS;
  
     Random rand = new Random();
     
     Slide_Receiver()
     {
     }
    
     public void run() throws IOException, InterruptedException
     {
          reciever = new ServerSocket(1500,10);
          conc = reciever.accept();
           
          if(conc!=null)
              System.out.println("Connection established :");
                            
          out = new ObjectOutputStream(conc.getOutputStream());
          in = new ObjectInputStream(conc.getInputStream());
             
          while(LFR<15)
          {
              try
              {  
                   pkt = (String)in.readObject();
                   String []str = pkt.split("\\s");
                  
                   ack = str[0];
                   data = str[1];
                                                         
                   LFR = Integer.parseInt(ack);
                  
                   if((SeqNum<=LFR)||(SeqNum>LAF))
                   {
                        System.out.println("\nMsg received : "+data);
                        delay = rand.nextInt(5);
                       
                        if(delay<3 || LFR==15)
                        {  
                             out.writeObject(ack);
                             out.flush();
                             System.out.println("sending ack " +ack);
                             SeqNum++;
                        }
                        else
                             System.out.println("Not sending ack");
                   }
                   else
                   {
                        out.writeObject(LFR);
                        out.flush();
                        System.out.println("resending ack " +LFR);
                   }  
              }                 
              catch(Exception e)
              {  
              }
          }  
          in.close();
          out.close();
          reciever.close();
          System.out.println("\nConnection Terminated.");
     }
     public static void main(String args[]) throws IOException, InterruptedException
     {
          Slide_Receiver R = new Slide_Receiver();
          R.run();
     }
}





Output:

Sender Window:

>javac Slide_Sender.java
>java Slide_Sender
Sent  1  a
Sent  2  b
Sent  3  c
Sent  4  d
Sent  5  e
ack received : 3
Sent  6  a
Sent  7  b
Sent  8  c
ack received : 5
Sent  9  d
Sent  10  e
ack received : 7
Sent  11  a
Sent  12  b
ack received : 8
Sent  13  c
ack received : 10
Sent  14  d
Sent  15  e
ack received : 11
ack received : 12
ack received : 15

Connection Terminated.

>  




Receiver Window:

>javac Slide_Receiver.java
>java Slide_Receiver
Connection established :

Msg received : a
Not sending ack

Msg received : b
Not sending ack

Msg received : c
sending ack 3

Msg received : d
Not sending ack

Msg received : e
sending ack 5

Msg received : a
Not sending ack

Msg received : b
sending ack 7

Msg received : c
sending ack 8

Msg received : d
Not sending ack

Msg received : e
sending ack 10

Msg received : a
sending ack 11

Msg received : b
sending ack 12

Msg received : c
Not sending ack

Msg received : d
Not sending ack

Msg received : e
sending ack 15

Connection Terminated.
     >       
