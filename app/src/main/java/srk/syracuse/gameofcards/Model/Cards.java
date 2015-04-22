package srk.syracuse.gameofcards.Model;

import java.io.Serializable;

public class Cards implements Serializable {
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
    public String imageID;
    public boolean cardFaceUp;


    public enum Suits {
        hearts,
        spades,
        diamonds,
        clubs,
        joker
    }

    public Cards() {

    }

    public Cards(int _suit, int _rank) {
        if (_suit != SPADES && _suit != HEARTS && _suit != DIAMONDS &&
                _suit != CLUBS && _suit != JOKER)
            throw new IllegalArgumentException("Illegal playing card suit");
        if (_suit != JOKER && (_rank < 1 || _rank > 13))
            throw new IllegalArgumentException("Illegal playing card value");

        this.suit = _suit;
        this.rank = _rank;
        this.cardFaceUp = false;
        this.imageID = getSuitAsString() + "_" + getValueAsString();
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSuitAsString() {
        switch (suit) {
            case SPADES:
                return "spades";
            case HEARTS:
                return "hearts";
            case DIAMONDS:
                return "diamonds";
            case CLUBS:
                return "clubs";
            default:
                return "joker";
        }
    }

    public String getValueAsString() {
        if (suit == JOKER)
            return "" + rank;
        else {
            switch (rank) {
                case 1:
                    return "ace";
                case 2:
                    return "two";
                case 3:
                    return "three";
                case 4:
                    return "four";
                case 5:
                    return "five";
                case 6:
                    return "six";
                case 7:
                    return "seven";
                case 8:
                    return "eight";
                case 9:
                    return "nine";
                case 10:
                    return "ten";
                case 11:
                    return "jack";
                case 12:
                    return "queen";
                default:
                    return "king";
            }
        }
    }

//    public String toString() {
//        if (suit == JOKER) {
//            if (suit == 1)
//                return "Joker";
//            else
//                return "Joker #" + rank;
//        } else
//            return getValueAsString() + " of " + getSuitAsString();
//    }

    public boolean isEqual(Cards card) {
        if (suit == card.suit && rank == card.rank)
            return true;
        return false;
    }
}
