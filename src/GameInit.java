//import Questions.Categories.Kategori;
import Questions.Categories.Kategori;
import Questions.QuestionInit;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInit {


    QuestionInit qInit = new QuestionInit();
    List<Kategori> categoryList = qInit.getCategories();


    public GameInit() throws IOException {

    }

    public List<Kategori> getCategoryList() {
        return categoryList;
    }


    //Todo; fixa så att om det inte finns 4st svar att loopen inte fastnar
    public List<Kategori> getCategoryOptions() {

        List<Kategori> listOfOptions = new ArrayList<>();
        Collections.shuffle(categoryList);

        for (Kategori cat : categoryList) {
            if (listOfOptions.size() < 4 && !cat.getUsed()) {
                listOfOptions.add(cat);
                cat.setUsed(true);
            }
        }
        return listOfOptions;
    }


    public void makeNotChosenCategoryAvailable(List<Kategori> list, Kategori selectedItem) {
        for (Kategori cat : list) {
            for (Kategori originalCat : categoryList)
                if (cat.equals(originalCat) && !cat.equals(selectedItem))
                    originalCat.setUsed(false);
        }
    }


    public void skrivnågot() {
        System.out.println("Ser båda klienter?");
    }


    public void setQuestionAndAnswers(List<Kategori> categoryOptions, JTextArea questionTextArea, JButton firstAnswer, JButton secondAnswer, JButton thirdAnswer, JButton fourthAnswer, int i) {

        questionTextArea.setText(categoryOptions.get(i).getListOfQuestions().get(0).getQuestionText());
        firstAnswer.setText(categoryOptions.get(i).getListOfQuestions().get(0).getAnswer1().getAnswerText());
        secondAnswer.setText(categoryOptions.get(i).getListOfQuestions().get(0).getAnswer2().getAnswerText());
        thirdAnswer.setText(categoryOptions.get(i).getListOfQuestions().get(0).getAnswer3().getAnswerText());
        fourthAnswer.setText(categoryOptions.get(i).getListOfQuestions().get(0).getAnswer4().getAnswerText());
    }

    public void setCategories(List<Kategori> categoryOptions, JButton firstCategory, JButton secondCategory, JButton thirdCategory, JButton fourthCategory) {


        firstCategory.setText(categoryOptions.get(0).getCategoryName());
        secondCategory.setText(categoryOptions.get(1).getCategoryName());
        thirdCategory.setText(categoryOptions.get(2).getCategoryName());
        fourthCategory.setText(categoryOptions.get(3).getCategoryName());
    }


}

