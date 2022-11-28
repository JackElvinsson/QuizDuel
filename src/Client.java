import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client extends JFrame implements Serializable {


    private int playerID;
    private int opponentID;
    private String playerName;
    private String opponentName;
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

    private class ClientSideConnection {
        private Socket socket;

        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;


        public ClientSideConnection() {
            System.out.println("---Client---");
            try (Socket socket = new Socket("localhost", 52731);
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());) {
                playerID = inputStream.readInt();
            } catch (IOException e) {
                System.out.println("IO Exception from CSC Constructor");
            }
        }



        public void listeningPostClient(){
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

        public void getAndSetOpponentName() {
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
        }


        public static void main(String[] args) throws IOException {
            Client c = new Client();
            c.connectToServer();
            c.startGUI();
        }

    }
