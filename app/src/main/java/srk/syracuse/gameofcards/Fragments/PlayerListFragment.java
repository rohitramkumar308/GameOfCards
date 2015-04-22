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
                    initializeGame();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new GameFragment(gameObject)).addToBackStack(GameFragment.class.getName())
                            .commit();
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
        gameObject = new Game(deviceList, 1, 5, true, restrictedCards, HostFragment.gameName.getText().toString());

        if (SettingsFragment.dealExact != null && SettingsFragment.dealExact.isChecked()) {
            gameObject.setDrawEqual(false);
            gameObject.setNumberOfCardsDraw(Integer.valueOf(SettingsFragment.dealExactCards.getSelectedItem().toString()));
        }
        if (SettingsFragment.deckNumber != null && SettingsFragment.deckNumber.getText().length() > 0) {
            int numberOfDecks = Integer.valueOf(SettingsFragment.deckNumber.toString());
            if (numberOfDecks >= 1 && numberOfDecks <= 6) {
                gameObject.setNumberOfDeck(numberOfDecks);
            }
        }

        gameObject.gameBackground = SettingsFragment.selectedTableImage;
        gameObject.cardBackImage = SettingsFragment.selectedCardImage;


//        if (DeckCustomizeDialog.exclusionCardList != null) {
//            Cards card = new Cards();
//            for (int i = 0; i < DeckCustomizeDialog.exclusionCardList.size(); i +=) {
//                card.imageID
//            }
//        }
        ServerHandler.sendToAll(gameObject);
    }


}
