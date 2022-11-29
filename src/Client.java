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

                    System.out.println("IO Exception from CSC Constructor");

                } catch (UnknownHostException e) {
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
            while(true){
            String postIdentifier = "";  //postIdentifier skickas av objektstream från server.
            while (postIdentifier.isBlank()) {
                try {
                    postIdentifier = (String) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            switch (postIdentifier) {
                case "hej":

                    break;
                case "123":

                    break;
            }

        }

        public void sendPlayerName(String playerName) {
            String senderIDName = "sendPlayerName";
            try {
                System.out.println("Sending " + senderIDName + " to server");
                outputStream.writeObject(senderIDName);
                outputStream.writeObject(playerName);
            } catch (IOException ex) {
                System.out.println("IOException from sendPlayerName()");
                throw new RuntimeException(ex);
            }

        }

        public void getAndSetOpponentName() { //Skickar förfrågan till server, får tillbaka opponentName
            String senderIDName = "getOpponentName";
            String opponentName = "";

            try {
                System.out.println("Sending " + senderIDName + " to server");
                outputStream.writeObject(senderIDName);
            } catch (IOException ex) {
                System.out.println("IOException from getOpponentName()- When sending request string, senderIDName, to server");
                throw new RuntimeException(ex);
            }

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
            setOpponentName(opponentName);
            gui.getOppName().setText(opponentName);

        }

    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.startGUI();
        c.connectToServer();

    }

    }
