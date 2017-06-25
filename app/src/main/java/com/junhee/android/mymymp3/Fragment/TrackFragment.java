package com.junhee.android.mymymp3.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junhee.android.mymymp3.R;
import com.junhee.android.mymymp3.adapter.TrackAdapter;
import com.junhee.android.mymymp3.domain.Music;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTrackListener}
 * interface.
 */
public class TrackFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnTrackListener trackListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrackFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TrackFragment newInstance(int columnCount) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TrackFragment", "=============== onCreat : " + mColumnCount);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            Music music = Music.getInstance();
            music.loader(getContext());
            Log.d("TrackFragment", "=============== trackListener : " + trackListener);

            recyclerView.setAdapter(new TrackAdapter(music.getItems(), trackListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        Log.d("TrackFragment", "=============== onAttach");
        super.onAttach(context);
        if (context instanceof OnTrackListener) {
            trackListener = (OnTrackListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnArtistListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        trackListener = null;
    }

    public interface OnTrackListener {

        void goDetailInteraction(int position);
        void setMainPlayer(Music.Item item);
    }
}

