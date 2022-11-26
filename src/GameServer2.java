//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Properties;
//
//public class GameServer2 {
//    private ServerSocket ss;
//    private int numPlayers;
//    private ServerSideConnection2 player1;
//    private ServerSideConnection2 player2;
//
//    private String player1Name = "P1";
//    private String player2Name = "P2";
//
//    public GameServer2() {
//        System.out.println("----Game Server is running----");
//
//
//        try {
//            ss = new ServerSocket(52733);
//        } catch (IOException e) {
//            System.out.println("IOEXCEPTION FROM CONSTRUCTOR!");
//        }
//    }
//
//    public void acceptConnections2() {
//        try {
//            System.out.println("Waiting for connections...");
//            System.out.println(numPlayers);
//            while (numPlayers < 2) {
//                Socket s = ss.accept(); //Börjar accpetera anslutningar
//                System.out.println(numPlayers);
//                numPlayers++;
//                System.out.println("Player #" + numPlayers + " has connected");
//                ServerSideConnection2 ssc = new ServerSideConnection2(s, numPlayers); //Sätter ID till siffran av numPlayers. 1 || 2.
//                if (numPlayers == 1) {
//                    player1 = ssc;
//                } else {
//                    player2 = ssc;
//                }
//                Thread t = new Thread(ssc);
//                t.start();
//            }
//            System.out.println("We now have two players, no longer accepting connections");
//        } catch (IOException e) {
//            System.out.println("IOEXCEPTION from acceptConnections()");
//        }
//    }
//
//    private class ServerSideConnection2 implements Runnable {
//
//        private Socket socket;
//        private DataInputStream dataIn;
//        private DataOutputStream dataOut;
//        private BufferedReader buffIn;
//        private PrintWriter buffOut;
//
//
//        private int playerID;
//
//        public ServerSideConnection2(Socket s, int id) { //Constructor.
//            socket = s;
//            playerID = id;
//            try {
//                dataIn = new DataInputStream(socket.getInputStream());
//                dataOut = new DataOutputStream(socket.getOutputStream());
//                buffIn = new BufferedReader(new InputStreamReader(
//                        socket.getInputStream()));
//                buffOut = new PrintWriter(socket.getOutputStream(), true);
//            } catch (IOException e) {
//                System.out.println("IO Exception from SSC Constructor");
//            }
//        }
//
//        public void run() { //separat run tråd för spelarna.
//            try {
//                dataOut.writeInt(playerID);   //skickar INT med vilket ID man fått. 1 || 2 till client.
//                System.out.println("Skickat playerID:" + playerID);
//
//                //setname logiken.
//                while (true) {
//                    if (playerID == 1) {
//                        player1Name = dataIn.readUTF();
//                        System.out.println("Player 1 name is set to: " + player1Name);
//                        break;
//                    } else if (playerID == 2) {
//                        player2Name = dataIn.readUTF();
//                        System.out.println("Player 2 name is set to: " + player2Name);
//                        break;
//                    }
//                }
//
//                //Väntar på 2 spelare.
//                if(numPlayers!=2){
//                    System.out.println("Waiting for second player");
//                }
//                while(numPlayers!=2){
//                    System.out.println("Waiting for players, number of players:"+numPlayers);
//                }
//                System.out.println("ID:" +playerID+" is Sending user name to opponent");
//
//
//
//                sendUserName();
//
//
//            } catch (IOException e) {
//                System.out.println("IOException from run() SSC");
//            }
//        }
//
//
//        public void setName() throws IOException {
////            try {
//            while (true) {
//                if (playerID == 1) {
//                    player1Name = dataIn.readUTF();
//                    System.out.println("Player 1 name is set to: " + player1Name);
//                    break;
//                } else if (playerID == 2) {
//                    player2Name = dataIn.readUTF();
//                    System.out.println("Player 2 name is set to: " + player2Name);
//                    sendUserName();
//                    break;
//                }
//
//            }
//
//        }
//
//        public void sendUserName() { //skickar useName till server
//            String opponentName="";
//            System.out.println("Player one: "+player1Name+"\nPlayer two: "+player2Name);
//            if (playerID == 1) {
//                opponentName=player2Name;
//            } else if (playerID == 2){
//                opponentName=player1Name;
//            }
//            try {
//                System.out.println("Sending opponentName from sendUserName(), opponentName sent: "+opponentName+" and player ID is: "+playerID);
//                dataOut.writeUTF(opponentName);
//                dataOut.flush();
//            } catch (IOException ex) {
//                System.out.println("IOException from sendUserName");
//            }
//        }
//
//        public void closeConnection() {
//            try {
//                socket.close();
//                System.out.println("Connection closed");
//            } catch (IOException ex) {
//                System.out.println("IOException on closeConnection");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        GameServer gs = new GameServer();
//        gs.acceptConnections();
//    }
//}
//
////
////public class Server implements Runnable {
////
////    @Override
////    public void run() {
////        int port = 9999;
////
////        try (ServerSocket sSocket = new ServerSocket(port)) {
////            while (true) {
////
////                ServerConnection player1 = new ServerConnection(sSocket.accept(), game, 1);
////                ServerConnection player2 = new ServerConnection(sSocket.accept(), game, 2);
////
////                game.setPlayers(p1, p2);
////
////                new Thread(player1).start();
////                new Thread(player2).start();
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////
////    public static void main(String[] args) {
////        new Thread(new Server()).start();
////    }
////}
////}