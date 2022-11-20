package Questions;

import Questions.Categories.Allmänt;
import Questions.Categories.Sport;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuestionInit {


    public static void main(String[] args) throws IOException {

        Path AllmäntPath = Paths.get("src/Questions/Files/Allmänt.txt");
        Allmänt generalQ = new Allmänt("Allmän", AllmäntPath);

        Path SportPath = Paths.get("src/Questions/Files/Sport.txt");
        Sport sportQ = new Sport("Sport", SportPath);


    }
}
