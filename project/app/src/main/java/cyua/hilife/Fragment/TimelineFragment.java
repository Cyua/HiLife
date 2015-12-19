package cyua.hilife.Fragment;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cyua.hilife.Adapter.TimeLineAdapter;
import cyua.hilife.Aty.DiaryActivity;
import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.CustomerView.TimeLineModel;
import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.Database.DbQueryHelper;
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


        dbOpenHelper = new DbOpenHelper(this.getContext());

        DbQueryHelper dbQueryHelper = new DbQueryHelper(this.getContext());
        AvatarImageView avatarImageView = (AvatarImageView)view.findViewById(R.id.title_avatar);
        avatarImageView.setImageDrawable(dbQueryHelper.getAvatar(dbQueryHelper.getUserName()));
        dbQueryHelper.closeDb();

        initData();
        initView();
        return view;
    }

    private void initView(){
        listView = (ListView)view.findViewById(R.id.listview);
        adapter = new TimeLineAdapter(this.getContext(), list);

        listView.setAdapter(adapter);

        // on click: Go to diary activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TimeLineModel tlm = (TimeLineModel) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(TimelineFragment.this.getContext(), DiaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(DiaryActivity.ARG_KEY, tlm);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initData(){
        list = new ArrayList<TimeLineModel>();

        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM diary", new String[]{});

        while (cursor.moveToNext()) {
            TimeLineModel tlm = new TimeLineModel(R.drawable.medicalcheck2,
                    cursor.getString(cursor.getColumnIndex("datetime")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("audio")));
            list.add(tlm);
        }

        db.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }
}
