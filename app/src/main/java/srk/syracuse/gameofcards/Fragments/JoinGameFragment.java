package srk.syracuse.gameofcards.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import srk.syracuse.gameofcards.Connections.ClientConnectionThread;
import srk.syracuse.gameofcards.Connections.ClientListenerThread;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.R;

public class JoinGameFragment extends Fragment {

    public static TextView gameName;
    public static TextView userName;
    public static Game gameobject;

    public JoinGameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.join_select_layout, container, false);
        Button joinGame = (Button) rootView.findViewById(R.id.joinGame);
        gameName = (TextView) rootView.findViewById(R.id.gameName);
        userName = (TextView) rootView.findViewById(R.id.userName);
        userName.setText(MainFragment.userName.getText());
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameobject != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new GameFragment(gameobject, ClientConnectionThread.socket)).addToBackStack(PlayerListFragment.class.getName())
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Game setup not complete. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }


}
