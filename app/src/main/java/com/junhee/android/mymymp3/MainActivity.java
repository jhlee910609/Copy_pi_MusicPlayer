package com.junhee.android.mymymp3;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.junhee.android.mymymp3.Fragment.AlbumFragment;
import com.junhee.android.mymymp3.Fragment.AlbumSongListFragment;
import com.junhee.android.mymymp3.Fragment.ArtistFragment;
import com.junhee.android.mymymp3.Fragment.ArtistSongFragment;
import com.junhee.android.mymymp3.Fragment.DetailFragment;
import com.junhee.android.mymymp3.Fragment.TrackFragment;
import com.junhee.android.mymymp3.adapter.ViewPagerAdapter;
import com.junhee.android.mymymp3.domain.Music;
import com.junhee.android.mymymp3.util.PermissionControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PermissionControl.CallBack, TrackFragment.OnTrackListener, AlbumFragment.Listener, DetailFragment.PlayerInterface, View.OnClickListener, ArtistFragment.OnArtistListener, AlbumSongListFragment.OnAlbumSongListener {

    ViewPagerAdapter adapter;
    Fragment trackFragment, albumFragment, artistFragment, albumSongListFragment, playlistFragment;
    List<Fragment> fragDatas;
    DetailFragment detailFragment;
    Intent service;

    @BindView(R.id.player_albumArt)
    ImageView player_albumArt;

    @BindView(R.id.player_title)
    TextView player_title;

    @BindView(R.id.player_artist)
    TextView player_artist;

    @BindView(R.id.player_btnPlay)
    ImageButton player_btnPlay;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.list_viewPager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        service = new Intent(this, PlayerService.class);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        trackFragment = TrackFragment.newInstance(1);
        albumFragment = AlbumFragment.newInstance(3);
        artistFragment = ArtistFragment.newInstance(1);
        detailFragment = DetailFragment.newInstance();
        albumSongListFragment = AlbumSongListFragment.newInstance(1);
        //
        playlistFragment = new PlayListFragment();

        PermissionControl.checkVersion(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onResult(this, requestCode, grantResults);

    }

    @Override
    public void init() {
        setTabs();
        setPagerFragments();
        setViewPager();
        setListener();

    }

    private void setTabs() {
        tabs.addTab(tabs.newTab().setText("트랙"));
        tabs.addTab(tabs.newTab().setText("앨범"));
        tabs.addTab(tabs.newTab().setText("아티스트"));
        tabs.addTab(tabs.newTab().setText("재생목록"));
    }

    private void setPagerFragments() {
        fragDatas = new ArrayList<>();
        Log.d("add fragment", "=========== add frag");
        fragDatas.add(trackFragment);
        fragDatas.add(albumFragment);
        fragDatas.add(artistFragment);
        //
        fragDatas.add(playlistFragment);
    }

    private void setViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragDatas);
        pager.setAdapter(adapter);
    }

    private void setListener() {
        player_btnPlay.setOnClickListener(this);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_layout, fragment);
        transaction.commit();
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_layout, fragment);
        // 백스택에 쌓는지 여부
        transaction.addToBackStack(null);
        transaction.commit();
    }

//
//    @Override
//    protected void onDestroy() {
//        if(detailFragment != null) {
//            Log.d("MAIN ONESTORY", "=============== onDestory");
//            detailFragment.setDestroy();
//        }
//        super.onDestroy();
//    }

    // album_artist > detailfragment
    @Override
    public void goDetailInteraction(int position) {
        Log.d("MainActivity", "=============== goDetailInteraction");
        detailFragment.setPosition(position);
        addFragment(detailFragment);
    }

    @Override
    public void setMainPlayer(Music.Item item) {
        Log.d("MainActivity", "============== setMainPlayer");
        player_title.setText(item.title);
        player_artist.setText(item.artist);

        Glide
                .with(this)
                .load(item.albumArtUri)
                .into(player_albumArt);
    }

    @Override
    public void playerPlayer() {
        // 1. 서비스를 생성한다.
        // 서비스에 명령어를 담아 넘긴다.
        service.setAction(Const.Action.PLAY);
        startService(service);
    }

    @Override
    public void stopPlayer() {
        service.setAction(Const.Action.STOP);
        startService(service);
    }

    @Override
    public void pausePlayer() {
        service.setAction(Const.Action.PAUSE);
        startService(service);
    }

    @Override
    public void initPlayer() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.player_btnPlay) {

        }
    }

    @Override
    public void deliverAlbumSongList() {
        albumSongListFragment = new AlbumSongListFragment();
        addFragment(albumSongListFragment);
    }

    @Override
    public void goArtistSonglist() {
        artistFragment = new ArtistSongFragment();
        addFragment(artistFragment);

    }
}


