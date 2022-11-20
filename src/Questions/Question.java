package Questions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Question {
    String quest;
    String correct;

    String filePath;
    String questionText;
    Answer answer1;
    Answer answer2;
    Answer answer3;
    Answer answer4;
    boolean used =false;


    /* Frågeklass med initialiserings objekt att knyta till
     lista eller array från annan fråge klass för att lagra objekt*/
    public Question(String quest, String correct) {
        this.quest = quest;
        this.correct = correct;

    }

    public Question(String questionText, Answer answer1, Answer answer2, Answer answer3, Answer answer4) {
        this.questionText=questionText;
        this.answer1=answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.answer4=answer4;
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
}


