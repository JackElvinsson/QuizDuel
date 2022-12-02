import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Serverplayer extends Thread{


    public ObjectOutputStream getOutputStreamServerPlayer() {
        return outputStreamServerPlayer;
    }

    public void setOutputStreamServerPlayer(ObjectOutputStream outputStreamServerPlayer) {
        this.outputStreamServerPlayer = outputStreamServerPlayer;
    }

    public ObjectInputStream getInputStreamServerPlayer() {
        return inputStreamServerPlayer;
    }

    public void setInputStreamServerPlayer(ObjectInputStream inputStreamServerPlayer) {
        this.inputStreamServerPlayer = inputStreamServerPlayer;
    }

    public int getPlayerIDServerPlayer() {
        return playerIDServerPlayer;
    }

    public void setPlayerIDServerPlayer(int playerIDServerPlayer) {
        this.playerIDServerPlayer = playerIDServerPlayer;
    }

    private ObjectOutputStream outputStreamServerPlayer;
    private ObjectInputStream inputStreamServerPlayer;
    private int playerIDServerPlayer;
    private Socket socket;



    public Serverplayer(Socket socket, int id) {
        playerIDServerPlayer = id;
        this.socket = socket;
        try {
            outputStreamServerPlayer = new ObjectOutputStream(socket.getOutputStream());
            inputStreamServerPlayer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception from SSC Constructor");
        }
    }


}



