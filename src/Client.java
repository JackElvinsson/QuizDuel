import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread implements Runnable {

    private PrintWriter out;
    BufferedReader in;
    String name;
    String answer;


    public void run() {
        try {
            Socket client = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            int i = 0;
            String inServer;
            String question = "";
            String answerOne = "";
            String answerTwo = "";
            String answerThree = "";
            String answerFour = "";
            boolean roundOver = false;

            // TODO: anropa gui från client
            // TODO: Frågor


            while ((inServer = in.readLine()) != null) {
                if (inServer.contains("Player")) {
                    name = inServer.split(":")[1];
                }

                if (inServer.contains("Resultat:")){
                    JOptionPane.showMessageDialog(null, inServer);
                }
                //TODO Markera frågor och rätt svarsalternativ.
                if (inServer.contains("?")) {
                    roundOver = true;
                    question = inServer;
                    continue;
                }
                if (roundOver && i == 0) {
                    answerOne = inServer;
                    i++;
                } else if (roundOver && i == 1) {
                    answerTwo = inServer;
                    i++;
                } else if (roundOver && i == 2) {
                    answerThree = inServer;
                    i++;
                } else if (roundOver && i == 3) {
                    answerFour = inServer;
                    i++;
                    // Anrop metod med förslagsvis GUI?
                    game(question, answerOne, answerTwo, answerThree, answerFour, out);
                } else {
                    roundOver = false;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();


        }}public void game(String question,String answerOne,String answerTwo,String answerThree, String answerFour, PrintWriter out){

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                answer = ((JButton) e.getSource()).getText();
                out.println(name + ":" + answer);
            }
        };
    }
}





