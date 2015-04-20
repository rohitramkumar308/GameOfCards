package srk.syracuse.gameofcards.Connections;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSenderThread extends Thread {

    private Socket hostThreadSocket;
    int cnt;

    ServerSenderThread(Socket socket, int c) {
        hostThreadSocket = socket;
        cnt = c;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        byte[] msgReply = new byte[1024];
        int len;
        try {
            outputStream = hostThreadSocket.getOutputStream();
            msgReply = "Hello  from Server".getBytes();
            outputStream.write(msgReply);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
