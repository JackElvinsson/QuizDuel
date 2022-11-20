package Questions;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuestionInit {
    Path generalQuestions = Paths.get("src/Questions/tempquestfile.txt");


    CatGeneral generalQ = new CatGeneral("General questions",generalQuestions);


    public QuestionInit() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        Path generalQuestions = Paths.get("src/Questions/tempquestfile.txt");
        CatGeneral generalQ = new CatGeneral("General questions",generalQuestions);

        System.out.println(generalQ.getCategoryName());
        System.out.println(generalQ.getListOfQuestions().get(0).getQuestionText());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer1().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer1().getIsAnswerCorrect());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer2().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer2().getIsAnswerCorrect());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer3().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer3().getIsAnswerCorrect());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer4().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(0).getAnswer4().getIsAnswerCorrect());
        System.out.println("\n\n\n");
        System.out.println(generalQ.getListOfQuestions().get(1).getQuestionText());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer1().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer1().getIsAnswerCorrect());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer2().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer2().getIsAnswerCorrect());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer3().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer3().getIsAnswerCorrect());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer4().getAnswerText());
        System.out.println(generalQ.getListOfQuestions().get(1).getAnswer4().getIsAnswerCorrect());

        System.out.println(generalQ.getListOfQuestions().size());


    }
}
