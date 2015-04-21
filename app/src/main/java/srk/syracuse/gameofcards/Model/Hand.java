package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable
{
    private ArrayList<Cards> gameHand;
    private int numberOfCards;

    Hand(ArrayList<Cards> cards)
    {
        this.gameHand=cards;
        this.numberOfCards=cards.size();
    }

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

    public boolean playCards(ArrayList<Cards> cards)
    {
        return false;
    }

    public void printHand()
    {
        for (Cards card:gameHand)
        {
            System.out.println(card.toString());
        }
    }
}