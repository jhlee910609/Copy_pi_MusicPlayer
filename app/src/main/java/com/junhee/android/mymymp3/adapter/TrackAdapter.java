package com.junhee.android.mymymp3.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junhee.android.mymymp3.Fragment.TrackFragment;
import com.junhee.android.mymymp3.Fragment.TrackFragment.OnTrackListener;
import com.junhee.android.mymymp3.Player;
import com.junhee.android.mymymp3.R;
import com.junhee.android.mymymp3.domain.Music;
import com.junhee.android.mymymp3.dummy.DummyContent.DummyItem;

import java.util.List;


/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link TrackFragment.OnTrackListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private List<Music.Item> datas;
    private Context context = null;
    private final TrackFragment.OnTrackListener mTrackListener;

    public TrackAdapter(List<Music.Item> datas, OnTrackListener listener) {
        mTrackListener = listener;
        this.datas = datas;
    }

    public void setDatas(List<Music.Item> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.position = position;
        holder.title.setText(datas.get(position).title);
        holder.artist.setText(datas.get(position).artist);
        holder.musicUri = datas.get(position).musicUri;

        Glide
                .with(context)
                .load(datas.get(position).albumArtUri)
                .into(holder.albumArt);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void goDetail(int position) {

        mTrackListener.goDetailInteraction(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public final View mView;
        public final TextView title, artist;
        public final ImageView albumArt;
        public Uri musicUri;
        public Spinner spinner;
        private List<String> list;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.track_title);
            artist = (TextView) view.findViewById(R.id.track_artist);
            albumArt = (ImageView) view.findViewById(R.id.track_img);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTrackListener.setMainPlayer(datas.get(position));
                    Player.init(musicUri, mView.getContext(), null);
                    Player.play();

                }
            });

            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    goDetail(position);
                    return true;
                }
            });
            addItemsOnSpinner();
        }

        public void addItemsOnSpinner() {

            spinner = (Spinner) mView.findViewById(R.id.track_spinner);

        }
    }
}

