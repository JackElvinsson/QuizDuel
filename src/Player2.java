import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;


public class Player2 extends JFrame {
    private int width;
    private int height;
    private Container contentpane;
    private JTextArea message;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private int playerID;
    private int otherPlayer;
    private int[] values;
    private int maxTurns;
    private int turnsMade;
    private int myPoints;
    private int enemyPoints;
    private boolean buttonsEnabled;

    private String otherPlayerName;


    private ClientSideConnection csc;

    public Player2() throws IOException {
        //används för att kalla på inner class.


    }


    public void connectToServer() {
        csc = new ClientSideConnection();

    }


    //Client Connection Inner Class
    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private BufferedReader inputBuffered;
        private PrintWriter outputPrint;


        public ClientSideConnection() {
            System.out.println("---Client---");
            try {
                socket = new Socket("localhost", 52734);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                ////////////////////////////////////////////////////////////////
                inputBuffered = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                outputPrint = new PrintWriter(socket.getOutputStream(), true);
                /////////////////////////////////////////////////////////////////


                playerID = dataIn.readInt(); //Läser in playerID från server
                //sätter other player beroende på vilket ID man fått.
                if (playerID == 1) {
                    otherPlayer = 2;
                } else {
                    otherPlayer = 1;
                }

                System.out.println("My id is: " + playerID);
                sendUserName("BEATA");
                receiveName();

                System.out.println("Opponent name: " + otherPlayerName);


            } catch (IOException e) {
                System.out.println("IO Exception from CSC Constructor");
            }
        }


        /**
         * Metod för att skicka till servern vad man har för användarnamn.
         * <p>
         * Inparaametern ska vara det som man har valt i GUI't.
         */

        public void sendUserName(String chosenName) { //Inparameter är namnet som använder väljer i GUI't.

            try {
                System.out.println("Försöker skicka namn, " + chosenName + " till server");
                dataOut.writeUTF(chosenName);
                System.out.println("Skickat namnet");
                dataOut.flush();

            } catch (IOException ex) {
                System.out.println("IOException from sendUserName() CSC");
            }

        }


        public void receiveName() {
            String opponentName = "";
            while (opponentName.equals("")) {
                try {
                    opponentName = dataIn.readUTF();
                    System.out.println("player:" + otherPlayer + " sent username:" + opponentName);
                } catch (IOException ex) {
                    System.out.println("IOException from receiveName() csc");
                }
            }
            otherPlayerName = opponentName;
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

    public static void main(String[] args) throws IOException {
        Player2 p = new Player2();
//        p.setUpGUI();
        p.connectToServer(); //Göra så "till lobby kör denna metod?
//        p.csc.sendUserName("Test Arne");

//        p.setUpButtons();

    }

}
