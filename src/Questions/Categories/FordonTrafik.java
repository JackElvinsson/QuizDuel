package Questions.Categories;

import Questions.Question;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

public class FordonTrafik extends Kategori {

    public FordonTrafik(String categoryName, String filePathforQuestionsFile) throws IOException {
        super(categoryName);
        setFileWithQuestions(filePathforQuestionsFile);
        List<Question> list = getQuestionsFromFile(filePathforQuestionsFile);
        this.setListOfQuestions(list);
    }

}
