package cyua.hilife.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cyua.hilife.Aty.EditAccountActivity;
import cyua.hilife.Aty.MainActivity;
import cyua.hilife.Aty.OneDayActivity;
import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.Database.DbQueryHelper;
import cyua.hilife.R;
/**
 * Created by Cyua on 15/12/7.
 */
public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_calendar_fragment,null);
        TextView title = (TextView) view.findViewById(R.id.title_tv);
        Button button = (Button) view.findViewById(R.id.header_imgbtn);
        title.setText("日历");
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setText("进入");
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = datePicker.getYear();
                int m = datePicker.getMonth();
                int d = datePicker.getDayOfMonth();
                Intent intent = new Intent(view.getContext(), OneDayActivity.class);
                intent.putExtra("year",y);
                intent.putExtra("month",m);
                intent.putExtra("day",d);
                startActivity(intent);
            }
        });

        DbQueryHelper dbQueryHelper = new DbQueryHelper(this.getContext());
        AvatarImageView avatarImageView = (AvatarImageView)view.findViewById(R.id.title_avatar);
        avatarImageView.setImageDrawable(dbQueryHelper.getAvatar(dbQueryHelper.getUserName()));
        dbQueryHelper.closeDb();

        datePicker.init(2015, 11, 20, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(isDateAfter(view)){
                    view.init(2015,11,20,this);
                }
                if(isDateBefore(view)){
                    view.init(2015,1,1,this);
                }
            }

            private boolean isDateAfter(DatePicker tempView){
                Calendar c = Calendar.getInstance();
                if(tempView.getYear()>c.get(Calendar.YEAR)){
                    return true;
                }
                else
                    return false;
            }

            private boolean isDateBefore(DatePicker tempView){
                Calendar c = Calendar.getInstance();
                if(tempView.getYear()<c.get(Calendar.YEAR)){
                    return true;
                }
                else
                    return false;
            }
        });


        return view;
    }
}
