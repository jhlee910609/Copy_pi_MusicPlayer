package com.junhee.android.mymymp3;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.junhee.android.mymymp3.Fragment.DetailFragment;

/**
 * Created by JunHee on 2017. 6. 20..
 */

public class Player {
    private static MediaPlayer player = null;
    public static int status = Const.Player.STOP;



    /**
     * ===[음원 세팅]===
     * @param musicUri
     * @param context
     * @param handler SeekBar 음원 조작하는 handler
     */

    public static void init(Uri musicUri, Context context, final Handler handler) {
        if(player != null) {
            player.release();
        }
        player = MediaPlayer.create(context, musicUri);
        player.setLooping(false); // 반복여부
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 음악 플레이가 종료되면 호출된다.
                // 이 때 seekBar thread를 멈춰야 한다.
                if(handler != null)
                    handler.sendEmptyMessage(DetailFragment.STOP_THREAD);
            }
        });
    }

    public static void play(){

        player.start();
        status = Const.Player.PLAY;
    }

    public static void pause(){
        player.pause();
        status = Const.Player.PAUSE;
    }

    public static void replay(){
        player.start();
        status = Const.Player.PLAY;
    }

    public static int getDuration(){
        if(player != null){
            return player.getDuration();
        }else{
            return 0;
        }
    }

    // 현재 실행 구간
    public static int getCurrent(){
        Log.d("Player","getCurrent======="+player);
        if(player != null){
            try {
                return player.getCurrentPosition();
            }catch(Exception e){
                Log.e("Player",e.toString());
            }
        }
        return 0;
    }
    // current 로 실행구간 이동시키기
    public static void setCurrent(int current){
        if(player != null)
            player.seekTo(current);
    }
}