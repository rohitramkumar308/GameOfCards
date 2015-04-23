package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;

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

//    public ArrayList<Cards> getCopyForAll(String cardRank) {
//        ArrayList<Cards> tempCardSuitList = new ArrayList();
//        if (cardRank.equals("joker")) {
//            Cards card = new Cards();
//            card.imageID = cardRank;
//            tempCardSuitList.add(card);
//        } else {
//            Cards card = new Cards();
//            card.imageID = "diamonds_" + cardRank;
//            tempCardSuitList.add(card);
//            card = new Cards();
//            card.imageID = "hearts_" + cardRank;
//            tempCardSuitList.add(card);
//            card = new Cards();
//            card.imageID = "clubs_" + cardRank;
//            tempCardSuitList.add(card);
//            card = new Cards();
//            card.imageID = "spades_" + cardRank;
//            tempCardSuitList.add(card);
//        }
//        return tempCardSuitList;
//    }

    public ArrayList<Cards> getCopyForAll(String cardRank) {
        ArrayList<Cards> tempCardSuitList = new ArrayList();
        if (cardRank.equals("joker")) {
            Cards card = new Cards();
            String[] meta = card.imageID.split("_");
            card.rank = getRankFromString(meta[1]);
            card.suit = getSuitFromString(meta[0]);
            card.imageID = meta[0];
            tempCardSuitList.add(card);
        } else {
            Cards card = new Cards();

            card.imageID = "diamonds_" + cardRank;
            String[] meta = card.imageID.split("_");
            card.rank = getRankFromString(meta[1]);
            card.suit = getSuitFromString(meta[0]);
            tempCardSuitList.add(card);

            card = new Cards();
            card.imageID = "hearts_" + cardRank;
            String[] meta1 = card.imageID.split("_");
            card.rank = getRankFromString(meta1[1]);
            card.suit = getSuitFromString(meta1[0]);
            tempCardSuitList.add(card);

            card = new Cards();
            card.imageID = "clubs_" + cardRank;
            String[] meta2 = card.imageID.split("_");
            card.rank = getRankFromString(meta2[1]);
            card.suit = getSuitFromString(meta2[0]);
            tempCardSuitList.add(card);

            card = new Cards();
            card.imageID = "spades_" + cardRank;
            String[] meta3 = card.imageID.split("_");
            card.rank = getRankFromString(meta3[1]);
            card.suit = getSuitFromString(meta3[0]);
            tempCardSuitList.add(card);
        }
        return tempCardSuitList;
    }

    public int getSuitFromString(String suit) {
        if (suit.equals("diamonds"))
            return DIAMONDS;
        else if (suit.equals("hearts"))
            return HEARTS;
        else if (suit.equals("clubs"))
            return CLUBS;
        else if (suit.equals("spades"))
            return SPADES;

        return JOKER;
    }

    public int getRankFromString(String rank) {
        if (suit == JOKER)
            return 0;
        switch (rank) {
            case "ace":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
            case "ten":
                return 10;
            case "jack":
                return JACK;
            case "queen":
                return QUEEN;
            case "joker":
                return JOKER;
            default:
                return KING;
        }
    }

    public boolean isEqual(Cards card) {
        if (suit == card.suit && rank == card.rank)
            return true;
        return false;
    }
}
