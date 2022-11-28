//import Questions.Categories.Kategori;

import Questions.Categories.Kategori;
import Questions.Question;
import Questions.QuestionInit;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInit implements Serializable {

    Kategori selectedCategory;


    QuestionInit qInit = new QuestionInit();
    List<Kategori> categoryList = qInit.getCategories();


    public Kategori getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Kategori selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

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
        setSelectedCategory(selectedItem);
        for (Kategori cat : list) {
            for (Kategori originalCat : categoryList) {
                if (cat.equals(originalCat) && !cat.equals(selectedItem))
                    originalCat.setUsed(false);
            }
        }
    }

    /**
     * Använd "Kategori selectedCategory" som inparameter när man kallar på metoden.
     * Kategori selectedCategory sätts utav metoden makeNotChosenCategoryAvailable.
     */
    public List<Question> getQuestions(Kategori chosenCategory, int numberOfQuestions) throws IOException {

        return chosenCategory.generateQuestions(selectedCategory, numberOfQuestions);
    }

    public void skrivnågot() {
        System.out.println("Ser båda klienter?");
    }
}

