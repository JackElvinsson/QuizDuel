import Questions.Categories.Kategori;
import Questions.Question;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Client extends JFrame implements Serializable, Closeable {


    private int playerID;
    //    private int turnOrder;
    private int opponentID;
    private int playerScore;
    private String playerName;
    private String opponentName = "";
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private List<Question> listOfQuestions;
    private List<Question> opponentselected;
    private List<Kategori> listOfCategoryOptions;

    private int opponentScore;

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    private int numberOfQuestions;
    private int numberOfRounds;
    private GUI gui;
    private ClientSideConnection csc;
    private boolean myTurn = false; //todo var true från början
    private int score;
    private int[] roundResults; //Första elementet avser vilken fråga, andra elementet avser om frågan är korrekt eller fel. 1 anses rätt.

    private int gameScore;
    private int oppGameScore;

    public Client() {

    }

    public void startGUI() throws IOException {
        gui = new GUI();
    }

    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getOpponentID() {
        return opponentID;
    }

    public void setOpponentID(int opponentID) {
        this.opponentID = opponentID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    @Override
    public void close() throws IOException {

    }

    private class ClientSideConnection {
        private Socket socket;
//
//        private DataInputStream dataIn;
//        private DataOutputStream dataOut;

        ObjectInputStream inputStream;

        ObjectOutputStream outputStream;

        /**
         * 1. Anslutning sker.
         * 2. Läser in playerID från servern.
         * 3  Läser in numberOfQuestions från servern
         * 4  Läser in numberOfRounds från servern
         * 5. Väntar på att spelare sätter namn i GUI
         * 6. Skickar spelarnamn till server med metoden: sendPlayerName(getPlayerName());
         * 7. Startar en tråd som kör igång metoden: listeningPostClient()
         * 8. skickar att man klickat på spela till servern  med metoden: handShake(); -
         * 9. startGamePlayer1(); Player1 får kategorier. Sen sköter server vems tur det är.
         */
        /*Public Properties(){
         * Properties p = new Properties();
         * try{
         * properties.load(new FileInputStream("src/Server/Settings.properties"));
         *String numberOfQuestionsString = properties.GetProperty("numberOfRounds","2");
         * numberOfRounds = Integer.parseInt(numberOfRoundsString);
         * String numberOfQuestionsString = properties.getProperty("numberOfQuestions","2");
         *numberOfQuestions = Integer.parseInt(numberOfQuestionsString);
         * }catch (FileNotFoundException e){
         * System.out.println(" Failed to load file");
         * }catch (IOException e){
         * System.out.println("IOException");
         * */
        public ClientSideConnection() {
            System.out.println("---Client---");
            try {

                socket = new Socket("localhost", 52731);
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                playerID = inputStream.readInt();
                System.out.println("CLIENT: I am playerID:" + playerID);
                numberOfQuestions = inputStream.readInt();
                System.out.println("CLIENT: Number of questions per round set to: " + numberOfQuestions);
                numberOfRounds = inputStream.readInt();
                System.out.println("CLIENT: Number of rounds(categories) per game set to: " + numberOfRounds);
                gui.setUpStatsPanel(numberOfQuestions, numberOfRounds);


                if (playerID == 2) {
                    myTurn = false;
                }

                //todo fanns inte från början
                if (playerID == 1) {
                    myTurn = true;
                }


                synchronized (this) {
                    while (gui.user.getName().isBlank()) {
                        wait(800);
                    }
                    setPlayerName(gui.user.getName());
                }

                System.out.println("CLIENT: namnet är satt: " + playerName);
            } catch (UnknownHostException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("CLIENT: IO Exception from CSC Constructor");
                throw new RuntimeException(e);
            }


            sendPlayerName(getPlayerName());
            Thread clientListenerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        listeningPostClient();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            clientListenerThread.start();
            handShake();


//            startGame();

        }


        public void listeningPostClient() throws InterruptedException {
            while (true) {
                System.out.println("CLIENT: listeningPostClient är aktiv");
                String postIdentifier = "";  //postIdentifier skickas av objektstream från server.
                while (postIdentifier.isBlank()) {
                    try {
                        postIdentifier = (String) inputStream.readObject();
                        System.out.println("postIdentifier = " + postIdentifier);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                switch (postIdentifier) {
                    case "retrieveOpponentName":
                        getAndSetOpponentName();
                        break;
                    case "waitingforPlayersHandshake":
                        System.out.println("Kallar på metoden playersReady() från switch-case.");
                        playersReady();
                        System.out.println("Metoden playersReady() är kallad");
                        break;
                    case "getCategoryOptionsList":
                        displayCategoryOptions();
                        break;
                    case "Questions":
                        readQuestions();
                        break;
                    case "syncPlayerResults":
                        continueGame();
                        System.out.println("");
                        break;
                    case "start":
                        startGame();
                        break;
                    case "surrender":
                        oppsurrendered();
                        break;
                    case "endgame":
                        gameOver();
                        break;
                }
                postIdentifier = "";
            }
        }

        public void oppsurrendered() {
            System.out.println("CLIENT: motståndaren gav upp. Du vann!");
            gui.setOpponentGameScore(gui.user.gameScore+1 );
            /**
             * Ändra scen, nollställ saker i GUI.
             * */

            if (gui.getPlayPanel().isVisible() || gui.getStatsPanel().isVisible() || gui.getCategoryPanel().isVisible()) {
                gui.getPlayPanel().setVisible(false);
                gui.getPlayPanel().setVisible(false);
                gui.getPlayPanel().setVisible(false);
                gui.getLobbyPanel().setVisible(true);

            }
        }

        public void gameOver() {

            if (playerScore > opponentScore) {
            } else if (playerScore == opponentScore) {

            } else if (opponentScore > playerScore) {
                oppGameScore++;
            }
        }


        void checkGaveUP() throws InterruptedException {
            if (gui.isGaveUp()) {
                giveUp();
                gui.setGaveUp(false);
            }
        }


        public void giveUp() {
            String senderID = "giveUp";
            try {
                outputStream.writeObject(senderID);

            } catch (IOException ex) {
                System.out.println("CLIENT: IOException from giveup()");
                throw new RuntimeException(ex);
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /***
             * change scenes, nollställ.
             */

            if (gui.getStatsPanel().isVisible()) {
                gui.changeScene(gui.getStatsPanel(), gui.getLobbyPanel());
            } else if (gui.getWaitingPanel().isVisible()) {
                gui.changeScene(gui.getWaitingPanel(), gui.getLobbyPanel());


            }
        }


        public void startGame() {
//            gui.setNewRoundReady(false);
            if (myTurn) {
                String senderID = "startGame";
                try {
                    outputStream.writeObject(senderID);

                } catch (IOException ex) {
                    System.out.println("CLIENT: IOException from startGame()");
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }else
            if(gui.getLobbyPanel().isVisible()){
                gui.changeScene(gui.getLobbyPanel(),gui.getWaitingPanel());
            }
            /**if in lobby, change to waiting for player. */
        }

        public void displayCategoryOptions() throws InterruptedException {
            try {
                listOfCategoryOptions = (List<Kategori>) inputStream.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("CLIENT: IOException from sendPlayerName()");
                throw new RuntimeException(ex);
            }

            gui.categoryListTaxi(listOfCategoryOptions);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (gui.getStatsPanel().isVisible()) {
                gui.changeScene(gui.getStatsPanel(), gui.getCategoryPanel());
            } else
                gui.changeScene(gui.getLobbyPanel(), gui.getCategoryPanel());

            checkGaveUP();
            while (!gui.isSelectedCheck()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            gui.setSelectedCheck(true);
            askForQuestions();
            gui.setSelectedCheck(false);
        }


        public void readQuestions() throws InterruptedException {


            try {
                listOfQuestions = (List<Question>) inputStream.readObject();
                System.out.println("CLIENT: Received List of questions. Questiontext: " + listOfQuestions.get(0).getQuestionText());

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < numberOfQuestions; i++) {
                System.out.println(listOfQuestions.get(i).getAnswer1().getAnswerText());
                System.out.println(listOfQuestions.get(i).getAnswer2().getAnswerText());
                System.out.println(listOfQuestions.get(i).getAnswer3().getAnswerText());
                System.out.println(listOfQuestions.get(i).getAnswer4().getAnswerText());
            }
            gui.questionListTaxi(listOfQuestions);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (myTurn) {
                gui.changeScene(gui.getCategoryPanel(), gui.getPlayPanel());
            } else {
                gui.changeScene(gui.getWaitingPanel(), gui.getPlayPanel());
            }

            sendPlayerResults();
        }

        public void askForQuestions() {

            String senderID = "retrieveQuestions";

            try {
                outputStream.writeObject(senderID);
                outputStream.writeObject(gui.getSelectedItem());
                System.out.println("CLIENT: Skickar tillbaks valda kategorin till server");
                System.out.println("CLIENT: 4 " + gui.getSelectedItem().getCategoryName());

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        public void playersReady() throws InterruptedException {
            System.out.println("CLIENT: Försöker trigga scenbyte");
            checkGaveUP();
            if (!myTurn) {
                if (gui.getStatsPanel().isVisible()) {
                    gui.changeScene(gui.getStatsPanel(), gui.getWaitingPanel());
                }
                gui.changeScene(gui.getLobbyPanel(), gui.getWaitingPanel());
            }
            System.out.println("CLIENT: Scenbyte genomfört--?");


        }

        public void sendPlayerName(String playerName) {
            String senderID = "sendPlayerName";
            try {

                System.out.println("CLIENT: Sending " + senderID + " to server");
                outputStream.writeObject(senderID);
                System.out.println("CLIENT: Efter IDName");
                outputStream.writeObject(playerName);
                System.out.println("CLIENT: efter playerName");

            } catch (IOException ex) {
                System.out.println("CLIENT: IOException from sendPlayerName()");
                ex.printStackTrace();
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void getAndSetOpponentName() { //Skickar förfrågan till server, får tillbaka opponentName

            try {
                while (opponentName.isBlank()) {
                    opponentName = (String) inputStream.readObject();
                }

                System.out.println("CLIENT: Got opponentName from server: " + opponentName);
            } catch (IOException ex) {
                System.out.println("CLIENT: IOException from getOpponentName()- When trying to recieve back name String");
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            gui.getOppName().setText(opponentName);
            gui.getPlayerWaiting().setText(opponentName + " Väljer kategori..");
            gui.getPlayer2Stats().setText(opponentName);
        }



        public void handShake() {
            System.out.println("CLIENT: handshake method is running");
            while (!gui.readyToPlay) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("CLIENT: Gui.isready is now true, continuing");
            String senderID = "handShake";
            try {
                System.out.println("CLIENT: Sending senderID");
                outputStream.writeObject(senderID);
                System.out.println("CLIENT: Efter senderID");

            } catch (IOException ex) {
                System.out.println("CLIENT: IOException from handShake()");
                throw new RuntimeException(ex);
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        public void continueGame() throws InterruptedException {
            System.out.println("CLIENT: Method continueGame is running");
            int[] opponentArray = new int[numberOfQuestions];//gamla
             List<Question> opponentSelectedQuestions =new LinkedList<Question>();


            try {
                opponentScore = (int) inputStream.readObject();
                System.out.println("CLIENT: opponentScore: " + opponentScore);
                opponentArray = (int[]) inputStream.readObject();
                System.out.println("CLIENT: selectedquestionslist  is read");
                System.out.println("CLIENT: Player 1 score is read");

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("CLIENT: syncPlayerResults");
            }

            gui.getOpponentCurrentScore().setText(opponentScore + "/" + gui.rowsToString(numberOfQuestions * numberOfRounds));

            gui.opponentArrayTaxi(opponentArray, opponentScore);


            System.out.println("CLIENT: Nu trycker jag på newTurn()");
            newTurn();
        }

        public void checkGameEnd() {
            if (myTurn) {
                String senderID = "checkGameEnd";
                try {
                    outputStream.writeObject(senderID);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        outputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        public void rePlay(){
            while(!gui.isNextGame()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                handShake();
                System.out.println("Handshake från rePLAY kördes");
            }
        }
        public void newTurn() throws InterruptedException {
            System.out.println("CLIENT: Försöker sätta i while loop på väntan att man klickar newTurnReady");

            while (!gui.isNewTurnReady()) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            System.out.println("CLIENT: Ute från loopen i början av newTurn");
            System.out.println("CLIENT: newTurnReady är: " + gui.isNewTurnReady() + "till false!");
            System.out.println("CLIENT: FLOODGATES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            gui.setNewTurnReady(false);
            System.out.println("CLIENT: " + playerID + " myTurn= " + myTurn);
            myTurn ^= true;
            if (myTurn) {
                System.out.println("CLIENT: " + playerID + " myTurn= " + myTurn);
                startGame();
            } else
                System.out.println("CLIENT: " + playerID + " myTurn= " + myTurn);
//            checkGameEnd();
            playersReady();
        }

        public void sendPlayerResults() throws InterruptedException {

            System.out.println("CLIENT: Waiting for Gui.isReadyToSend to be true");
            checkGaveUP();
            while (!gui.isReadyToSend()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //Väntar på condition i GUI
            }

            System.out.println("CLIENT: isReadyToSend was set to true, continuing");
            System.out.println("CLIENT: Setting ReadyToSend to false.");
            gui.setReadyToSend(false);
            System.out.println("CLIENT: Readytosend is now: " + gui.isReadyToSend());
            String senderID = "sendPlayerResults";

            try {
                System.out.println("CLIENT: Sending senderID: " + senderID);
                outputStream.writeObject(senderID);
                System.out.println("CLIENT: Efter senderID: " + senderID);
                playerScore = gui.user.getScore();
                System.out.println("CLIENT: My playerScore is: " + playerScore);
                outputStream.writeObject(playerScore);
                outputStream.reset();
                outputStream.writeObject(gui.getAnswerResult()); /*ÄNDRA TILL QUESTION LIST**/
                System.out.println("///////////SKICKAR///////////CLIENT: " + Arrays.toString(gui.getAnswerResult()));

                System.out.println("CLIENT: user.getScore: " + gui.user.getScore());
                System.out.println("CLIENT: Efter writeObject enligt playerScore i client: " + playerScore);
            } catch (IOException ex) {
                System.out.println("CLIENT: waitingForUserToAnswer");
                throw new RuntimeException(ex);
            } finally {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            gui.turnCounter++;
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.startGUI();
        c.connectToServer();
    }

}
