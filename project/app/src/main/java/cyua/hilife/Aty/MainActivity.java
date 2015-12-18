package cyua.hilife.Aty;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.Database.DbQueryHelper;
import cyua.hilife.Fragment.CalendarFragment;
import cyua.hilife.Fragment.RecordFragment;
import cyua.hilife.Fragment.TimelineFragment;
import cyua.hilife.R;
import cyua.hilife.utils.PollingService;
import cyua.hilife.utils.PollingUtils;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private View mainView, slideView;
    private RadioButton timeline_rbtn;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        View editAccount = findViewById(R.id.editInfomation);
        editAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    void initData(){
        DbQueryHelper dbQueryHelper = new DbQueryHelper(this);
        TextView textView = (TextView)findViewById(R.id.username);
        textView.setText(dbQueryHelper.getUserName());

        TextView motto = (TextView)findViewById(R.id.motto);
        motto.setText(dbQueryHelper.getMotto());

        AvatarImageView avatarImageView = (AvatarImageView)findViewById(R.id.title_avatar);
        avatarImageView.setImageDrawable(dbQueryHelper.getAvatar(dbQueryHelper.getUserName()));

        timeline_rbtn = (RadioButton) findViewById(R.id.timeline_rbtn);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        timeline_rbtn.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);

        fragmentTransaction.add(R.id.fragment, new TimelineFragment());
        fragmentTransaction.commit();

        dbQueryHelper.closeDb();
    }

    void switchFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.timeline_rbtn:
                switchFragment(new TimelineFragment());
                setPolling(true);
                break;
            case R.id.calendar_rbtn:
                switchFragment(new CalendarFragment());
                setPolling(true);
                break;
            case R.id.record_rbtn:
                switchFragment(new RecordFragment());
                setPolling(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        PollingUtils.stopPollingService(this,PollingService.class,PollingService.ACTION);
    }

    public void setPolling(boolean b){
//        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(this, PollingUtils.class), 0);
////        if(b) {
//            Calendar c = Calendar.getInstance();
//            c.setTimeInMillis(System.currentTimeMillis());
//            c.add(Calendar.SECOND, 10);
//            am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
////        }
////        else{
////            am.cancel(pendingIntent);
////        }
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Context context = getApplicationContext();
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("title").setContentText(".....").setSmallIcon(R.mipmap.ic_hilife)
                .setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent)
                .setAutoCancel(true).setSubText("*****");
        manager.notify(1,builder.build());

    }
}
