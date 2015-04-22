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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import srk.syracuse.gameofcards.Adapters.DesignAdapter;
import srk.syracuse.gameofcards.R;

public class SettingsFragment extends Fragment {

    protected RecyclerView mCardRecyclerView;
    protected RecyclerView mTableRecyclerView;
    protected DesignAdapter mCardAdapter;
    protected DesignAdapter mTableAdapter;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;
    public static int selectedCardImage = -1;
    public static int selectedTableImage = -1;
    public RadioButton dealEven;
    public RadioButton dealExact;
    private int[] mCardDataSet;
    private int[] mTableDataSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_setting_layout, container, false);
        Button apply = (Button) rootView.findViewById(R.id.applySettings);
        Button manageDeck = (Button) rootView.findViewById(R.id.manageDeck);
        Button cancel = (Button) rootView.findViewById(R.id.cancelChanges);
        dealEven = (RadioButton) rootView.findViewById(R.id.radioEven);
        dealExact = (RadioButton) rootView.findViewById(R.id.radioExact);
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
        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        mCardAdapter = new DesignAdapter(mCardDataSet, true);
        mCardRecyclerView.setAdapter(mCardAdapter);

        mTableLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTableRecyclerView = (RecyclerView) rootView.findViewById(R.id.tableDesignList);
        mTableRecyclerView.setLayoutManager(mTableLayoutManager);
        mTableAdapter = new DesignAdapter(mTableDataSet, false);
        mTableRecyclerView.setAdapter(mTableAdapter);
        mCardAdapter.setOnItemClickListener(new DesignAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView image = (ImageView) view.findViewById(R.id.cardDesign);
                selectedCardImage = image.getId();
            }
        });

        mTableAdapter.setOnItemClickListener(new DesignAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView image = (ImageView) view.findViewById(R.id.cardDesign);
                selectedTableImage = image.getId();
                rootView.setBackgroundResource(selectedTableImage);
            }
        });
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    private void initDataset() {
        mCardDataSet = new int[]{R.drawable.cardback1, R.drawable.cardback2, R.drawable.cardback3, R.drawable.cardback4};
        mTableDataSet = new int[]{R.drawable.table_back1, R.drawable.table_back3};
        if (selectedTableImage == -1) {
            selectedTableImage = getActivity().getResources().getIdentifier("table_back1", "drawable", getActivity().getPackageName());
        }
        if (selectedCardImage == -1) {
            selectedCardImage = getActivity().getResources().getIdentifier("cardback1", "drawable", getActivity().getPackageName());
        }
    }
}
