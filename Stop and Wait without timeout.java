// Stop and Wait Protocol
// Sender

import java.io.*;
import java.net.*;

public class Sdr
{
     Socket sender;
   
     ObjectOutputStream out;
    ObjectInputStream in;
   
     String pkt, ack, data;
    
    int n, i = 0, seq = 0;
    
     Sdr()
     {
     }
   
     public void run() throws IOException
     {
        try
          {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           
              sender = new Socket("localhost",1500);
            seq = 0;

            out = new ObjectOutputStream(sender.getOutputStream());
            in = new ObjectInputStream(sender.getInputStream());
                       
            while(true)
              {
                System.out.print("\nEnter the data to send : ");
              data = br.readLine().trim();
                       
                   pkt = String.valueOf(seq);
                   pkt = pkt.concat(data);
                   out.writeObject(pkt);

                   out.flush();
                  
                   if(data.equals("Terminate"))
                        break;                       
                       
                   ack = (String)in.readObject();
                       
                   if(ack.equals(String.valueOf(seq)))
                   {
                        System.out.println("ack recerived. ");    
                        seq = (seq==0)?1:0;
                   }
                   else
                   {     
                        System.out.println("Time out resending data....\n\n");
                   }            
            }
          }
          catch(Exception e){}
       
          finally
          {
              in.close();
              out.close();
              sender.close();
              System.out.println("\nConnection Terminated.");
          }
    }
    
    public static void main(String args[]) throws IOException
     {
        Sdr s = new Sdr();
            s.run();
    }
}



// Stop and Wait Protocol
// Reciever

import java.io.*;
import java.net.*;

public class Rcr
{
    ServerSocket reciever;
    
    Socket conc = null;
   
     ObjectOutputStream out;
    ObjectInputStream in;
   
     String ack, pkt, data="";
    int i = 0, seq = 0;
  
     Rcr()
     {
     }
   
    
     public void run() throws IOException
     {
        try
          {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           
              reciever = new ServerSocket(1500,10);
              conc = reciever.accept();
           
              if(conc!=null)
                   System.out.println("Connection established :");
                            
              out = new ObjectOutputStream(conc.getOutputStream());
              in = new ObjectInputStream(conc.getInputStream());
             
              while(true)
              {
                   pkt = (String)in.readObject();
                   ack = pkt.substring(0,1);
                   data = pkt.substring(1);
                  
                   if(data.equals("Terminate"))
                        break;
                  
                   if(Integer.valueOf(ack) == seq)
                   {
                        System.out.println("\nMsg received : " + data);
                        out.writeObject(ack);
                        seq = (seq==0)?1:0;
                   }
                   else
                   {
                        System.out.println("\nMsg : "+ data + "(-> Duplicate data)");
                   }
              }                 
          }
          catch(Exception e)
          {  
          }
          finally
          {
              System.out.println("\nConnection Terminated.");
              in.close();
              out.close();
              reciever.close();
          }
    }
    
    public static void main(String args[]) throws IOException
     {
        Rcr r = new Rcr();
          r.run();
     }
}
Output:

Sender Window

>javac Sdr.java
>java Sdr
Enter the data to send : asd
ack recerived.
Enter the data to send : qwe
ack recerived.
Enter the data to send : zxc
ack recerived.
Enter the data to send : Terminate
Connection Terminated.
> 

Receiver window

>javac Rcr.java
>java Rcr
Connection established :
Msg received : asd
Msg received : qwe
Msg received : zxc
Connection Terminated.
>           
                           
             
