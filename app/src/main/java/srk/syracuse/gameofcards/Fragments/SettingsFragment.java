package srk.syracuse.gameofcards.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.rengwuxian.materialedittext.MaterialEditText;

import srk.syracuse.gameofcards.Adapters.DesignAdapter;
import srk.syracuse.gameofcards.R;

public class SettingsFragment extends Fragment {

    protected RecyclerView mCardRecyclerView;
    protected RecyclerView mTableRecyclerView;
    protected DesignAdapter mAdapter;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;

    protected String[] mDataset;

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
        final MaterialEditText dealNumber = (MaterialEditText) rootView.findViewById(R.id.dealNumber);
        dealEven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dealNumber.setVisibility(View.GONE);
                } else {
                    dealNumber.setVisibility(View.VISIBLE);
                }
            }
        });
        dealExact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dealNumber.setVisibility(View.VISIBLE);
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
                CardExceptionFragment cardException = new CardExceptionFragment();
                cardException.setTargetFragment(SettingsFragment.this, 2);
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
        mAdapter = new DesignAdapter(mDataset);
        mCardRecyclerView.setAdapter(mAdapter);
        mTableRecyclerView.setAdapter(mAdapter);
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
        mDataset = new String[60];
        for (int i = 0; i < 60; i++) {
            mDataset[i] = "This is element #" + i;
        }
    }
}
