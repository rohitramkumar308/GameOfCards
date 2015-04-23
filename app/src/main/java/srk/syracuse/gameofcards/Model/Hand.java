package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {
    public ArrayList<Cards> gameHand;
    public int numberOfCards;
    public boolean handFaceUp;

    Hand() {
        this.handFaceUp = false;
        this.gameHand = new ArrayList<Cards>();
    }

    Hand(ArrayList<Cards> cards) {
        this.gameHand = cards;
        this.numberOfCards = cards.size();
        this.handFaceUp = false;
    }

    public void addCard(Cards card) {
        gameHand.add(card);
    }

    public Cards getCard() {
        Cards ret = gameHand.get(numberOfCards - 1);
        gameHand.remove(--numberOfCards);
        return ret;
    }

    public Cards getCard(int position) {
        Cards ret = gameHand.get(position);
        return ret;
    }

    public boolean playCards(ArrayList<Cards> cards) {
        return false;
    }

    public void printHand() {
        for (Cards card : gameHand) {
            System.out.println(card.toString());
        }
    }

    public void isHandFaceUp() {
        for (int i = 0; i < this.gameHand.size(); i++)
            if (!this.getCard(i).cardFaceUp) {
                this.handFaceUp = false;
                return;
            }
        this.handFaceUp = true;
    }
}