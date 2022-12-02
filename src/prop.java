//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Properties;
//
//public class prop {
//
//    public Properties getP() {
//        return p;
//    }
//
//    Properties p = new Properties();
//
//    int maxRounds;
//
//        try {
//            p.load(new FileInputStream("Server/Settings.properties"));
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//
//        int rounds = Integer.parseInt(p.getProperty("numberOfRounds", "2"));
//        int questions = Integer.parseInt(p.getProperty("numberOfQuestions", "2"));
//
//        if (rounds > 6 || rounds < 1) {
//            rounds = 3;
//        }
//        if (questions > 5 || questions < 1) {
//            questions = 3;
//        }
//        maxRounds = rounds;
//                ? =new ? (questions);
//    }
//
//    public prop() throws FileNotFoundException, IOException {
//
//    }
//
//
//
//}
//
