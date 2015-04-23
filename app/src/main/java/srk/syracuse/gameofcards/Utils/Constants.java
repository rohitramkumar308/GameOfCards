package srk.syracuse.gameofcards.Utils;

import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.Player;

public abstract class Constants {
    public static final int MOVE_FOLD = 0;
    public static final int UPDATE_GAME_NAME = 1;
    public static final int GAME_PLAY = 2;
    public static final int PLAYER_LIST_UPDATE = 3;
    public static final int NEW_GAME = 4;
    public static final int DEAL_CARD = 5;
    public static final String ACTION_KEY = "action";
    public static final String DATA_KEY = "data";

    public static boolean isPlayerActive(String userName, Game gameObject) {
        for (int i = 0; i < gameObject.getPlayers().size(); i++) {
            Player play = gameObject.getPlayers().get(i);
            if (play.username.equals(userName) && play.isActive) {
                return true;
            }
        }
        return false;
    }
}
