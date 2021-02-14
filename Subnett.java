import java.util.Scanner;
import java.net.*;
import java.io.*;
public class subnett {
public static void main(String[] args) throws Exception
    {
Scanner sc = new Scanner(System.in);
System.out.print("Enter Class B (128.0.0.0-192.0.0.0) Ip address: ");
String ip = sc.nextLine();
String split_ip[] = ip.split("\\.");
System.out.println("Enter the total number of blocks : ");
int b = sc.nextInt();

int tot_ip[]=new int[b],i;
int j=0;
while(j<b)
{
System.out.println("\nEnter the needed IP's for block (max 65534) "+j+":");
tot_ip[j]=sc.nextInt();
int bits = (int)Math.ceil(Math.log(tot_ip[j])/Math.log(2));
int mask = 32-bits;
System.out.println("CIDR Representation :/"+ mask);
System.out.println("Default mask : 255.255.0.0");
int mask1 = 0xffffffff<<(32-mask);
int value = mask1;
byte[] bytes = new byte[]{    (byte)(value >>> 24),
                             (byte)(value >> 16 & 0xff),
                             (byte)(value >> 8 & 0xff),
                             (byte)(value & 0xff)
                             };

InetAddress netAddr = InetAddress.getByAddress(bytes);
System.out.println("Subnet Mask = " + netAddr.getHostAddress());
InetAddress ia = InetAddress.getByName(ip);
String []adr1 = ia.getHostAddress().split("\\.");
String []adr2 = netAddr.getHostAddress().split("\\.");
int []nadr = new int[4];

for(i=0;i<4;i++)
     nadr[i] = Integer.parseInt(adr1[i])&Integer.parseInt(adr2[i]);

String nkadr = nadr[0]+"."+nadr[1]+"."+nadr[2]+"."+nadr[3];
System.out.println("network address = " + nkadr);

int []hadr = new int[4];
for(i=0;i<4;i++)
     hadr[i] = Integer.parseInt(adr1[i])^nadr[i];

String hostadr = hadr[0]+"."+hadr[1]+"."+hadr[2]+"."+hadr[3];        
System.out.println("host address = " + hostadr);
             
j++;
}

System.out.println();
}
}
