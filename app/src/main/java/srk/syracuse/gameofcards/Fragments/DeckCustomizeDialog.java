package srk.syracuse.gameofcards.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;


import java.util.ArrayList;

import java.util.List;

import srk.syracuse.gameofcards.Adapters.DeckCustomizeAdapter;
import srk.syracuse.gameofcards.Model.CardCustomize;
import srk.syracuse.gameofcards.R;

public class DeckCustomizeDialog extends DialogFragment {

    public static List<CardCustomize> cardList;
    public static List<CardCustomize> exclusionCardList;

    public DeckCustomizeDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (cardList == null) {
            cardList = new ArrayList();
            exclusionCardList = new ArrayList();
            initCardList();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.card_icon);
        builderSingle.setTitle("Customize Deck");
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_fragment, null);
        builderSingle.setView(rootView);
        ListView cardListView = (ListView) rootView.findViewById(R.id.cardListView);
        final DeckCustomizeAdapter customizeDeck = new DeckCustomizeAdapter(cardList, getActivity());
        cardListView.setAdapter(customizeDeck);
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
                        exclusionCardList = new ArrayList();
                        initCardList();
                        dialog.dismiss();
                    }
                });

        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CardCustomize cardCustom = cardList.get(i);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cardSelect);
                cardCustom.setIsSelected(!checkBox.isChecked());
                checkBox.setChecked(!checkBox.isChecked());
                if (!cardCustom.isSelected() && !exclusionCardList.contains(cardCustom)) {
                    exclusionCardList.add(cardCustom);
                } else {
                    exclusionCardList.remove(cardCustom);
                }
                customizeDeck.notifyDataSetChanged();
            }
        });
        return builderSingle.create();
    }

    private void initCardList() {
        CardCustomize cards = new CardCustomize();
        cards.setCardImage(R.drawable.joker_zero);
        cards.setIsSelected(true);
        cards.setCardTitle("Joker");
        cardList.add(cards);
        String[] cardNames = getActivity().getResources().getStringArray(R.array.card_array);
        for (int j = 0; j < cardNames.length; j++) {
            cards = new CardCustomize();
            int resID = getResources()
                    .getIdentifier("hearts_" + cardNames[j], "drawable", getActivity().getPackageName());
            cards.setCardImage(resID);
            cards.setIsSelected(true);
            cards.setCardTitle(cardNames[j]);
            cardList.add(cards);
        }
    }
}
