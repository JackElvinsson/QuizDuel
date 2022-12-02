//import Questions.Categories.Kategori;
//import Questions.Question;
//import Server.Bridge;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.List;
//
//public class Host {
//
//    private ServerSocket ss;
//    private int numPlayers;
//    private ServerSideConnection player1;
//    private ServerSideConnection player2;
//
//    private int numberOfQuestions = 2;
//    private int numberOfRounds = 2;
//    private int turnsMade = 0;
//
//    private int[] player1Results;
//    private int[] player2Results;
//
//    private int opponentScore;
//    private int player1Score;
//    private int player2Score;
//    private boolean player1Ready = false;
//    private boolean player2Ready = false;
//
//    private String player1Name = "";
//    private String player2Name = "";
//
//
//    GameInit gameInit = new GameInit();
//    private List<Question> listOfQuestions;
//    private Kategori selectedCategoryForRound;
//    public List<Kategori> getCategoryOptions() {
//        return categoryOptions;
//    }
//    public void setCategoryOptions(List<Kategori> categoryOptions) {
//        this.categoryOptions = categoryOptions;
//    }
//
//    private List<Kategori> categoryOptions;
//
//
//    public String getPlayer1Name() {
//        return player1Name;
//    }
//
//
//    public void setPlayer1Name(String player1Name) {
//        this.player1Name = player1Name;
//    }
//
//    public String getPlayer2Name() {
//        return player2Name;
//    }
//
//    public void setPlayer2Name(String player2Name) {
//        this.player2Name = player2Name;
//    }
//
//
//    public Host() throws IOException {
//
//        /**
//         * Sätta inparameter för constructorn att ta 2st intar, som inläsningen av properties styr?
//         * allt styra upp det här direkt via properties?
//         * */
//        System.out.println("----Game Server is running----");
//
//        try {
//            ss = new ServerSocket(52731);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("IOEXCEPTION FROM CONSTRUCTOR!");
//        }
//
//        player1Results = new int[numberOfQuestions];
//        player2Results = new int[numberOfQuestions];
//
//    }
//
//    public void acceptConnections() {
//        try {
//            System.out.println("Waiting for connections...");
//            System.out.println(numPlayers);
//            Bridge bridge = new Bridge();
//            while (numPlayers < 2) {
//                Socket s = ss.accept();
//                System.out.println(numPlayers);
//                numPlayers++;
//                System.out.println("Player #" + numPlayers + " has connected");
//                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers, bridge); //Sätter ID till siffran av numPlayers. 1 || 2.
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
//            e.printStackTrace();
//            System.out.println("IOEXCEPTION from acceptConnections()");
//        }
//    }
//
//    private class ServerSideConnection implements Runnable {
//        private ObjectOutputStream outputStream;
//        private ObjectInputStream inputStream;
//        private int playerID;
//        private Socket socket;
//
//        public ServerSideConnection(Socket s, int id, Bridge bridge) {
//            playerID = id;
//            socket = s;
//            try {
//                outputStream = new ObjectOutputStream(socket.getOutputStream());
//                inputStream = new ObjectInputStream(socket.getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("IO Exception from SSC Constructor");
//            }
//        }
//
//        @Override
//        public void run() {
//            System.out.println("Run is running.");
//            try {
//                outputStream.writeInt(playerID);
//                System.out.println("Sending playerID" + playerID);
//                outputStream.writeInt(numberOfQuestions);
//                System.out.println("Sending numberOfQuestions to playerID:" + playerID);
//                outputStream.writeInt(numberOfRounds);
//                System.out.println("Sending numberOfRounds to playerID:" + playerID);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } finally {
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//
//            Thread serverListenerThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("Startar Listeningpost");
//                    listeningPostServer();
//                }
//            });
//            serverListenerThread.start();
//
//            System.out.println("PlayerID: " + playerID + " Efter ListeningPost är startad");
//
//            while (player2Name.isBlank() || player1Name.isBlank()) {
//
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            System.out.println("PlayerID:" + playerID + "is trying to retrieve opponents name");
//            retrieveOpponentName();
//            System.out.println("playerID:" + playerID + " ran retrieveOpponentName() method.");
//
//
//            waitingforPlayersHandshake();
//
//
//        }
//
//        /**
//         * ListeningPostServer lyssnat efter in kommande inputstream. Tills inputstreamen får något i sig ligger den passiv.
//         * Clienterna skickar en String med senderID som ListeningPost tar och sätter som postIdentifier, postIdentifier används
//         * sedan i Switch-case för att determinera vilken metod som ska köras.
//         */
//        public void listeningPostServer() {
//            while (true) {
//                System.out.println("PlayerID:" + playerID + "listeningPost är aktiv");
//                String postIdentifier = "";//postIdentifier skickas av objektstream från klient.
//                while (postIdentifier.isBlank()) {
//                    try {
//                        postIdentifier = (String) inputStream.readObject();
//                    } catch (IOException | ClassNotFoundException e) {
//                        e.printStackTrace();
//                        System.out.println("EXCEPTION IN LISTENINGPOSTSERVER");
//                    }
//                }
//                System.out.println(postIdentifier);
//                switch (postIdentifier) {
//                    case "sendPlayerName":
//                        getAndSetPlayerName();
//                        break;
//                    case "getOpponentName":
//                        retrieveOpponentName();
//                        break;
//                    case "handShake": //skickas handshake sätts ready till true.
//                        playerReady();
//                        break;
//                    case "startGamePlayer1":
//                        getCategoryOptionsList();
//                        break;
//                    case "retrieveQuestions":
//                        categoryOptionsReturn();
//                        break;
//                    case "sendPlayerResults":
//                        syncPlayerResults();
//                        break;
//                }
//            }
//        }
//
//        public void playerReady() {
//            if (playerID == 1) {
//                player1Ready = true;
//            } else if (playerID == 2) {
//                player2Ready = true;
//            }
//        }
//
//        public void getAndSetPlayerName() {
//            String playername = "";
//            try {
//                while (playername.isBlank()) {
//                    System.out.println("Playername loop");
//                    playername = (String) inputStream.readObject();
//
//                }
//                System.out.println("playerID:" + playerID + " sent name: " + playername);
//            } catch (IOException ex) {
//                System.out.println("IOException from getAndSetOpponentName()- When trying to receive  name String");
//                throw new RuntimeException(ex);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            if (playerID == 1) {
//                player1Name = playername;
//            } else if (playerID == 2) {
//                player2Name = playername;
//            }
//        }
//
//        public void syncPlayerResults() {
//            int[] resultsArray = new int[numberOfQuestions];
//            int[] opponentArray = new int[numberOfQuestions];
//
//            try {
//
//                resultsArray = (int[]) inputStream.readObject();
//                player1Ready = false;
//                player2Ready = false;
//
//                if (playerID == 1) {
//                    System.out.println("player1Ready is set to false, so it can wait for player2 ready signal");
//                    player1Results=resultsArray;
//                    System.out.println("player1Results array is read");
//                    player1Score = inputStream.readInt();
//                    System.out.println("Player 1 score is read");
//
//                } else if (playerID == 2) {
//                    System.out.println("player2Ready is set to false, so it can wait for player1 ready signal");
//                    player2Results=resultsArray;
//                    System.out.println("player2Results array is read");
//                    player2Score = inputStream.readInt();
//                    System.out.println("Player 2 score is read");
//                }
//                playerReady();
//
//
//                while (!player1Ready || !player2Ready) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
////                    System.out.println("Both players are ready");
//                }
//
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                System.out.println("syncPlayerResults");
//            }
//
//            String senderID = "syncPlayerResults";
//            try {
//
//                if (playerID == 1) {
//                    opponentScore = player2Score;
//                    opponentArray = player2Results;
//                } else if (playerID == 2) {
//                    opponentArray = player1Results;
//                    opponentScore = player1Score;
//                }
//
//                outputStream.writeObject(senderID);
//                outputStream.writeObject(opponentArray);
//                outputStream.writeInt(opponentScore);
//
//            } catch (IOException ex) {
//                System.out.println("IOException from syncPlayerResults()-- Sending score");
//                throw new RuntimeException(ex);
//            } finally {
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }
//
//        public void waitingforPlayersHandshake() {
//            String senderID = "waitingforPlayersHandshake";
//
//            while (!player1Ready || !player2Ready) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println("Both players are ready");
//            }
//
//            try {
//                System.out.println("playerID" + playerID + " is sending ready signal");
//                outputStream.writeObject(senderID);
//                System.out.println("playerID" + playerID + "has sent ready signal");
//
//            } catch (IOException ex) {
//                System.out.println("IOException from playersReady()");
//                throw new RuntimeException(ex);
//            } finally {
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }
//
//        public void retrieveOpponentName() {
//            String opponentName = "";
//            String senderID = "retrieveOpponentName";
//            if (playerID == 1) {
//                opponentName = player2Name;
//            }
//            if (playerID == 2) {
//                opponentName = player1Name;
//            }
//            try {
//                System.out.println("playerID" + playerID + " is retrieving opponent Name " + opponentName);
//                outputStream.writeObject(senderID);
//                outputStream.writeObject(opponentName);
//            } catch (IOException ex) {
//                System.out.println("IOException from getOpponentName()");
//                throw new RuntimeException(ex);
//            } finally {
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//
//        /**
//         * Sätter categoryOptionslistan till det som metoden retunerar.
//         * (default, 4st kategorier)
//         */
//        public void getCategoryOptionsList() {
//            System.out.println("Skickar lista med kategorival.");
//            String senderID = "getCategoryOptionsList";
//            setCategoryOptions(gameInit.getCategoryOptions());
//            try {
//                outputStream.writeObject(senderID);
//                outputStream.writeObject(categoryOptions);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } finally {
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                turnsMade++;
//            }
//        }
//
//        /**
//         * Gör de icke-valda kategorierna tillgängliga igen.
//         */
//        public void categoryOptionsReturn() {
//            try {
//                Kategori selectedItem = (Kategori) inputStream.readObject();
//                System.out.println("selectedItem read");
//                gameInit.setSelectedCategory(selectedItem);
//                gameInit.makeNotChosenCategoryAvailable(categoryOptions, selectedItem);
//                listOfQuestions = selectedItem.generateQuestions(selectedItem, numberOfQuestions);
//
//
//                for (int i = 0; i < numberOfQuestions; i++) {
//                    System.out.println(listOfQuestions.get(i).getAnswer1().getAnswerText());
//                    System.out.println(listOfQuestions.get(i).getAnswer2().getAnswerText());
//                    System.out.println(listOfQuestions.get(i).getAnswer3().getAnswerText());
//                    System.out.println(listOfQuestions.get(i).getAnswer4().getAnswerText());
//                }
////                selectedCategoryForRound=selectedItem;
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                System.out.println("EXCEPTION IN categoryOptionsReturn");
//            }
//            player1.sendQuestions();
//            player2.sendQuestions();
//        }
//
//        public void sendQuestions() {
//            System.out.println("Trying to send questions");
//            String senderString = "Questions";
//            try {
//                outputStream.writeObject(senderString);
//                outputStream.writeObject(listOfQuestions);
//
//                System.out.println("skickade sträng:" + senderString);
//                System.out.println("Skickade listOfQuestions: " + listOfQuestions.size());
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } finally {
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//            System.out.println("Questions sent, after outpustream. First question, index 0: " + listOfQuestions.get(0).getQuestionText());
//        }
//
//
//    }
//
//    public static void main(String[] args) throws IOException {
//        Host host = new Host();
//
//        host.acceptConnections();
//
//
//
//
//    }
//
//}
////
////Try(ServerSocket listner = new ServerSocket(9999)){
////    while(true){
////        Gio g = new gui();
////        playerOne = new ServersidePlayer(listner.accept(),player_1)
////        playerTwo = new ServerSidePlayer(listner.accept(),player_2)
////        g.setPlayers(playerOne, playerTwo);
////
////        playerTwo.start();
////        playerOne.start();
////    }
////
