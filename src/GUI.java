import Questions.Answer;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GUI extends JFrame {

    boolean readyToPlay = false;
    int gridLoopCounter = 0;

    public boolean isNextGame() {
        return nextGame;
    }

    public void setNextGame(boolean nextGame) {
        this.nextGame = nextGame;
    }

    boolean nextGame = false;


    public boolean isNewRound() {
        return nextQuestion;
    }

    public void setNewRound(boolean newRound) {
        this.nextQuestion = newRound;
    }

    private boolean nextQuestion = false;

    public boolean isGaveUp() {
        return gaveUp;
    }

    public void setGaveUp(boolean gaveUp) {
        this.gaveUp = gaveUp;
    }

    private boolean gaveUp = false;

    public boolean isReadyToSend() {
        return readyToSend;
    }

    public int turnCounter=0;
    public void setReadyToSend(boolean readyToSend) {
        this.readyToSend = readyToSend;
    }

    public void setChoosing(boolean choosing) {
        isChoosing = choosing;
    }

    private boolean isChoosing;

    public void setReady(boolean ready) {
        readyToPlay = ready;
    }

    private boolean readyToSend = false;

    public boolean isNewTurnReady() {
        return newTurnReady;
    }

    public void setNewTurnReady(boolean newRoundReady) {
        this.newTurnReady = newRoundReady;
    }

    private boolean newTurnReady = false;


    public boolean isReady() {
        return readyToPlay;
    }

    public int getOpponentGameScore() {
        return opponentGameScore;
    }

    private int opponentGameScore = 0;

    public void setOpponentGameScore(int opponentGameScore) {
        this.opponentGameScore = opponentGameScore;
    }


    private int rows;

    int columns;
    int categoryTracker = -1;
    protected int questionsPerRound = rows;
    protected int categoriesPerRound = columns;
    protected int answerCounter = 0;
    protected int categoryCounter = 0;
    private Kategori selectedItem;
    private List<Kategori> guiKategoriList;

    private List<Question> opponentSelectedQs;

    public List<Question> getGuiQuestionList() {
        return guiQuestionList;
    }

    public void setGuiQuestionList(List<Question> guiQuestionList) {
        this.guiQuestionList = guiQuestionList;
    }

    private List<Question> guiQuestionList;
    private int oppArray[];

    public int[] getAnswerResult() {
        return answerResult;
    }

    private int[] answerResult;

    private int finalOppScore;

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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getStartPanel() {
        return startPanel;
    }

    public JPanel getLobbyPanel() {
        return lobbyPanel;
    }

    public JPanel getPlayPanel() {
        return playPanel;
    }

    public void setPlayPanel(JPanel playPanel) {
        this.playPanel = playPanel;
    }

    public JPanel getCategoryPanel() {
        return categoryPanel;
    }

    public void setCategoryPanel(JPanel categoryPanel) {
        this.categoryPanel = categoryPanel;
    }

    public JPanel getWaitingPanel() {
        return waitingPanel;
    }

    public void setWaitingPanel(JPanel waitingPanel) {
        this.waitingPanel = waitingPanel;
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public void setStatsPanel(JPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    // JFrame components
    private JPanel statsPanel;
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
    private JButton continueGame;
    private JButton statsPanelGiveUp;
    protected JPanel statsGridPlayer;
    protected JPanel statsGridOpponent;
    private JLabel playerCurrentScore;

    public JLabel getOpponentCurrentScore() {
        return opponentCurrentScore;
    }

    public void opponentscore(JLabel opponentCurrentScore) {
        this.opponentCurrentScore = opponentCurrentScore;
    }

    private JLabel opponentCurrentScore;
    private JButton fourthCategory;

    public JLabel getPlayer2Stats() {
        return player2Stats;
    }

    public void setPlayer2Stats(JLabel player2Stats) {
        this.player2Stats = player2Stats;
    }

    private JLabel player2Stats;
    private JLabel player1Stats;
    private JButton forts??ttButton;

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

////         Stats gridPanel test
//        statsGridPlayer.setLayout(new GridLayout(columns, rows, 10, 10));
//        statsGridOpponent.setLayout(new GridLayout(columns, rows, 10, 10));
//        statsGridPlayer.setForeground(new Color(144, 184, 252));
//        statsGridOpponent.setForeground(new Color(144, 184, 252));
//
//        integerList = generateIntegerList(rows, columns);
//        answerArray = generateAnswerArray(integerList, rows, columns);
//        boxGrid = generateBoxGrid(answerArray, rows, columns);
//        boxGrid2 = generateBoxGrid2(answerArray, rows, columns);


        forts??ttButton.setVisible(false);


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
         * F??rsta scenen. Till Lobby
         * */
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setName(textField1.getText());
                textField1.setEnabled(false);//Anv??ndarnamnet s??tts.
                if (!textField1.getText().isBlank()) {
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

                player1Stats.setText(user.getName());

                if (user.getName().equals("")) {
                    JOptionPane.showMessageDialog(null, "V??lj ett namn och tryck\nenter f??r att registrera", "Meddelande", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    changeScene(startPanel, lobbyPanel);
                    categoryCounter = 0;
                }

                //                    Player player = new Player(user.getName());
//                    setPlayer(player);
//
//                    player.connectToServer();

//                    while (oppName.getText().equals("Spelare2")) {
//                        oppName.setText();
//                    }
            }
        });

        /**
         * Vad som h??nder n??r man trycker p?? SPELA knappen.
         * */

        spela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                readyToPlay = true;
                nextGame = true;
                System.out.println("nextGame = true");
//                    setCategories(player.getCategoryOptions(), firstCategory, secondCategory, thirdCategory, fourthCategory);

                //TODO: N??sta spelare f??r v??lja kategori
                //TODO: Spelare som ej v??ljer hamnar i waitingPanel

                //TODO: Uppdatera Fr??ga och svarsalternativ
                //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox


            }
        });

        lamnaLobby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);


            }
        });

        firstCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 0;
