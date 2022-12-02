
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class ReceptionServer extends Thread {



        public static void main(String[] args) throws IOException {
            int numPlayers=0;
            Properties p = new Properties();
            ServerSocket ss = new ServerSocket(52731);

            try{
                p.load(new FileInputStream("src/Server/Settings.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            int numberOfRounds = Integer.parseInt(p.getProperty("numberOfRounds", "2"));
            int numberOfQuestions = Integer.parseInt(p.getProperty("numberOfQuestions", "2"));



            System.out.println("Property file read");

            System.out.println("Server is running...");

            try {
                while(true) {
                    /**
                     *
                     *
                     * */
                    Serverplayer player1 = new Serverplayer(ss.accept(), 1);
                    System.out.println("Player 1 has connected");
                    Serverplayer player2 = new Serverplayer(ss.accept(), 2);
                    System.out.println("Plyer 2 has connected");
                    Gameserver g=new Gameserver(player1, player2, numberOfQuestions,numberOfRounds);
                    System.out.println("gameServer created");
                Thread t=new Thread(g);
                t.start();
                    System.out.println("GameServer started");
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOEXCEPTION from acceptConnections()");
            }finally {
                ss.close();
            }
        }




    }




