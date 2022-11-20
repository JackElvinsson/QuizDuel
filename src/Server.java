import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    public boolean finish;
    public ServerSocket server;
    public ExecutorService treadpool;
    public ArrayList<ConnectionHandler> connect;


    public Server(){
        connect = new ArrayList<>();
        finish = false;
    }

    @Override
    public void run() {
        try {

            server = new ServerSocket(9999);
            treadpool = Executors.newCachedThreadPool();
            while(!finish ) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connect.add(handler);
                treadpool.execute(handler);
            }

        } catch (IOException e) {
            e.printStackTrace(System.out);

        }}

    class ConnectionHandler implements Runnable {

        private Socket client;
        public String playerName;

        private BufferedReader in;
        private PrintWriter out;

        public ConnectionHandler(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Välkommen till Quizkampen \n" +
                        "Välj ett namn:");
                playerName = in.readLine();
                System.out.println(playerName + " gick med i Quizkamp matchen");

            } catch (Exception e) {
                shutdown();
            }
        }

        public void shutdown () {
            try{
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e){
                e.printStackTrace(System.out);
            }

        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}






