package srk.syracuse.gameofcards.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import srk.syracuse.gameofcards.R;

public class JoinGameFragment extends Fragment {

    public static TextView gameName;
    public static TextView userName;

    public JoinGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.join_select_layout, container, false);
        gameName = (TextView) rootView.findViewById(R.id.gameName);
        userName = (TextView) rootView.findViewById(R.id.userName);
        userName.setText(MainFragment.userName.getText());
        return rootView;
    }


}
