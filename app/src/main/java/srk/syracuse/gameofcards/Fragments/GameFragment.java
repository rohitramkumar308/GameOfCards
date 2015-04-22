package srk.syracuse.gameofcards.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import srk.syracuse.gameofcards.Adapters.CardHandAdapter;
import srk.syracuse.gameofcards.Adapters.TableViewAdapter;
import srk.syracuse.gameofcards.Connections.ClientConnectionThread;
import srk.syracuse.gameofcards.Connections.ClientSenderThread;
import srk.syracuse.gameofcards.Connections.ServerConnectionThread;
import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.Player;
import srk.syracuse.gameofcards.R;
import srk.syracuse.gameofcards.Utils.Flipping;
import srk.syracuse.gameofcards.Utils.ServerHandler;


public class GameFragment extends Fragment {

    public static View rootView;
    public static Game gameObject;
    public static Context context;
    public RecyclerView mCardHand;
    public RecyclerView mTableView;
    public static CardHandAdapter mCardHandAdapter;
    public static TableViewAdapter mTableViewAdapter;

    public static Player thisPlayer = null;

    public static Socket socket;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;

    public GameFragment(Game gameObject, Socket socket) {
        this.gameObject = gameObject;
        this.socket = socket;
    }

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
        if (SettingsFragment.selectedTableImage != -1) {
            rootView.setBackgroundResource(SettingsFragment.selectedTableImage);
        }
        context = getActivity();
        updatePlayers();
        updateHand();
        mCardHand = (RecyclerView) rootView.findViewById(R.id.cardHand);
        mTableView = (RecyclerView) rootView.findViewById(R.id.tableView);

        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTableLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardHand.setLayoutManager(mCardLayoutManager);
        mCardHandAdapter = new CardHandAdapter(context, thisPlayer.hand.gameHand, gameObject.cardBackImage);
        mCardHand.setAdapter(mCardHandAdapter);
        mCardHandAdapter.setOnItemCLickListener(new CardHandAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                View rootLayout = (View) v.getParent();
                View cardFace = rootLayout.findViewById(R.id.cardDesignBack);
                View cardBack = rootLayout.findViewById(R.id.cardDesign);

                Flipping flipAnimation = new Flipping(cardFace, cardBack, cardFace.getWidth() / 2, cardFace.getHeight() / 2);

                updateHand();
                if (cardFace.getVisibility() == View.GONE) {
                    flipAnimation.reverse();
                    setCardFaceUp(position, true);
                } else
                    setCardFaceUp(position, false);
                rootLayout.startAnimation(flipAnimation);
            }

            @Override
            public void OnItemLongClick(View v, int position) {
                gameObject.mTable.TableCards.add(thisPlayer.hand.gameHand.get(position));
                thisPlayer.hand.gameHand.remove(position);
                mTableViewAdapter.notifyItemInserted(gameObject.mTable.TableCards.size() - 1);
                mCardHandAdapter.notifyItemRemoved(position);
                updateGameForAll(gameObject);
            }
        });
        mTableViewAdapter = new TableViewAdapter(context, gameObject.mTable.TableCards, gameObject.cardBackImage);
        mTableView.setLayoutManager(mTableLayoutManager);
        mTableView.setAdapter(mTableViewAdapter);

        mTableViewAdapter.setOnItemCLickListener(new TableViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {

            }

            @Override
            public void OnItemLongClick(View v, int position) {
                thisPlayer.hand.gameHand.add(gameObject.mTable.TableCards.get(position));
                gameObject.mTable.TableCards.remove(position);
                mCardHandAdapter.notifyItemInserted(gameObject.mTable.TableCards.size() - 1);
                mTableViewAdapter.notifyItemRemoved(position);
                updateGameForAll(gameObject);
            }
        });

        return rootView;
    }

    public void setCardFaceUp(int position, boolean isFaceUp) {
        this.thisPlayer.hand.getCard(position).cardFaceUp = isFaceUp;
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

    public static void updateTable() {
        ArrayList<Cards> tableList = gameObject.mTable.TableCards;
        mTableViewAdapter.setCards(tableList);
        mTableViewAdapter.notifyDataSetChanged();
    }

    public static void updatePlayerStatus() {
        List<Player> playerList = gameObject.players;
        ImageView playerImage;
        for (int i = 1; i <= playerList.size(); i++) {
            playerImage = (ImageView) rootView.findViewById(
                    GameFragment.context.getResources().getIdentifier("player" + i + "Image", "drawable", GameFragment.context.getPackageName()));
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
        if (mCardHandAdapter != null) {
            mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
            mCardHandAdapter.notifyDataSetChanged();
        }
    }

    public void updateGameForAll(Game gameObject) {
        if (ClientConnectionThread.socket != null) {
            ClientSenderThread sendGameChange = new ClientSenderThread(ClientConnectionThread.socket, gameObject);
            sendGameChange.start();
        } else {
            ServerHandler.sendToAll(gameObject);
        }
    }
}


