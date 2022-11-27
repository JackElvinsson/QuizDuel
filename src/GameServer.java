import Questions.Categories.Kategori;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public String player1Name = "";
    public String player2Name = "";

    public boolean threadWait = true;
    GameInit gameInit = new GameInit();


    public GameServer() throws IOException {
        System.out.println("----Game Server is running----");


        try {
            ss = new ServerSocket(52731);
        } catch (IOException e) {
            System.out.println("IOEXCEPTION FROM CONSTRUCTOR!");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            System.out.println(numPlayers);
            while (numPlayers < 2) {
                Socket s = ss.accept(); //Börjar accpetera anslutningar
                System.out.println(numPlayers);
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has connected");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers); //Sätter ID till siffran av numPlayers. 1 || 2.
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();

            }
            System.out.println("We now have two players, no longer accepting connections");
        } catch (IOException e) {
            System.out.println("IOEXCEPTION from acceptConnections()");
        }
    }

    private class ServerSideConnection implements Runnable {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private BufferedReader buffIn;
        private PrintWriter buffOut;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        private int playerID;

        public ServerSideConnection(Socket s, int id) { //Constructor.

            socket = s;
            playerID = id;

            try {

                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
//                oos = new ObjectOutputStream(socket.getOutputStream());
//                ois = new ObjectInputStream(socket.getInputStream());

//                buffIn = new BufferedReader(new InputStreamReader(
//                        socket.getInputStream()));
//                buffOut = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                System.out.println("IO Exception from SSC Constructor");
            }
        }

        public void run() { //separat run tråd för spelarna.
            try {
                dataOut.writeInt(playerID);   //skickar INT med vilket ID man fått. 1 || 2 till client.
                System.out.println("Skickat playerID:" + playerID);
//                countDownLatch.await();
                //setname logiken.
                while (true) {
                    if (playerID == 1) {
                        player1Name = dataIn.readUTF();
                        System.out.println("Player 1 name is set to: " + player1Name);
//                        countDownLatch.countDown();
                        break;
                    } else if (playerID == 2) {
                        player2Name = dataIn.readUTF();
                        System.out.println("Player 2 name is set to: " + player2Name);
//                        countDownLatch.countDown();
                        break;
                    }
                }

                synchronized (this) {
                    while (player2Name.isBlank()) {
                        wait(1500);
                        System.out.println("playerID " + playerID + " waited 1,5sec");
                    }
                    System.out.println("Player " + playerID + " continues");
                }


                System.out.println("ID:" + playerID + " is Sending username to opponent");

                sendUserName();

                if (playerID == 1) {

                    sendObject(gameInit.getCategoryList());
                    System.out.println("kommer till send object");
                    sendList(gameInit.getCategoryList(), socket);
                }


            } catch (IOException e) {
                System.out.println("IOException from run() SSC");
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        public void setName() throws IOException {
//            try {

            while (true) {
                if (playerID == 1) {
                    player1Name = dataIn.readUTF();
                    System.out.println("Player 1 name is set to: " + player1Name);
                    break;
                } else if (playerID == 2) {
                    player2Name = dataIn.readUTF();
                    System.out.println("Player 2 name is set to: " + player2Name);
                    break;
                }

            }

        }


        public void sendUserName() { //skickar useName till server
            String opponentName = "";
            System.out.println("Player one: " + player1Name + "\nPlayer two: " + player2Name);
            if (playerID == 1) {
                opponentName = player2Name;
            } else if (playerID == 2) {
                opponentName = player1Name;
            }
            try {
                System.out.println("Sending opponentName from sendUserName(), opponentName sent: " + opponentName + " and player ID is: " + playerID);
                dataOut.writeUTF(opponentName);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendUserName");
            }
        }

        public void closeConnection() {
            try {
                socket.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException on closeConnection");
            }
        }


        /////////////////// OBJECT STREAM TEST ///////////////////////////////////////////
        public void sendObject(Object outObject) {
            try {
                System.out.println("Server tries to send " + outObject + " to client " + "player: " + playerID);
                oos.writeObject(outObject);
                oos.flush();
                oos.reset();
                System.out.println("Server not sucsessful to send" + outObject + " to client " + "player: " + playerID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /////////////////// OBJECT STREAM TEST ///////////////////////////////////////////
        public void sendList(List<Kategori> kategoriList, Socket s) throws IOException {

            try (ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(s.getInputStream()))  {

                System.out.println("Kommer till innan output");

                out.writeObject(kategoriList);

                System.out.println(kategoriList.get(0).getCategoryName());
                System.out.println(kategoriList.get(1).getCategoryName());
                System.out.println(kategoriList.get(2).getCategoryName());
                System.out.println(kategoriList.get(3).getCategoryName());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
