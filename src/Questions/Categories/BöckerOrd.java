package Questions.Categories;

import Questions.Question;

import java.io.IOException;
import java.util.List;

public class BöckerOrd extends Kategori {

    public BöckerOrd(String categoryName, String filePathforQuestionsFile) throws IOException {
        super(categoryName);
        setFileWithQuestions(filePathforQuestionsFile);
        List<Question> list = getQuestionsFromFile(filePathforQuestionsFile);
        this.setListOfQuestions(list);
    }

}
