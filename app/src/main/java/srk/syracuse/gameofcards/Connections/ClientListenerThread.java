package srk.syracuse.gameofcards.Connections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientListenerThread extends Thread {

    String response = "";
    Socket socket;

    ClientListenerThread(Socket soc) {
        socket = soc;
    }

    @Override
    public void run() {
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        try {
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
