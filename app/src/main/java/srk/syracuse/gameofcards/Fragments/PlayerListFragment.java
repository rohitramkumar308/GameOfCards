package srk.syracuse.gameofcards.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import srk.syracuse.gameofcards.Adapters.PlayerAdapter;
import srk.syracuse.gameofcards.Connections.ServerConnectionThread;
import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.R;
import srk.syracuse.gameofcards.Utils.ServerHandler;


public class PlayerListFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    public static Game gameObject;
    public static ArrayList<String> deviceList = new ArrayList();

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mPlayerList;
    public static PlayerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.player_list_layout, container, false);
        rootView.setTag(TAG);
        Button gameSettings = (Button) rootView.findViewById(R.id.gameSettings);
        Button playGame = (Button) rootView.findViewById(R.id.playGame);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        gameSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SettingsFragment()).addToBackStack(SettingsFragment.class.getName())
                        .commit();
            }
        });
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ServerConnectionThread.allPlayersJoined) {
                    try {
                        initializeGame();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, new GameFragment(gameObject)).addToBackStack(GameFragment.class.getName())
                                .commit();
                    } catch (IllegalArgumentException exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Waiting for all players to Join the game", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPlayerList = (RecyclerView) rootView.findViewById(R.id.gameList);


        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new PlayerAdapter(deviceList);
        mPlayerList.setAdapter(mAdapter);
        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mPlayerList.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mPlayerList.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mPlayerList.setLayoutManager(mLayoutManager);
        mPlayerList.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void initializeGame() {
        ArrayList<Cards> restrictedCards = new ArrayList();
        deviceList.add(MainFragment.userName.getText().toString());
        boolean dealExact = false;
        int numberOfDecks = 1;
        int numberOfCardsDraw = 5;

        if (SettingsFragment.dealExact != null && SettingsFragment.dealExact.isChecked()) {
            dealExact = true;
            numberOfCardsDraw = Integer.valueOf(SettingsFragment.dealExactCards.getSelectedItem().toString());
        }
        if (SettingsFragment.deckNumber != null && SettingsFragment.deckNumber.getText().length() > 0) {
            numberOfDecks = Integer.valueOf(SettingsFragment.deckNumber.getText().toString());
            if (!(numberOfDecks >= 1 && numberOfDecks <= 6)) {
                numberOfDecks = 1;
                Toast.makeText(getActivity(), "Only a total of 6 decks is allowed", Toast.LENGTH_SHORT).show();
            }
        }
        if (DeckCustomizeDialog.exclusionCardList != null) {
            Cards card = new Cards();
            for (int i = 0; i < DeckCustomizeDialog.exclusionCardList.size(); i++) {
                restrictedCards.addAll(card.getCopyForAll(DeckCustomizeDialog.exclusionCardList.get(i).getCardTitle()));
            }
        }
        gameObject = new Game(deviceList, numberOfDecks, numberOfCardsDraw, dealExact, restrictedCards, HostFragment.gameName.getText().toString());

        if (SettingsFragment.selectedTableImage == -1) {
            gameObject.gameBackground = R.drawable.table_back1;
        } else {
            gameObject.gameBackground = SettingsFragment.selectedTableImage;
        }
        if (SettingsFragment.selectedCardImage == -1) {
            gameObject.cardBackImage = R.drawable.cardback1;
        } else {
            gameObject.cardBackImage = SettingsFragment.selectedCardImage;
        }
        ServerHandler.sendToAll(gameObject);

    }


}
