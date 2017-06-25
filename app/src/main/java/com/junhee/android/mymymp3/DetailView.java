package com.junhee.android.mymymp3;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.junhee.android.mymymp3.Fragment.DetailFragment;
import com.junhee.android.mymymp3.adapter.DetailAdapter;
import com.junhee.android.mymymp3.domain.Music;
import com.junhee.android.mymymp3.util.TimeUtil;

import java.util.List;

import static com.junhee.android.mymymp3.Fragment.DetailFragment.CHANGE_SEEKBAR;
import static com.junhee.android.mymymp3.Fragment.DetailFragment.STOP_THREAD;
import static com.junhee.android.mymymp3.util.TimeUtil.miliToMinSec;

/**
 * Created by JunHee on 2017. 6. 20..
 */

public class DetailView implements View.OnClickListener {


    Context context;
    View view;
    ViewPager viewPager;
    ImageButton btnPlay, btnNext, btnPrev;
    SeekBar seekBar;
    TextView current, duration;

    DetailFragment.PlayerInterface playerInterface;
    SeekbBarThread seekBarThread = null;
    DetailFragment presenter;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case CHANGE_SEEKBAR:
                    setSeekBarPosition(msg.arg1);
                    break;

                case STOP_THREAD:
                    seekBarThread.setRunFlag(false);
                    break;
            }
        }
    };

    public View getView(){
        return view;
    }

    public DetailView(View view, DetailFragment presenter, DetailFragment.PlayerInterface playerInterface) {
        this.playerInterface = playerInterface;
        this.context = view.getContext();
        this.view = view;
        this.presenter = presenter;

        viewPager = (ViewPager) view.findViewById(R.id.detail_viewPager);
        current = (TextView) view.findViewById(R.id.current);
        duration = (TextView) view.findViewById(R.id.duration);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        btnPrev = (ImageButton) view.findViewById(R.id.btnPrev);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        current = (TextView) view.findViewById(R.id.current);
        duration = (TextView) view.findViewById(R.id.duration);
    }

    public void init(int position) {
        setOnClickListener();
        setViewPager(position);
        setSeekBar();
        setSeekBarThraad();

    }

    private void setOnClickListener() {
        btnPlay.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private List<Music.Item> getDatas() {
        Music music = Music.getInstance();
        music.loader(context);
        return music.getItems();
    }

    private void setViewPager(int position) {
        DetailAdapter adapter = new DetailAdapter(getDatas());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerListner);
        viewPager.setCurrentItem(position);
    }

    private void setSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Player.setCurrent(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSeekBarThraad() {
        seekBarThread = new SeekbBarThread(handler);
        seekBarThread.start();
    }

    public void setDuration(int time) {
        String formatted = miliToMinSec(time);
        duration.setText(formatted);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                play();
                break;

            case R.id.btnNext:
                next();
                break;

            case R.id.btnPrev:
                prev();
                break;
        }

    }

    public void play() {

        switch (Player.status) {
            case Const.Player.PLAY:
                playerInterface.pausePlayer();
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                break;

            case Const.Player.PAUSE:
                playerInterface.playerPlayer();
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                break;

            case Const.Player.STOP:
                playerInterface.playerPlayer();
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                break;
        }
    }

    public void next() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void prev() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void setSeekBarPosition(int time) {
        seekBar.setProgress(time);
        current.setText(TimeUtil.miliToMinSec(time));
    }

    // 최초 호출될 경우 페이지의 이동이 없으므로 호출되지 않음
    ViewPager.OnPageChangeListener viewPagerListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        // 페이지의 변경사항을 체크해서 현재 페이지 값을 알려줌
        @Override
        public void onPageSelected(int position) {
            musicInit(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void musicInit(int position) {
        Uri musicUri = getDatas().get(position).musicUri;
        Player.init(musicUri, context, handler);

        int musicDuration = Player.getDuration();
        setDuration(musicDuration);
        seekBar.setMax(Player.getDuration());

        if (Player.status == Const.Player.PLAY) {
            playerInterface.playerPlayer();
        }
    }

    public void setDestroy() {
        seekBarThread.setRunFlag(false);
        seekBarThread.interrupt();
    }

}
