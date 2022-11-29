import Questions.Categories.Kategori;
import Questions.Question;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUI extends JFrame {

//    MethodsGUI methodsGUI = new MethodsGUI();

    boolean isReady = false;
    //    Client client = new Client();
//    IOUtils ioUtils = new IOUtils();
    int rows = 1;
    int columns = 2;
    int categoryTracker = -1;
    protected int questionsPerRound = columns;
    protected int categoriesPerRound = rows;
    protected int answerCounter = 0;
    protected int categoryCounter = 0;
    private Kategori selectedItem;

    public boolean isSelectedCheck() {
        return selectedCheck;
    }

    public void setSelectedCheck(boolean selectedCheck) {
        this.selectedCheck = selectedCheck;
    }

    private boolean selectedCheck = false;

    private List<Kategori> selectedList;

    public List<Kategori> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<Kategori> selectedList) {
        this.selectedList = selectedList;
    }


    public Kategori getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Kategori selectedItem) {
        this.selectedItem = selectedItem;
    }


    List<Integer> integerList;
    JTextArea[][] boxGrid;
    JTextArea[][] boxGrid2;
    int[][] answerArray;
    protected User user = new User();
    private Random randomGenerator;
//    public Kategori selectedItem;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;
    // JFrame components
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

    public void setOppName(JLabel oppName) {
        this.oppName = oppName;
    }

    public JLabel getOppName() {
        return oppName;
    }

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

    public JLabel getPlayerWaiting() {
        return playerWaiting;
    }

    public void setPlayerWaiting(JLabel playerWaiting) {
        this.playerWaiting = playerWaiting;
    }

    private JLabel playerWaiting;
    private JPanel statsPanel;
    private JButton continueGame;
    private JButton statsPanelGiveUp;
    protected JPanel statsGridPlayer;
    protected JPanel statsGridOpponent;
    private JLabel playerCurrentScore;
    private JLabel opponentCurrentScore;
    private JButton fourthCategory;

    Border border = new BevelBorder(BevelBorder.RAISED);

    GUI() throws IOException {

        // Add panels to frame
        setLayout(new FlowLayout());
        add(mainPanel);
        startPanel.setVisible(true);
        lobbyPanel.setVisible(false);
        categoryPanel.setVisible(false);
        playPanel.setVisible(false);
        waitingPanel.setVisible(false);
        statsPanel.setVisible(false);

        // Stats gridPanel test
        statsGridPlayer.setLayout(new GridLayout(rows, columns, 10, 10));
        statsGridOpponent.setLayout(new GridLayout(rows, columns, 10, 10));
        statsGridPlayer.setForeground(new java.awt.Color(144, 184, 252));
        statsGridOpponent.setForeground(new java.awt.Color(144, 184, 252));

        integerList = generateIntegerList();
        answerArray = generateAnswerArray(integerList);
        boxGrid = generateBoxGrid(answerArray);
        boxGrid2 = generateBoxGrid2(answerArray);

        playerCurrentScore.setText("0/" + rowsToString(rows * columns));
//        opponentCurrentScore.setText("0/" + rowsToString(rows * columns));

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
        /**
         * Första scenen. Till Lobby
         * */
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setName(textField1.getText());
                textField1.setEnabled(false);//Användarnamnet sätts.
                if(!textField1.getText().isBlank()) {
                    tillLobbyButton.setEnabled(true);
                }
            }
        });
        /**
         * Knappen till Lobby
         * */
        tillLobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName.setText(user.getName());

                if (user.getName().equals("")) {
                    JOptionPane.showMessageDialog(null, "Välj ett namn och tryck\nenter för att registrera", "Meddelande", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    changeScene(startPanel, lobbyPanel);
                }

                //                    Player player = new Player(user.getName());
//                    setPlayer(player);
//
//                    player.connectToServer();

//                    while (oppName.getText().equals("Spelare2")) {
//                        oppName.setText(getPlayer().getOtherPlayerName());
//                    }
            }
        });

        /**
         * Vad som händer när man trycker på SPELA knappen.
         * */

        spela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryCounter = 0;

                if (player.getPlayerID() == 2) {
                    System.out.println("Innanför player2 scenbyte");
                    changeScene(lobbyPanel, waitingPanel);

                } else {

                    setCategories(player.getCategoryOptions(), firstCategory, secondCategory, thirdCategory, fourthCategory);
                    changeScene(lobbyPanel, categoryPanel);

                    //TODO: Nästa spelare får välja kategori
                    //TODO: Spelare som ej väljer hamnar i waitingPanel

                    //TODO: Uppdatera Fråga och svarsalternativ
                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
                }
            }
        });

        lamnaLobby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lobbyPanel.setVisible(false);
                startPanel.setVisible(true);
                changeScene(lobbyPanel, startPanel);

                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
            }
        });

        firstCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 0;
                categoryCounter++;

                player.setSelectedCategory(player.getCategoryOptions().get(categoryTracker));
                player.getCsc().sendListBackToServer(player.getCategoryOptions(), player.getSelectedCategory());

