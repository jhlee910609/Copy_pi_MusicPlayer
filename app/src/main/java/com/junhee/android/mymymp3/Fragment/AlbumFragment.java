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
import com.junhee.android.mymymp3.adapter.AlbumAdapter;
import com.junhee.android.mymymp3.domain.Album;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Listener}
 * interface.
 */
public class AlbumFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Listener onAlbumListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlbumFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlbumFragment newInstance(int columnCount) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            Album album = Album.getInstance();
            album.loader(context);

            if (mColumnCount <= 1) {
                Log.d("AlbumFragment", "============ mColumnCount" + mColumnCount);
                recyclerView.setAdapter(new AlbumAdapter(album.getItems(), onAlbumListener));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setAdapter(new AlbumAdapter(album.getItems(), onAlbumListener));
                Log.d("AlbumFragment", "============ mColumnCount" + mColumnCount);
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            onAlbumListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnArtistListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onAlbumListener = null;
    }

    public interface Listener {
        // TODO: Update argument type and album_title
        void deliverAlbumSongList();
    }
}
