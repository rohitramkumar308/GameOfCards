package srk.syracuse.gameofcards.Connections;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import srk.syracuse.gameofcards.Fragments.HostFragment;


public class ServerConnectionThread extends Thread {

    static final int SocketServerPORT = 8080;
    public static Set<Socket> socketSet = new HashSet();
    public static boolean serverStarted = false;
    ServerSocket serverSocket;


    public ServerConnectionThread() {

    }

    @Override
    public void run() {
        if (serverSocket == null) {

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                serverStarted = true;
                while (true) {
                    Socket socket = serverSocket.accept();
                    Thread socketListenThread = new Thread(new ServerListenerThread(socket));
                    socketListenThread.start();
                    ServerSenderThread sendGameName = new ServerSenderThread(socket, new String(HostFragment.gameName.getText().toString()));
                    sendGameName.start();
                    socketSet.add(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
