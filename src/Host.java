import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {

    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    private int turnsMade;
    private int maxTurns;
    private int[] values;
    private int player1ButtonNum;
    private int player2ButtonNum;
    private String player1Name;
    private String Player2Name;

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return Player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        Player2Name = player2Name;
    }


    public Host() {
        System.out.println("----Game Server is running----");

        try {
            ss = new ServerSocket(52734);
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
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;
        private int playerID;

        public ServerSideConnection(int id) {

            playerID = id;
            try (Socket socket = new Socket("localhost", 52731);
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());) {
            } catch (IOException e) {
                System.out.println("IO Exception from SSC Constructor");
            }
        }


        @Override
        public void run() {


        }

        public void listeningPostServer(){
            String postIdentifier="";//postIdentifier skickas av objektstream från klient.
            while(postIdentifier.isBlank()) {
                try {
                    postIdentifier = (String) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            switch (postIdentifier) {
                case "sendPlayerName":
                    getAndSetPlayerName();
                    break;
                case "getOpponentName":
                    getOpponentName();
                    break;
            }

        }

        public void getAndSetPlayerName() {
            String playername = "";
//            String opponentName;
            try {
                while (playername.isBlank()) {
                    playername = (String) inputStream.readObject();
                }
                System.out.println("playerID" + playerID + " sent name: " + playername);
            } catch (IOException ex) {
                System.out.println("IOException from getAndSetOpponentName()- When trying to receive  name String");
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (playerID == 1) {
                setPlayer1Name(playername);
            }
            if (playerID == 2) {
                setPlayer2Name(playername);
            }
        }

        public void getOpponentName() {
            String opponentName="";
            if (playerID == 1) {
                opponentName = getPlayer2Name();
            }
            if (playerID == 2) {
                opponentName = getPlayer1Name();
            }
            try {
                System.out.println("playerID" + playerID + " is sending it's name, "+opponentName+" to opponent");
                outputStream.writeObject(opponentName);
            } catch (IOException ex) {
                System.out.println("IOException from getOpponentName()");
                throw new RuntimeException(ex);
            }
        }


    }

}
