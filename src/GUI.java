import java.util.ArrayList;
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
    int rows = 4;
    int columns = 3;
    protected int questionsPerRound = columns;
    protected int categoriesPerRound = rows;
    protected int answerCounter = 0;
    protected int categoryCounter = 0;
    List<Integer> integerList;
    JTextArea[][] boxGrid;
    JTextArea[][] boxGrid2;
    int[][] answerArray;

    protected User user = new User();

    GUI() {

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

        integerList = generateIntegerList();
        answerArray = generateAnswerArray(integerList);
        boxGrid = generateBoxGrid(answerArray);
        boxGrid2 = generateBoxGrid2(answerArray);

        // colorTest
//        boxGrid[0][1].setBackground(Color.red);
//        boxGrid2[0][2].setBackground(Color.red);

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
                    changeScene(startPanel, lobbyPanel);
                }
            }
        });

        spela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                changeScene(lobbyPanel, categoryPanel);

//                if (isChoosing) {
//                lobbyPanel.setVisible(false);
//                categoryPanel.setVisible(true);

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
                changeScene(lobbyPanel, startPanel);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        firstCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryCounter++;
                changeScene(categoryPanel, playPanel);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        secondCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryCounter++;
                changeScene(categoryPanel, playPanel);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        thirdCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryCounter++;
                changeScene(categoryPanel, playPanel);

                //TODO: Uppdatera Fråga och svarsalternativ
                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

            }
        });
        firstAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                answerCounter++;

                if (endOfRound(answerCounter)) {
                    changeScene(playPanel, statsPanel);
                    answerCounter = 0;

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
                    changeScene(playPanel, statsPanel);
                    answerCounter = 0;

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
                    changeScene(playPanel, statsPanel);
                    answerCounter = 0;

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
                    changeScene(playPanel, statsPanel);
                    answerCounter = 0;

                } else {

                    //TODO: Uppdatera Fråga och svarsalternativ
                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
                }
            }
        });

        waitingPanelGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                changeScene(waitingPanel, lobbyPanel);

                //TODO: Utdelning av poäng till motståndare om en användare väljer att ge upp

            }
        });
        continueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (endOfGame(categoryCounter)) {
                    changeScene(statsPanel, lobbyPanel);
                } else

//                if (isChoosing) {
                    changeScene(statsPanel, categoryPanel);

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
        return answerCounter >= questionsPerRound;
    }

    public boolean endOfGame(int categoryCounter) {
        return categoryCounter >= categoriesPerRound;
    }

    public void changeScene(JPanel fromScene, JPanel toScene) {
        fromScene.setVisible(false);
        toScene.setVisible(true);
    }

    //TODO: isChoosing()
    public void isChoosing() {

    }


    public List<Integer> generateIntegerList() {

        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }

        return listOfNumbers;
    }

    public int[][] generateAnswerArray(List<Integer> listOfNumbers) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
        }

        return gameBoardArray;
    }


    //TODO Hur löser vi uppdatering av motståndarens spelplanshalva?
    //TODO räcker det med en generateBoxGrid som skapas av två olika klienter?
    public JTextArea[][] generateBoxGrid(int[][] statsGrid) {
        JTextArea[][] boxArray = new JTextArea[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boxArray[i][j] = new JTextArea();
                boxArray[i][j].setBackground(new java.awt.Color(0, 102, 204));
                boxArray[i][j].setFocusable(false);

                statsGridPlayer.add(boxArray[i][j]);
            }
        }
        return boxArray;
    }

    public JTextArea[][] generateBoxGrid2(int[][] statsGrid) {
        JTextArea[][] boxArray2 = new JTextArea[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                boxArray2[i][j] = new JTextArea();
                boxArray2[i][j].setBackground(new java.awt.Color(0, 102, 204));
                boxArray2[i][j].setFocusable(false);

                statsGridOpponent.add(boxArray2[i][j]);
            }
        }
        return boxArray2;
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
