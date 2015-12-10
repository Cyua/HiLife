package cyua.hilife.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cyua.hilife.Adapter.TimeLineAdapter;
import cyua.hilife.CustomerView.TimeLineModel;
import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.R;
/**
 * Created by Cyua on 15/12/9.
 */
public class TimelineFragment extends Fragment{
    private ListView listView;
    private List<TimeLineModel> list;
    private TimeLineAdapter adapter;
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timeline_fragment,null);
        TextView title = (TextView) view.findViewById(R.id.title_tv);
        Button button = (Button) view.findViewById(R.id.header_imgbtn);
        title.setText("时光轴");
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setText("添加");

        dbOpenHelper = new DbOpenHelper(this.getContext());

        initData();
        initView();
        return view;
    }

    private void initView(){
        listView = (ListView)view.findViewById(R.id.listview);
        adapter = new TimeLineAdapter(this.getContext(), list);

        listView.setAdapter(adapter);
    }

    private void initData(){
        list = new ArrayList<TimeLineModel>();

        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM diary", new String[]{});

        while (cursor.moveToNext()) {
            TimeLineModel tlm = new TimeLineModel(R.drawable.medicalcheck2,
                    cursor.getString(cursor.getColumnIndex("datetime")),
                    cursor.getString(cursor.getColumnIndex("content")));
            list.add(tlm);
        }

        db.close();
        /*
        list.add(new TimeLineModel(R.drawable.medicalcheck2, "10月10日","hahahah"));

        list.add(new TimeLineModel(R.drawable.nurse_visit2, "11月11日","233333"));

        list.add(new TimeLineModel(R.drawable.nursingcareplan2, "12月12日","Today is Dec 9th. And I feel really bad.."));

        list.add(new TimeLineModel(R.drawable.medicalcheck2, "1月1日","Forget the message above! 2333333"));

        list.add(new TimeLineModel(R.drawable.nurse_visit2, "2月2日","Hellooooooooooo! Yohoooooooo!"));

        list.add(new TimeLineModel(R.drawable.nursingcareplan2, "3月3日","I'm gonna be crazy..."));
*/

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }
}
