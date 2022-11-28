import Questions.Categories.Kategori;
import Questions.Question;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    public String player1Name = "";
    public String player2Name = "";

    public boolean player1Wait = true;
    public boolean player2Wait = true;


    public void setPlayer1Wait(boolean wait) {
        this.player1Wait = wait;
    }

    public boolean getPlayer1Wait() {
        return player1Wait;
    }

    public void setPlayer2Wait(boolean wait) {
        this.player2Wait = wait;
    }

    public boolean getPlayer2Wait() {
        return player2Wait;
    }

    GameInit gameInit = new GameInit();
    private List<Question> listOfQuestions;
    private List<Kategori> categoryOptions = gameInit.getCategoryOptions();


    public GameServer() throws IOException {
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

        CountDownLatch countDownLatch = new CountDownLatch(1);
        private Socket socket;
        private DataOutputStream dataOut;
        private DataInputStream dataIn;
        private BufferedReader buffIn;
        private PrintWriter buffOut;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        private int playerID;
        private boolean idle = true;

        public boolean getIdle() {
            return idle;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }


        public ServerSideConnection(Socket s, int id) { //Constructor.

            socket = s;
            playerID = id;

            try {

                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());



            } catch (IOException e) {
                System.out.println("IO Exception from SSC Constructor");
            } finally {
                try {
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void run() { //separat run tråd för spelarna.
            try {

                dataOut.writeInt(playerID);   //skickar INT med vilket ID man fått. 1 || 2 till client.
                System.out.println("Skickat playerID:" + playerID);
//                countDownLatch.await();
                //setname logiken.
                while (true) {
                    if (playerID == 1) {
                        player1Name = dataIn.readUTF();
                        System.out.println("Player 1 name is set to: " + player1Name);
                        break;
                    } else if (playerID == 2) {
                        player2Name = dataIn.readUTF();
                        System.out.println("Player 2 name is set to: " + player2Name);
                        break;
                    }
                }

                synchronized (this) {
                    while (player2Name.isBlank()) {
                        wait(1500);
                        System.out.println("playerID " + playerID + " waited 1,5sec");
                    }
                    System.out.println("Player " + playerID + " continues");
                }


                System.out.println("ID:" + playerID + " is Sending username to opponent");
                sendUserName();


                if (playerID == 1) {

                    sendListOfCategoryOptions(categoryOptions);

                    System.out.println("Försöker köra waitingForData()");
                    waitingForDataServer();
                    System.out.println("waitingForData(); är klar");
                    System.out.println("Försöker köra stopOpponentIdle");

                    stopOpponentIdle();
                    System.out.println("Klar med stopOpponentIdle()");

                } else { //Player 2 väntar.
                    idleMe();
                }


                sendQuestions();
                System.out.println("Klar med sendQuestions()");

            } catch (IOException e) {
                System.out.println("IOException from run() SSC");
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        public void setName() throws IOException {
            while (true) {
                if (playerID == 1) {
                    player1Name = dataIn.readUTF();
                    System.out.println("Player 1 name is set to: " + player1Name);
                    break;
                } else if (playerID == 2) {
                    player2Name = dataIn.readUTF();
                    System.out.println("Player 2 name is set to: " + player2Name);
                    break;
                }
            }
        }

        public void sendListOfCategoryOptions(List<Kategori> optionsList) {
            try {
                oos.writeObject(optionsList);
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(0).getCategoryName());
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(1).getCategoryName());
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(2).getCategoryName());
                System.out.println("sendListOfCategoryOptions: " + optionsList.get(3).getCategoryName());


            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("IOException from sendListOfCategoryOptions");
            } finally {
                try {
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        public void sendUserName() { //skickar useName till server
            String opponentName = "";
            System.out.println("Player one: " + player1Name + "\nPlayer two: " + player2Name);
            if (playerID == 1) {
                opponentName = player2Name;
            } else if (playerID == 2) {
                opponentName = player1Name;
            }
            try {
                System.out.println("Sending opponentName from sendUserName(), opponentName sent: " + opponentName + " and player ID is: " + playerID);
                dataOut.writeUTF(opponentName);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendUserName");
            }
        }

        public void syncQuestionList(List<Kategori> selectedList, Kategori selectedItem) {
            gameInit.makeNotChosenCategoryAvailable(selectedList, selectedItem);
            System.out.println("Efter makeNotChosenCategoryAvailable");
            System.out.println("Selecteditem qText " + selectedItem.getListOfQuestions().get(0).getQuestionText());
            System.out.println("gameInit qText " + gameInit.getSelectedCategory().getListOfQuestions().get(0).getQuestionText());
            try {
                listOfQuestions = gameInit.getQuestions(gameInit.selectedCategory, 1);
                System.out.println("ListofQuestions = gameInit.getQuestions(gameInit.selectedCategory. Fråga 1 i frågelistan: " + listOfQuestions.get(0).getQuestionText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //TODO Lägg till hantering av antal frågor ifrån properties
        }


        public void sendQuestions() {
            System.out.println("Trying to send questions");
            String senderString = "Questions";
            try {
                oos.writeObject(senderString);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
//
//              dataOut.writeUTF(senderString);
            try {
                oos.writeObject(listOfQuestions);

                System.out.println("skickade sträng:" + senderString);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Questions sent, after outpustream. First question, index 0: " + listOfQuestions.get(0).getQuestionText());
        }

        public void waitingForDataServer() {
            System.out.println(playerID + " is waiting for data");
            String s = "";

            while (s.isBlank()) {
                try {
                    s = (String) ois.readObject();

                    System.out.println("1 " + s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (s.equals("sendListBackToServer")) {
                    try {
                        System.out.println("Försöker ta emot lista och objekt");
                        List<Kategori> selectedList = (List<Kategori>) ois.readObject();
                        System.out.println("fått tillbaka listan med kategorier.");

                        Kategori selectedItem = (Kategori) ois.readObject();
                        System.out.println("Tagit emot 'selectedItem' försöker sätta ChosenCategory");
                        System.out.println("selectedItem är: " + selectedItem.getCategoryName());

                        gameInit.setSelectedCategory(selectedItem);
                        System.out.println("ChosenCategory satt");
                        syncQuestionList(selectedList, selectedItem);
                        System.out.println("Har synkat frågelistan");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        public void idleMe() {
            System.out.println("PlayerID: " + playerID + " is now idle");
            while (idle) {
            }
            System.out.println("PlayerID " + playerID + " I'm awake! I'm awake!");
        }


        public void stopOpponentIdle() {
            if (playerID == 1) {
                if (player2Wait == true) {
                    setPlayer2Wait(false);
                    player2.setIdle(false);
                }
            }
            if (playerID == 2) {
                if (player1Wait == true) {
                    setPlayer1Wait(false);
                    player1.setIdle(false);
                }
            }
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
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}

