import Questions.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread implements Runnable {

    PrintWriter out;
    BufferedReader in;
    String name;
    String answer;
    JFrame frame;
    public Client(JFrame frame){
        this.frame = frame;
    }


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

            // TODO: Frågor


            while ((inServer = in.readLine()) != null) {
                if (inServer.contains("Player")) {
                    name = inServer.split(":")[1];
                }

                if (inServer.contains("Resultat:")) {
                    JOptionPane.showMessageDialog(null, inServer);
                }
                if (inServer.contains("*")) {
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

                    quiz(question, answerOne, answerTwo, answerThree, answerFour, out);
                } else {
                    roundOver = false;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


        public void quiz(String q, String answerOne, String answerTwo, String answerThree, String answerFour, PrintWriter out) {
            // Uppdaterar vy
            frame.getContentPane().removeAll();
            //GUI för användare.
            JPanel main = new JPanel();
            main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
            JPanel p0 = new JPanel();
            p0.setLayout(new GridLayout(2, 2));
            JLabel question = new JLabel("Question");
            question.setHorizontalAlignment(JLabel.CENTER);
            question.setFont(new Font("Serif", Font.BOLD, 20));
            p0.setBackground(Color.GRAY);
            JPanel p00 = new JPanel();
            p00.setLayout(new GridLayout(2, 2));
            p00.setBackground(Color.GRAY);
            JLabel text = new JLabel(q);
            text.setHorizontalAlignment(JLabel.CENTER);
            text.setBackground(Color.GRAY);
            JPanel p000 = new JPanel();
            p000.setBackground(Color.GRAY);
            JButton buttonOne = new JButton(answerOne);
            buttonOne.setHorizontalAlignment(JLabel.RIGHT);
            buttonOne.setBackground(Color.GRAY);
            JButton buttonTwo = new JButton(answerTwo);
            buttonTwo.setHorizontalAlignment(JLabel.LEFT);
            buttonTwo.setBackground(Color.GRAY);
            JPanel p0000 = new JPanel();
            p0000.setBackground(Color.GRAY);
            JButton buttonThree = new JButton(answerThree);
            buttonThree.setHorizontalAlignment(JLabel.RIGHT);
            buttonThree.setBackground(Color.GRAY);
            JButton buttonFour = new JButton(answerFour);
            buttonFour.setHorizontalAlignment(JLabel.LEFT);
            buttonFour.setBackground(Color.GRAY);
            buttonFour.setSize(60,60);

            p0.add(question);
            p00.add(text);
            p000.add(buttonOne);
            p000.add(buttonTwo);
            p0000.add(buttonThree);
            p0000.add(buttonFour);
            main.add(p0);
            main.add(p00);
            main.add(p000);
            main.add(p0000);
            frame.add(main);

            // AL reg tryckt knapp i GUI, skickar därefter svar till sever.
            ActionListener listener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    answer = ((JButton)e.getSource()).getText();
                   // Skickar klietnens namn till server efter klientens musval.
                    out.println(name + ":" + answer);
                }
            };
            buttonOne.addActionListener(listener);
            buttonTwo.addActionListener(listener);
            buttonThree.addActionListener(listener);
            buttonFour.addActionListener(listener);

            frame.getContentPane().validate();
            frame.getContentPane().repaint();
        }
    }










