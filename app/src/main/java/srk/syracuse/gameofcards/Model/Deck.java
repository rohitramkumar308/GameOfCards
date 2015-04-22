package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {
    public Cards[] cards;
    public int numberOfCards;

    Deck()
    {
        cards=new Cards[52];
        initDeck();
    }

    Deck(ArrayList<Cards> restrictedCards)
    {
        cards=new Cards[52-restrictedCards.size()];
        initDeckWithRestrictedCards(restrictedCards);
    }

    private void initDeck()
    {
        int cardIndex=0;
        for (int rank=1;rank<=13;rank++)
        {
            for (int suit=0;suit<4;suit++)
            {
                cards[cardIndex]=new Cards(suit, rank);
                cardIndex++;
            }
        }
        numberOfCards=cardIndex;
    }

    private void initDeckWithRestrictedCards(ArrayList<Cards> restrictedCards)
    {
        int cardIndex=0;
        boolean contains;
        for (int rank=1;rank<=13;rank++)
        {
            for (int suit=0;suit<4;suit++)
            {
                Cards temp=new Cards(suit, rank);
                contains=false;
                for(Cards crd : restrictedCards)
                {
                    if(temp.isEqual(crd))
                    {
                        contains=true;
                    }
                }

                if(!contains)
                {
                    cards[cardIndex]=new Cards(suit, rank);
                    cardIndex++;
                }
            }
        }
        numberOfCards=cardIndex;
    }

    Cards deal()
    {
        if (numberOfCards==0) return null;

        numberOfCards--;
        return cards[numberOfCards];
    }

    public void shuffleDeck()
    {
        int random;
        for (int i=0;i<numberOfCards;i++)
        {
            random=getRandomCard(i);
            Cards temp=cards[i];
            cards[i]=cards[random];
            cards[random]=temp;
        }
    }

    public Cards removeCard()
    {
        Cards crd=cards[numberOfCards-1];
        numberOfCards--;
        return crd;
    }

    static int getRandomCard(int cardNumber)
    {
        return (int)(Math.random() * cardNumber + 1);
    }

    public void articulate()
    {
        for (Cards card : cards)
        {
            System.out.println(card.toString());
        }

        System.out.println("Number of cards:"+ numberOfCards);
    }
}