package srk.syracuse.gameofcards.Connections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerListenerThread extends Thread {

    private Socket hostThreadSocket;
    String response = "";
    String name = null;

    ServerListenerThread(Socket soc, String clientName) {
        hostThreadSocket = soc;
        name = clientName;
    }

    @Override
    public void run() {
        while (true) {

            try {
                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = hostThreadSocket.getInputStream();

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    response = "";
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8") + " : " + name;

                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }

        }
    }
}
