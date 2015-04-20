package srk.syracuse.gameofcards.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import srk.syracuse.gameofcards.R;


public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_game_fragment, container, false);
        Button hostGame = (Button) rootView.findViewById(R.id.hostGame);
        Button joinGame = (Button) rootView.findViewById(R.id.joinGame);
        WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        for (Method method : wmMethods) {
            if (method.getName().equals("isWifiApEnabled")) {
                try {
                    boolean isWifiAPEnabled = (Boolean) method.invoke(wifi);
                    final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    if (isWifiAPEnabled) {
                        joinGame.setVisibility(View.GONE);
                        hostGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container, new HostFragment()).addToBackStack(HostFragment.class.getName())
                                        .commit();
                            }
                        });
                    } else {
                        hostGame.setVisibility(View.GONE);
                        joinGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container, new MainFragment()).addToBackStack(null)
                                        .commit();
                            }
                        });
                    }

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
