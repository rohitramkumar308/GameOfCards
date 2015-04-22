package srk.syracuse.gameofcards.Connections;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import srk.syracuse.gameofcards.Fragments.HostFragment;


public class ServerConnectionThread extends Thread {

    static final int SocketServerPORT = 8080;
    public static HashMap<Socket, String> socketUserMap;
    public static boolean serverStarted = false;
    public static ServerSocket serverSocket;


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
                    ServerSenderThread sendGameName = new ServerSenderThread(socket, HostFragment.gameName.getText().toString());
                    sendGameName.start();
                    socketUserMap.put(socket, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
