package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Game implements Serializable {
    private int numberOfPlayer;
    private int numberOfDeck;
    private int numberOfCardsDraw;
    private boolean drawEqual;
    private ArrayList<Deck> decks;
    public ArrayList<Player> players;
    public String gameName;
    public int cardBackImage;
    public String senderUsername;
    public Table mTable;
    private int actionKey;
    public int gameBackground;

    public Game(ArrayList<String> usernames, int numberOfDeck, int numberOfCardsDraw, boolean drawEqual, ArrayList<Cards> restrictedCards, String gameName) {
        this.senderUsername = null;
        this.decks = new ArrayList();
        this.players = new ArrayList();
        this.numberOfPlayer = usernames.size();
        this.numberOfDeck = numberOfDeck;
        this.numberOfCardsDraw = numberOfCardsDraw;
        this.drawEqual = drawEqual;
        this.gameName = gameName;
        mTable = new Table();
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
            players.add(new Player(i + 1, usernames.get(i), getHand(), true));
        }

    }

    public Hand getHand() {
        ArrayList<Cards> handCards = new ArrayList();
        int deckNum;
        boolean didGive;
        for (int i = 0; i < this.numberOfCardsDraw; i++) {
            didGive = false;
            while (!didGive) {
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
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public void articulate() {
        for (Player player : players) {
            System.out.println(player.playerID + " " + player.username);
            player.hand.printHand();
        }
    }

    public int getActionKey() {
        return actionKey;
    }

    public void setActionKey(int actionKey) {
        this.actionKey = actionKey;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getNumberOfDeck() {
        return numberOfDeck;
    }

    public void setNumberOfDeck(int numberOfDeck) {
        this.numberOfDeck = numberOfDeck;
    }

    public int getNumberOfCardsDraw() {
        return numberOfCardsDraw;
    }

    public void setNumberOfCardsDraw(int numberOfCardsDraw) {
        this.numberOfCardsDraw = numberOfCardsDraw;
    }

    public boolean isDrawEqual() {
        return drawEqual;
    }

    public void setDrawEqual(boolean drawEqual) {
        this.drawEqual = drawEqual;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Table getmTable() {
        return mTable;
    }

    public void setmTable(Table mTable) {
        this.mTable = mTable;
    }
}

