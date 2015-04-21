package srk.syracuse.gameofcards.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import srk.syracuse.gameofcards.Fragments.HostFragment;
import srk.syracuse.gameofcards.Fragments.JoinGameFragment;
import srk.syracuse.gameofcards.Fragments.PlayerListFragment;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.PlayerInfo;

/**
 * Created by rohitramkumar on 4/20/15.
 */
public class ServerHandler extends Handler {

    public final static String PLAYER_LIST_UPDATE = "updatePlayerList";
    public final static String ACTION_KEY = "action";
    public final static String DATA_KEY = "data";


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle messageData = msg.getData();
        String value = messageData.getString(ACTION_KEY);
        Object gameObject = (Object) messageData.getSerializable(DATA_KEY);

        if (gameObject instanceof PlayerInfo) {
            PlayerInfo playerInfo = (PlayerInfo) gameObject;
            PlayerListFragment.deviceList.add(playerInfo.username);
            PlayerListFragment.mAdapter.notifyItemInserted(PlayerListFragment.deviceList.size() - 1);
        }


    }
}