//                while(player.getCsc().getListOfQuestions().get(0).getQuestionText().isBlank()) {
//                }
                while(!player.getSelectedCategory().toString().equals(firstCategory.getText())) {

                }
                System.out.println("efter while loopen i kategori");
                setQuestionAndAnswers(player.getCsc().getListOfQuestions(), playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 0);
                changeScene(categoryPanel, playPanel);

                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
            }
        });

        secondCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 1;
                categoryCounter++;

                player.setSelectedCategory(player.getCategoryOptions().get(categoryTracker));
                player.getCsc().sendListBackToServer(player.getCategoryOptions(), player.getSelectedCategory());


                while(!player.getSelectedCategory().toString().equals(secondCategory.getText())) {

                }
//                while(player.getCsc().getListOfQuestions().isEmpty()) {
//                }

                setQuestionAndAnswers(player.getCsc().getListOfQuestions(), playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 1);
                changeScene(categoryPanel, playPanel);

                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
            }
        });

        thirdCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 2;
                categoryCounter++;

                player.setSelectedCategory(player.getCategoryOptions().get(categoryTracker));
                player.getCsc().sendListBackToServer(player.getCategoryOptions(), player.getSelectedCategory());

                while(!player.getSelectedCategory().toString().equals(thirdCategory.getText())) {

                }
//                while(player.getCsc().getListOfQuestions().isEmpty()) {
//                }

                setQuestionAndAnswers(player.getCsc().getListOfQuestions(), playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 2);
                changeScene(categoryPanel, playPanel);

                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
            }
        });

        fourthCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 3;
                categoryCounter++;

                player.setSelectedCategory(player.getCategoryOptions().get(categoryTracker));
                player.getCsc().sendListBackToServer(player.getCategoryOptions(), player.getSelectedCategory());

                while(!player.getSelectedCategory().toString().equals(fourthCategory.getText())) {

                }

//                while(player.getCsc().getListOfQuestions().isEmpty()) {
//                }

                setQuestionAndAnswers(player.getCsc().getListOfQuestions(), playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 3);
                changeScene(categoryPanel, playPanel);

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

                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

//                    isCorrectAnswer(categoryOptions, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 0);
                    String correctAnswer = markCorrectAnswer(player.getCategoryOptions(), firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, categoryTracker);
                    markUserAnswer(correctAnswer, firstAnswer);
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

                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

//                    isCorrectAnswer(categoryOptions, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 1);
                    String correctAnswer = markCorrectAnswer(player.getCategoryOptions(), firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, categoryTracker);
                    markUserAnswer(correctAnswer, secondAnswer);
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

                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

//                    isCorrectAnswer(categoryOptions, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 2);
                            String correctAnswer = markCorrectAnswer(player.getCategoryOptions(), firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, categoryTracker);
                    markUserAnswer(correctAnswer, thirdAnswer);
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

                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

//                    isCorrectAnswer(categoryOptions, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, 3);
                    String correctAnswer = markCorrectAnswer(player.getCategoryOptions(), firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, categoryTracker);
                    markUserAnswer(correctAnswer, fourthAnswer);
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

                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox

                firstAnswer.setBackground(new Color(0, 102, 204));
                secondAnswer.setBackground(new Color(0, 102, 204));
                thirdAnswer.setBackground(new Color(0, 102, 204));
                fourthAnswer.setBackground(new Color(0, 102, 204));
            }
        });

        statsPanelGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: Utdelning av poäng till motståndare om en användare väljer att ge upp
            }
        });
    }


