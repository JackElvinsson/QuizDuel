import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private BufferedReader buffIn;
        private PrintWriter buffOut;
        private ObjectInputStream in;
        private ObjectOutputStream out;


        private int playerID;



        public ServerSideConnection(Socket s, int id) { //Constructor.
            socket = s;
            playerID = id;

            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                Data data = new Data();
                Data.player = player;
                broadcast(data);

                while (true) {
                    Data inData = (Data) in.readObject();
                    System.out.println("Server recieved" + indata + "from Client" + player);
                }
            } catch(IOException e){
                e.printStackTrace();
            }finally {
                try {
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            public void broadcast(Data dataOut){
                try{
                    System.out.println("Server sent" + dataOut 0 "To client" + player);
                    out.writeObject(dataOut);
                    out.flush();
                    out.reset();
                    System.out.println("Server succesfull" + outData "to client" + player);
            }catch (IOException e){
                    e.printStackTrace();
            }
            }
        }
        public class Data implements Serializable {

                public int player;
                public String playerName;
                public String opponentName;
                public List<> playerOneScore;
                public List<> opponentScore;
            }
        }



                /*
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                buffIn = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                buffOut = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("IO Exception from SSC Constructor");
            }
        }*/

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
                    while(player2Name.isBlank()) {
                        wait(1500);
                        System.out.println("playerID "+playerID+" waited 1,5sec");
                    }
                    System.out.println("Player "+playerID+" continues");
                }


                System.out.println("ID:" + playerID + " is Sending user name to opponent");


                sendUserName();


            } catch (IOException e) {
                System.out.println("IOException from run() SSC");
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
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
