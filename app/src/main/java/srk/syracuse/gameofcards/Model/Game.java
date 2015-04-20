package srk.syracuse.gameofcards.Model;

import java.util.ArrayList;

/**
 * Created by kunalshrivastava on 4/20/15.
 */
public class Game
{
    private int numberOfPlayer;
    private int numberOfDeck;
    private int numberOfCardsDraw;
    private boolean drawEqual;
    private ArrayList<Deck> decks;

    Game(int numberOfPlayer, int numberOfDeck, int numberOfCardsDraw, boolean drawEqual, ArrayList<Cards> restrictedCards)
    {
        if(numberOfPlayer>6)
        {
            throw new IllegalArgumentException("Number of players above the allowed limit (6)");
        }
        if(numberOfCardsDraw>((numberOfDeck*(52-restrictedCards.size()))/numberOfPlayer))
        {
            throw new IllegalArgumentException("Cards to be drawn per person not a valid number");
        }
        this.numberOfPlayer=numberOfPlayer;
        this.numberOfDeck=numberOfDeck;
        for (int i=0;i<numberOfDeck;i++)
        {
            if(restrictedCards.size()>0)
                decks.add(new Deck(restrictedCards));
            else
                decks.add(new Deck());
        }
        this.numberOfCardsDraw=numberOfCardsDraw;
        this.drawEqual=drawEqual;
    }
}

