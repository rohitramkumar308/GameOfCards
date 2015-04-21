package srk.syracuse.gameofcards.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import srk.syracuse.gameofcards.Adapters.DesignAdapter;
import srk.syracuse.gameofcards.R;

public class SettingsFragment extends Fragment {

    protected RecyclerView mCardRecyclerView;
    protected RecyclerView mTableRecyclerView;
    protected DesignAdapter mCardAdapter;
    protected DesignAdapter mTableAdapter;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;

    private int[] mCardDataSet;
    private int[] mTableset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_setting_layout, container, false);
        Button apply = (Button) rootView.findViewById(R.id.applySettings);
        Button manageDeck = (Button) rootView.findViewById(R.id.manageDeck);
        Button cancel = (Button) rootView.findViewById(R.id.cancelChanges);
        RadioButton dealEven = (RadioButton) rootView.findViewById(R.id.radioEven);
        RadioButton dealExact = (RadioButton) rootView.findViewById(R.id.radioExact);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.deal_exactly, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        dealEven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    spinner.setVisibility(View.GONE);
                } else {
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });
        dealExact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                spinner.setVisibility(View.VISIBLE);
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        manageDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeckCustomizeDialog cardException = new DeckCustomizeDialog();
                cardException.setTargetFragment(SettingsFragment.this, 2);
                cardException.setCancelable(false);
                cardException.show(getFragmentManager(), "Remove Cards");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mCardRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardDesignList);
        mTableRecyclerView = (RecyclerView) rootView.findViewById(R.id.tableDesignList);
        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTableLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        mTableRecyclerView.setLayoutManager(mTableLayoutManager);
        mCardAdapter = new DesignAdapter(mCardDataSet, true);
        mTableAdapter = new DesignAdapter(mCardDataSet, false);
        mCardRecyclerView.setAdapter(mCardAdapter);
        mTableRecyclerView.setAdapter(mTableAdapter);
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        mCardDataSet = new int[]{R.drawable.cardback1, R.drawable.cardback2, R.drawable.cardback3, R.drawable.cardback4};
        mTableset = new int[]{R.drawable.ericcartman};
    }
}
