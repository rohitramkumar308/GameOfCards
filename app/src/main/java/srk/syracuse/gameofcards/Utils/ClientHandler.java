package srk.syracuse.gameofcards.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import srk.syracuse.gameofcards.Fragments.JoinGameFragment;
import srk.syracuse.gameofcards.Model.Game;

/**
 * Created by rohitramkumar on 4/20/15.
 */
public class ClientHandler extends Handler {

    public final static String UPDATE_GAME_NAME = "gameName";
    public final static String ACTION_KEY = "action";
    public final static String DATA_KEY = "data";
    Bundle messageData;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        messageData = msg.getData();
        String value = messageData.getString(ACTION_KEY);
        if (value.equals(UPDATE_GAME_NAME)) {
            String gameName = (String) messageData.getSerializable(DATA_KEY);
            JoinGameFragment.gameName.setText(gameName);
        } else {
            Game gameObject = (Game) messageData.getSerializable(DATA_KEY);

        }
    }
}
