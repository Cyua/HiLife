package cyua.hilife.Aty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cyua.hilife.Fragment.CalendarFragment;
import cyua.hilife.Fragment.RecordFragment;
import cyua.hilife.Fragment.TimelineFragment;
import cyua.hilife.R;


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
    }

    void initData(){
        timeline_rbtn = (RadioButton) findViewById(R.id.timeline_rbtn);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        timeline_rbtn.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);

        fragmentTransaction.add(R.id.fragment, new TimelineFragment());
        fragmentTransaction.commit();
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
}
