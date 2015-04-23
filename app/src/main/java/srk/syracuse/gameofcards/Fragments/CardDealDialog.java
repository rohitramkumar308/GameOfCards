package srk.syracuse.gameofcards.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import srk.syracuse.gameofcards.R;
import srk.syracuse.gameofcards.Utils.ServerHandler;


public class CardDealDialog extends DialogFragment {

    public static RadioButton toPlayer;
    public static RadioButton ontable;
    public static MaterialEditText numberCards;

    public CardDealDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.deck_icon);
        builderSingle.setTitle("Deal Deck");
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.card_deal_layout, null);
        toPlayer = (RadioButton) rootView.findViewById(R.id.toPlayersRadio);
        ontable = (RadioButton) rootView.findViewById(R.id.onTableRadio);
        numberCards = (MaterialEditText) rootView.findViewById(R.id.dealCards);

        builderSingle.setView(rootView);

        builderSingle.setPositiveButton("DEAL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean checked = ((RadioButton) toPlayer).isChecked();
                        if (numberCards.getText() != null && numberCards.getText().toString().trim().length() > 0) {
                            int numCards = Integer.parseInt(numberCards.getText().toString());
                            if (checked) {
                                if (GameFragment.gameObject.deckCards.size() >= (numCards * GameFragment.gameObject.getNumberOfPlayer())) {
                                    GameFragment.gameObject.getHand(numCards);
                                    GameFragment.mCardHandAdapter.notifyDataSetChanged();
                                    ServerHandler.sendToAll(GameFragment.gameObject);
                                } else {
                                    Toast.makeText(getActivity(), "Not enough cards to DEAL!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (GameFragment.gameObject.deckCards.size() >= numCards) {
                                    GameFragment.gameObject.mTable.putCardsOnTable(GameFragment.gameObject.getCardsFromDeck(numCards));
                                    GameFragment.mTableViewAdapter.notifyDataSetChanged();
                                    ServerHandler.sendToAll(GameFragment.gameObject);
                                } else {
                                    Toast.makeText(getActivity(), "Not enough cards to DEAL!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        dialog.dismiss();
                    }
                }

        );
        builderSingle.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener()

                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );

        return builderSingle.create();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.toPlayersRadio:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.onTableRadio:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

}
