//import javax.swing.*;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
////public class kladd {
////
//////
//////    out = new PrintWriter(client.getOutputStream(), true);
//////    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//////                JOptionPane.showInputDialog(null, "Pick a username:");
//////    nickname = in.readLine().trim();
//////                gui1.textArea1.append(nickname + " CONNECTED");
//////    broadcast(nickname + " Joined the chat");
//////    String message;
//////}
//
//
//
//
//
//
//
//
//
//
//
//
//public class Server implements Runnable {
//
//    private ArrayList<ConnectionHandler> connections;
//    private ServerSocket server;
//    private boolean done;
//    private ExecutorService pool;
//
//    public Server(){
//        connections = new ArrayList<>();
//        done = false;
//    }
//
//    @Override
//    public void run() {
//        try {
//            server = new ServerSocket(9999);
//            pool = Executors.newCachedThreadPool();
//            while(!done ) {
//                Socket client = server.accept();
//                ConnectionHandler handler = new ConnectionHandler(client);
//                connections.add(handler);
//                pool.execute(handler);
//            }
//
//        } catch (IOException e) {
//        }
//    }
//    public void broadcast (String message){
//        for (ConnectionHandler ch:connections){
//            if(ch != null){
//                ch.sendMessage(message);
//            }
//        }
//    }
//
//    public void shutdown() {
//        try {
//            done = true;
//            if (!server.isClosed()) {
//                server.close();
//            }
//            for (ConnectionHandler ch: connections){
//                ch.shutdown();
//            }
//        }catch (IOException e) {
//
//        }
//    }
//
//    class ConnectionHandler implements Runnable {
//
//        private Socket client;
//        private BufferedReader in;
//        private PrintWriter out;
//        private String nickname;
//
//        public ConnectionHandler(Socket client){
//            this.client = client;
//        }
//        @Override
//        public void run() {
//            try {
//
//
//
//                out = new PrintWriter(client.getOutputStream(), true);
//                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
////                out.println("Enter nickname:");
//                nickname = in.readLine();
//
//
//
//
//
//
//
//
//
//
//
//
//                System.out.println(nickname + "connected");
//                broadcast(nickname + " joined the chat! ");
//                String message;
//                while ((message = in.readLine()) != null) {
//                    if (message.startsWith("/nick")) {
//                        String[] messageSplit = message.split("", 2);
//                        if (messageSplit.length == 2) {
//                            broadcast(nickname + "renamed themselves to" + messageSplit[1]);
//                            System.out.println(nickname + "renamed themselves to" + messageSplit[1]);
//                            nickname = messageSplit[1];
//                            out.println("Successfully changed nickname to" + nickname);
//                        } else {
//                            out.println("No nickname provided!");
//
//                        }
//                    } else if (message.startsWith("/quit")) {
//                        broadcast(nickname + " left the chat");
//                        shutdown();
//                    } else {
//                        broadcast(nickname + ":" + message);
//
//                    }
//                }
//
//            } catch (Exception e) {
//                shutdown();
//            }
//        }
//
//        public void sendMessage(String message){
//            out.println(message);
//
//        }
//        public void shutdown() {
//            try{
//                in.close();
//                out.close();
//                if (!client.isClosed()) {
//                    client.close();
//                }
//            }catch (IOException e){
//
//
//            }
//
//        }
//    }
//
//    public static void main(String[] args) {
//        Server server = new Server();
//        server.run();
//    }
//}
//
//public class Client implements Runnable {
//
//    private Socket client;
//    private BufferedReader in;
//    private PrintWriter out;
//    private boolean done;
//
//    @Override
//    public void run() {
//        try{
//            Socket client = new Socket("127.0.0.1", 9999);
//            out = new PrintWriter(client.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//
//            inputHandler inHandler = new inputHandler();
//            Thread t = new Thread(inHandler);
//            t.start();
//
//            String inMessage;
//            while ((inMessage = in.readLine()) !=null){
//                System.out.println(inMessage);
//
//            }
//
//        }catch (IOException e) {
//            shutdown();
//
//        }
//    }
//
//    public void shutdown() {
//        done = true;
//        try{
//            in.close();
//            out.close();
//            if (!client.isClosed()){
//                client.close();
//            }
//        }catch (IOException e){
//
//        }
//    }
//
//    class inputHandler implements Runnable{
//        @Override
//        public void run(){
////
//            try{
//                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
//                while (!done) {
//                    String message = inReader.readLine(); //Läser vad klienten skriver(namN)
//                    if (message.equals("/quit")) {
//                        inReader.close();
//                        shutdown();
//                    } else {
//                        out.println(message);
//                    }
//                }
//            }catch(IOException e){
//                shutdown();
//            }
//        }
//
//    }
//    public static void main (String[] args){
//        Client client = new Client();
//        client.run();
//
//    }