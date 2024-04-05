package security.manager;

public class Main {
    public static void main(String[] args) throws Exception{
        KDC kdc = new KDC();

        AppClient client = new AppClient("1","192.168.56.1", 12345, kdc);

        AuthenticationServer server = new AuthenticationServer(null, new int[] {12345, 1}, kdc);
        server.start();
        Thread.sleep(4000);
        client.start();

        client.join();
        server.join();
    }
}