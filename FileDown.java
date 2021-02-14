import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
public class FileDown extends Thread {
 
    private String fileDir;
 
    private int port;
 
    private boolean stop;
 
    public String getFileDir() {
        return fileDir;
    }
 
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
 
    public int getPort() {
        return port;
    }
 
    public void setPort(int port) {
        this.port = port;
    }
 
    public boolean isStop() {
        return stop;
    }
 
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    public static void main(String[] args) {
        FileDown fd = new FileDown();
        fd.setFileDir("C:\\Users\\Admin\\Desktop\\");
        fd.setPort(9005);
        fd.start();
    }
 
    
    @Override
    public void run() {
        Socket socket = null;
        try {
            ServerSocket ss = new ServerSocket(port);
            do {
                socket = ss.accept();
 
    
 System.out.println("Create a socket link");
                DataInputStream inputStream = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                

                int bufferSize = 8192;
                byte[] buf = new byte[bufferSize];
                long passedlen = 0;
                long len = 0;
 
 
                String file=fileDir + inputStream.readUTF();
                DataOutputStream fileOut = new DataOutputStream(
                        new BufferedOutputStream(new FileOutputStream(file)));
                len = inputStream.readLong();
 
 System.out.println("The length of the file is:" + len + "\n");
 System.out.println("Start receiving files!" + "\n");
 
                while (true) {
                    int read = 0;
                    if (inputStream != null) {
                        read = inputStream.read(buf);
                    }
                    passedlen += read;
                    if (read == -1) {
                        break;
                    }
 
 System.out.println("File Received" + (passedlen * 100 / len)
                            + "%\n");
                    fileOut.write(buf, 0, read);
                }
 System.out.println("Receive completed, file saved as" + file + "\n");
 
                fileOut.close();
            } while (!stop);
        } catch (Exception e) {
 System.out.println("Receive Message Error" + "\n");
            e.printStackTrace();
            return;
        }
    }
}
 
