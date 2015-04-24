package srk.syracuse.gameofcards.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import srk.syracuse.gameofcards.Adapters.CardHandAdapter;
import srk.syracuse.gameofcards.Adapters.TableViewAdapter;
import srk.syracuse.gameofcards.Connections.ClientConnectionThread;
import srk.syracuse.gameofcards.Connections.ServerConnectionThread;
import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.Player;
import srk.syracuse.gameofcards.R;
import srk.syracuse.gameofcards.Utils.ClientHandler;
import srk.syracuse.gameofcards.Utils.Constants;
import srk.syracuse.gameofcards.Utils.ServerHandler;


public class GameFragment extends Fragment {

    public static View rootView;
    public static Game gameObject;
    public static Context context;
    public RecyclerView mCardHand;
    public RecyclerView mTableView;
    public static CardHandAdapter mCardHandAdapter;
    public static TableViewAdapter mTableViewAdapter;
    public CircleButton foldButton;

    public CircleButton playButton;
    public TextView myImageViewText;
    public Button newGameButton;
    public Button dealCardButton;
    public CircleButton hideButton;

    public static Player thisPlayer = null;
    public AlertDialog dialog;
    public static Socket socket;
    protected RecyclerView.LayoutManager mCardLayoutManager;
    protected RecyclerView.LayoutManager mTableLayoutManager;
    public static ImageView currentUserImage;
    public static TextView currentUserText;
    public static boolean tableChanged = false;
    public static ArrayList<Cards> tempHandCards = new ArrayList<Cards>();

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
        updatePlayerStatus();
        updateHand();
        mCardHand = (RecyclerView) rootView.findViewById(R.id.cardHand);
        mTableView = (RecyclerView) rootView.findViewById(R.id.tableView);
        foldButton = (CircleButton) rootView.findViewById(R.id.foldCardsButton);
        newGameButton = (Button) rootView.findViewById(R.id.newGameButton);
        dealCardButton = (Button) rootView.findViewById(R.id.dealCardsButton);
        if (ServerConnectionThread.socketUserMap != null && ServerConnectionThread.socketUserMap.size() > 0) {
            LinearLayout linearLayout3 = (LinearLayout) rootView.findViewById(R.id.linearLayout3);
            linearLayout3.setVisibility(View.VISIBLE);
        } else {
            RelativeLayout.LayoutParams layoutParams;
            layoutParams = (RelativeLayout.LayoutParams) mCardHand
                    .getLayoutParams();
            layoutParams.addRule(RelativeLayout.RIGHT_OF,
                    0);
            mCardHand.setLayoutParams(layoutParams);
        }
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = confirmMove(Constants.NEW_GAME).create();
                dialog.show();
            }
        });
        dealCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameObject.deckCards.size() == 0) {
                    Toast.makeText(getActivity(), "Not enough cards to DEAL!", Toast.LENGTH_SHORT).show();
                } else {
                    CardDealDialog dealCards = new CardDealDialog();
                    dealCards.setTargetFragment(GameFragment.this, 2);
                    dealCards.show(getFragmentManager(), "Deal Cards");
                }
            }
        });
        playButton = (CircleButton) rootView.findViewById(R.id.playButton);
        myImageViewText = (TextView) rootView.findViewById(R.id.myImageViewText);
        hideButton = (CircleButton) rootView.findViewById(R.id.hideCardsButton);

        mCardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTableLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardHand.setLayoutManager(mCardLayoutManager);
        mCardHandAdapter = new CardHandAdapter(context, thisPlayer.hand.gameHand, gameObject.cardBackImage);
        mCardHand.setAdapter(mCardHandAdapter);

        mCardHandAdapter.setOnItemClickListener(new CardHandAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                View rootLayout = (View) v.getParent();
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
                tempHandCards.add(thisPlayer.hand.gameHand.get(position));
                if (tempHandCards.size() > 0) {
                    playButton.setVisibility(View.VISIBLE);
                    myImageViewText.setVisibility(View.VISIBLE);
                }
                thisPlayer.hand.gameHand.remove(position);
                mTableViewAdapter.notifyItemInserted(gameObject.mTable.TableCards.size() - 1);
                mCardHandAdapter.notifyItemRemoved(position);
                return true;

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
                tableChanged = true;
                thisPlayer.hand.gameHand.add(gameObject.mTable.TableCards.get(position));
                if (tempHandCards.contains(gameObject.mTable.TableCards.get(position))) {
                    tempHandCards.remove(gameObject.mTable.TableCards.get(position));
                }
                if (tempHandCards.size() == 0 && !tableChanged) {
                    playButton.setVisibility(View.INVISIBLE);
                    myImageViewText.setVisibility(View.INVISIBLE);
                } else {
                    playButton.setVisibility(View.VISIBLE);
                    myImageViewText.setVisibility(View.VISIBLE);
                }

                gameObject.mTable.TableCards.remove(position);
                mCardHandAdapter.notifyItemInserted(thisPlayer.hand.gameHand.size() - 1);
                mTableViewAdapter.notifyItemRemoved(position);
                gameObject.setActionKey(Constants.GAME_PLAY);
            }
        });

        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = confirmMove(Constants.MOVE_FOLD).create();
                dialog.show();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempHandCards.size() > 0 || tableChanged) {
                    tableChanged = false;
                    playButton.setVisibility(View.INVISIBLE);
                    myImageViewText.setVisibility(View.INVISIBLE);
                    updateGameForAll(gameObject, Constants.GAME_PLAY);
                }
            }
        });

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisPlayer.hand.handFaceUp)
                    for (int i = 0; i < thisPlayer.hand.gameHand.size(); i++) {
                        setCardFaceUp(i, false);
                        mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
                    }
                else
                    for (int i = 0; i < thisPlayer.hand.gameHand.size(); i++) {
                        setCardFaceUp(i, true);
                        mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
                    }
                mCardHandAdapter.notifyDataSetChanged();

            }
        });

        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisPlayer.hand.handFaceUp)
                    for (int i = 0; i < thisPlayer.hand.gameHand.size(); i++) {
                        setCardFaceUp(i, false);
                        mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
                        thisPlayer.hand.handFaceUp = false;
                    }
                else
                    for (int i = 0; i < thisPlayer.hand.gameHand.size(); i++) {
                        setCardFaceUp(i, true);
                        mCardHandAdapter.setCards(thisPlayer.hand.gameHand);
                        thisPlayer.hand.handFaceUp = true;
                    }
                mCardHandAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    public void setCardFaceUp(int position, boolean isFaceUp) {
        this.thisPlayer.hand.getCard(position).cardFaceUp = isFaceUp;
        this.thisPlayer.hand.isHandFaceUp();
        TextView myImageViewText1 = (TextView) rootView.findViewById(R.id.myImageViewText1);
        if (thisPlayer.hand.handFaceUp)
            myImageViewText1.setText("Hide Cards");
        else
            myImageViewText1.setText("Show Cards");
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
            if (playerList.get(i - 1).username.equals(MainFragment.userName.getText().toString())) {
                playerImage = (ImageView) rootView.findViewById(R.id.currentPlayerImage);
                if (playerList.get(i - 1).isActive) {
                    playerImage.setImageResource(R.drawable.active_icon);
                } else {
                    playerImage.setImageResource(R.drawable.inactive_icon);
                }
            } else if (playerList.get(i - 1).isActive) {
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

    public void updateGameForAll(Game gameObject, int message) {
        if (ClientConnectionThread.serverStarted) {
            onFold(message);
            ClientHandler.sendToServer(gameObject);
        } else {
            if (Constants.isPlayerActive(MainFragment.userName.getText().toString(), gameObject)) {
                onFold(message);
                ServerHandler.sendToAll(gameObject);
            }
        }
    }

    private void onFold(int actionMessage) {
        if (Constants.MOVE_FOLD == actionMessage) {
            thisPlayer.isActive = false;
            for (int i = 0; i < gameObject.players.size(); i++) {
                if (thisPlayer.username.equals(gameObject.players.get(i).username)) {
                    gameObject.players.set(i, thisPlayer);
                    break;
                }
            }
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
            case Constants.NEW_GAME:
                alertMessage = "Do you want to start a New Game ?";
                break;
        }
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder
                .setMessage(alertMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        actionTaken(message);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder;
    }

    private void actionTaken(int keyword) {
        switch (keyword) {
            case Constants.MOVE_FOLD:
                currentUserImage.setImageResource(R.drawable.inactive_icon);
                gameObject.setActionKey(Constants.MOVE_FOLD);
                updateGameForAll(gameObject, Constants.MOVE_FOLD);
                break;
            case Constants.NEW_GAME:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}


