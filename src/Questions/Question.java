package Questions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {


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

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public boolean isUsed()  {
        return used;
    }


    /**
     * Takes all existing answers for the question and shuffles them.
     *
     *
     */







    public void shuffleQAnswers() {
        List<Answer> answersList=new ArrayList<>();
        answersList.add(answer1);
        answersList.add(answer2);
        answersList.add(answer3);
        answersList.add(answer4);


    }

//    public void setUsed(Boolean used) {
//        this.used = used;
//    }


}


