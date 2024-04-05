package security.manager;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

// need to do the rehandshake
// need to sync KDC with database

public class AppClient extends Thread {
    String selfIP;
    String destinationIP;
    String userId;
    private SecretKey decryptionKey;
    private String TGS_Key;
    private String  SS_Key;
    private int port;
    private KDC kdc;
    private static final int KEYLENGTH = 16;

    public AppClient(String userId, String destinationIP, int port, KDC kdc) throws NoSuchAlgorithmException {
        this.destinationIP = destinationIP;
        this.kdc = kdc;
        this.decryptionKey = kdc.getKey(userId);
        this.port = port;
        this.userId = userId;

    }


    private String authentication(PrintWriter sender, BufferedReader receiver){
        String userKey = CryptoMethods.SKeyToString(this.decryptionKey);
        String message = userId +"/" +userKey;
        sender.println(message);
        try{
            String msg = null;
            while(msg == null) {
                msg = receiver.readLine();
            }
            if(msg.equals("INVALID")){
                return "INVALID";
            }
            System.out.println("decrypting sessionKey");
            System.out.println(msg.length());
            String sessionKeyMsg = CryptoMethods.decryption(msg, decryptionKey);
            System.out.println("return sessionKey");
            return sessionKeyMsg;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            this.interrupt();
        }

        return "INVALID";
    }

    private boolean TGS(){
        return false;
    }

    private boolean ServiceApplication(){
        return false;
    }

    @Override
    public void run(){

        PrintWriter sender;
        BufferedReader receiver;

        try {
            InetAddress host = InetAddress.getLocalHost();
            System.out.println("client address is "+host.getHostAddress());
            Socket socket = new Socket(host.getHostName(), port);
            System.out.println(socket.getInetAddress());
            while(!socket.isConnected()){
                System.out.println("socket not connected");
                try {
                    Thread.sleep(3000);
                }
                catch(InterruptedException ex){

                }
            }
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sessionKeyMsg = this.authentication(sender, receiver);
        if(sessionKeyMsg.equals("INVALID")){
            throw new RuntimeException("authentication failed");
        }
        SecretKey secretKey = CryptoMethods.StringToSKey(sessionKeyMsg);
        System.out.println("we have out sessionKey, we are basically done");

    }

}
