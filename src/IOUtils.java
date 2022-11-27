import Questions.Categories.Kategori;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class IOUtils {

    public IOUtils() {

    }
    public List<Kategori> fetchList() {

        List<Kategori> kategoriList;

        try (Socket socket = new Socket("127.0.0.1", 44444);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            kategoriList = (List<Kategori>) inputStream.readObject();
            System.out.println(kategoriList.get(0).getCategoryName());
            System.out.println(kategoriList.get(1).getCategoryName());
            System.out.println(kategoriList.get(2).getCategoryName());
            System.out.println(kategoriList.get(3).getCategoryName());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return kategoriList;
    }
}
