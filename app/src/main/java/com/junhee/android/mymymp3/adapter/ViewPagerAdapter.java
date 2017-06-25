package com.junhee.android.mymymp3.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.junhee.android.mymymp3.Fragment.AlbumFragment;
import com.junhee.android.mymymp3.Fragment.ArtistFragment;
import com.junhee.android.mymymp3.Fragment.TrackFragment;

import java.util.List;

/**
 * Created by JunHee on 2017. 6. 20..
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> datas;


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrackFragment();

            case 1:
                return new AlbumFragment();

            case 2:
                return new ArtistFragment();

            case 3:
                return new PlayListFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
