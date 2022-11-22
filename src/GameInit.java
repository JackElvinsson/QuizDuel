import Questions.Categories.Kategori;
import Questions.Question;
import Questions.QuestionInit;

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


    public List<Kategori> generateCatOptionsList() {
        List<Kategori> categoryOptionsList = new ArrayList<>(4);
        return categoryOptionsList;
    }


}

