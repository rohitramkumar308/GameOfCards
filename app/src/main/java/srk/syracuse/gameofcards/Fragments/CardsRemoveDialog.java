package srk.syracuse.gameofcards.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Date;

import srk.syracuse.gameofcards.Activities.GameActivity;
import srk.syracuse.gameofcards.R;

/**
 * Created by rohitramkumar on 4/20/15.
 */
public class CardsRemoveDialog extends DialogFragment {


    public CardsRemoveDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        builderSingle.setIcon(R.drawable.eric_icon);
        builderSingle.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");
        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                getActivity());
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
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

}
