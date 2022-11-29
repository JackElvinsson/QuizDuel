import Questions.Categories.Kategori;
import Questions.Question;

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


    private String player1Name = "";
    private String player2Name = "";


    GameInit gameInit = new GameInit();
    private List<Question> listOfQuestions;
    private Kategori selectedCategoryForRound;

    public List<Kategori> getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(List<Kategori> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    private List<Kategori> categoryOptions;


    public String getPlayer1Name() {
        return player1Name;
    }


    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        Player2Name = player2Name;
    }


    public Host() throws IOException {
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
                System.out.println("Sending playerID " + playerID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Thread serverListenerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Startar Listeningpost");
                    listeningPostServer();
                }
            });
            serverListenerThread.start();

            System.out.println("PlayerID: "+playerID+" Efter ListeningPost är startad");

            while (player2Name.isBlank() && player1Name.isBlank()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("PlayerID" + playerID + "is sending name");
            sendOpponentName();
            System.out.println("playerID:" + playerID + " Name sent to oppponent");

        }

        /**
         * ListeningPostServer lyssnat efter in kommande inputstream. Tills inputstreamen får något i sig ligger den passiv.
         * Clienterna skickar en String med senderID som ListeningPost tar och sätter som postIdentifier, postIdentifier används
         * sedan i Switch-case för att determinera vilken metod som ska köras.
         */
        public void listeningPostServer() {
            while (true) {
                System.out.println("listeningPost är aktiv");
                String postIdentifier = "";//postIdentifier skickas av objektstream från klient.
                while (postIdentifier.isBlank()) {
                    try {
                        postIdentifier = (String) inputStream.readObject();
                        System.out.println(postIdentifier);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION IN LISTENINGPOSTSERVER");
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
        }

        public void getAndSetPlayerName() {
            String playername = "";
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
                opponentName = player2Name;
            }
            if (playerID == 2) {
                opponentName = player1Name;
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


        /**
         * Sätter categoryOptionslistan till det som metoden retunerar.
         * (default, 4st kategorier)
         */
        public void getCategoryOptionsList() {
            setCategoryOptions(gameInit.getCategoryOptions());
        }

        /**
         * Gör de icke-valda kategorierna tillgängliga igen.
         */
        public void categoryOptionsReturn() {

            try {
                List<Kategori> selectedList = (List<Kategori>) inputStream.readObject();
                System.out.println("selectedList read");
                Kategori selectedItem = (Kategori) inputStream.readObject();
                System.out.println("selectedItem read");
                gameInit.makeNotChosenCategoryAvailable(selectedList, selectedItem);
                gameInit.setSelectedCategory(selectedItem);
//                selectedCategoryForRound=selectedItem;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("EXCEPTION IN categoryOptionsReturn");
            }


        }


    }

    public static void main(String[] args) throws IOException {
        Host host = new Host();
        host.acceptConnections();


    }

}
