import Questions.Categories.Kategori;
import Questions.Question;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;


public class Player extends JFrame implements Serializable {
    private static int playerID;
    private static int otherPlayer;
    private static String otherPlayerName;
    private static String userName;
    private static List<Kategori> categoryOptions;

    private static Kategori selectedCategory;

    List<Question> listOfQuestions;

    public String getPlayerName() {
        return userName;
    }

    private static ClientSideConnection csc;

    public Player(String playerName) throws IOException {
        userName = playerName;
        //används för att kalla på inner class.
    }

    public void connectToServer() {
        csc = new ClientSideConnection();

    }

    public Kategori getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Kategori selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getOtherPlayerName() {
        return otherPlayerName;
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public ClientSideConnection getCsc() {
        return csc;
    }

    public List<Kategori> getCategoryOptions() {
        return categoryOptions;
    }

//    public static void setCategoryOptions(List<Kategori> categoryOptions) {
//        this.categoryOptions = categoryOptions;
//    }

    //Client Connection Inner Class
    static public class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private BufferedReader inputBuffered;
        private PrintWriter outputPrint;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;


        public ClientSideConnection() {
            System.out.println("---Client---");
            try {
                socket = new Socket("localhost", 52731);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                ////////////////////////////////////////////////////////////////

                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                /////////////////////////////////////////////////////////////////

                playerID = dataIn.readInt(); //Läser in playerID från server
                //sätter other player beroende på vilket ID man fått.
                if (playerID == 1) {
                    otherPlayer = 2;
                } else {
                    otherPlayer = 1;
                }

                System.out.println("My id is: " + playerID);
                sendUserName(userName);
                receiveName();
                System.out.println("Opponent name: " + otherPlayerName);

                if (playerID == 1) {
                    categoryOptions = ((List<Kategori>) inputStream.readObject());
                    System.out.println("Lyckades sätta kategorier");
                    synchronized (this) {
                        System.out.println("Sätter till wait");
//                        if(selectedCategory==null) {
//                            wait(3000);
//                        }

                        Thread t2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                waitingForSelection();
                            }
                        });
                        t2.start();
//                        waitingForSelection();
                    }
                }


                /**
                 if id = 2 wait for turn
                 1 = lyssna efter objekt (listan)
                 1. Tar emot objekt, svarar med lista + objekt.
                 3. server tar emot lista & objekt (Servern markerar plyerID med att ha valt kategori)
                 wait for turn

                 server skickar till båda player. Lista med frågor
                 (Serverside, gör om så att om du inte har valt, så ska du välja nästa.)
                 player svarar på frågor, skickar tillbaka resultat.
                 1. 0/1

                 wait for turn(results)
                 Wait for turn(kategori)

                 */


            } catch (IOException e) {
                System.out.println("IO Exception from CSC Constructor");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
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

//        public void waitforlist() {
//
//            if (playerID==1){
//                categoryOptions=((List<Kategori>) inputStream.readObject());
//                System.out.println("Lyckades sätta kategorier");
//                synchronized(this){
//                    System.out.println("Sätter till wait");
//                    if(selectedCategory==null) {
//                        wait(3000);
//                    }
//                    waitingForSelection();
//                }
//            }
//        }


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

        public void getSelectedItem(){

        }

        public void closeConnection() {
            try {
                socket.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException on closeConnection");
            }
        }

        public void sendListBackToServer(List<Kategori> selectedList, Kategori selectedItem ) {
            System.out.println("---Försöker--- skicka lista & kategori till server");
            try {
                outputStream.writeObject(senderString);
                System.out.println(senderString);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                outputStream.writeObject(selectedList);
                System.out.println(selectedList.get(0).getCategoryName());

                outputStream.writeObject(selectedItem);
                System.out.println(selectedItem.getCategoryName());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            System.out.println("Skickade till lista & kategori servern.");
        }

        //Väntar på att användare väljer kategori.  Skickar sedan info till servern. Nullar selectedCategory efter skickat till server.
        public void waitingForSelection() {

            while (selectedCategory == null) {
            }
            sendListBackToServer(categoryOptions, selectedCategory);
        }
    }
}

//    public static void main(String[] args) throws IOException {
////        Player p = new Player();
////        p.setUpGUI();
//        p.connectToServer(); //Göra så "till lobby kör denna metod?
////        p.csc.sendUserName("Test Arne");
//
////        p.setUpButtons();
//
//    }
