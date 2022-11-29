//import Questions.Categories.Kategori;
//import Questions.Question;
//
//import javax.swing.*;
//import java.io.*;
//import java.net.Socket;
//import java.util.List;
//
//
//public class Player extends JFrame implements Serializable {
//    private static int playerID;
//    private static int otherPlayer;
//    private static String otherPlayerName;
//    private static String userName;
//    private static List<Kategori> categoryOptions;
//    private static Kategori selectedCategory;
//    static List<Question> listOfQuestions;
//
//    public String getPlayerName() {
//        return userName;
//    }
//
//    private static ClientSideConnection csc;
//
//    public Player(String playerName) throws IOException {
//        userName = playerName;
//        //används för att kalla på inner class.
//    }
//
//    public void connectToServer() {
//        csc = new ClientSideConnection();
//
//    }
//
//    public Kategori getSelectedCategory() {
//        return selectedCategory;
//    }
//
//    public void setSelectedCategory(Kategori selectedCategory) {
//        Player.selectedCategory = selectedCategory;
//    }
//
//    public int getPlayerID() {
//        return playerID;
//    }
//
//    public String getOtherPlayerName() {
//        return otherPlayerName;
//    }
//
//    public List<Question> getListOfQuestions() {
//        return listOfQuestions;
//    }
//
//    public ClientSideConnection getCsc() {
//        return csc;
//    }
//
//    public List<Kategori> getCategoryOptions() {
//        return categoryOptions;
//    }
//
////    public static void setCategoryOptions(List<Kategori> categoryOptions) {
////        this.categoryOptions = categoryOptions;
////    }
//
//    //Client Connection Inner Class
//    static public class ClientSideConnection {
//        private Socket socket;
//        private DataInputStream dataIn;
//        private DataOutputStream dataOut;
//        private BufferedReader inputBuffered;
//        private PrintWriter outputPrint;
//        ObjectInputStream inputStream;
//        ObjectOutputStream outputStream;
//        private boolean idle = true;
//
//        public boolean getIdle() {
//            return idle;
//        }
//
//        public void setIdle(boolean idle) {
//            this.idle = idle;
//        }
//
//        private List<Question> listOfQuestions;
//
//        public List<Question> getListOfQuestions() {
//            return listOfQuestions;
//        }
//
//        public void setListOfQuestions(List<Question> listOfQuestions) {
//            this.listOfQuestions = listOfQuestions;
//        }
//
//
//        public ClientSideConnection() {
//            System.out.println("---Client---");
//            try {
//                socket = new Socket("localhost", 52731);
//                dataIn = new DataInputStream(socket.getInputStream());
//                dataOut = new DataOutputStream(socket.getOutputStream());
//                ////////////////////////////////////////////////////////////////
//
//                inputStream = new ObjectInputStream(socket.getInputStream());
//                outputStream = new ObjectOutputStream(socket.getOutputStream());
//                /////////////////////////////////////////////////////////////////
//
//                playerID = dataIn.readInt(); //Läser in playerID från server
//                //sätter other player beroende på vilket ID man fått.
//                if (playerID == 1) {
//                    otherPlayer = 2;
//                } else {
//                    otherPlayer = 1;
//                }
//
//                System.out.println("My id is: " + playerID);
//                sendUserName(userName);
//                receiveName();
//                System.out.println("Opponent name: " + otherPlayerName);
//
//                if (playerID == 1) {
//
//                    synchronized (this) {
//                        Thread t2 = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                try {
//                                    categoryOptions = ((List<Kategori>) inputStream.readObject());
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                } catch (ClassNotFoundException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                System.out.println("Lyckades sätta kategorier");
//                                System.out.println("Sätter till wait");
//                                waitingForSelection();
//
//                            }
//                        });
//                        t2.start();
//                    }
//
//                    synchronized (this) {
//                        Thread t3 = new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                System.out.println("Mellan waiting waitingForSelection() & waitingForData();");
//                                waitingForData();
//                                System.out.println("Sätter idle till false");
//                                idle = false;
//                                System.out.println("idle är nu " + idle);
//                            }
//                        });
//                        t3.start();
//
//
//                } if (playerID == 2) {
//                    synchronized (this) {
//                        Thread t3 = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                waitingForData();
//                            }
//                        });
//                        t3.start();
//
//                    }
//                }
//            }
//        }catch (IOException e) {
//                System.out.println("IO Exception from CSC Constructor");
//                e.printStackTrace();
//            }
//
//
//        /**
//         * Metod för att skicka till servern vad man har för användarnamn.
//         * <p>
//         * Inparaametern ska vara det som man har valt i GUI't.
//         */
//        }
//        public void sendUserName(String chosenName) { //Inparameter är namnet som använder väljer i GUI't.
//
//            try {
//                System.out.println("Försöker skicka namn, " + chosenName + " till server");
//                dataOut.writeUTF(chosenName);
//                System.out.println("Skickat namnet");
//                dataOut.flush();
//
//            } catch (IOException ex) {
//                System.out.println("IOException from sendUserName() CSC");
//            }
//
//        }
//
//
//        public void waitingForData() {
//            System.out.println("Client waitingForData()");
//            String s = "";
//
//            while (s.isBlank()) {
//                System.out.println("waitingForData()-- Whileloop startar");
//                try {
//                    System.out.println("Innanför try-catch");
//                    s = (String) inputStream.readObject();
//                    System.out.println("1 " + s);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            switch (s) {
//                case "Questions":
//                    try {
//                        System.out.println("Case Questions running");
//                        listOfQuestions = (List<Question>) inputStream.readObject();
//                        //                    s = String.valueOf(inputStream.readObject());
//                        System.out.println("Switch confirm2");
//                        break;
//                    } catch (IOException e) {
//                        e.printStackTrace(System.out);
//                        throw new RuntimeException(e);
//                    } catch (ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//            }
//        }
//
//
//        public void receiveName() {
//            String opponentName = "";
//            while (opponentName.equals("")) {
//                try {
//                    opponentName = dataIn.readUTF();
//                    System.out.println("player:" + otherPlayer + " sent username:" + opponentName);
//                } catch (IOException ex) {
//                    System.out.println("IOException from receiveName() csc");
//                }
//            }
//            otherPlayerName = opponentName;
//        }
//
//        public void getSelectedItem() {
//
//        }
//
//        public void closeConnection() {
//            try {
//                socket.close();
//                System.out.println("Connection closed");
//            } catch (IOException ex) {
//                System.out.println("IOException on closeConnection");
//            }
//        }
//
//        public void sendListBackToServer(List<Kategori> selectedList, Kategori selectedItem) {
//            System.out.println("1 ---Försöker--- skicka lista & kategori till server");
//
//            String senderString = "sendListBackToServer";
//            try {
//                System.out.println("2 " + senderString);
//                outputStream.writeObject(senderString);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            try {
//                outputStream.writeObject(selectedList);
//                System.out.println("3 " + selectedList.get(0).getCategoryName());
//
//                outputStream.writeObject(selectedItem);
//                System.out.println("4 " + selectedItem.getCategoryName());
//
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            System.out.println("Skickade  lista & kategori till servern.");
//        }
//
//        //Väntar på att användare väljer kategori.  Skickar sedan info till servern. Nullar selectedCategory efter skickat till server.
//        public void waitingForSelection() {
//
//            while (selectedCategory == null) {
//            }
//            sendListBackToServer(categoryOptions, selectedCategory);
//
//        }
//    }
//}
//
//
////    public static void main(String[] args) throws IOException {
//////        Player p = new Player();
//////        p.setUpGUI();
////        p.connectToServer(); //Göra så "till lobby kör denna metod?
//////        p.csc.sendUserName("Test Arne");
////
//////        p.setUpButtons();
////
////    }
