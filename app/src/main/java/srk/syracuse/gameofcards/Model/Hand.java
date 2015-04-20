package srk.syracuse.gameofcards.Model;

import java.util.ArrayList;

public class Hand
{
    private ArrayList<Cards> gameHand;
    private int numberOfCards;



    public void addCard(Cards card)
    {
        gameHand.add(card);
    }

    public Cards getCard()
    {
        Cards ret=gameHand.get(numberOfCards-1);
        gameHand.remove(--numberOfCards);
        return ret;
    }
}
