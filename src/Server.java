import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Server {
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
        public void run(){
            try{
                String correctAnswer = "";
                out.println("Player:" + name);
                out.println(database.getQuestion().getQuestion());
                for(Database.Answer answer : database.getQuestion().answers) {
                    if(answer.correct) {
                        correctAnswer = answer.answer;
                    }
                    out.println(answer.answer);
                }

                while (true) {
                    String command = in.readLine();
                    if(command.contains(name + ":")) {
                        answered = true;
                        answer = command.split(":")[1];
                    }
                    if (answered && opponent.answered) {
                        String[] info = command.split(":");
                        if (answer.equals(correctAnswer) && opponent.answer.equals(correctAnswer)) {
                            out.println("Resultat: LIKA");
                            opponent.out.println("Resultat: LIKA");
                        } else if(answer.equals(correctAnswer) && !opponent.answer.equals(correctAnswer)) {
                            out.println("Resultat: DU VANN");
                            opponent.out.println("Resultat: DU FÖRLORADE");
                        } else if(!answer.equals(correctAnswer) && opponent.answer.equals(correctAnswer)) {
                            out.println("Resultat: DU FÖRLORADE");
                            opponent.out.println("Resultat: DU VANN");
                        } else {
                            out.println("Resultat: DU FÖRLORADE");
                            opponent.out.println("Resultat: DU FÖRLORADE");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }
    }
}
