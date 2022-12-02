package Questions;

import Questions.Categories.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionInit {

//    final Path AllmäntPath = Paths.get("src/Questions/Files/Allmänt.txt");
//    Allmänt allmäntQ = new Allmänt("Allmänt", AllmäntPath);
//    final Path SportPath = Paths.get("src/Questions/Files/Sport.txt");
//    Sport sportQ = new Sport("Sport", SportPath);
//    final Path djurNaturPath=Paths.get("src/Questions/Files/Djur & Natur.txt");
//    DjurNatur djurNaturQ=new DjurNatur("Djur & Natur", djurNaturPath);
//    final Path tvFilmPath = Paths.get("src/Questions/Files/TV-Serier & Film.txt");
//    TVFilm tvFilmQ = new TVFilm("TV-Serier & Film", tvFilmPath);
//    final Path religionMytologiPath = Paths.get("src/Questions/Files/Religion & Mytologi.txt");
//    ReligionMytologi religionMytologiQ = new ReligionMytologi("Religion & Mytologi.txt", religionMytologiPath);
//    final Path historiaPath = Paths.get("src/Questions/Files/Historia.txt");
//    Historia historiaQ = new Historia("Historia", historiaPath);
//    final Path musikPath = Paths.get("src/Questions/Files/Musik.txt");
//    Musik musikQ = new Musik("Musik", musikPath);
//    final Path politikPath = Paths.get("src/Questions/Files/Politik.txt");
//    Politik politikQ = new Politik("Politik", politikPath);
//    final Path kulturPath = Paths.get("src/Questions/Files/Kultur.txt");
//    Kultur kulturQ = new Kultur("Kultur", kulturPath);
//    final Path geografiPath = Paths.get("src/Questions/Files/Geografi.txt");
//    Geografi geografiQ=new Geografi("Geografi", geografiPath);
//    final Path böckerOrdPath = Paths.get("src/Questions/Files/Böcker & Ord.txt");
//    BöckerOrd böckerOrdQ=new BöckerOrd("Böcker & Ord", böckerOrdPath);
//    final Path fordonTrafikPath = Paths.get("src/Questions/Files/Fordon & Trafik.txt");
//    FordonTrafik fordonTrafikQ=new FordonTrafik("Fordon & Trafik", fordonTrafikPath);

    final String AllmäntPath = ("src/Questions/Files/Allmänt.txt");
    Allmänt allmäntQ = new Allmänt("Allmänt", AllmäntPath);
    final String SportPath = ("src/Questions/Files/Sport.txt");
    Sport sportQ = new Sport("Sport", SportPath);
    final String djurNaturPath = ("src/Questions/Files/Djur & Natur.txt");
    DjurNatur djurNaturQ = new DjurNatur("Djur & Natur", djurNaturPath);
    final String tvFilmPath = ("src/Questions/Files/TV-Serier & Film.txt");
    TVFilm tvFilmQ = new TVFilm("TV-Serier & Film", tvFilmPath);
    final String religionMytologiPath = ("src/Questions/Files/Religion & Mytologi.txt");
    ReligionMytologi religionMytologiQ = new ReligionMytologi("Religion & Mytologi", religionMytologiPath);
    final String historiaPath = ("src/Questions/Files/Historia.txt");
    Historia historiaQ = new Historia("Historia", historiaPath);
    final String musikPath = ("src/Questions/Files/Musik.txt");
    Musik musikQ = new Musik("Musik", musikPath);
    final String politikPath = ("src/Questions/Files/Politik.txt");
    Politik politikQ = new Politik("Politik", politikPath);
    final String kulturPath = ("src/Questions/Files/Kultur.txt");
    Kultur kulturQ = new Kultur("Kultur", kulturPath);
    final String geografiPath = ("src/Questions/Files/Geografi.txt");
    Geografi geografiQ = new Geografi("Geografi", geografiPath);
    final String böckerOrdPath = ("src/Questions/Files/Böcker & Ord.txt");
    BöckerOrd böckerOrdQ = new BöckerOrd("Böcker & Ord", böckerOrdPath);
    final String fordonTrafikPath = ("src/Questions/Files/Fordon & Trafik.txt");
    FordonTrafik fordonTrafikQ = new FordonTrafik("Fordon & Trafik", fordonTrafikPath);


    public QuestionInit() throws IOException {
    }


//    public static void main(String[] args) {
//        QuestionInit wasd=new QuestionInit();
//        wasd.getCategories();
//    }

    public List<Kategori> getCategories() {
        List<Kategori> categoriesList = new ArrayList<>();
        categoriesList.add(allmäntQ);
        categoriesList.add(böckerOrdQ);
        categoriesList.add(djurNaturQ);
        categoriesList.add(fordonTrafikQ);
        categoriesList.add(geografiQ);
        categoriesList.add(historiaQ);
        categoriesList.add(kulturQ);
        categoriesList.add(musikQ);
        categoriesList.add(politikQ);
        categoriesList.add(religionMytologiQ);
        categoriesList.add(sportQ);
        categoriesList.add(tvFilmQ);
        return categoriesList;
    }


}
