package srk.syracuse.gameofcards.Fragments;/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import srk.syracuse.gameofcards.Adapters.PlayerAdapter;
import srk.syracuse.gameofcards.Connections.ServerConnectionThread;
import srk.syracuse.gameofcards.Connections.ServerSenderThread;
import srk.syracuse.gameofcards.Model.Cards;
import srk.syracuse.gameofcards.Model.Game;
import srk.syracuse.gameofcards.R;


public class PlayerListFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mPlayerList;
    public static PlayerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<String> deviceList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.player_list_layout, container, false);
        rootView.setTag(TAG);
        Button gameSettings = (Button) rootView.findViewById(R.id.gameSettings);
        Button playGame = (Button) rootView.findViewById(R.id.playGame);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        gameSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SettingsFragment()).addToBackStack(SettingsFragment.class.getName())
                        .commit();
            }
        });
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, new MainFragment()).addToBackStack(HostFragment.class.getName())
//                        .commit();
                initializeGame();
            }
        });
        mPlayerList = (RecyclerView) rootView.findViewById(R.id.gameList);


        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new PlayerAdapter(deviceList);
        mPlayerList.setAdapter(mAdapter);
        return rootView;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mPlayerList.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mPlayerList.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mPlayerList.setLayoutManager(mLayoutManager);
        mPlayerList.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void initializeGame() {
        Iterator<Socket> socketIterator = ServerConnectionThread.socketSet.iterator();
        while (socketIterator.hasNext()) {
            Socket soc = socketIterator.next();
            ArrayList<Cards> restrictedCards = new ArrayList<Cards>();
            restrictedCards.add(new Cards("diamonds_10"));
            restrictedCards.add(new Cards("spades_10"));
            ArrayList<String> username = new ArrayList<String>();
            username.add("Rohit");
            username.add("Shivank");
            username.add("Kunal");
            Game game = new Game(username, 3, 5, false, restrictedCards, "Game of Cards");
            ServerSenderThread sendServerGame = new ServerSenderThread(soc, game);
            sendServerGame.start();
        }
    }
}
