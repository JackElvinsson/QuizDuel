package Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {


    String questionText;
    Answer answer1;
    Answer answer2;
    Answer answer3;
    Answer answer4;
    Boolean used = false;


    public Question(String questionText, Answer answer1, Answer answer2, Answer answer3, Answer answer4) {
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Answer getAnswer1() {
        return answer1;
    }

    public Answer getAnswer2() {
        return answer2;
    }

    public Answer getAnswer3() {
        return answer3;
    }

    public Answer getAnswer4() {
        return answer4;
    }

    public boolean isUsed() {
        return used;
    }


    /**
     * Takes all existing answers for the question and shuffles them.
     *
     */
    public void shuffleQuestions() {
        List<Answer> answersList=new ArrayList<>();
        answersList.add(answer1);
        answersList.add(answer2);
        answersList.add(answer3);
        answersList.add(answer4);

        Collections.shuffle(answersList);
        for(int i=0; i<=4 ;i++){

            switch (i) {
                case 0:
                    answer1=answersList.get(0);
                    break;
                case 1:
                    answer1=answersList.get(1);
                    break;
                case 2:
                    answer1=answersList.get(2);
                    break;
                case 3:
                    answer1=answersList.get(3);
                    break;
        }



    }

//    public void setUsed(Boolean used) {
//        this.used = used;
//    }
}}


