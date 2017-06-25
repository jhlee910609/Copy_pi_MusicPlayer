package com.junhee.android.mymymp3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junhee.android.mymymp3.Fragment.ArtistFragment.OnArtistListener;
import com.junhee.android.mymymp3.R;
import com.junhee.android.mymymp3.domain.Artist;
import com.junhee.android.mymymp3.domain.Music;
import com.junhee.android.mymymp3.domain.SongsTempStorage;
import com.junhee.android.mymymp3.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnArtistListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private final List<Artist.Item> artistsItem;
    private Artist artist;
    private final OnArtistListener onArtistListener;
    private Context context;
    private SongsTempStorage sts;
    private Music music;

    public ArtistAdapter(List<Artist.Item> artists, OnArtistListener listener) {
        this.artistsItem = artists;
        onArtistListener = listener;
        artist = Artist.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.position = position;
        holder.artistName.setText(artistsItem.get(position).artistName);
        holder.numberOfAlbums.setText(" 총 앨범 수 : " + artistsItem.get(position).numberOfAlbums);

        Glide
                .with(context)
                .load(artistsItem.get(position).albumArtUri)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.artistImg);
    }

    @Override
    public int getItemCount() {
        return (null != artistsItem ? artistsItem.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public final View mView;
        public final TextView artistName;
        public final TextView numberOfAlbums;
        public final ImageView artistImg;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            artistName = (TextView) view.findViewById(R.id.artist_name);
            numberOfAlbums = (TextView) view.findViewById(R.id.artist_numberOfAlbums);
            artistImg = (ImageView) view.findViewById(R.id.artist_img);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getArtistSongs(position);
                    onArtistListener.goArtistSonglist();
                }
            });

        }
    }

    public void getArtistSongs(int position) {
        artist.loader(context);
        music = Music.getInstance();
        music.loader(context);

        List<Music.Item> tempItemps = new ArrayList<>();
        sts = SongsTempStorage.getInstance();

        Log.d("ArtistAdapter", "========== artist.size() == " + artist.getItems().size());

        for (Music.Item tempItem : music.getItems()) {
            Log.d("ArtistAdapter", "=============== artists.get(position).artistName === " + artistsItem.get(position).artistName);
            Log.d("ArtistAdapter", "============= tempItem.aritst === " + tempItem.artist);
            if (tempItem.artist.equals(artistsItem.get(position).artistName.toString())) {
                tempItemps.add(tempItem);

            }
        }
        sts.setItems(tempItemps);
    }
}
