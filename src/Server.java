import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Server {
    //TODO: Anropa GUI från Serversida
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(9999);
        System.out.println("Server för Quizkampen kör");
        try {
            Quiz.Player waiting = null;
            Quiz quiz = new Quiz();
            while (true) {
                if (waiting == null) {
                    waiting = quiz.new Player(listener.accept(), " Ett");
                } else {
                    Quiz.Player playerTwo = quiz.new Player(listener.accept(), " Två");
                    waiting.setOpponent(playerTwo);
                    playerTwo.setOpponent(waiting);
                    waiting.start();
                    playerTwo.start();
                }
            }
        } finally {
            listener.close();
        }
    }
}
class Quiz {
    Quiz quiz;

    public Quiz() {
        quiz = new Quiz();
    }

    class Player extends Thread {
        Player opponent;
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String name;
        String answer;
        boolean answered;

        public Player(Socket socket, String name) {
            this.socket = socket;
            this.name = name;
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {

            }
        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }
    }
}

        ///TODO: Hitta ett sätt att hantera frågorna genom ex bool, string anserw om anserw = true eller anserw =
        // TODO: correct answer
        /*public void run() {
            try {

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e){}
            }
        }
    }*/