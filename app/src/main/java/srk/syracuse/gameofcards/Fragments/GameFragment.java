package srk.syracuse.gameofcards.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import srk.syracuse.gameofcards.Adapters.CardHandAdapter;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.Player;
import srk.syracuse.gameofcards.R;


public class GameFragment extends Fragment {

    public static View rootView;
    public static Game gameObject;
    public static Context context;
    public RecyclerView mCardHand;
    //public static RecyclerView.LayoutManager mCardHandLayoutManager;
    public CardHandAdapter mCardHandAdapter;
    public static Player thisPlayer = null;

    protected RecyclerView mCardRecyclerView;
    protected RecyclerView mTableRecyclerView;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;

    public GameFragment(Game gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_game_layout, container, false);
        context = getActivity();
        updatePlayers();
        updateHand();
        mCardHand = (RecyclerView) rootView.findViewById(R.id.cardHand);
        //mCardHand.setLayoutManager(new LinearLayoutManager(mCardHand.getContext()));

        //final RecyclerView.LayoutManager mCardHandLayoutManager = new LinearLayoutManager(context);
        //mCardHandLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardHand.setLayoutManager(mCardLayoutManager);
        mCardHandAdapter = new CardHandAdapter(context,thisPlayer.hand.gameHand, gameObject.cardBackImage);
        mCardHand.setAdapter(mCardHandAdapter);

//        final LinearLayoutManager mCardHandLayoutManager = new LinearLayoutManager(context);
//        mCardHandLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mCardHandAdapter = new CardHandAdapter(thisPlayer.hand.gameHand, gameObject.cardBackImage);
//        mCardHand.setAdapter(mCardHandAdapter);
//        mCardHand.setLayoutManager(mCardHandLayoutManager);
//        mCardHand.setHasFixedSize(true);
        return rootView;
    }

    public static void updatePlayers() {
        List<Player> playerList = gameObject.players;
        ImageView playerImage;
        TextView playerText;
        String currentUser = MainFragment.userName.getText().toString();
        for (int i = 1; i <= playerList.size(); i++) {
            if (!playerList.get(i - 1).username.equals(currentUser)) {
                playerImage = (ImageView) rootView.findViewById(
                        GameFragment.context.getResources().getIdentifier("player" + i + "Image", "id",
                                GameFragment.context.getPackageName()));
                playerText = (TextView) rootView.findViewById(
                        GameFragment.context.getResources().getIdentifier("player" + i + "Text", "id",
                                GameFragment.context.getPackageName()));
                playerText.setVisibility(View.VISIBLE);
                playerImage.setVisibility(View.VISIBLE);
                playerText.setText(playerList.get(i - 1).username);
            } else {
                playerText = (TextView) rootView.findViewById(R.id.currentPlayerText);
                playerText.setText(playerList.get(i - 1).username);
            }
        }
    }

    public static void updatePlayerStatus() {
        List<Player> playerList = gameObject.players;
        ImageView playerImage;
        for (int i = 1; i <= playerList.size(); i++) {
            playerImage = (ImageView) rootView.findViewById(
                    GameFragment.context.getResources().getIdentifier("R.id.player" + i + "Image", "drawable", GameFragment.context.getPackageName()));
            if (playerList.get(i - 1).isActive) {
                playerImage.setImageResource(R.drawable.active_icon);
            } else {
                playerImage.setImageResource(R.drawable.inactive_icon);
            }
        }
    }

    public static void updateHand() {
        List<Player> playerList = gameObject.players;

        String currentUser = MainFragment.userName.getText().toString();
        for (Player player : playerList) {
            if (player.username.equals(currentUser)) {
                thisPlayer = player;
            }
        }
//        if (mCardHandAdapter != null)
//            mCardHandAdapter.notifyDataSetChanged();
    }
}
