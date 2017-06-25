package com.junhee.android.mymymp3.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junhee.android.mymymp3.DetailView;
import com.junhee.android.mymymp3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public static final int CHANGE_SEEKBAR = 99;
    public static final int STOP_THREAD = 98;
    private int position = -1;

    public PlayerInterface playerInterface;
    private DetailView viewHolder = null;


    public DetailFragment() {

        Log.d("DetailFragment", "=============== constructor");


    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("DetailFragment", "=============== onAttach");


        if (context instanceof PlayerInterface) {
            playerInterface = (PlayerInterface) context;
        } else {
        }
    }

    @Override
    public void onDestroy() {
        Log.d("DetailFragment", "================ onDestroy");
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        if(viewHolder == null) {
           view =  inflater.inflate(R.layout.fragment_detail, container, false);
            Log.d("DetailFragment", "=============== onCreateView");

            viewHolder = new DetailView(view, this, playerInterface);
        } else {
            view = viewHolder.getView();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewHolder.init(position);
    }

    public void setDestroy() {
        Log.d("DetailFragment", "=============== setDestroy");
        viewHolder.setDestroy();
    }

    public interface PlayerInterface {

        void playerPlayer();

        void stopPlayer();

        void pausePlayer();

        void initPlayer();
    }
}
