package com.junhee.android.mymymp3.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junhee.android.mymymp3.R;
import com.junhee.android.mymymp3.domain.Music;

import java.util.List;

/**
 * Created by JunHee on 2017. 6. 20..
 */

public class DetailAdapter extends PagerAdapter {

    List<Music.Item> datas = null;

    public DetailAdapter(List<Music.Item> datas) {
        this.datas = datas;
    }

    // RecyclerView의 onBindViewHolder 같은 역할
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_detail_item, null);
        ImageView albumArt = (ImageView) view.findViewById(R.id.detail_albumArt);
        TextView title = (TextView) view.findViewById(R.id.detail_title);
        TextView artist = (TextView) view.findViewById(R.id.detail_artist);

        Glide
                .with(container.getContext())
                .load(datas.get(position).albumArtUri)
                .into(albumArt);

        title.setText(datas.get(position).title);
        artist.setText(datas.get(position).artist);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
