package cyua.hilife.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import cyua.hilife.Aty.MainActivity;
import cyua.hilife.R;
/**
 * Created by Cyua on 12/18/15.
 */
public class PollingService extends Service {
    public static final String ACTION = "cyua.hilife.utils.PollingService";
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }

    //初始化通知栏配置
    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    //弹出Notification
    private void showNotification() {
        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("HiLife").setContentText("It's time to write the diary!")
        .setSmallIcon(R.mipmap.ic_hilife).setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent).setAutoCancel(true).setSubText("......");
        mManager.notify(1, builder.build());
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            count ++;
            //当计数能被5整除时弹出通知
            if (count % 5 == 0) {
                showNotification();
                System.out.println("New message!");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}
