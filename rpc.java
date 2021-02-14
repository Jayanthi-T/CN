Client
import java.io.*;
import java.net.*;

class cli {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 3000);
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        System.out.println("Client ready, type and press Enter key");
        String receiveMessage, sendMessage, temp;
        while (true) {
            System.out.println("\nEnter operation to perform(add,sub,mul,div)....");
            temp = keyRead.readLine();
            sendMessage = temp.toLowerCase();
            pwrite.println(sendMessage);
            System.out.println("Enter first parameter :");
            sendMessage = keyRead.readLine();
            pwrite.println(sendMessage);
            System.out.println("Enter second parameter : ");
            sendMessage = keyRead.readLine();
            pwrite.println(sendMessage);
            System.out.flush();
            if ((receiveMessage = receiveRead.readLine()) != null) {
                System.out.println(receiveMessage);
            }
        }
    }
}

Server
import java.io.*;
import java.net.*;
class ser {

    public static void main(String[] args) throws Exception {
        ServerSocket sersock = new ServerSocket(3000);
        System.out.println("Server ready");
        Socket sock = sersock.accept();
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        String receiveMessage, sendMessage, fun;
        int a, b, c;
        while (true) {
            fun = receiveRead.readLine();
            if (fun != null) {
                System.out.println("Operation : " + fun);
            }
            a = Integer.parseInt(receiveRead.readLine());
            System.out.println("Parameter 1 : " + a);
            b = Integer.parseInt(receiveRead.readLine());
            if (fun.compareTo("add") == 0) {
                c = a + b;
                System.out.println("Addition = " + c);
                pwrite.println("Addition = " + c);
            }
            if (fun.compareTo("sub") == 0) {
                c = a - b;
                System.out.println("Substraction = " + c);
                pwrite.println("Substraction = " + c);
            }
            if (fun.compareTo("mul") == 0) {
                c = a * b;
                System.out.println("Multiplication = " + c);
                pwrite.println("Multiplication = " + c);
            }
            if (fun.compareTo("div") == 0) {
                c = a / b;
                System.out.println("Division = " + c);
                pwrite.println("Division = " + c);
            }
            System.out.flush();
        }
    }

}
