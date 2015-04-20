package srk.syracuse.gameofcards.Model;

public class Player 
{
	public int playerID;
	public String username;
	public Hand hand;
	
	Player(int playerID, String username, Hand hand)
	{
		this.playerID=playerID;
		this.username=username;
		this.hand=hand;
	}
}
