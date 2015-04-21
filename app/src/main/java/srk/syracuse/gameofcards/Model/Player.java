package srk.syracuse.gameofcards.Model;

import java.io.Serializable;

public class Player implements Serializable {
    public int playerID;
    public String username;
    public Hand hand;

    Player(int playerID, String username, Hand hand) {
        this.playerID = playerID;
        this.username = username;
        this.hand = hand;
    }
}
