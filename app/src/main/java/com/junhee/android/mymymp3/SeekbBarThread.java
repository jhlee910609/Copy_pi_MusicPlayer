package com.junhee.android.mymymp3;

import android.os.Handler;
import android.os.Message;

import com.junhee.android.mymymp3.Fragment.DetailFragment;

/**
 * Created by JunHee on 2017. 6. 20..
 */

public class SeekbBarThread extends Thread {

    Handler handler;
    boolean runFlag = true;

    public SeekbBarThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while (runFlag) {

            int current = Player.getCurrent();

            Message msg = new Message();
            msg.what = DetailFragment.CHANGE_SEEKBAR;
            msg.arg1 = current;
            handler.sendMessage(msg);

            if (current >= Player.getDuration()) {
                runFlag = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void setRunFlag(boolean flag) {
        runFlag = flag;
    }
}
