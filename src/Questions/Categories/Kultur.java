package Questions.Categories;

import Questions.Question;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

public class Kultur extends Kategori implements Serializable {

    public Kultur(String categoryName, Path filePathforQuestionsFile) throws IOException {
        super(categoryName);
        setFileWithQuestions(filePathforQuestionsFile);
        List<Question> list = getQuestionsFromFile(filePathforQuestionsFile);
        this.setListOfQuestions(list);
    }

}
