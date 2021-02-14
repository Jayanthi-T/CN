import java.util.*;
import java.io.*;
import java.net.InetAddress;

public class PingAndTracert {

public void pingcmd() throws Exception
{
String ipAddress;
Scanner sc = new Scanner(System.in);

System.out.println("Ping Command");
System.out.println("Enter the IP Address");
ipAddress=sc.next();
InetAddress address = InetAddress.getByName(ipAddress);
System.out.println("Sending Ping Request to " + ipAddress);
if(address.isReachable(5000))
{
Process p = Runtime.getRuntime().exec("ping " + ipAddress);
BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

       String s = "";
       while ((s = inputStream.readLine()) != null)
            System.out.println(s);
System.out.println("Host is Reachable...");
}
else
System.out.println("\nTimeout!!!Host is not Reachable....");
}

public void traceroute() throws Exception
{
Scanner sc = new Scanner(System.in);

System.out.println("\nTraceRoute Command");
    System.out.println("Enter the IP");
    String ip=sc.next();
   
    Runtime run   =   Runtime.getRuntime();
    Process p   =   run.exec("tracert "+ip);

   BufferedReader in  =   new BufferedReader(new InputStreamReader(p.getInputStream()));

    String line;

    while((line=in.readLine())!=null){

        System.out.println(line);
    }
}
public static void main (String args[])throws Exception
{
PingAndTracert obj = new PingAndTracert();
obj.pingcmd();
obj.traceroute();
}

}
