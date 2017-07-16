package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Cards implements Serializable {

    private enum Suit {
        SPADES(0),
        HEARTS(1),
        DIAMONDS(2),
        CLUBS(3),
        JOKER(4);

        private int value;

        private Suit(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    private int suit;
    private int rank;
    public String imageID;
    public boolean cardFaceUp;
    
    public Cards() {

    }

    public Cards(int _suit, int _rank) {
        if (_suit != Suit.SPADES.value() &&
                _suit != Suit.HEARTS.value() &&
                _suit != Suit.DIAMONDS.value() &&
                _suit != Suit.CLUBS.value() &&
                _suit != Suit.JOKER.value()) {
            throw new IllegalArgumentException("Illegal playing card suit");
        }
        if (_suit != Suit.JOKER.value() && (_rank < 1 || _rank > 13)) {
            throw new IllegalArgumentException("Illegal playing card value");
        }

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
            case 0:
                return "spades";
            case 1:
                return "hearts";
            case 2:
                return "diamonds";
            case 3:
                return "clubs";
            default:
                return "joker";
        }
    }

    public String getValueAsString() {
        switch (rank) {
            case 0:
                return "zero";
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

    public ArrayList<Cards> getCopyForAll(String cardRank) {
        ArrayList<Cards> tempCardSuitList = new ArrayList();
        if (cardRank.equalsIgnoreCase("joker")) {
            Cards card = new Cards();
            card.rank = 0;
            card.suit = Suit.JOKER.value();
            card.imageID = "joker_zero";
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
            return Suit.DIAMONDS.value();
        else if (suit.equals("hearts"))
            return Suit.HEARTS.value();
        else if (suit.equals("clubs"))
            return Suit.CLUBS.value();
        else if (suit.equals("spades"))
            return Suit.SPADES.value();

        return Suit.JOKER.value();
    }

    public int getRankFromString(String rank) {
        if (suit == Suit.JOKER.value()) {
            return 0;
        }
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
                return 11;
            case "queen":
                return 12;
            case "king":
                return 13;
            default:
                return 0;
        }
    }

    public boolean isEqual(Cards card) {
        if (suit == card.suit && rank == card.rank)
            return true;
        return false;
    }
}
