import Questions.Categories.Kategori;
import Questions.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.List;

public class Gameserver extends Thread {

    private ServerSocket ss;
    private int numPlayers;
    private Serverplayer player1;
    private Serverplayer player2;

    private int numberOfQuestions;

    private int numberOfRounds;
    private int turnsMade = 0;

    private List<Question> player1Results;
    private List<Question> player2Results;

    private int player1Score;
    private int player2Score;
    public boolean player1Ready = false;
    public boolean player2Ready = false;

    public boolean player1SyncReady = false;
    public boolean player2SyncReady = false;
    private String player1Name = "";
    private String player2Name = "";
    private int maxTurns;


    Serverplayer player11;
    Serverplayer player22;

    private int Turncounter;
    ServerSideConnection ssc1;
    ServerSideConnection ssc2;
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
        this.player2Name = player2Name;
    }

    public Gameserver() throws IOException {

    }


    public Gameserver(Serverplayer player1, Serverplayer player2, int nrofQuestions, int nrofRounds) throws IOException {
        this.player1 = player1;
        this.player2 = player2;
        this.numberOfQuestions = nrofQuestions;
        this.numberOfRounds = nrofRounds;
        System.out.println("Constructor i GameServer1");
        Thread t1 = new Thread(ssc1 = new ServerSideConnection(player1));
        Thread t2 = new Thread(ssc2 = new ServerSideConnection(player2));
        t1.start();
        t2.start();
        System.out.println("Constructor i GameServer2");

        maxTurns=nrofRounds;


//    ssc1=new ServerSideConnection(player1);
//    ssc2=new ServerSideConnection(player2);


    }


    private class ServerSideConnection implements Runnable {

        Serverplayer player;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;
        private int playerID;

        public ServerSideConnection(Serverplayer PLAYER) {
            player = PLAYER;
            outputStream = PLAYER.getOutputStreamServerPlayer();
            inputStream = PLAYER.getInputStreamServerPlayer();
            playerID = PLAYER.getPlayerIDServerPlayer();
        }


        @Override
        public void run() {
            System.out.println("Run is running.");
            try {
                outputStream.writeInt(playerID);
                System.out.println("Sending playerID" + playerID);
                outputStream.writeInt(numberOfQuestions);
                System.out.println("Sending numberOfQuestions, " + numberOfQuestions + " to playerID:" + playerID);
                outputStream.writeInt(numberOfRounds);
                System.out.println("Sending numberOfRounds(categories), " + numberOfRounds + " to playerID:" + playerID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
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

            System.out.println("PlayerID: " + playerID + " Efter ListeningPost är startad");

            while (player2Name.isBlank() || player1Name.isBlank()) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("PlayerID:" + playerID + "is trying to retrieve opponents name");
            retrieveOpponentName();
            System.out.println("playerID:" + playerID + " ran retrieveOpponentName() method.");

            waitforplayerstopressplay();
            waitingforPlayersHandshake();
            start();


        }

        /**
         * ListeningPostServer lyssnat efter in kommande inputstream. Tills inputstreamen får något i sig ligger den passiv.
         * Clienterna skickar en String med senderID som ListeningPost tar och sätter som postIdentifier, postIdentifier används
         * sedan i Switch-case för att determinera vilken metod som ska köras.
         */

        public void surrender() {
            System.out.println("PlayerID:" + playerID + " gav upp. Skickar till motståndaren vinst.");
            String senderID = "surrender";
            try {

                if (playerID == 1) {
                    ssc2.outputStream.writeObject(senderID);
                } else if (playerID == 2) {
                    ssc1.outputStream.writeObject(senderID);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void listeningPostServer() {
            while (true) {
                System.out.println("PlayerID:" + playerID + "listeningPost är aktiv");
                String postIdentifier = "";//postIdentifier skickas av objektstream från klient.
                while (postIdentifier.isBlank()) {
                    try {
                        postIdentifier = (String) inputStream.readObject();
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
                        retrieveOpponentName();
                        break;
                    case "handShake": //skickas handshake sätts ready till true.
                        playerReady();
                        break;
                    case "startGame":
                        getCategoryOptionsList();
                        break;
                    case "retrieveQuestions":
                        categoryOptionsReturn();
                        break;
                    case "sendPlayerResults":
                        syncPlayerResults();
                        break;
                    case "giveUp":
                        surrender();
                        break;
                    case "checkGameEnd":
                        break;
                    case "checkGaveUP":

                        break;
                }
                postIdentifier = "";
            }
        }

        public void endGame(){
            if (turnsMade/2==maxTurns){
                String senderID = "endgame";

                try {
                    outputStream.writeObject(senderID);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.reset();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }

        public void start() {
            String senderID = "start";
            try {
                outputStream.writeObject(senderID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void waitforplayerstopressplay() {
            String senderID = "handShake";

            try {
                outputStream.writeObject(senderID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        public void playerReady() {
            System.out.println("Method playerReady is running. Trying to set player1&2Ready.");
            if (playerID == 1) {
                player1Ready = true;
                System.out.println(player1Ready);
            } else if (playerID == 2) {
                player2Ready = true;
                System.out.println(player2Ready);
            }

            player1SyncReady = false;
            player2SyncReady = false;
        }

        public void getAndSetPlayerName() {
            String playername = "";
            try {
                while (playername.isBlank()) {
                    System.out.println("Playername loop");
                    playername = (String) inputStream.readObject();

                }
                System.out.println("playerID:" + playerID + " sent name: " + playername);
            } catch (IOException ex) {
                System.out.println("IOException from getAndSetOpponentName()- When trying to receive  name String");
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (playerID == 1) {
                player1Name = playername;
            } else if (playerID == 2) {
                player2Name = playername;
            }
        }

        public void syncPlayerResults() {
            System.out.println("sencPlayerResults is running");
            try {






                System.out.println("Playerid: " + playerID + "Har tagit emot resultsArray (syncPlayerResults)");


                if (playerID == 1) {
                    player1Score = (int) inputStream.readObject();
                    System.out.println("Player 1 score is read: " + player1Score);
                    player1Results = (List<Question>) inputStream.readObject();
                    System.out.println("player1Results list is read");
                    System.out.println("PlayerID: " + playerID + " Player1SyncReady= " + player1SyncReady);
                    System.out.println("PlayerID: " + playerID + " Player2SyncReady= " + player2SyncReady);
                    player1SyncReady = true;
//                    System.out.println("Server received the following array from player1: ");
//                    for (int i = 0; i < player1Results.length; i++) {
//                        System.out.println(" array index:" + i + " = " + player1Results[i]);
//                    }

                } else if (playerID == 2) {
                    player2Score = (int) inputStream.readObject();
                    System.out.println("Player 2 score is read: " + player2Score);
                    player2Results = (List<Question>) inputStream.readObject();
                    System.out.println("player2Results list is read");
                    player2SyncReady = true;
                    System.out.println("PlayerID: " + playerID + " Player1SyncReady= " + player1SyncReady);
                    System.out.println("PlayerID: " + playerID + " Player2SyncReady= " + player2SyncReady);
//                    System.out.println("Server received the following array from player2: ");
//                    for (int i = 0; i < player2Results.length; i++) {
//                        System.out.println(" array index:" + i + " = " + player2Results[i]);
//                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("PlayerID: " + playerID + "syncPlayerResults");
            }

            System.out.println("Player1SyncReady= " + player1SyncReady);
            System.out.println("Player2SyncReady= " + player2SyncReady);
            while (!player1SyncReady || !player2SyncReady) {
                try {
                    System.out.println("stuck in loop");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                    System.out.println("Both players are ready");
            }


            System.out.println("Sätter senderID=syncPlayerResults");
            String senderID = "syncPlayerResults";
            try {

                System.out.println("PlayerID: " + playerID + "Försöker skicka: " + senderID);
                outputStream.writeObject(senderID);
                System.out.println(senderID + " skickat");

                if (playerID == 1) {
                    outputStream.writeObject(player2Score);
                    outputStream.writeObject(player2Results);


//                    System.out.println("Server sent player2's array to to player1: ");
//                    for (int i = 0; i < player2Results.length; i++) {
//                        System.out.println(" array index:" + i + " = " + player2Results[i]);
//                    }

                    System.out.println("-----PlayerID : " + playerID + " OpponentScore skickat: " + player2Score + "-----");
                } else if (playerID == 2) {
                    outputStream.writeObject(player1Score);
                    outputStream.writeObject(player1Results);


//                    System.out.println("Server sent player1's array to to player2: ");
//                    for (int i = 0; i < player1Results.length; i++) {
//                        System.out.println(" array index:" + i + " = " + player1Results[i]);
//                    }
//                    System.out.println("-----PlayerID: " + playerID + "OpponentScore skickat: " + player1Score + "-----");
                }


                System.out.println("Klar med syncPlayerResults");

            } catch (IOException ex) {
                System.out.println("IOException from syncPlayerResults()-- Sending score");
                throw new RuntimeException(ex);
            } finally {
                try {
                    outputStream.flush();
//                    outputStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        public void waitingforPlayersHandshake() {
            String senderID = "waitingforPlayersHandshake";

            while (!player1Ready || !player2Ready) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//            System.out.println("Both players are ready");
            }

            try {
                System.out.println("playerID" + playerID + " is sending ready signal");
                outputStream.writeObject(senderID);
                System.out.println("playerID" + playerID + "has sent ready signal");

            } catch (IOException ex) {
                System.out.println("IOException from playersReady()");
                throw new RuntimeException(ex);
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                if(playerID==1){
//                    player1Ready=false;
//                }else if(playerID==2) {
//                    player2Ready=false;
//                }
            }
        }

        public void retrieveOpponentName() {
            String opponentName = "";
            String senderID = "retrieveOpponentName";
            if (playerID == 1) {
                opponentName = player2Name;
            }
            if (playerID == 2) {
                opponentName = player1Name;
            }
            try {
                System.out.println("playerID" + playerID + " is retrieving opponent Name " + opponentName);
                outputStream.writeObject(senderID);
                outputStream.writeObject(opponentName);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException ex) {
                System.out.println("IOException from getOpponentName()");
                throw new RuntimeException(ex);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
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
            System.out.println("Skickar lista med kategorival.");
            String senderID = "getCategoryOptionsList";
            setCategoryOptions(gameInit.getCategoryOptions());
            player1SyncReady = false;
            player2SyncReady = false;
            try {
                outputStream.writeObject(senderID);
                outputStream.writeObject(categoryOptions);


            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                turnsMade++;
            }
        }

        /**
         * Gör de icke-valda kategorierna tillgängliga igen.
         */
        public void categoryOptionsReturn() {
            try {
                Kategori selectedItem = (Kategori) inputStream.readObject();
                System.out.println("selectedItem read");
                gameInit.setSelectedCategory(selectedItem);
                gameInit.makeNotChosenCategoryAvailable(categoryOptions, selectedItem);
                listOfQuestions = selectedItem.generateQuestions(selectedItem, numberOfQuestions);
                System.out.println("Storlek på frågelista: " + listOfQuestions.size());


                System.out.println("---SERVER---" + numberOfQuestions);





//                selectedCategoryForRound=selectedItem;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("EXCEPTION IN categoryOptionsReturn");
            }
            ssc1.sendQuestions();
            ssc2.sendQuestions();
        }

        public void sendQuestions() {
            System.out.println("Trying to send questions");
            String senderString = "Questions";
            try {
                outputStream.writeObject(senderString);
                outputStream.writeObject(listOfQuestions);


                System.out.println("skickade sträng:" + senderString);
                System.out.println("Skickade listOfQuestions: " + listOfQuestions.size());

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                    outputStream.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }//Låser upp. Gör det här för att inte låsa en tråd i en while-lock
            if (playerID == 1) {
                player1Ready = false;
            } else if (playerID == 2) {
                player2Ready = false;
            }
            System.out.println("Questions sent, after outpustream. First question, index 0: " + listOfQuestions.get(0).getQuestionText());
        }


    }

}
