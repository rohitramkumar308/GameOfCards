package srk.syracuse.gameofcards.Model;

/**
 * Created by kunalshrivastava on 4/19/15.
 */
public class Cards
{
    public final static int SPADES = 0;
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;
    public final static int JOKER = 4;

    public final static int ACE = 1;
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;

    private int suit;
    private int rank;
    private int imageID;
    private String cardName;

    public enum Suits
    {
        hearts,
        spades,
        diamonds,
        clubs,
        joker
    }

    Cards(String cardName)
    {
        if(cardName.equals("joker"))
        {
            this.rank=JOKER;
            this.suit=1;
        }

        String[] meta=cardName.split("_");

    }

    Cards(int _suit,int _rank)
    {
        if (_suit != SPADES && _suit != HEARTS && _suit != DIAMONDS &&
                _suit != CLUBS && _suit != JOKER)
            throw new IllegalArgumentException("Illegal playing card suit");
        if (_suit != JOKER && (_rank < 1 || _rank > 13))
            throw new IllegalArgumentException("Illegal playing card value");

        this.suit=_suit;
        this.rank=_rank;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }


    public int getSuit()
    {
        return suit;
    }

    public void setSuit(int suit)
    {
        this.suit = suit;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public String getSuitAsString()
    {
        switch ( suit ) {
            case SPADES:   return "Spades";
            case HEARTS:   return "Hearts";
            case DIAMONDS: return "Diamonds";
            case CLUBS:    return "Clubs";
            default:       return "Joker";
        }
    }

    public String getValueAsString()
    {
        if (suit == JOKER)
            return "" + rank;
        else {
            switch ( rank ) {
                case 1:   return "Ace";
                case 2:   return "2";
                case 3:   return "3";
                case 4:   return "4";
                case 5:   return "5";
                case 6:   return "6";
                case 7:   return "7";
                case 8:   return "8";
                case 9:   return "9";
                case 10:  return "10";
                case 11:  return "Jack";
                case 12:  return "Queen";
                default:  return "King";
            }
        }
    }

    public int getSuitAsInt(String suit)
    {
        switch (Suits.valueOf(suit))
        {
            case diamonds:
                return DIAMONDS;
            case spades:
                return SPADES;
            case clubs:
                return CLUBS;
            case hearts:
                return HEARTS;
            case joker:
                return JOKER;
            default:
                return -1;
        }
    }

    public String toString()
    {
        if (suit == JOKER)
        {
            if (suit == 1)
                return "Joker";
            else
                return "Joker #" + rank;
        }
        else
            return getValueAsString() + " of " + getSuitAsString();
    }
}
