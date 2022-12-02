package Questions.Categories;

import Questions.Answer;
import Questions.Question;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class Kategori implements Serializable {


    private String categoryName;
    private List<Question> listOfQuestions;
    String fileWithQuestions;
    Boolean used = false;

    public Kategori(){

    }

    /**
     * Creates an Array list of the class Question from a file. To use, first call created object of the type QuestionCategory or it's subclasses.
     * callable methods through list is all the callable methods from the class Question.
     *
     * @see Question
     */
    public List<Question> getQuestionsFromFile(String questionsFile) throws IOException {
        List<Question> listOfQuestions = new ArrayList<>();
        String questionText = "";
        Answer answer1 = null;
        Answer answer2 = null;
        Answer answer3 = null;
        Answer answer4 = null;

        try {

            Scanner readTextFile = new Scanner(new File(questionsFile));

            while (readTextFile.hasNextLine()) {
                for (int lineCounter = 0; lineCounter <= 4; lineCounter++) { //todo; loopa om 0-4.
                    if (readTextFile.hasNextLine()) {
                        String tempLine = readTextFile.nextLine();
//                        System.out.println("från tempLine" + tempLine);

                        switch (lineCounter) {
                            case 0:
                                questionText = tempLine;
                                break;
                            case 1:
                                answer1 = answerChecker(tempLine);
                                break;
                            case 2:
                                answer2 = answerChecker(tempLine);
                                break;
                            case 3:
                                answer3 = answerChecker(tempLine);
                                break;
                            case 4:
                                answer4 = answerChecker(tempLine);
                                break;
                        }
                        if (lineCounter == 4) {
                            listOfQuestions.add(new Question(questionText, answer1, answer2, answer3, answer4));
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong, please contact the system administrator.");
            e.printStackTrace();
            System.exit(0);
        }
        return listOfQuestions;
    }


    /**
     * Returns an object of the class 'Answer'. If provided String tempLine starts with char(*) boolean 'isCorrectAnswer' is set to true and
     * the char(*) is removed from the string. The object String 'answerText' is now set as to the same value as the local String tempLine.
     *
     * @see Answer
     */
    public Answer answerChecker(String tempLine) {
        Answer tempAnswer = new Answer(tempLine, false);

        if (tempLine.charAt(0) == '*') {
            tempLine = tempLine.replace("*", "");
            tempAnswer.setIsAnswerCorrect(true);
            tempAnswer.setAnswerText(tempLine);
        }
        return tempAnswer;
    }

    /**
     * Shuffle the answers of the desired questions.
     * Same function as in Question, but simplyfied the process to use it.
     *
     * @see Question
     */

    public List<Question> generateQuestions(Kategori chosenCategory, int numberOfQuestions) {

        List<Question> listWithQuestions = new ArrayList<>();
        Collections.shuffle(chosenCategory.listOfQuestions);
        for (int i = 0; i < numberOfQuestions; i++) {
            Question q = chosenCategory.getListOfQuestions().get(i);
            System.out.println(chosenCategory.getListOfQuestions().get(i).getAnswer1().getAnswerText());
            System.out.println(chosenCategory.getListOfQuestions().get(i).getAnswer2().getAnswerText());
            System.out.println(chosenCategory.getListOfQuestions().get(i).getAnswer3().getAnswerText());
            System.out.println(chosenCategory.getListOfQuestions().get(i).getAnswer4().getAnswerText());
            if (!q.getUsed()) {
//                q.shuffleQAnswers();
                listWithQuestions.add(q);

            }else {
                i--;
            }
        }
        return listWithQuestions;
    }

//    public void shuffleQuestionsFromList(int indexOfQuestion) {
//        listOfQuestions.get(indexOfQuestion).shuffleQAnswers();
//    }

    public void shuffleEntireListOfQuestions() {
        Collections.shuffle(listOfQuestions);
    }


    public Kategori(String categoryName) {
        setCategoryName(categoryName);
    }

    public void setFileWithQuestions(String fileWithQuestions) {
        this.fileWithQuestions = fileWithQuestions;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Question> getListOfQuestions() {
        return listOfQuestions;
    }

    public void setListOfQuestions(List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}

//    public void getQuestions() {
//        int i = 0;
//        String row;
//        String question = "";
//        String rightAnswer = "";
//        String wrongAnswer = "";
//        String[] wrongAnswers = new String[3];