package srk.syracuse.gameofcards.Connections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import srk.syracuse.gameofcards.Fragments.PlayerListFragment;
import srk.syracuse.gameofcards.Model.Game;

public class ServerSenderThread extends Thread {

    private Socket hostThreadSocket;
    Object message;

    public ServerSenderThread(Socket socket, Object message) {
        hostThreadSocket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(message);
            if (message instanceof Game) {
                PlayerListFragment.gameObject = (Game) message;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
