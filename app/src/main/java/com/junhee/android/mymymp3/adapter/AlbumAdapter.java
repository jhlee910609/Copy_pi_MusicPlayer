package com.junhee.android.mymymp3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.junhee.android.mymymp3.Fragment.AlbumFragment.Listener;
import com.junhee.android.mymymp3.R;
import com.junhee.android.mymymp3.domain.Album;
import com.junhee.android.mymymp3.domain.SongsTempStorage;
import com.junhee.android.mymymp3.domain.Music;
import com.junhee.android.mymymp3.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link Listener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album.Item> albums;
    private Context context;
    private Listener mAlbumsListener;
    private List<Music.Item> allSongs;
    private SongsTempStorage ats;
    private Music music;

    public AlbumAdapter(List<Album.Item> albums, Listener listener) {
        this.albums = albums;
        this.mAlbumsListener = listener;
        ats = SongsTempStorage.getInstance();
        music = Music.getInstance();
        allSongs = music.getItems();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.position = position;
        holder.album_title.setText(albums.get(position).albumName);
        holder.album_artist.setText(albums.get(position).artist);
        holder.album_numberOfSongs.setText(albums.get(position).numberOfSongs);

        Glide
                .with(context)
                .load(albums.get(position).albumArtUri)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.album_albumArt);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public int position;
        public final TextView album_title;
        public final TextView album_artist;
        public final ImageView album_albumArt;
        public final TextView album_numberOfSongs;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            album_title = (TextView) view.findViewById(R.id.album_name);
            album_artist = (TextView) view.findViewById(R.id.album_artist);
            album_albumArt = (ImageView) view.findViewById(R.id.album_albumArt);
            album_numberOfSongs = (TextView) view.findViewById(R.id.album_numberOfSongs);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Log.d("AlbumAdapter", " ==== ats.size()" + ats.getSize());
                    getAlbumSongs(ViewHolder.this);
                    mAlbumsListener.deliverAlbumSongList();

                    // 인터페이스 핸들링 하기가 어려워서 액티비티를 새로 띄움
//                    Intent intent = new Intent(context, AlbumSongActivity.class);
//                    intent.putExtra(Const.IntentList.ALBUM_SONGS, getAlbumSongs());
//                    context.startActivity(intent);

                    Toast.makeText(context, position + " 정상작동 중", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAlbumSongs(ViewHolder viewHolder) {
        allSongs = music.getItems();
        // tempAlbumSongs = new ArrayList<>();
        // ats.init();
        Log.d("AlbumAdapter", "========= ats is Existed? " + ats.toString());
        Log.d("AlbumAdapter", "========= allSongs.size() : " + allSongs.size());

        for (Music.Item tempItem : allSongs) {
            Log.d("AlbumAdapter", "=========== tempItem : " + tempItem.albumId.toString());
            String tempId = tempItem.albumId;
            String tempAlbumId = albums.get(viewHolder.position).albumId.toString();

            Log.d("AlbumAdapter", "============== albums.albumID " + tempAlbumId);

            // / TODO ============== 여기 아래로 못 넘어옵니다.. ======================== ㅠㅠㅠㅠㅠㅠㅠ
            if (tempId.equals(tempAlbumId)) {
                Log.d("AlbumAdapter", "save : " + tempItem.albumName.toString());
                ats.additem(tempItem);
            }
        }
        Log.d("AlbumAdapter", "size :" + ats.getSize());

    }

}
