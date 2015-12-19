package cyua.hilife.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.Database.DbQueryHelper;
import cyua.hilife.R;
/**
 * Created by Cyua on 15/12/7.
 */
public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_calendar_fragment,null);
        TextView title = (TextView) view.findViewById(R.id.title_tv);
        Button button = (Button) view.findViewById(R.id.header_imgbtn);
        title.setText("日历");
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setText("进入");

        DbQueryHelper dbQueryHelper = new DbQueryHelper(this.getContext());
        AvatarImageView avatarImageView = (AvatarImageView)view.findViewById(R.id.title_avatar);
        avatarImageView.setImageDrawable(dbQueryHelper.getAvatar(dbQueryHelper.getUserName()));
        dbQueryHelper.closeDb();


        CalendarView cv = (CalendarView)view.findViewById(R.id.calendarView);
        cv.setMaxDate(cv.getDate());
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
        return view;
    }
}
