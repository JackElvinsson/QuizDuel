//import Questions.Categories.Kategori;
//import Questions.Question;
//
//import javax.swing.*;
//import javax.swing.border.BevelBorder;
//import javax.swing.border.Border;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class GUIBACKUP extends JFrame {
//
//    boolean isReady = false;
//    int gridLoopCounter = 0;
//
//
//    public boolean isNewRound() {
//        return newRound;
//    }
//
//    public void setNewRound(boolean newRound) {
//        this.newRound = newRound;
//    }
//
//    private boolean newRound = false;
//
//
//    public boolean isReadyToSend() {
//        return readyToSend;
//    }
//
//    public void setReadyToSend(boolean readyToSend) {
//        this.readyToSend = readyToSend;
//    }
//
//    public void setChoosing(boolean choosing) {
//        isChoosing = choosing;
//    }
//
//    private boolean isChoosing;
//
//    public void setReady(boolean ready) {
//        isReady = ready;
//    }
//
//    private boolean readyToSend = false;
//
//    public boolean isNewRoundReady() {
//        return newRoundReady;
//    }
//
//    public void setNewRoundReady(boolean newRoundReady) {
//        this.newRoundReady = newRoundReady;
//    }
//
//    private boolean newRoundReady = false;
//
//
//    public boolean isReady() {
//        return isReady;
//    }
//
//    int rows = 1;
//
//    int columns = 1;
//    int categoryTracker = -1;
//    protected int questionsPerRound = columns;
//    protected int categoriesPerRound = rows;
//    protected int answerCounter = 0;
//    protected int categoryCounter = 0;
//    private Kategori selectedItem;
//    private List<Kategori> guiKategoriList;
//    private List<Question> guiQuestionList;
//    private int oppArray[];
//
//    public int[] getAnswerResult() {
//        return answerResult;
//    }
//
//    private int[] answerResult;
//
//    public boolean isSelectedCheck() {
//        return selectedCheck;
//    }
//
//    public void setSelectedCheck(boolean selectedCheck) {
//        this.selectedCheck = selectedCheck;
//    }
//
//    private boolean selectedCheck = false;
//
//    private List<Kategori> selectedList;
//
//    public List<Kategori> getSelectedList() {
//        return selectedList;
//    }
//
//    public void setSelectedList(List<Kategori> selectedList) {
//        this.selectedList = selectedList;
//    }
//
//
//    public Kategori getSelectedItem() {
//        return selectedItem;
//    }
//
//    public void setSelectedItem(Kategori selectedItem) {
//        this.selectedItem = selectedItem;
//    }
//
//
//    List<Integer> integerList;
//    JTextArea[][] boxGrid;
//    JTextArea[][] boxGrid2;
//    int[][] answerArray;
//    protected User user = new User();
//    private Random randomGenerator;
////    public Kategori selectedItem;
//
//    public JPanel getMainPanel() {
//        return mainPanel;
//    }
//
//    public JPanel getStartPanel() {
//        return startPanel;
//    }
//
//    public JPanel getLobbyPanel() {
//        return lobbyPanel;
//    }
//
//    public JPanel getPlayPanel() {
//        return playPanel;
//    }
//
//    public void setPlayPanel(JPanel playPanel) {
//        this.playPanel = playPanel;
//    }
//
//    public JPanel getCategoryPanel() {
//        return categoryPanel;
//    }
//
//    public void setCategoryPanel(JPanel categoryPanel) {
//        this.categoryPanel = categoryPanel;
//    }
//
//    public JPanel getWaitingPanel() {
//        return waitingPanel;
//    }
//
//    public void setWaitingPanel(JPanel waitingPanel) {
//        this.waitingPanel = waitingPanel;
//    }
//
//    public JPanel getStatsPanel() {
//        return statsPanel;
//    }
//
//    public void setStatsPanel(JPanel statsPanel) {
//        this.statsPanel = statsPanel;
//    }
//
//    // JFrame components
//    private JPanel statsPanel;
//    private JPanel mainPanel;
//    private JPanel startPanel;
//    private JPanel lobbyPanel;
//    protected JPanel playPanel;
//    private JPanel categoryPanel;
//    private JPanel waitingPanel;
//    private JButton tillLobbyButton;
//    private JButton avslutaButton;
//    private JTextField textField1;
//    private JButton spela;
//    private JButton lamnaLobby;
//    private JLabel userName;
//
//
//    public void setOppName(JLabel oppName) {
//        this.oppName = oppName;
//    }
//
//    public JLabel getOppName() {
//        return oppName;
//    }
//
//    private JLabel oppName;
//    private JLabel userWins;
//    private JLabel oppWins;
//    private JButton firstCategory;
//    private JButton secondCategory;
//    private JButton thirdCategory;
//    protected JProgressBar progressBar;
//    private JTextArea playTextArea;
//    private JButton firstAnswer;
//    private JButton secondAnswer;
//    private JButton thirdAnswer;
//    private JButton fourthAnswer;
//    private JButton waitingPanelGiveUp;
//
//
//    public JLabel getPlayerWaiting() {
//        return playerWaiting;
//    }
//
//    public void setPlayerWaiting(JLabel playerWaiting) {
//        this.playerWaiting = playerWaiting;
//    }
//
//    private JLabel playerWaiting;
//    private JButton continueGame;
//    private JButton statsPanelGiveUp;
//    protected JPanel statsGridPlayer;
//    protected JPanel statsGridOpponent;
//    private JLabel playerCurrentScore;
//
//    public JLabel getOpponentCurrentScore() {
//        return opponentCurrentScore;
//    }
//
//    public void setOpponentCurrentScore(JLabel opponentCurrentScore) {
//        this.opponentCurrentScore = opponentCurrentScore;
//    }
//
//    private JLabel opponentCurrentScore;
//    private JButton fourthCategory;
//
//    public JLabel getPlayer2Stats() {
//        return player2Stats;
//    }
//
//    public void setPlayer2Stats(JLabel player2Stats) {
//        this.player2Stats = player2Stats;
//    }
//
//    private JLabel player2Stats;
//    private JLabel player1Stats;
//    private JButton fortsättButton;
//
//    Border border = new BevelBorder(BevelBorder.RAISED);
//
//    GUIBACKUP() throws IOException {
//
//        // Add panels to frame
//        setLayout(new FlowLayout());
//        add(mainPanel);
//        startPanel.setVisible(true);
//        lobbyPanel.setVisible(false);
//        categoryPanel.setVisible(false);
//        playPanel.setVisible(false);
//        waitingPanel.setVisible(false);
//        statsPanel.setVisible(false);
//
//        // Stats gridPanel test
////        statsGridPlayer.setLayout(new GridLayout(rows, columns, 10, 10));
////        statsGridOpponent.setLayout(new GridLayout(rows, columns, 10, 10));
////        statsGridPlayer.setForeground(new Color(144, 184, 252));
////        statsGridOpponent.setForeground(new Color(144, 184, 252));
//
////        integerList = generateIntegerList(rows, columns);
////        answerArray = generateAnswerArray(integerList, rows, columns);
////        boxGrid = generateBoxGrid(answerArray, rows, columns);
////        boxGrid2 = generateBoxGrid2(answerArray, rows, columns);
//
//
//        fortsättButton.setVisible(false);
//
//
//        // colorTest
////        boxGrid[0][1].setBackground(Color.red);
////        boxGrid2[0][2].setBackground(Color.red);
//
//        pack();
//        setVisible(true);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        // Action listeners
//
//        avslutaButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//        /**
//         * Första scenen. Till Lobby
//         * */
//        textField1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                user.setName(textField1.getText());
//                textField1.setEnabled(false);//Användarnamnet sätts.
//                if (!textField1.getText().isBlank()) {
//                    tillLobbyButton.setEnabled(true);
//                }
//            }
//        });
//        /**
//         * Knappen till Lobby
//         * */
//        tillLobbyButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                userName.setText(user.getName());
//
//                player1Stats.setText(user.getName());
//
//                if (user.getName().equals("")) {
//                    JOptionPane.showMessageDialog(null, "Välj ett namn och tryck\nenter för att registrera", "Meddelande", JOptionPane.INFORMATION_MESSAGE);
//
//                } else {
//                    changeScene(startPanel, lobbyPanel);
//                    categoryCounter = 0;
//                }
//
//                //                    Player player = new Player(user.getName());
////                    setPlayer(player);
////
////                    player.connectToServer();
//
////                    while (oppName.getText().equals("Spelare2")) {
////                        oppName.setText();
////                    }
//            }
//        });
//
//        /**
//         * Vad som händer när man trycker på SPELA knappen.
//         * */
//
//        spela.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                isReady = true;
//
////                    setCategories(player.getCategoryOptions(), firstCategory, secondCategory, thirdCategory, fourthCategory);
//
//                //TODO: Nästa spelare får välja kategori
//                //TODO: Spelare som ej väljer hamnar i waitingPanel
//
//                //TODO: Uppdatera Fråga och svarsalternativ
//                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//
//
//            }
//        });
//
//        lamnaLobby.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                System.exit(0);
//
//
//            }
//        });
//
//        firstCategory.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                categoryTracker = 0;
//                isReady = false;
//
//                setSelectedItem(selectedList.get(categoryTracker));
//                selectedCheck = true;
//                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//
//            }
//        });
//
//        secondCategory.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                categoryTracker = 1;
//                isReady = false;
//                setSelectedItem(selectedList.get(categoryTracker));
//                selectedCheck = true;
//                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//            }
//        });
//
//        thirdCategory.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                categoryTracker = 2;
//                isReady = false;
//                setSelectedItem(selectedList.get(categoryTracker));
//                selectedCheck = true;
//                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//            }
//        });
//
//        fourthCategory.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                categoryTracker = 3;
//                isReady = false;
//                setSelectedItem(selectedList.get(categoryTracker));
//                selectedCheck = true;
//                //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//            }
//        });
//
//        firstAnswer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//
////                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//
//                /**
//                 * Kollar i listan efter det korrekta svaret, behåller det korrekta svaret och jämför med knappen som användaren valde
//                 */
//                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
//                markUserAnswer(correctAnswer, firstAnswer);
//
//            }
//        });
//
//        secondAnswer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//
////                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
////
//                selectedCheck = false;
//                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
//                markUserAnswer(correctAnswer, secondAnswer);
//            }
//        });
//
//        thirdAnswer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
////
////                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
////
//                selectedCheck = false;
//                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
//                markUserAnswer(correctAnswer, thirdAnswer);
//            }
//        });
//
//        fourthAnswer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
////
////                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
////
//                selectedCheck = false;
//                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
//                markUserAnswer(correctAnswer, fourthAnswer);
//            }
//        });
//
//        waitingPanelGiveUp.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                changeScene(waitingPanel, lobbyPanel);
//
//                selectedCheck = false;
//                //TODO: Utdelning av poäng till motståndare om en användare väljer att ge upp
//            }
//        });
//
//        continueGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                System.out.println("Rows: " + rows);
//                System.out.println("AnswerCounter: " + answerCounter);
//                if (categoryCounter >= columns) {
//                    categoryCounter = 0;
//                    answerCounter = 0;
//                    gridLoopCounter = 0;
//                    isReady = false;
//                    readyToSend = false;
//                    newRoundReady = false;
//
//                    //TODO Uppdatera kategorialternati
//                    //TODO: Nästa spelare får välja kategori
//                    //TODO: Spelare som ej väljer hamnar i waitingPanel
//                    //TODO: Lägg till poäng till statPanel och uppdatera statPanel answerBox
//                } else if (answerCounter >= rows - 1) {
//                    isReady = false;
//                    newRound = false;
//                    answerCounter = 0;
//                    gridLoopCounter++;
//                    resetAnswerButtons(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
//                    newRoundReady = true;
//
//                }
//            }
//        });
//
//        statsPanelGiveUp.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                //TODO: Utdelning av poäng till motståndare om en användare väljer att ge upp
//            }
//
//
//        });
//        fortsättButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
////                answerCounter++;
//                if (answerCounter >= rows) {
//                    answerCounter = rows - 1;
//
//                }
//                if (answerCounter == rows - 1) {
//
//                    categoryCounter++;
//
//
//                    readyToSend = true;
//                    newRound = true;
//
//                    changeScene(playPanel, statsPanel);
//                    resetAnswerButtons(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
//
//                    System.out.println("**GUI** categoryCounter: " + categoryCounter);
//                    System.out.println("**GUI** readyToSend = " + readyToSend);
//                    System.out.println("**GUI** newRound = " + newRound);
//
//
//                } else {
//                    System.out.println("answerCounter från fortsättButton = " + answerCounter);
//                    answerCounter++;
//
//                    resetAnswerButtons(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
//                    setQuestionAndAnswers(guiQuestionList, playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
//
//                }
//
//
//            }
//        });
//    }
//
//
//    public String rowsToString(int rows) {
//        return String.valueOf(rows);
//    }
//
//    public boolean endOfRound(int answerCounter) {
//        return answerCounter >= questionsPerRound;
//    }
//
//    public boolean endOfGame(int categoryCounter) {
//        return categoryCounter >= categoriesPerRound;
//    }
//
//    public void changeScene(JPanel fromScene, JPanel toScene) {
//        fromScene.setVisible(false);
//        toScene.setVisible(true);
//    }
//
//    //TODO: isChoosing()
//    public void isChoosing() {
//
//    }
//
//    //TODO: updateBoxGrid()
//
//
//    /**
//     * Hur gör vi så att man endast ser vad motståndaren har svarat när hela rundan är avklarad?
//     */
//
//
//    public List<Integer> generateIntegerList(int rows, int columns) {
//
//        List<Integer> listOfNumbers = new ArrayList<>();
//        for (int i = 0; i < columns * rows; i++) {
//            listOfNumbers.add(i);
//        }
//
//        return listOfNumbers;
//    }
//
//    public int[][] generateAnswerArray(List<Integer> listOfNumbers, int rows, int columns) {
//        int[][] gameBoardArray = new int[rows][columns];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++)
//                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
//        }
//
//        return gameBoardArray;
//    }
//
//
//    /**
//     * Hur löser vi uppdatering av motståndarens spelplanshalva?
//     * räcker det med en generateBoxGrid som skapas av två olika klienter?
//     * <p>
//     * Detta blir löst genom att hämta motståndarens data
//     */
//
//
//    public JTextArea[][] generateBoxGrid(int[][] statsGrid, int rows, int columns) {
//        JTextArea[][] boxArray = new JTextArea[rows][columns];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                boxArray[i][j] = new JTextArea();
//                boxArray[i][j].setBackground(new Color(0, 102, 204));
//                boxArray[i][j].setBorder(border);
//                boxArray[i][j].setFocusable(false);
//
//                statsGridPlayer.add(boxArray[i][j]);
//            }
//        }
//        return boxArray;
//    }
//
//    public JTextArea[][] generateBoxGrid2(int[][] statsGrid, int rows, int columns) {
//        JTextArea[][] boxArray2 = new JTextArea[rows][columns];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//
//                boxArray2[i][j] = new JTextArea();
//                boxArray2[i][j].setBackground(new Color(0, 102, 204));
//                boxArray2[i][j].setBorder(border);
//                boxArray2[i][j].setFocusable(false);
//
//                statsGridOpponent.add(boxArray2[i][j]);
//            }
//        }
//        return boxArray2;
//    }
//
//    public void setUpStatsPanel(int numberOfQuestions, int numberOfRounds) {
//
//        rows = numberOfQuestions;
//        columns = numberOfRounds;
//
//        statsGridPlayer.setLayout(new GridLayout(rows, columns, 10, 10));
//        statsGridOpponent.setLayout(new GridLayout(rows, columns, 10, 10));
//        statsGridPlayer.setForeground(new Color(144, 184, 252));
//        statsGridOpponent.setForeground(new Color(144, 184, 252));
//
//        answerResult = new int[rows];
//        playerCurrentScore.setText("0/" + rowsToString(rows * columns));
//        opponentCurrentScore.setText("0/" + rowsToString(rows * columns));
//
//        integerList = generateIntegerList(rows, columns);
//        answerArray = generateAnswerArray(integerList, rows, columns);
//        boxGrid = generateBoxGrid(answerArray, rows, columns);
//        boxGrid2 = generateBoxGrid2(answerArray, rows, columns);
//    }
//
//    public void resetAnswerButtons(JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer) {
//
//        fortsättButton.setVisible(false);
//        System.out.println("Resetting buttons");
//        firstAnswer.setBackground(new Color(0, 102, 204));
//        secondAnswer.setBackground(new Color(0, 102, 204));
//        thirdAnswer.setBackground(new Color(0, 102, 204));
//        fourthAnswer.setBackground(new Color(0, 102, 204));
//        firstAnswer.setEnabled(true);
//        secondAnswer.setEnabled(true);
//        thirdAnswer.setEnabled(true);
//        fourthAnswer.setEnabled(true);
//    }
//
//
//    public void setQuestionAndAnswers(List<Question> listOfQuestions, JTextArea questionTextArea, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int i) {
//
//        System.out.println("Questiontext: " + listOfQuestions.get(i).getQuestionText());
//        System.out.println("AnswerText1: " + listOfQuestions.get(i).getAnswer1().getAnswerText());
//        System.out.println("AnswerText2: " + listOfQuestions.get(i).getAnswer2().getAnswerText());
//        System.out.println("AnswerText3: " + listOfQuestions.get(i).getAnswer3().getAnswerText());
//        System.out.println("AnswerText4: " + listOfQuestions.get(i).getAnswer4().getAnswerText());
//        questionTextArea.setText(listOfQuestions.get(i).getQuestionText());
//        firstAnswer.setText(listOfQuestions.get(i).getAnswer1().getAnswerText());
//        secondAnswer.setText(listOfQuestions.get(i).getAnswer2().getAnswerText());
//        thirdAnswer.setText(listOfQuestions.get(i).getAnswer3().getAnswerText());
//        fourthAnswer.setText(listOfQuestions.get(i).getAnswer4().getAnswerText());
//    }
//
//    //TODO Lägg till dynamiskt antal frågor
//
//    public void setCategories(List<Kategori> categoryOptions, JButton firstCategory, JButton secondCategory, JButton thirdCategory, JButton fourthCategory) {
//
//        firstCategory.setText(categoryOptions.get(0).getCategoryName());
//        secondCategory.setText(categoryOptions.get(1).getCategoryName());
//        thirdCategory.setText(categoryOptions.get(2).getCategoryName());
//        fourthCategory.setText(categoryOptions.get(3).getCategoryName());
//    }
//
//    public String markCorrectAnswer(List<Question> quiQuestionList, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int answerCounter) {
//
//        if (quiQuestionList.get(answerCounter).getAnswer1().getIsAnswerCorrect()) {
//            firstAnswer.setBackground(Color.GREEN);
//
//            System.out.println(quiQuestionList.get(answerCounter).getAnswer1().getAnswerText());
//            return firstAnswer.getText();
//        } else if (quiQuestionList.get(answerCounter).getAnswer2().getIsAnswerCorrect()) {
//            secondAnswer.setBackground(Color.GREEN);
//
//            System.out.println(quiQuestionList.get(answerCounter).getAnswer2().getAnswerText());
//            return secondAnswer.getText();
//        } else if (quiQuestionList.get(answerCounter).getAnswer3().getIsAnswerCorrect()) {
//            thirdAnswer.setBackground(Color.GREEN);
//
//            System.out.println(quiQuestionList.get(answerCounter).getAnswer3().getAnswerText());
//            return thirdAnswer.getText();
//        } else if (quiQuestionList.get(answerCounter).getAnswer4().getIsAnswerCorrect()) {
//            fourthAnswer.setBackground(Color.GREEN);
//
//            System.out.println(quiQuestionList.get(answerCounter).getAnswer4().getAnswerText());
//            return fourthAnswer.getText();
//        } else
//            return null;
//    }
//
//    // colorTest
////        boxGrid[0][1].setBackground(Color.red);
////        boxGrid2[0][2].setBackground(Color.red);
//
//    public void markUserAnswer(String correctAnswer, JButton answer) {
//
//        if (!answer.getText().equals(correctAnswer)) {
//            boxGrid[categoryCounter][answerCounter].setBackground(Color.red);
//            System.out.println("categoryCounter: " + categoryCounter + " " + " answerCounter: " + answerCounter);
//            answerResult[answerCounter] = 0;
//            answer.setBackground(Color.red);
//            fortsättButton.setVisible(true);
//            disableAnswerButtons();
//        } else {
//            boxGrid[categoryCounter][answerCounter].setBackground(Color.green);
//            System.out.println("categoryCounter: " + categoryCounter + " " + " answerCounter: " + answerCounter);
//            answerResult[answerCounter] = 1;
//            answer.setBackground(Color.green);
//            fortsättButton.setVisible(true);
//            System.out.println("PlayerScore BEFORE add: " + user.getScore());
//            user.setScore(user.getScore() + 1);
//            playerCurrentScore.setText(user.getScore() + "/" + rowsToString(rows * columns));
//            disableAnswerButtons();
//            System.out.println("PlayerScore AFTER add: " + user.getScore());
//        }
//    }
//
//    public void disableAnswerButtons() {
//
//        firstAnswer.setEnabled(false);
//        secondAnswer.setEnabled(false);
//        thirdAnswer.setEnabled(false);
//        fourthAnswer.setEnabled(false);
//    }
//
//    public void categoryListTaxi(List<Kategori> list) {
//
//        setCategories(list, firstCategory, secondCategory, thirdCategory, fourthCategory);
//        setSelectedList(list);
//
//    }
//
//    public void questionListTaxi(List<Question> questionList) {
//
//        System.out.println(answerCounter);
//        setQuestionAndAnswers(questionList, playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
//        guiQuestionList = questionList;
////        rows=guiQuestionList.size();
//    }
//
//    public void opponentArrayTaxi(int[] opponentArray) {
//
//        for (int i = 0; i <opponentArray.length; i++) {
//            System.out.println("***GUI*** Mottaged array, index:" + i + " = " + opponentArray[i]);
//        }
//        setOpponentStatGrid(opponentArray);
//
//
//    }
//
//    public void isChoosingTaxi(boolean myTurn) {
//        isChoosing = myTurn;
//    }
//
//
//    public int generateRandomCategoryIndex(List<Kategori> categoryOptions) {
//        randomGenerator = new Random();
//        return randomGenerator.nextInt(categoryOptions.size());
//    }
//
//    public int generateRandomQuestionIndex(List<Question> questionList) {
//        randomGenerator = new Random();
//        return randomGenerator.nextInt(questionList.size());
//    }
//
//    public void setOpponentStatGrid(int[] opponentArray) {
//
//
//        for (int j = 0; j < opponentArray.length; j++) {
//
//            if (opponentArray[j] == 1) {
//                boxGrid2[gridLoopCounter][j].setBackground(Color.green);
//                System.out.println("Satte boxgrid rutea till GRÖN" + boxGrid2[gridLoopCounter][j].getBackground().toString());
//            } else {
//                boxGrid2[gridLoopCounter][j].setBackground(Color.red);
//                System.out.println("Satte boxgrid rutea till RÖD" + boxGrid2[gridLoopCounter][j].getBackground().toString());
//
//            }
//        }
//    }
//
//
////    public boolean countDown() {
////
////        int counter = 100;
////        boolean timeOut = false;
////
////        while (!timeOut) {
////
////            if (counter > 0) {
////
////                progressBar.setValue(counter);
////
////                try {
////                    Thread.sleep(100);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////
////                counter -= 1;
////                System.out.println(counter);
////
////            } else {
////                timeOut = true;
////            }
////
////            }
////        return timeOut;
////    }
//
////    public static void main(String[] args) throws IOException {
////        new GUI();
//////        gui.getPlayer();
////    }
//}
//
//
