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
import com.junhee.android.mymymp3.adapter.AlbumSongListAdapter;
import com.junhee.android.mymymp3.domain.SongsTempStorage;
import com.junhee.android.mymymp3.domain.Music;


public class AlbumSongListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private SongsTempStorage as;
    private OnAlbumSongListener onAlbumSongListener;

    public AlbumSongListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlbumSongListFragment newInstance(int columnCount) {
        AlbumSongListFragment fragment = new AlbumSongListFragment();
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
        View view = inflater.inflate(R.layout.albumsong_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // TODO 데이터를 받아와서 셋팅하는 변수와 로직을 만든다. 어차피 어댑터가 할 일이기 때문에

            as = SongsTempStorage.getInstance();
            Log.d("AlbumSongListFragment", "=========== as.size()" + as.getSize());
            recyclerView.setAdapter(new AlbumSongListAdapter(as.getItems(), onAlbumSongListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.d("TrackFragment", "=============== onAttach");
        super.onAttach(context);
        if (context instanceof OnAlbumSongListener) {
            onAlbumSongListener = (OnAlbumSongListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnArtistListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("AlbumSongListFragment", "==================== onDetach();");
        as.removeAllSongs();
        onAlbumSongListener = null;
    }

    public interface OnAlbumSongListener {

        void goDetailInteraction(int position);

        void setMainPlayer(Music.Item item);

    }
}
