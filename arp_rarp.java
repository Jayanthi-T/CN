// Socket Programming
// ARP and RARP Protocol
// arp_rarp.java

/*
This program automatically creates a file with the IP address of machines, their MAC address and type.

ARP protocol is simulated by reading an IP address and returning the MAC address. RARP protocol is simulated by reading an MAC address and returning the IP address

The program can be extended to read an IP Address / Mac Address and a message and send a packet to the specified machine using TCP / IP or Datagram sockets
*/




import java.io.*;
import java.util.*;

public class arp_rarp
{
     private static final String Command = "arp -a";
    
     public static void getARPTable(String cmd) throws Exception
     {  
          File fp = new File("ARPTable.txt");
          FileWriter fw = new FileWriter(fp);

          BufferedWriter bw = new BufferedWriter(fw);
         
          Process P = Runtime.getRuntime().exec(cmd);
          Scanner S = new Scanner(P.getInputStream()).useDelimiter("\\A");
          
          while(S.hasNext())
              bw.write(S.next());

          bw.close();
          fw.close();
     }
    
    



     public static void findMAC(String ip) throws Exception
     {
          File fp = new File("ARPTable.txt");
          FileReader fr = new FileReader(fp);
          BufferedReader br = new BufferedReader(fr);
         
          String line;
         
          while ((line = br.readLine()) != null)
          {
               if (line.contains(ip))
              {
                   System.out.println("Internet Address      Physical Address      Type");
                   System.out.println(line);
                   break;
              }
          }
          if((line == null))
              System.out.println("Not found");
         
          fr.close(); 
          br.close();        
     }
    
    
    



     public static void findIP(String mac) throws Exception
     {
          File fp = new File("ARPTable.txt");
          FileReader fr = new FileReader(fp);
          BufferedReader br = new BufferedReader(fr);
         
          String line;
         
          while ((line = br.readLine()) != null)
          {
              if (line.contains(mac))
              {
                   System.out.println("Internet Address      Physical Address      Type");
                   System.out.println(line);
                   break;
              }
          }
         
          if((line == null))
              System.out.println("Not found");
         
          fr.close(); 
          br.close();        
     }
    




     public static void main(String as[]) throws Exception
     {
          getARPTable(Command);

          Scanner S = new Scanner(System.in);

          System.out.println("ARP Protocol.");
          System.out.print("Enter IP Address: ");
          String IP = S.nextLine();
          findMAC(IP);

          System.out.println("RARP Protocol.");
          System.out.print("Enter MAC Address: ");
          String MAC = S.nextLine();
          findIP(MAC);
     }
}











OUTPUT:

>javac arp_rarp.java
>java arp_rarp
ARP Protocol.
Enter IP Address: 10.0.15.253
Internet Address             Physical Address             Type
  10.0.15.253                   00-16-76-bd-41-27           dynamic

RARP Protocol.
Enter MAC Address: 01-00-5e-00-00-fc
Internet Address             Physical Address             Type
  224.0.0.252                   01-00-5e-00-00-fc            static

>java arp_rarp
ARP Protocol.
Enter IP Address: 10.0.15.121
Not found

RARP Protocol.
Enter MAC Address: 01-00-5e-00-00-ff
Not found

D:\cnlab>



ARPTable.txt

Interface: 10.0.15.202 --- 0x3
  Internet Address      Physical Address        Type
  10.0.15.1                    54-78-1a-1e-6a-4f        dynamic  
  10.0.15.72                  00-13-20-b7-49-c9       dynamic  
  10.0.15.253                00-16-76-bd-41-27      dynamic  
  10.0.15.255                ff-ff-ff-ff-ff-ff               static   
  224.0.0.22                  01-00-5e-00-00-16       static   
  224.0.0.251                01-00-5e-00-00-fb        static   
  224.0.0.252                01-00-5e-00-00-fc        static   
  239.255.255.250        01-00-5e-7f-ff-fa          static   
