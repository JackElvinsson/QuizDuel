package Questions;

import java.io.Serializable;

public class Answer implements Serializable {

    String answerText;
    Boolean isAnswerCorrect;

    public Answer() {
    }

    public Answer(String answer, Boolean isAnswerCorrect) {
        this.answerText = answer;
        this.isAnswerCorrect = isAnswerCorrect;
    }


    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setIsAnswerCorrect(Boolean isAnswerCorrect) {
        this.isAnswerCorrect = isAnswerCorrect;
    }
}
