import java.io.*;
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
    private String player1Name="";
    private String Player2Name="";
//    private Socket socket;

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


    public Host()throws IOException {
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
                ServerSideConnection ssc = new ServerSideConnection(s ,numPlayers); //Sätter ID till siffran av numPlayers. 1 || 2.
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
        private Socket socket;

        public ServerSideConnection(Socket s, int id) {
            playerID = id;
            socket = s;

            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("IO Exception from SSC Constructor");
            }
        }


        @Override
        public void run() {
            System.out.println("Run is running.");
            try {
                outputStream.writeInt(playerID);
                System.out.println("Sending playerID "+playerID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Thread serverListenerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startar Listeningpost");
                listeningPostServer();
            }
        });serverListenerThread.start();

        while (!Player2Name.isBlank()&&!player1Name.isBlank()){
            sendOpponentName();

            }
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
            System.out.println(postIdentifier);
            switch (postIdentifier) {
                case "sendPlayerName":
                    getAndSetPlayerName();
                    break;
                case "getOpponentName":
                    sendOpponentName();
                    break;
            }

        }

        public void getAndSetPlayerName() {
            String playername = "";
//            String opponentName;
            try {
                while (playername.isBlank()) {
                    System.out.println("Playername loop");
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
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public static void main(String[] args) throws IOException {
        Host host = new Host();
        host.acceptConnections();
    }

}
