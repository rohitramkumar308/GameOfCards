package srk.syracuse.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Table implements Serializable{
    private Stack<Cards> TableCards;

    public Table()
    {
        this.TableCards = new Stack<Cards>();
    }

    public int getTableCardsCount()
    {
        return this.TableCards.size();
    }

    public void removeCardsFromTable(int count)
    {
        for(int i=0; i<count; i++)
            this.TableCards.pop();
    }

    public void putCardsOnTable(ArrayList<Cards> cards){
        for(int i=0; i<cards.size(); i++)
            this.TableCards.push(cards.get(i));
    }
}
