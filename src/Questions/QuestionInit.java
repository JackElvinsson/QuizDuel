package Questions;

import Questions.Categories.*;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuestionInit {

    public static void main(String[] args) throws IOException {
        final Path AllmäntPath = Paths.get("src/Questions/Files/Allmänt.txt");
        Allmänt generalQ = new Allmänt("Allmän", AllmäntPath);

        final Path SportPath = Paths.get("src/Questions/Files/Sport.txt");
        Sport sportQ = new Sport("Sport", SportPath);

        final Path djurNaturPath=Paths.get("src/Questions/Files/Djur & Natur.txt");
        DjurNatur djurNaturQ=new DjurNatur("Djur & Natur", djurNaturPath);

        final Path tvFilmPath = Paths.get("src/Questions/Files/TV-Serier & Film.txt");
        TVFilm tvFilmQ = new TVFilm("TV-Seier & Film", tvFilmPath);

        final Path religionMytologiPath = Paths.get("src/Questions/Files/Religion & Mytologi.txt");
        ReligionMytologi religionMytologiQ = new ReligionMytologi("Religion & Mytologi.txt", religionMytologiPath);

        final Path historiaPath = Paths.get("src/Questions/Files/Historia.txt");
        Historia historiaQ = new Historia("Historia", historiaPath);

        final Path musikPath = Paths.get("src/Questions/Files/Musik.txt");
        Musik musikQ = new Musik("Musik", musikPath);

        final Path politikPath = Paths.get("src/Questions/Files/Politik.txt");
        Politik politikQ = new Politik("Politik", politikPath);

        final Path kulturPath = Paths.get("src/Questions/Files/Kultur.txt");
        Kultur kulturQ = new Kultur("Kultur", kulturPath);

        final Path geografiPath = Paths.get("src/Questions/Files/Geografi.txt");
        Geografi geografiQ=new Geografi("Geografi", geografiPath);

        final Path böckerOrdPath = Paths.get("src/Questions/Files/Böcker & Ord.txt");
        BöckerOrd böckerOrdQ=new BöckerOrd("Böcker & Ord", böckerOrdPath);

        final Path fordonTrafikPath = Paths.get("src/Questions/Files/Fordon & Trafik.txt");
        FordonTrafik fordonTrafikQ=new FordonTrafik("Fordon & Trafik", fordonTrafikPath);



    }




}
