package security.manager;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class AuthenticationServer extends Thread {
    private HashMap<String, String> database;
    private String selfIp;
    private int[] ports;

    private KDC kdc;

    public AuthenticationServer(HashMap<String, String> database, int[] ports, KDC kdc)throws Exception{
        //will use different port later
        System.out.println("AuthenticationServer currently only supports one port in queue");
        this.database = database;

        this.selfIp = Inet4Address.getLocalHost().getHostAddress();
        System.out.println("Server IP:"+selfIp);

        this.ports = ports;
        this.kdc = kdc;

    }
    public boolean validateUser(String user_Id){
        return true;
    }
    private void serve(Socket socket, PrintWriter sender, BufferedReader receiver){
        try {
            String msg = receiver.readLine();
            String[] message = msg.split("/");
            if(!validateUser(message[0])){
                sender.println("INVALID");
                System.out.println("Server rejected such connection");
            }
            else{
                SecretKey encryptionKey = CryptoMethods.StringToSKey(message[1]); // find user key
                SecretKey sessionKey = kdc.getSessionKey(message[0]); // get user session key
                String sessionKey_msg = CryptoMethods.SKeyToString(sessionKey); //
                //encrypt user session key with user key

                String msgToSent = CryptoMethods.encryption(sessionKey_msg, encryptionKey);;
                sender.println(msgToSent);
                System.out.println("Server send user session Key length:" + msgToSent.length());

            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            socket.close();
        }
        catch(Exception exp){
            System.out.println("unable to close socket");
        }
    }
    @Override
    public void run() {
//        while(true) {
        for (int port : ports) {
            try {
                ServerSocket socket;
                socket = new ServerSocket(port);
                while (true) {

                    PrintWriter sender;
                    BufferedReader receiver;

                    try {
                        System.out.println("waiting for connection");
                        Socket clientSocket = socket.accept();
                        System.out.println("connected accepted, IP: " + clientSocket.getInetAddress());
                        receiver = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        sender = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                        new Thread(() -> {
                            serve(clientSocket, sender, receiver);
                        }).start();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                this.interrupt();
            }
        }
        //}
    }


}
