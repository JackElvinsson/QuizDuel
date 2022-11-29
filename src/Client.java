import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame implements Serializable {


    private int playerID;
    private int opponentID;
    private String playerName;
    private String opponentName;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private GUI gui;
    private ClientSideConnection csc;

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


        public ClientSideConnection() {
            System.out.println("---Client---");
            try {

                socket = new Socket("localhost", 52731);
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                    playerID = inputStream.readInt();
                    System.out.println("I am playerID:" + playerID);


                    synchronized (this) {
                        while (gui.user.getName().isBlank()) {
                            wait(800);
                        }
                        setPlayerName(gui.user.getName());
                    }

                System.out.println("namnet är satt: " + playerName);
            } catch (UnknownHostException | InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            sendPlayerName(getPlayerName());

            Thread clientListenerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    listeningPostClient();
                }
            });
            clientListenerThread.start();

        }


        public void listeningPostClient() {
            while (true) {
                System.out.println("listeningPostClient är aktiv");
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
                    case "playersReady":
                        System.out.println("Kallar på metoden playersReady() från switch-case.");
                        playersReady();
                        System.out.println("Metoden är kallad");
                        break;
                    case "getCategoryOptionsList":
                        displayCategoryOptions();
                        break;
                    case "Questions":
                        readQuestions();
                        break;
                }
            }
        }

        public void startGamePlayer1() {
            if (playerID == 1) {
                String senderID = "startGamePlayer1";
                try {
                    outputStream.writeObject(senderID);

                } catch (IOException ex) {
                    System.out.println("IOException from startGamePlayer1()");
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        public void displayCategoryOptions() {
            try {
                listOfCategoryOptions = (List<Kategori>) inputStream.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("IOException from sendPlayerName()");
                throw new RuntimeException(ex);
            }

            gui.categoryListTaxi(listOfCategoryOptions);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gui.changeScene(gui.getLobbyPanel(), gui.getCategoryPanel());

            while (!gui.isSelectedCheck()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            gui.setSelectedCheck(true);
            askForQuestions();

        }


        public void readQuestions() {


            try {
                listOfQuestions = (List<Question>) inputStream.readObject();
                System.out.println("Received List of questions. Questiontext: " +listOfQuestions.get(0).getQuestionText());

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
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
        }

        public void askForQuestions() {

            String senderID = "retrieveQuestions";

            try {
                outputStream.writeObject(senderID);
                outputStream.writeObject(gui.getSelectedItem());
                System.out.println("Skickar tillbaks valda kategorin till server");
                System.out.println("4 " + gui.getSelectedItem().getCategoryName());

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

        public void playersReady() {
            System.out.println("Försöker trigga scenbyte");
            gui.changeScene(gui.getLobbyPanel(), gui.getWaitingPanel());
            System.out.println("Scenbyte genomfört--?");


        }

        public void sendPlayerName(String playerName) {
            String senderID = "sendPlayerName";
            try {

                System.out.println("Sending " + senderID + " to server");
                outputStream.writeObject(senderID);
                System.out.println("Efter IDName");
                outputStream.writeObject(playerName);
                System.out.println("efter playerName");

            } catch (IOException ex) {
                System.out.println("IOException from sendPlayerName()");
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
            String senderIDName = "getOpponentName";
            String opponentName = "";

            try {
                while (opponentName.isBlank()) {
                    opponentName = (String) inputStream.readObject();
                }

                System.out.println("Got opponentName from server: " + opponentName);
            } catch (IOException ex) {
                System.out.println("IOException from getOpponentName()- When trying to recieve back name String");
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            gui.getOppName().setText(opponentName);

        }

    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.startGUI();
        c.connectToServer();

    }

    }
