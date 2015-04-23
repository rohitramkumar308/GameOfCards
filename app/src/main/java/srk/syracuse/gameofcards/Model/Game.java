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
            usernames.remove(usernames.size() - 1);
            throw new IllegalArgumentException("Number of players above the allowed limit (6)");
        }
        if (drawEqual && numberOfCardsDraw > ((numberOfDeck * (52 - restrictedCards.size())) / numberOfPlayer)) {
            usernames.remove(usernames.size() - 1);
            throw new IllegalArgumentException("Cards to be drawn per person not a valid number");
        }

        for (int i = 0; i < numberOfDeck; i++) {
            if (restrictedCards.size() > 0)
                decks.add(new Deck(restrictedCards));
            else
                decks.add(new Deck());
        }

        if (!drawEqual) {
            ArrayList<Hand> hands = getHand();

            for (int i = 0; i < numberOfPlayer; i++) {
                players.add(new Player(i + 1, usernames.get(i), hands.get(i), true));
            }
        } else {
            ArrayList<Hand> hands = getHand(this.numberOfCardsDraw);
            for (int i = 0; i < numberOfPlayer; i++) {
                players.add(new Player(i + 1, usernames.get(i), hands.get(i), true));
            }
        }
    }

    public ArrayList<Hand> getHand(int number) {
        ArrayList<Hand> allHands = new ArrayList();
        ArrayList<Cards> allCards = new ArrayList();
        ArrayList<Cards> handCards = new ArrayList();
        int deckNum;
        boolean didGive;

        for (int i = 0; i < numberOfDeck; i++) {
            Deck temp = decks.get(i);
            for (Cards card : temp.cards) {
                allCards.add(card);
            }
        }

        for (int k = 0; k < numberOfPlayer; k++) {
            for (int i = 0; i < number; i++) {
                shuffleDeck(allCards);
                handCards.add(allCards.get(0));
                allCards.remove(0);
            }
            allHands.add(new Hand(handCards));
            handCards = new ArrayList<>();
        }
        return allHands;
    }

    public ArrayList<Hand> getHand() {
        ArrayList<Hand> allHands = new ArrayList<Hand>(numberOfPlayer);
        ArrayList<Cards> allCards = new ArrayList();

        for (int j = 0; j < numberOfPlayer; j++) {
            allHands.add(new Hand());
        }

        for (int i = 0; i < numberOfDeck; i++) {
            Deck temp = decks.get(i);
            for (Cards card : temp.cards) {
                allCards.add(card);
            }
        }

        while (allCards.size() > 0) {
            int playernum = 0;
            while (playernum < numberOfPlayer && allCards.size() > 0) {
                if (allCards.size() != 1)
                    shuffleDeck(allCards);
                allHands.get(playernum).addCard(allCards.get(0));
                allCards.remove(0);
                playernum++;
            }
        }
        return allHands;
    }

    public ArrayList<Cards> shuffleDeck(ArrayList<Cards> allCards) {
        int random;
        for (int i = 0; i < allCards.size(); i++) {
            random = getRandomCard(i);
            Cards temp = allCards.get(i);
            allCards.set(i, allCards.get(random));
            allCards.set(random, temp);
        }
        return allCards;
    }

    static int getRandomCard(int cardNumber) {
        return (int) (Math.random() * cardNumber + 1);
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

