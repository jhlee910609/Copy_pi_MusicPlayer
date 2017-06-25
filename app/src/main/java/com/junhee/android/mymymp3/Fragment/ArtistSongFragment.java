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
import com.junhee.android.mymymp3.adapter.ArtistSongListAdapter;
import com.junhee.android.mymymp3.domain.Music;
import com.junhee.android.mymymp3.domain.SongsTempStorage;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnArtistListener}
 * interface.
 */
public class ArtistSongFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ArtistFragment.OnArtistListener onArtistListener;
    private SongsTempStorage sts;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArtistSongFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ArtistSongFragment newInstance(int columnCount) {
        ArtistSongFragment fragment = new ArtistSongFragment();
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

           // TODO 아티스트 노래 관련 데이터 처리
            sts = SongsTempStorage.getInstance();
            Log.d("ArtistSongFragment", "========== sts.size" + sts.getItems().size());
            recyclerView.setAdapter(new ArtistSongListAdapter(sts.getItems(), onArtistListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        Log.d("TrackFragment", "=============== onAttach");
        super.onAttach(context);
        if (context instanceof ArtistFragment.OnArtistListener) {
            onArtistListener = (ArtistFragment.OnArtistListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnArtistListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onArtistListener = null;
    }

    public interface OnArtistListener {

        void goDetailInteraction(int position);
        void setMainPlayer(Music.Item item);
    }
}

