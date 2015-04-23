package srk.syracuse.gameofcards.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.net.Socket;
import java.util.Iterator;

import srk.syracuse.gameofcards.Connections.ServerConnectionThread;
import srk.syracuse.gameofcards.Connections.ServerSenderThread;
import srk.syracuse.gameofcards.Fragments.GameFragment;
import srk.syracuse.gameofcards.Fragments.PlayerListFragment;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.PlayerInfo;


public class ServerHandler extends Handler {


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle messageData = msg.getData();
        Object gameObject = messageData.getSerializable(Constants.DATA_KEY);

        if (gameObject instanceof PlayerInfo) {
            PlayerInfo playerInfo = (PlayerInfo) gameObject;
            PlayerListFragment.deviceList.add(playerInfo.username);
            PlayerListFragment.mAdapter.notifyItemInserted(PlayerListFragment.deviceList.size() - 1);
        }
        if (gameObject instanceof Game) {
            if (GameFragment.gameObject != null) {
                GameFragment.gameObject = (Game) gameObject;
                GameFragment.updatePlayerStatus();
                GameFragment.updateTable();
                sendToAll(gameObject);
            } else {
                PlayerListFragment.gameObject = (Game) gameObject;
            }
        }


    }

    public static void sendToAll(Object gameObject) {
        Iterator<Socket> socketIterator = ServerConnectionThread.socketUserMap.keySet().iterator();
        Socket socket;
        while (socketIterator.hasNext()) {
            socket = socketIterator.next();
            if (!ServerConnectionThread.socketUserMap.get(socket).equals(((Game) gameObject).senderUsername)) {
                ServerSenderThread sendGameName = new ServerSenderThread(socket, gameObject);
                sendGameName.start();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
