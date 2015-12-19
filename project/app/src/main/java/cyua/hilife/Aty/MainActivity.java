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
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

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
        setPolling(true);
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
                break;
            case R.id.calendar_rbtn:
                switchFragment(new CalendarFragment());
                break;
            case R.id.record_rbtn:
                switchFragment(new RecordFragment());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        Intent intent = new Intent(MainActivity.this,PollingUtils.class);
        PendingIntent sender0 = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        PendingIntent sender1 = PendingIntent.getBroadcast(MainActivity.this,1,intent,0);
        PendingIntent sender2 = PendingIntent.getBroadcast(MainActivity.this,2,intent,0);
        PendingIntent sender3 = PendingIntent.getBroadcast(MainActivity.this,3,intent,0);


        Calendar morningCal = Calendar.getInstance();
        morningCal.setTimeInMillis(System.currentTimeMillis());
        morningCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        morningCal.set(Calendar.HOUR_OF_DAY, 8);
        morningCal.set(Calendar.MINUTE,0);
        morningCal.set(Calendar.SECOND,0);
        if(morningCal.getTimeInMillis() < System.currentTimeMillis()){
            morningCal.set(Calendar.DAY_OF_YEAR,morningCal.get(Calendar.DAY_OF_YEAR)+1);
        }

        Calendar afternoonCal = Calendar.getInstance();
        afternoonCal.setTimeInMillis(System.currentTimeMillis());
        afternoonCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        afternoonCal.set(Calendar.HOUR_OF_DAY, 12);
        afternoonCal.set(Calendar.MINUTE,0);
        afternoonCal.set(Calendar.SECOND,0);
        if(afternoonCal.getTimeInMillis() < System.currentTimeMillis()){
            afternoonCal.set(Calendar.DAY_OF_YEAR,afternoonCal.get(Calendar.DAY_OF_YEAR)+1);
        }

        Calendar eveningCal = Calendar.getInstance();
        eveningCal.setTimeInMillis(System.currentTimeMillis());
        eveningCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        eveningCal.set(Calendar.HOUR_OF_DAY, 18);
        eveningCal.set(Calendar.MINUTE,0);
        eveningCal.set(Calendar.SECOND,0);
        if(eveningCal.getTimeInMillis() < System.currentTimeMillis()){
            eveningCal.set(Calendar.DAY_OF_YEAR,eveningCal.get(Calendar.DAY_OF_YEAR)+1);
        }

        Calendar testCal = Calendar.getInstance();
        testCal.setTimeInMillis(System.currentTimeMillis());
        testCal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        testCal.set(Calendar.HOUR_OF_DAY, 15);
        testCal.set(Calendar.MINUTE,48);
        testCal.set(Calendar.SECOND,0);
        if(testCal.getTimeInMillis() < System.currentTimeMillis()){
            testCal.set(Calendar.DAY_OF_YEAR,testCal.get(Calendar.DAY_OF_YEAR)+1);
        }

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,testCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,sender0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,morningCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,sender1);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,eveningCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,sender2);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,afternoonCal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,sender3);
    }
}
