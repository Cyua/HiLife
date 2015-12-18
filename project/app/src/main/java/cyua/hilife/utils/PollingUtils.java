package cyua.hilife.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import cyua.hilife.Aty.MainActivity;
import cyua.hilife.R;

/**
 * Created by Cyua on 12/18/15.
 */
public class PollingUtils extends BroadcastReceiver{
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "闹铃响了, 可以做点事情了~~", Toast.LENGTH_LONG).show();
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent playIntent  = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("title").setContentText(".....").setSmallIcon(R.mipmap.ic_hilife)
                .setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent)
                .setAutoCancel(true).setSubText("*****");
        manager.notify(1,builder.build());
    }
}
