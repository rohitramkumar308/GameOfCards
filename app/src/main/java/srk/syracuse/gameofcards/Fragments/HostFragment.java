package srk.syracuse.gameofcards.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import srk.syracuse.gameofcards.R;

/**
 * Created by rohitramkumar on 4/19/15.
 */
public class HostFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.host_game_layout, container, false);
        Button startGame = (Button) rootView.findViewById(R.id.joinGame);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlayerListFragment()).addToBackStack(PlayerListFragment.class.getName())
                        .commit();
            }
        });
        return rootView;
    }
}