//                readyToPlay = false;

                setSelectedItem(selectedList.get(categoryTracker));
                selectedCheck = true;
                //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox

            }
        });

        secondCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 1;
//                readyToPlay = false;
                setSelectedItem(selectedList.get(categoryTracker));
                selectedCheck = true;
                //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox
            }
        });

        thirdCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 2;
//                readyToPlay = false;
                setSelectedItem(selectedList.get(categoryTracker));
                selectedCheck = true;
                //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox
            }
        });

        fourthCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryTracker = 3;
//                readyToPlay = false;
                setSelectedItem(selectedList.get(categoryTracker));
                selectedCheck = true;
                //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox
            }
        });

        firstAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


//                    //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox

                /**
                 * Kollar i listan efter det korrekta svaret, beh??ller det korrekta svaret och j??mf??r med knappen som anv??ndaren valde
                 */
                selectedAnswer1(guiQuestionList);
                selectedCheck = false;
                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
                markUserAnswer(correctAnswer, firstAnswer);

            }
        });

        secondAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


//                    //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox
//
                selectedAnswer2(guiQuestionList);
                selectedCheck = false;
                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
                markUserAnswer(correctAnswer, secondAnswer);
            }
        });

        thirdAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//
//                    //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox
//
                selectedAnswer3(guiQuestionList);
                selectedCheck = false;
                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
                markUserAnswer(correctAnswer, thirdAnswer);
            }
        });

        fourthAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//
