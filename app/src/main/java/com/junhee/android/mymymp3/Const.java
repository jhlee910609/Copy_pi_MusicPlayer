package com.junhee.android.mymymp3;

/**
 * Created by JunHee on 2017. 6. 20..
 */

// 상수만 모아둔 클래스

public class Const {
    public class Action {

        public static final String PLAY = "com.junhee.android.mymymp3.Action.play";
        public static final String PAUSE = "com.junhee.android.mymymp3.Action.pause";
        public static final String STOP = "com.junhee.android.mymymp3.Action.stop";
        public static final String INIT = "com.junhee.android.mymymp3.Action.init";
        public static final String PREV = "com.junhee.android.mymymp3.Action.prev";
        public static final String NEXT = "com.junhee.android.mymymp3.Action.next";
    }

    public class Player {
        public static final int STOP = 0;
        public static final int PLAY = 1;
        public static final int PAUSE = 2;
    }

    public class IntentList {
        public static final String ALBUM_SONGS = "albumSongs";
    }
}
