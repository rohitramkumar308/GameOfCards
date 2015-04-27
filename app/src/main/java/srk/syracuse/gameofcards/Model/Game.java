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
    public ArrayList<Cards> deckCards;

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
        this.deckCards = populateDecks();
        for (int i = 0; i < numberOfPlayer; i++) {
            players.add(new Player(i + 1, usernames.get(i), new Hand(), true));
        }
        if (!drawEqual) {
            getHand();
        } else {
            getHand(this.numberOfCardsDraw);
        }
        usernames.remove(usernames.size() - 1);
    }

    public ArrayList<Cards> populateDecks() {
        ArrayList<Cards> allCards = new ArrayList();
        for (int i = 0; i < numberOfDeck; i++) {
            Deck temp = decks.get(i);
            for (Cards card : temp.cards) {
                allCards.add(card);
            }
        }
        return allCards;
    }

    public void getHand(int number) {
        for (int k = 0; k < numberOfPlayer; k++) {
            for (int i = 0; i < number; i++) {
                shuffleDeck(this.deckCards);
                players.get(k).hand.addCard(this.deckCards.get(0));
                this.deckCards.remove(0);
            }
        }
    }

    public void setHandPlayer(int number, String userName) {
        for (int k = 0; k < numberOfPlayer; k++) {
            if (players.get(k).username.equals(userName)) {
                for (int i = 0; i < number; i++) {
                    shuffleDeck(this.deckCards);
                    players.get(k).hand.addCard(this.deckCards.get(0));
                    this.deckCards.remove(0);
                }
                break;
            }
        }
    }

    public ArrayList<Cards> getCardsFromDeck(int number) {
        ArrayList<Cards> cardList = new ArrayList();
        shuffleDeck(this.deckCards);
        for (int i = 0; i < number; i++) {
            Cards card = this.deckCards.get(0);
            card.cardFaceUp = true;
            cardList.add(card);
            this.deckCards.remove(0);
        }
        return cardList;
    }

    public void getHand() {
        while (this.deckCards.size() > 0) {
            int playernum = 0;
            while (playernum < numberOfPlayer && this.deckCards.size() > 0) {
                if (this.deckCards.size() != 1)
                    shuffleDeck(this.deckCards);
                players.get(playernum).hand.addCard(this.deckCards.get(0));
                this.deckCards.remove(0);
                playernum++;
            }
        }
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

