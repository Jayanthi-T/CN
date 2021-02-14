import java.io.*;
import java.util.*;
import java.lang.*;
public class NAT {
public static void main(String args[])throws Exception
{
Random rand = new Random();
int a = rand.nextInt(255);
int b = rand.nextInt(255);
int c = rand.nextInt(255);
System.out.println("Ip Address of your Subnet is "+a+"."+b+"."+c+".0/15");
List<String> host_ip = new ArrayList<String>();
List<String> host_port = new ArrayList<String>();
System.out.println("Enter the number of System in your sub-network");
Scanner sc = new Scanner(System.in);
int count = sc.nextInt();
for (int i=0;i<count;i++)
{
String str =
String.valueOf(a)+"."+String.valueOf(b)+"."+String.valueOf(c)+"."+String.valueOf(i);
String t = String.valueOf(rand.nextInt(65353));
host_ip.add(str);
host_port.add(t);
}
String router_ip="";
for(int j=1;j<4;j++)
{
router_ip = router_ip+String.valueOf(rand.nextInt(255));
if(j<3)
router_ip = router_ip+".";
}
System.out.println("Your Router IP is:"+router_ip);
int again=1;
while(again==1)
{
System.out.println("Select your source(private IP address)");
FileWriter file1=new FileWriter("NAT_host.txt");
for (int i=0;i<count;i++)
{
file1.write(host_ip.get(i)+"\t"+host_port.get(i)+"\n");
System.out.println(i+1 +"--> "+host_ip.get(i)+","+host_port.get(i));
}
System.out.println("Enter the source IP");
int source_ip = sc.nextInt();
source_ip -=1;
String sourceIP=String.valueOf(host_ip.get(source_ip))+","+host_port.get(source_ip);
System.out.println("Your Source:"+sourceIP);
int flag=0;
int check=0;
String destination_ip = null;
while(flag<1)
{
System.out.println("Enter Your Destination IP Address:");
String dest_ip=sc.next();
String ip[]=dest_ip.split(".");
String word=".";
int k=0;
for(int i=0;i<ip.length;i++)
{
if(word.equals(ip[i]))
k++;
}
if(k==3)
{
flag=flag+1;
//int n;
for (int i=0,n=dest_ip.length();i<n;i++)
{
char ch=dest_ip.charAt(i);
if(Character.isDigit(ch))
check=check+1;
if((check==1 & i!=a)|(check==2 & i!=b)|(check==3 & i!=c)|(check==4))
flag=flag+1;
if(i<0|i>255)
flag=flag-1;
}
}
destination_ip=dest_ip;
flag=flag+1;
System.out.println("Enetr the port number of the destination : ");
int dest_port = sc.nextInt();
String alt_port=String.valueOf(rand.nextInt(65353));
File file2 = new File("NAT_table.txt");
FileWriter writer=new FileWriter("NAT_table.txt");
System.out.println("Message is sent from sourse to destination...");
System.out.println("Source : \n IP : "+router_ip+"\n port : "+alt_port+"\n");
writer.write(sourceIP+"\n"+router_ip+","+alt_port);
System.out.println("Destination : \n IP : " + destination_ip + "\n port : "+dest_port);
System.out.println("Waiting for Response.....");
Thread.sleep(1000);
System.out.println("Router Notofication: Reply arrived from Destination..\n Information
:\n Source : "+destination_ip+"\n Port : "+dest_port);
System.out.println("Destination : "+router_ip+"\n port : "+alt_port);
System.out.println("");
System.out.println("The corresponding private system's IP is : " );
writer.close();
String trans=" ";
List<String> x = new ArrayList<String>();
BufferedReader rd=new BufferedReader(new FileReader("NAT_table.txt"));
String line = rd.readLine();
x.add(line);
for(int i=0;i<x.size();i++)
 System.out.println("Source's Private Ip , Port Number : "+x.get(i));
writer.close();
System.out.println("Do you want to send another ? [Yes/No]");
String choice = sc.next();
if(choice.equals("Yes")|choice.equals("yes"))
again = 1;
else
again =0;
}
}
}
}
