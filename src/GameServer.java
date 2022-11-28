import Questions.Categories.Kategori;
import Questions.Question;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    public String player1Name = "";
    public String player2Name = "";

    public boolean player1Wait = true;
    public boolean player2Wait = true;


    public void setPlayer1Wait(boolean wait) {
        this.player1Wait = wait;
    }

    public boolean getPlayer1Wait() {
        return player1Wait;
    }

    public GameServer() {
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

        CountDownLatch countDownLatch = new CountDownLatch(1);
        private Socket socket;
        private DataOutputStream dataOut;
        private DataInputStream dataIn;
        private BufferedReader buffIn;
        private PrintWriter buffOut;


        private int playerID;

        public ServerSideConnection(Socket s, int id) { //Constructor.

            socket = s;
            playerID = id;

            try {

                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                buffIn = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                buffOut = new PrintWriter(socket.getOutputStream(), true);
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

        public void sendListOfCategoryOptions(List<Kategori> optionsList) {
            try {
                oos.writeObject(optionsList);
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(0).getCategoryName());
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(1).getCategoryName());
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(2).getCategoryName());
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(3).getCategoryName());

                oos.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("IOException from sendListOfCategoryOptions");
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
    }

    public static void main(String[] args) throws IOException {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}

