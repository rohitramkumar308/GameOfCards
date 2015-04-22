package srk.syracuse.gameofcards.Connections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import srk.syracuse.gameofcards.Fragments.GameFragment;
import srk.syracuse.gameofcards.Model.Game;

public class ClientSenderThread extends Thread {

    private Socket hostThreadSocket;
    Object message;

    public ClientSenderThread(Socket socket, Object message) {
        hostThreadSocket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;
        if (hostThreadSocket.isConnected()) {
            try {
                outputStream = hostThreadSocket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(message);
                if (message instanceof Game && !GameFragment.thisPlayer.isActive) {
                    ClientConnectionThread.closeServerSocket();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
