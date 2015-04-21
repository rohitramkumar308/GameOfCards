package srk.syracuse.gameofcards.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import srk.syracuse.gameofcards.Activities.GameActivity;
import srk.syracuse.gameofcards.Adapters.DeckCustomizeAdapter;
import srk.syracuse.gameofcards.Model.CardCustomize;
import srk.syracuse.gameofcards.R;

/**
 * Created by rohitramkumar on 4/20/15.
 */
public class DeckCustomizeDialog extends DialogFragment {

    public static List<CardCustomize> cardList;

    public DeckCustomizeDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (cardList == null) {
            cardList = new ArrayList();
            initCardList();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.card_icon);
        builderSingle.setTitle("Customize Deck");
        final DeckCustomizeAdapter customizeDeck = new DeckCustomizeAdapter(cardList, getActivity());
        builderSingle.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cardList = new ArrayList();
                        initCardList();
                        dialog.dismiss();
                    }
                });
        builderSingle.setAdapter(customizeDeck,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = customizeDeck.getList().get(which).getCardTitle();
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                getActivity());
                        builderInner.setMessage(strName);
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        return builderSingle.create();
    }

    private void initCardList() {
        CardCustomize cards = new CardCustomize();
        cards.setCardImage(R.drawable.joker);
        cards.setIsSelected(true);
        cards.setCardTitle("Jokers");
        cardList.add(cards);
        cards = new CardCustomize();
        cards.setCardImage(R.drawable.clubs_king);
        cards.setIsSelected(true);
        cards.setCardTitle("Kings");
        cardList.add(cards);
        cards = new CardCustomize();
        cards.setCardImage(R.drawable.clubs_queen);
        cards.setIsSelected(true);
        cards.setCardTitle("Queens");
        cardList.add(cards);
        cards = new CardCustomize();
        cards.setCardImage(R.drawable.clubs_jack);
        cards.setIsSelected(true);
        cards.setCardTitle("Jacks");
        cardList.add(cards);
    }
}
