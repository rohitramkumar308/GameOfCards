package srk.syracuse.gameofcards.Model;

import java.awt.font.NumericShaper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kunalshrivastava on 4/20/15.
 */
public class Game implements Serializable {
    private int numberOfPlayer;
    private int numberOfDeck;
    private int numberOfCardsDraw;
    private boolean drawEqual;
    private ArrayList<Deck> decks;
    public ArrayList<Player> players;
    public String gameName;

    public Game(ArrayList<String> usernames, int numberOfDeck, int numberOfCardsDraw, boolean drawEqual, ArrayList<Cards> restrictedCards, String gameName) {
        this.decks = new ArrayList<Deck>();
        this.players = new ArrayList<Player>();
        this.numberOfPlayer = usernames.size();
        this.numberOfDeck = numberOfDeck;
        this.numberOfCardsDraw = numberOfCardsDraw;
        this.drawEqual = drawEqual;
        this.gameName = gameName;
        if (usernames.size() > 6) {
            throw new IllegalArgumentException("Number of players above the allowed limit (6)");
        }
        if (numberOfCardsDraw > ((numberOfDeck * (52 - restrictedCards.size())) / numberOfPlayer)) {
            throw new IllegalArgumentException("Cards to be drawn per person not a valid number");
        }

        for (int i = 0; i < numberOfDeck; i++) {
            if (restrictedCards.size() > 0)
                decks.add(new Deck(restrictedCards));
            else
                decks.add(new Deck());
        }

        for (int i = 0; i < usernames.size(); i++) {
            players.add(new Player(i + 1, usernames.get(i), getHand()));
        }

    }

    public Hand getHand() {
        ArrayList<Cards> handCards = new ArrayList<Cards>();
        int deckNum = 0;
        boolean didGive = false;
        for (int i = 0; i < this.numberOfCardsDraw; i++) {
            didGive = false;
            while (didGive != true) {
                deckNum = randInt(0, numberOfDeck - 1);
                Deck deck = decks.get(deckNum);
                if (deck.numberOfCards > 0) {
                    deck.shuffleDeck();
                    handCards.add(deck.removeCard());
                    didGive = true;
                }
            }
        }
        return new Hand(handCards);
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void articulate() {
        for (Player player : players) {
            System.out.println(player.playerID + " " + player.username);
            player.hand.printHand();
        }
    }
}

