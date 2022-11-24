package Questions;

import Questions.Categories.Allmänt;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class KategoriTest {

    Path AllmäntPath = Paths.get("src/Questions/Files/Allmänt.txt");
    Allmänt general=new Allmänt("Allmänt", AllmäntPath);

    KategoriTest() throws IOException {
    }


    @Test
    void answerChecker() {
        Answer testAnswer=null;
        String tempLine=("Hejsan, jag är ett falskt svar");

        testAnswer=general.answerChecker(tempLine);
        System.out.println(testAnswer.getAnswerText());
        System.out.println(testAnswer.getIsAnswerCorrect());

        Answer testAnswer2=general.answerChecker("*Hejsan jag är ett korrekt svar");
        System.out.println(testAnswer2.getAnswerText());
        System.out.println(testAnswer2.getIsAnswerCorrect());
    }
}