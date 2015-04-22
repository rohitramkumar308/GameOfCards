package srk.syracuse.gameofcards.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import srk.syracuse.gameofcards.Fragments.GameFragment;
import srk.syracuse.gameofcards.Fragments.JoinGameFragment;
import srk.syracuse.gameofcards.Model.Game;


public class ClientHandler extends Handler {


    Bundle messageData;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        messageData = msg.getData();
        String value = messageData.getString(Constants.ACTION_KEY);
        Object clientObject = messageData.getSerializable(Constants.DATA_KEY);
        if (value != null && value.equals(Constants.UPDATE_GAME_NAME)) {
            String gameName = (String) clientObject;
            JoinGameFragment.gameName.setText(gameName);
        }
        if (clientObject instanceof Game) {
            if (GameFragment.gameObject != null) {
                GameFragment.gameObject = (Game) clientObject;
                GameFragment.updatePlayerStatus();
                GameFragment.updateTable();
            } else {
                JoinGameFragment.gameobject = (Game) clientObject;
            }
        }
    }
}