//    public boolean correctAnswer(){
//
//        return false;
//    }
//
//    public void answerColor(ActionEvent e, JButton answer, boolean correctAnswer) {
//
//        if(e.equals(correctAnswer)){
//            answer.setBackground(new Color(70,255,0));
//        } else {
//            answer.setBackground(new Color(255,0,6));
//        }
//    }

    public String rowsToString(int rows) {
        return String.valueOf(rows);
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

    //TODO: updateBoxGrid()


    /**
     * Hur gör vi så att man endast ser vad motståndaren har svarat när hela rundan är avklarad?
     */


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


    /**
     * Hur löser vi uppdatering av motståndarens spelplanshalva?
     * räcker det med en generateBoxGrid som skapas av två olika klienter?
     * <p>
     * Detta blir löst genom att hämta motståndarens data
     */


    public JTextArea[][] generateBoxGrid(int[][] statsGrid) {
        JTextArea[][] boxArray = new JTextArea[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boxArray[i][j] = new JTextArea();
                boxArray[i][j].setBackground(new java.awt.Color(0, 102, 204));
                boxArray[i][j].setBorder(border);
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
                boxArray2[i][j].setBorder(border);
                boxArray2[i][j].setFocusable(false);

                statsGridOpponent.add(boxArray2[i][j]);
            }
        }
        return boxArray2;
    }


    public void setQuestionAndAnswers(List<Question> listOfQuestions, JTextArea questionTextArea, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int i) {

        while(listOfQuestions.isEmpty()) {
            questionTextArea.setText(listOfQuestions.get(0).getQuestionText());
            firstAnswer.setText(listOfQuestions.get(0).getAnswer1().getAnswerText());
            secondAnswer.setText(listOfQuestions.get(0).getAnswer2().getAnswerText());
            thirdAnswer.setText(listOfQuestions.get(0).getAnswer3().getAnswerText());
            fourthAnswer.setText(listOfQuestions.get(0).getAnswer4().getAnswerText());
        }
    }

    //TODO Lägg till dynamiskt antal frågor

    public void setCategories(List<Kategori> categoryOptions, JButton firstCategory, JButton secondCategory, JButton thirdCategory, JButton fourthCategory) {

        firstCategory.setText(categoryOptions.get(0).getCategoryName());
        secondCategory.setText(categoryOptions.get(1).getCategoryName());
        thirdCategory.setText(categoryOptions.get(2).getCategoryName());
        fourthCategory.setText(categoryOptions.get(3).getCategoryName());
    }

    public String markCorrectAnswer(List<Kategori> categoryOptions, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int categoryTracker) {

        if (categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer1().getIsAnswerCorrect()) {
            firstAnswer.setBackground(Color.GREEN);

            System.out.println(categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer1().getAnswerText());
            return firstAnswer.getText();
        } else if (categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer2().getIsAnswerCorrect()) {
            secondAnswer.setBackground(Color.GREEN);

            System.out.println(categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer2().getAnswerText());
            return secondAnswer.getText();
        } else if (categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer3().getIsAnswerCorrect()) {
            thirdAnswer.setBackground(Color.GREEN);

            System.out.println(categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer3().getAnswerText());
            return thirdAnswer.getText();
        } else if (categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer4().getIsAnswerCorrect()) {
            fourthAnswer.setBackground(Color.GREEN);

            System.out.println(categoryOptions.get(categoryTracker).getListOfQuestions().get(0).getAnswer4().getAnswerText());
            return fourthAnswer.getText();
        } else
            return null;
    }

    public void markUserAnswer(String correctAnswer, JButton answer) {

        if (!answer.getText().equals(correctAnswer)) {
            answer.setBackground(Color.red);
        } else {
            answer.setBackground(Color.green);
        }

    }


    public int generateRandomCategoryIndex(List<Kategori> categoryOptions) {
        randomGenerator = new Random();
        return randomGenerator.nextInt(categoryOptions.size());
    }

    public int generateRandomQuestionIndex(List<Question> questionList) {
        randomGenerator = new Random();
        return randomGenerator.nextInt(questionList.size());
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

    public static void main(String[] args) throws IOException {
        new GUI();
//        gui.getPlayer();
    }
}