//                    //TODO: L??gg till po??ng till statPanel och uppdatera statPanel answerBox
//
                selectedAnswer4(guiQuestionList);
                selectedCheck = false;
                String correctAnswer = markCorrectAnswer(guiQuestionList, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
                markUserAnswer(correctAnswer, fourthAnswer);
            }
        });

        waitingPanelGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gaveUp = true;
                changeScene(waitingPanel, lobbyPanel);
                System.out.println("***GUI*** waitingPanel give up");
            }
        });

        continueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Rows: " + rows);
                System.out.println("AnswerCounter: " + answerCounter);
                if (categoryCounter >= columns) {

                    endOfGame();

                } else if (answerCounter >= rows - 1) {
                    readyToPlay = false;
                    nextQuestion = false;
                    answerCounter = 0;
                    gridLoopCounter++;
                    resetAnswerButtons(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
                    newTurnReady = true;
                }
            }
        });

        statsPanelGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gaveUp = true;
                changeScene(statsPanel, lobbyPanel);
                System.out.println("***GUI*** statsPanel give up");
            }
        });

        forts??ttButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (answerCounter >= rows) {
                    answerCounter = rows - 1;
                }
                if (answerCounter == rows - 1) {

                    categoryCounter++;

                    nextQuestion = true;
                    readyToSend = true;

                    changeScene(playPanel, statsPanel);

                    System.out.println("***GUI*** answerCounter fr??n forts??ttButton OM VI G??R TILL STATSPANEL = " + answerCounter);
                    System.out.println("**GUI** categoryCounter: " + categoryCounter);
                    System.out.println("**GUI** readyToSend = " + readyToSend);
                    System.out.println("**GUI** newRound = " + nextQuestion);


                } else {
                    System.out.println("***GUI*** answerCounter fr??n forts??ttButton = " + answerCounter);
                    answerCounter++;

                    resetAnswerButtons(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer);
                    setQuestionAndAnswers(guiQuestionList, playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);

                }


            }
        });
    }


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


    public List<Integer> generateIntegerList(int rows, int columns) {

        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }

        return listOfNumbers;
    }

    public int[][] generateAnswerArray(List<Integer> listOfNumbers, int rows, int columns) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
        }

        return gameBoardArray;
    }


    /**
     * Hur l??ser vi uppdatering av motst??ndarens spelplanshalva?
     * r??cker det med en generateBoxGrid som skapas av tv?? olika klienter?
     * <p>
     * Detta blir l??st genom att h??mta motst??ndarens data
     */


    public JTextArea[][] generateBoxGrid(int[][] statsGrid, int rows, int columns) {
        JTextArea[][] boxArray = new JTextArea[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boxArray[i][j] = new JTextArea();
                boxArray[i][j].setBackground(new Color(0, 102, 204));
                boxArray[i][j].setBorder(border);
                boxArray[i][j].setFocusable(false);

                statsGridPlayer.add(boxArray[i][j]);
            }
        }
        return boxArray;
    }

    public JTextArea[][] generateBoxGrid2(int[][] statsGrid, int rows, int columns) {
        JTextArea[][] boxArray2 = new JTextArea[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                boxArray2[i][j] = new JTextArea();
                boxArray2[i][j].setBackground(new Color(0, 102, 204));
                boxArray2[i][j].setBorder(border);
                boxArray2[i][j].setFocusable(false);

                statsGridOpponent.add(boxArray2[i][j]);
            }
        }
        return boxArray2;
    }

    public void setUpStatsPanel(int numberOfQuestions, int numberOfRounds) {

        rows = numberOfQuestions;
        columns = numberOfRounds;
//        rows = numberOfRounds;
//        columns = numberOfQuestions;
        System.out.println("***GUI***Antal fr??gor : " + rows);
        System.out.println("***GUI***Antal rundor : " + columns);

        statsGridPlayer.setLayout(new GridLayout(rows, columns, 10, 10));
        statsGridOpponent.setLayout(new GridLayout(rows, columns, 10, 10));
        statsGridPlayer.setForeground(new Color(144, 184, 252));
        statsGridOpponent.setForeground(new Color(144, 184, 252));

        answerResult = new int[rows];
        playerCurrentScore.setText("0/" + rowsToString(rows * columns));
        opponentCurrentScore.setText("0/" + rowsToString(rows * columns));

        integerList = generateIntegerList(rows, columns);
        answerArray = generateAnswerArray(integerList, rows, columns);
        boxGrid = generateBoxGrid(answerArray, rows, columns);
        boxGrid2 = generateBoxGrid2(answerArray, rows, columns);
    }

    public void resetAnswerButtons(JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer) {

        forts??ttButton.setVisible(false);
        System.out.println("***GUI*** Resetting buttons");
        firstAnswer.setBackground(new Color(0, 102, 204));
        secondAnswer.setBackground(new Color(0, 102, 204));
        thirdAnswer.setBackground(new Color(0, 102, 204));
        fourthAnswer.setBackground(new Color(0, 102, 204));
        firstAnswer.setEnabled(true);
        secondAnswer.setEnabled(true);
        thirdAnswer.setEnabled(true);
        fourthAnswer.setEnabled(true);
    }


    public void setQuestionAndAnswers(List<Question> listOfQuestions, JTextArea questionTextArea, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int i) {

        System.out.println("***GUI*** Questiontext: " + listOfQuestions.get(i).getQuestionText());
        System.out.println("***GUI*** AnswerText1: " + listOfQuestions.get(i).getAnswer1().getAnswerText());
        System.out.println("***GUI*** AnswerText2: " + listOfQuestions.get(i).getAnswer2().getAnswerText());
        System.out.println("***GUI*** AnswerText3: " + listOfQuestions.get(i).getAnswer3().getAnswerText());
        System.out.println("***GUI*** AnswerText4: " + listOfQuestions.get(i).getAnswer4().getAnswerText());
        questionTextArea.setText(listOfQuestions.get(i).getQuestionText());
        firstAnswer.setText(listOfQuestions.get(i).getAnswer1().getAnswerText());
        secondAnswer.setText(listOfQuestions.get(i).getAnswer2().getAnswerText());
        thirdAnswer.setText(listOfQuestions.get(i).getAnswer3().getAnswerText());
        fourthAnswer.setText(listOfQuestions.get(i).getAnswer4().getAnswerText());
    }

    //TODO L??gg till dynamiskt antal fr??gor

    public void setCategories(List<Kategori> categoryOptions, JButton firstCategory, JButton secondCategory, JButton thirdCategory, JButton fourthCategory) {

        firstCategory.setText(categoryOptions.get(0).getCategoryName());
        secondCategory.setText(categoryOptions.get(1).getCategoryName());
        thirdCategory.setText(categoryOptions.get(2).getCategoryName());
        fourthCategory.setText(categoryOptions.get(3).getCategoryName());
    }

    public String markCorrectAnswer(List<Question> quiQuestionList, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int answerCounter) {

        if (quiQuestionList.get(answerCounter).getAnswer1().getIsAnswerCorrect()) {
            firstAnswer.setBackground(Color.GREEN);

            System.out.println("***GUI*** " + quiQuestionList.get(answerCounter).getAnswer1().getAnswerText());
            return firstAnswer.getText();
        } else if (quiQuestionList.get(answerCounter).getAnswer2().getIsAnswerCorrect()) {
            secondAnswer.setBackground(Color.GREEN);

            System.out.println("***GUI*** " + quiQuestionList.get(answerCounter).getAnswer2().getAnswerText());
            return secondAnswer.getText();
        } else if (quiQuestionList.get(answerCounter).getAnswer3().getIsAnswerCorrect()) {
            thirdAnswer.setBackground(Color.GREEN);

            System.out.println("***GUI*** " + quiQuestionList.get(answerCounter).getAnswer3().getAnswerText());
            return thirdAnswer.getText();
        } else if (quiQuestionList.get(answerCounter).getAnswer4().getIsAnswerCorrect()) {
            fourthAnswer.setBackground(Color.GREEN);

            System.out.println("***GUI*** " + quiQuestionList.get(answerCounter).getAnswer4().getAnswerText());
            return fourthAnswer.getText();
        } else
            return null;
    }


