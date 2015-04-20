package srk.syracuse.gameofcards.Connections;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ServerConnectionThread extends Thread {

    static final int SocketServerPORT = 8080;
    int count = 0;
    public static HashSet<Socket> socketSet = new HashSet<Socket>();
    ServerSocket serverSocket;

    ServerConnectionThread() {
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(SocketServerPORT);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread socketAcceptThread = new Thread(new ServerListenerThread(socket, "Client " + count));
                socketAcceptThread.start();
                socketSet.add(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
