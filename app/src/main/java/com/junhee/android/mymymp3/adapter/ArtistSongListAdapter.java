package com.junhee.android.mymymp3.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junhee.android.mymymp3.Fragment.ArtistFragment;
import com.junhee.android.mymymp3.Player;
import com.junhee.android.mymymp3.R;
import com.junhee.android.mymymp3.domain.Artist;
import com.junhee.android.mymymp3.domain.Music;

import java.util.List;

public class ArtistSongListAdapter extends RecyclerView.Adapter<ArtistSongListAdapter.ViewHolder> {

    private final List<Music.Item> datas;
    private Context context;
    private ArtistFragment.OnArtistListener onArtistListener;

    // TODO 데이터를 받아와서 셋팅하는 변수와 로직을 만든다. 어차피 어댑터가 할 일이기 때문에
    public ArtistSongListAdapter(List<Music.Item> items, ArtistFragment.OnArtistListener listener) {
        this.datas = items;
        this.onArtistListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (context != null) {
            context = parent.getContext();
//        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.albumsong_item, parent, false);
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
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.albumArt);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public final View mView;
        public final TextView title, artist;
        public final ImageView albumArt;
        public Uri musicUri;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.albumSong_title);
            artist = (TextView) view.findViewById(R.id.albumSong_artist);
            albumArt = (ImageView) view.findViewById(R.id.albumSong_img);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onArtistListener.setMainPlayer(datas.get(position));
                    Player.init(musicUri, mView.getContext(), null);
                    Player.play();
                }
            });
        }
    }
}