//    public void updateOpponentStatGrid() {
//        Answer answer = new Answer();
//
//        for (int i = 0; i < opponentSelectedQs.size(); i++) {
//
//
//            if (opponentSelectedQs.get(i).getAnswer1().isSelected()) {
//                answer = opponentSelectedQs.get(i).getAnswer1();
//            } else if (opponentSelectedQs.get(i).getAnswer2().isSelected()) {
//                answer = opponentSelectedQs.get(i).getAnswer1();
//            } else if (opponentSelectedQs.get(i).getAnswer3().isSelected()) {
//                answer = opponentSelectedQs.get(i).getAnswer1();
//            } else if (opponentSelectedQs.get(i).getAnswer4().isSelected()) {
//                answer = opponentSelectedQs.get(i).getAnswer1();
//            }
//
//            if (answer.getIsAnswerCorrect().equals(true)) {
//                boxGrid2[turnCounter-1][i].setBackground(Color.green);
//            } else
//                boxGrid2[turnCounter-1][i].setBackground(Color.red);
//
//        }
//    }
    // colorTest
//        boxGrid[0][1].setBackground(Color.red);
//        boxGrid2[0][2].setBackground(Color.red);

    public void markUserAnswer(String correctAnswer, JButton answer) {

        if (!answer.getText().equals(correctAnswer)) {

            boxGrid[gridLoopCounter][answerCounter].setBackground(Color.red);
            System.out.println("***GUI*** categoryCounter: " + categoryCounter + " " + " answerCounter: " + answerCounter);
            answerResult[answerCounter] = 0;
            answer.setBackground(Color.red);
            forts??ttButton.setVisible(true);
            disableAnswerButtons();

        } else {

            boxGrid[gridLoopCounter][answerCounter].setBackground(Color.green);
            System.out.println("***GUI*** categoryCounter: " + categoryCounter + " " + " answerCounter: " + answerCounter);
            answerResult[answerCounter] = 1;
            answer.setBackground(Color.green);
            forts??ttButton.setVisible(true);
            System.out.println("***GUI*** PlayerScore BEFORE add: " + user.getScore());
            user.setScore(user.getScore() + 1);
            playerCurrentScore.setText(user.getScore() + "/" + rowsToString(rows * columns));
            disableAnswerButtons();
            System.out.println("***GUI*** PlayerScore AFTER add: " + user.getScore());
        }
    }

    public void disableAnswerButtons() {

        firstAnswer.setEnabled(false);
        secondAnswer.setEnabled(false);
        thirdAnswer.setEnabled(false);
        fourthAnswer.setEnabled(false);
    }

    public void categoryListTaxi(List<Kategori> list) {

        setCategories(list, firstCategory, secondCategory, thirdCategory, fourthCategory);
        setSelectedList(list);

    }

    public void questionListTaxi(List<Question> questionList) {

        System.out.println(answerCounter);
        setQuestionAndAnswers(questionList, playTextArea, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, answerCounter);
        guiQuestionList = questionList;
//        rows=guiQuestionList.size();
    }

    public void opponentArrayTaxi(int[] opponentArray, int finalOpponentScore) {

//        opponentSelectedQs = opponentSelectedQuestions;
        finalOppScore = finalOpponentScore;
//        for (int i = 0; i < opponentArray.length; i++) {
//            System.out.println("***GUI*** Mottaged array, index:" + i + " = " + opponentArray[i]);
//        }

        System.out.println("///////////TAR EMOT///////////CLIENT: " + Arrays.toString(opponentArray));
        setOpponentStatGrid(opponentArray);
//        updateOpponentStatGrid();


    }

    public void isChoosingTaxi(boolean myTurn) {
        isChoosing = myTurn;
    }


    public int generateRandomCategoryIndex(List<Kategori> categoryOptions) {
        randomGenerator = new Random();
        return randomGenerator.nextInt(categoryOptions.size());
    }

    public int generateRandomQuestionIndex(List<Question> questionList) {
        randomGenerator = new Random();
        return randomGenerator.nextInt(questionList.size());
    }

    public void setOpponentStatGrid(int[] opponentArray) {


        for (int j = 0; j < opponentArray.length; j++) {

            if (opponentArray[j] == 1) {
                boxGrid2[gridLoopCounter][j].setBackground(Color.green);
                System.out.println("***GUI*** Satte boxgrid ruta " + gridLoopCounter + " " + j + " till GR??N");
            } else {
                boxGrid2[gridLoopCounter][j].setBackground(Color.red);
                System.out.println("***GUI*** Satte boxgrid ruta " + gridLoopCounter + " " + j + " till R??D");

            }
        }
    }

    public void endOfGame() {
        System.out.println("ENDOFGAME()");
        categoryCounter = 0;
        answerCounter = 0;
        gridLoopCounter = 0;
        readyToPlay = false;
        newTurnReady = false;
        nextQuestion = false;

        System.out.println("USER: " + user.getScore());
        System.out.println("OPPONENT SCORE: " + finalOppScore);
        if (user.getScore() > finalOppScore) {
            System.out.println("User vann");
            userWins.setText(String.valueOf(user.gameScore + 1));
        } else if (user.getScore() == finalOppScore) {
            System.out.println("lika!");
            oppWins.setText(String.valueOf(opponentGameScore + 1));
            userWins.setText(String.valueOf(user.gameScore + 1));
        } else {
            oppWins.setText(String.valueOf(opponentGameScore + 1));
            System.out.println("Du f??rlora");
        }
        changeScene(statsPanel, lobbyPanel);
    }

    public void resetValues() {
        categoryCounter = 0;
        answerCounter = 0;
        gridLoopCounter = 0;
        readyToPlay = false;
        newTurnReady = false;
        nextQuestion = false;
        selectedCheck = false;
    }

    public void selectedAnswer1(List<Question> questionList) {
        questionList.get(0).getAnswer1().setSelected(true);
    }

    public void selectedAnswer2(List<Question> questionList) {
        questionList.get(0).getAnswer2().setSelected(true);
    }

    public void selectedAnswer3(List<Question> questionList) {
        questionList.get(0).getAnswer3().setSelected(true);
    }

    public void selectedAnswer4(List<Question> questionList) {
        questionList.get(0).getAnswer4().setSelected(true);
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

//    public static void main(String[] args) throws IOException {
//        new GUI();
////        gui.getPlayer();
//    }
}


