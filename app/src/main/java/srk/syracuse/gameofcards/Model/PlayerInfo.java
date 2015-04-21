package srk.syracuse.gameofcards.Model;

import java.io.Serializable;

public class PlayerInfo implements Serializable {
    public String username;

    public PlayerInfo(String username) {
        this.username = username;
    }
}
