package com.junhee.android.mymymp3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

public class PlayerService extends Service {
    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction(); // 인텐트를 통해 전달받은 명령어 꺼냄
            switch (action) {
                case Const.Action.PLAY:
                    playerStart();
                    break;

                case Const.Action.PAUSE:
                    Player.pause();
                    break;

                case Const.Action.STOP:
                    stopService();
                    break;
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void playerStart(){
        NotificationCompat.Action pauseAction = makeAction(android.R.drawable.ic_media_pause, "Pause" , Const.Action.PAUSE);
        buildNotification(pauseAction, Const.Action.PLAY);
        Player.play();

    }

    private static int NOTIFICATION_ID = 124;

    private void stopService(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);

        Intent stopIntent = new Intent(getApplicationContext(), PlayerService.class);
        stopService(stopIntent);
    }

    private void buildNotification(NotificationCompat.Action action, String action_flag) {
        // 1.1 노티바를 만든다
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("음악제목")
                .setContentText("가수");

        // 1.2. 서비스 종료 인텐트 생성
        Intent delIntent = new Intent(getApplicationContext(), PlayerService.class);
        delIntent.setAction(Const.Action.STOP);
        PendingIntent pendingDelIntent = PendingIntent.getService(getApplicationContext(), 1, delIntent, 0);

        // 1.3. 서비스 종료 인텐트 등록
        builder.setContentIntent(pendingDelIntent);


        // 가.3 노티바에서 사용할 action(버튼) 등록
        NotificationCompat.Action prevAction = makeAction(android.R.drawable.ic_media_previous, "Prev", Const.Action.PREV);
        builder.addAction(prevAction);

        NotificationCompat.Action nextAction = makeAction(android.R.drawable.ic_media_next, "Next", Const.Action.NEXT);
        builder.addAction(nextAction);

        // 나. 노티바 등록
        // 나.1 매니저 가져오기
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 나.2 매니저를 통해 등록
        manager.notify(NOTIFICATION_ID, builder.build());

    }

    private android.support.v7.app.NotificationCompat.Action makeAction(int btnIcon, String btnTitle, String action) {
        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        intent.setAction(action);

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new android.support.v7.app.NotificationCompat.Action.Builder(btnIcon, btnTitle, pendingIntent).build();
    }

}

