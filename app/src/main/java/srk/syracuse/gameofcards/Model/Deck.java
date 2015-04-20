package srk.syracuse.gameofcards.Model;

/**
 * Created by kunalshrivastava on 4/19/15.
 */
public class Deck
{
    private Cards[] cards;
    private int numberOfCards=52;

    Deck()
    {
        cards=new Cards[52];
        initDeck();
    }

    private void initDeck()
    {
        int cardIndex=0;
        for (int rank=1;rank<=13;rank++)
        {
            for (int suit=0;suit<4;suit++)
            {
                cards[cardIndex]=new Cards(rank, suit);
                cardIndex++;
            }
        }
    }

    Cards deal()
    {
        if (numberOfCards==0) return null;

        numberOfCards--;
        return cards[numberOfCards];
    }

    private void shuffleDeck()
    {
        int random=0;
        for (int i=0;i<numberOfCards;i++)
        {
            random=getRandomCard(i);
            Cards temp=cards[i];
            cards[i]=cards[random];
            cards[random]=temp;
        }
    }

    static int getRandomCard(int cardNumber)
    {
        return (int)(Math.random() * cardNumber + 1);
    }
}
