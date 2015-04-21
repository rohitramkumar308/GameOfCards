package srk.syracuse.gameofcards.Connections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSenderThread extends Thread {

    private Socket hostThreadSocket;
    Object message;

    ClientSenderThread(Socket socket, Object message) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
