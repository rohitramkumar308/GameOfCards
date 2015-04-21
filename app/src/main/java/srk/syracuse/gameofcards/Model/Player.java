package srk.syracuse.gameofcards.Model;

import java.io.Serializable;

public class Player implements Serializable {
    public int playerID;
    public String username;
    public Hand hand;
    public boolean isActive;

    Player(int playerID, String username, Hand hand, boolean isActive) {
        this.playerID = playerID;
        this.username = username;
        this.hand = hand;
        this.isActive = isActive;
    }
}
