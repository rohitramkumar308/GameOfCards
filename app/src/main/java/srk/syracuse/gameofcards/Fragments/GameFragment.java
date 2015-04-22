package srk.syracuse.gameofcards.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import srk.syracuse.gameofcards.Adapters.CardHandAdapter;
import srk.syracuse.gameofcards.Adapters.TableViewAdapter;
import srk.syracuse.gameofcards.Connections.ClientConnectionThread;
import srk.syracuse.gameofcards.Connections.ClientSenderThread;
import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.Player;
import srk.syracuse.gameofcards.R;
import srk.syracuse.gameofcards.Utils.ClientHandler;
import srk.syracuse.gameofcards.Utils.Constants;
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
    public Button foldButton;
    public Button hideButton;
    public static Player thisPlayer = null;
    public AlertDialog dialog;
    public static Socket socket;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;
    public static ImageView currentUserImage;
    public static TextView currentUserText;

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
        currentUserImage = (ImageView) rootView.findViewById(R.id.currentPlayerImage);
        currentUserText = (TextView) rootView.findViewById(R.id.currentPlayerText);
        RelativeLayout relative = (RelativeLayout) rootView.findViewById(R.id.mainGameLayout);
        relative.setBackgroundResource(gameObject.gameBackground);
        context = getActivity();
        updatePlayers();
        updateHand();
        mCardHand = (RecyclerView) rootView.findViewById(R.id.cardHand);
        mTableView = (RecyclerView) rootView.findViewById(R.id.tableView);
        foldButton = (Button) rootView.findViewById(R.id.foldCardsButton);
        hideButton = (Button) rootView.findViewById(R.id.hideCardsButton);
        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTableLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardHand.setLayoutManager(mCardLayoutManager);
        mCardHandAdapter = new CardHandAdapter(context, thisPlayer.hand.gameHand, gameObject.cardBackImage);
        mCardHand.setAdapter(mCardHandAdapter);

        mCardHandAdapter.setOnItemClickListener(new CardHandAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                View rootLayout = (View) v.getParent();
                View cardFace = rootLayout.findViewById(R.id.cardDesign);
                View cardBack = rootLayout.findViewById(R.id.cardDesignBack);
                updateHand();
                cardBack.setVisibility(View.INVISIBLE);
                setCardFaceUp(position, true);
            }
        });

        mCardHandAdapter.setOnItemLongClickListener(new CardHandAdapter.OnItemLongClickListener() {
            @Override
            public boolean OnItemLongClick(View v, int position) {
                gameObject.mTable.TableCards.add(thisPlayer.hand.gameHand.get(position));
                thisPlayer.hand.gameHand.remove(position);
                mTableViewAdapter.notifyItemInserted(gameObject.mTable.TableCards.size() - 1);
                mCardHandAdapter.notifyItemRemoved(position);
                updateGameForAll(gameObject);
                return true;
            }
        });
        mTableViewAdapter = new TableViewAdapter(context, gameObject.mTable.TableCards);
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
                gameObject.setActionKey(Constants.GAME_PLAY);
                updateGameForAll(gameObject);
            }
        });

        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = confirmMove(Constants.MOVE_FOLD).create();
                dialog.show();
            }
        });

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thisPlayer.hand.handFaceUp)
                    for(int i=0; i<thisPlayer.hand.gameHand.size(); i++) {
                        setCardFaceUp(i, false);
                        mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
                    }
                else
                    for(int i=0; i<thisPlayer.hand.gameHand.size(); i++) {
                        setCardFaceUp(i, true);
                        mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
                    }
                mCardHandAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    public void setCardFaceUp(int position, boolean isFaceUp) {
        this.thisPlayer.hand.getCard(position).cardFaceUp = isFaceUp;
        this.thisPlayer.hand.isHandFaceUp();
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
                currentUserText.setText(playerList.get(i - 1).username);
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
                    GameFragment.context.getResources().getIdentifier("player" + i + "Image", "id", GameFragment.context.getPackageName()));
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
        if (ClientConnectionThread.serverStarted) {
            ClientHandler.sendToServer(gameObject);
        } else {
            ServerHandler.sendToAll(gameObject);
        }
    }

    private AlertDialog.Builder confirmMove(final int message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        String alertMessage = "Are you sure ?";
        switch (message) {
            case Constants.MOVE_FOLD:
                alertMessage = "Do you want to FOLD ?";
                break;
        }
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder
                .setMessage(alertMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        actionTaken(message);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return alertDialogBuilder;
    }

    private void actionTaken(int keyword) {
        switch (keyword) {
            case Constants.MOVE_FOLD:
                thisPlayer.isActive = false;
                for (int i = 0; i < gameObject.players.size(); i++) {
                    if (thisPlayer.username.equals(gameObject.players.get(i).username)) {
                        gameObject.players.set(i, thisPlayer);
                        break;
                    }
                }
                currentUserImage.setImageResource(R.drawable.inactive_icon);
                gameObject.setActionKey(Constants.MOVE_FOLD);
                updateGameForAll(gameObject);
                break;
        }
    }
}


