import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JPanel mainPanel;
    private JPanel startPanel;
    private JPanel lobbyPanel;
    protected JPanel playPanel;
    private JPanel categoryPanel;
    private JPanel waitingPanel;
    private JButton tillLobbyButton;
    private JButton avslutaButton;
    private JTextField textField1;
    private JButton spela;
    private JButton lamnaLobby;
    private JLabel userName;
    private JLabel oppName;
    private JLabel userWins;
    private JLabel oppWins;
    private JButton firstCategory;
    private JButton secondCategory;
    private JButton thirdCategory;
    protected JProgressBar progressBar;
    private JTextArea playTextArea;
    private JButton firstAnswer;
    private JButton secondAnswer;
    private JButton thirdAnswer;
    private JButton fourthAnswer;
    private JButton waitingPanelGiveUp;
    private JLabel playerWaiting;
    private JPanel statsPanel;
    private JButton continueGame;
    private JButton statsPanelGiveUp;
    private JPanel statsGridPlayer;
    private JPanel statsGridOpponent;
    private JLabel playerCurrentScore;
    private JLabel opponentCurrentScore;
    protected int answerCounter = 0;

    int rows = 4;
    int columns = 3;
    List<Integer> listOfNumbers;
    JTextArea[][] boxArray;
    int[][] statsGrid;

    protected User user = new User();

    GUI() {

        // Ändrar utseende
//        try {
//            UIManager.setLookAndFeel(new SynthLookAndFeel());
//        }catch (UnsupportedLookAndFeelException e) {
//            System.out.println("Ett fel inträffade");
//        }

        // Add panels to frame
        setLayout(new FlowLayout());
        add(mainPanel);
        startPanel.setVisible(false);
        lobbyPanel.setVisible(false);
        categoryPanel.setVisible(false);
        playPanel.setVisible(false);
        waitingPanel.setVisible(false);
        statsPanel.setVisible(true);

        // Stats gridPanel test
        statsGridPlayer.setLayout(new GridLayout(rows, columns, 10, 10));
        statsGridOpponent.setLayout(new GridLayout(rows, columns, 10, 10));
        statsGridPlayer.setForeground(new java.awt.Color(144, 184, 252));
        statsGridOpponent.setForeground(new java.awt.Color(144, 184, 252));

        listOfNumbers = generateListOfNumbers();
        statsGrid = generateBoardArray(listOfNumbers);
        boxArray = generateButtonArray(statsGrid);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Action listeners

        avslutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setName(textField1.getText());

            }
        });

        tillLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                userName.setText(user.getName());

                if (user.getName().equals("")) {
                    JOptionPane.showMessageDialog(null, "Välj ett namn och tryck\nenter för att registrera", "Meddelande", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    startPanel.setVisible(false);
                    lobbyPanel.setVisible(true);
                }
            }
        });

        spela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                lobbyPanel.setVisible(false);
                categoryPanel.setVisible(true);

//                if (isChoosing) {
                lobbyPanel.setVisible(false);
                categoryPanel.setVisible(true);

                //TODO Uppdatera kategorialternativ

//                } else {
//                    lobbyPanel.setVisible(false);
//                    waitingPanel.setVisible(true);
//                }

                //TODO: Nästa spelare får välja kategori
                //TODO: Spelare som ej väljer hamnar i waitingPanel

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox


            }
        });
        lamnaLobby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lobbyPanel.setVisible(false);
                startPanel.setVisible(true);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        firstCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryPanel.setVisible(false);
                playPanel.setVisible(true);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        secondCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryPanel.setVisible(false);
                playPanel.setVisible(true);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        thirdCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryPanel.setVisible(false);
                playPanel.setVisible(true);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        firstAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                answerCounter++;

                if (endOfRound(answerCounter)) {
                    playPanel.setVisible(false);
                    statsPanel.setVisible(true);

                    //TODO: Gå till statsPanel
                } else {

                    //TODO: Uppdatera Fråga och svarsalternativ
                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
                }
            }
        });
        secondAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                answerCounter++;

                if (endOfRound(answerCounter)) {
                    playPanel.setVisible(false);
                    statsPanel.setVisible(true);

                    //TODO: Gå till statsPanel
                } else {

                    //TODO: Uppdatera Fråga och svarsalternativ
                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
                }
            }
        });
        thirdAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                answerCounter++;

                if (endOfRound(answerCounter)) {
                    playPanel.setVisible(false);
                    statsPanel.setVisible(true);

                    //TODO: Gå till statsPanel
                } else {

                    //TODO: Uppdatera Fråga och svarsalternativ
                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
                }
            }
        });
        fourthAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                answerCounter++;

                if (endOfRound(answerCounter)) {
                    playPanel.setVisible(false);
                    statsPanel.setVisible(true);

                    //TODO: Gå till statsPanel
                } else {

                    //TODO: Uppdatera Fråga och svarsalternativ
                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
                }
            }
        });

        waitingPanelGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                waitingPanel.setVisible(false);
                lobbyPanel.setVisible(true);

                //TODO: Utdelning av poäng till motståndare om en användare väljer att ge upp

            }
        });
        continueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                if (isChoosing) {
                statsPanel.setVisible(false);
                categoryPanel.setVisible(true);

                //TODO Uppdatera kategorialternativ

//                } else {
//                    startPanel.setVisible(false);
//                    waitingPanel.setVisible(true);
//                }

                //TODO: Nästa spelare får välja kategori
                //TODO: Spelare som ej väljer hamnar i waitingPanel

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        statsPanelGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }


    public boolean endOfRound(int answerCounter) {
        return answerCounter >= 3;
    }

    public List<Integer> generateListOfNumbers() {

        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }

        return listOfNumbers;
    }

    public int[][] generateBoardArray(List<Integer> listOfNumbers) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
        }

        return gameBoardArray;
    }

    public JTextArea[][] generateButtonArray(int[][] statsGrid) {
        JTextArea[][] boxArray = new JTextArea[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boxArray[i][j] = new JTextArea();
//                boxArray[i][j].addActionListener(statsGridListener);
                boxArray[i][j].setBackground(new java.awt.Color(0, 102, 204));
                boxArray[i][j].setFocusable(false);


                statsGridPlayer.add(boxArray[i][j]);
                statsGridOpponent.add(boxArray[i][j]);
            }
        }
        return boxArray;
    }
//    public boolean countDown() {
//
//        int counter = 100;
//        boolean timeOut = false;
//
//        while (!timeOut) {
//
//            if (counter > 0) {
//
//                progressBar.setValue(counter);
//
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                counter -= 1;
//                System.out.println(counter);
//
//            } else {
//                timeOut = true;
//            }
//
//            }
//        return timeOut;
//    }
}
