package srk.syracuse.gameofcards.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.Model.Player;
import srk.syracuse.gameofcards.R;


public class GameFragment extends Fragment {

    public static View rootView;
    public static Game gameObject;
    public static Context context;

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


}
