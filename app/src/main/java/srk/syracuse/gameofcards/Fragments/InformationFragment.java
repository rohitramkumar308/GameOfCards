package srk.syracuse.gameofcards.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import srk.syracuse.gameofcards.R;


public class InformationFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.information_dialog, null);
        TextView infoContent = (TextView) rootView.findViewById(R.id.infoContent);
        infoContent.setLinksClickable(true);
        infoContent.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        return rootView;

    }
}
