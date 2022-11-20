package Questions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionCategoryTest {
    Answer testAnswer;
    QuestionCategory qc =new QuestionCategory("Test") {};



    @Test
    void answerChecker() {
        Answer testAnswer=null;
        String tempLine=("Hejsan, jag är ett falskt svar");

        testAnswer=qc.answerChecker(tempLine);
        System.out.println(testAnswer.getAnswerText());
        System.out.println(testAnswer.getIsAnswerCorrect());

        Answer testAnswer2=qc.answerChecker("*Hejsan jag är ett korrekt svar");
        System.out.println(testAnswer2.getAnswerText());
        System.out.println(testAnswer2.getIsAnswerCorrect());
    }
}