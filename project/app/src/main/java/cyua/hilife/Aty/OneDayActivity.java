package cyua.hilife.Aty;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cyua.hilife.Adapter.TimeLineAdapter;
import cyua.hilife.CustomerView.TimeLineModel;
import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.R;

public class OneDayActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private ListView listView;
    private List<TimeLineModel> list;
    private TimeLineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year",0);
        int month = intent.getIntExtra("month",0);
        int day = intent.getIntExtra("day",0);

        //Toast.makeText(OneDayActivity.this, ""+month, Toast.LENGTH_LONG).show();

        dbOpenHelper = new DbOpenHelper(this);

        // Init list
        list = new ArrayList<TimeLineModel>();

        db = dbOpenHelper.getReadableDatabase();

        String dateLower = String.format("%04d-%02d-%02d 00:00:00", year, month, day);
        String dateUpper = String.format("%04d-%02d-%02d 23:59:59", year, month, day);
        System.out.println(dateLower + dateUpper);
        Cursor cursor = db.rawQuery("SELECT * FROM diary WHERE datetime > ? AND datetime < ?",
                new String[]{dateLower, dateUpper});

        while (cursor.moveToNext()) {
            TimeLineModel tlm = new TimeLineModel(R.drawable.medicalcheck2,
                    cursor.getString(cursor.getColumnIndex("datetime")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("audio")));
            list.add(tlm);
        }

        db.close();

        // Init view
        listView = (ListView)findViewById(R.id.listview);
        adapter = new TimeLineAdapter(this, list);

        listView.setAdapter(adapter);

        // on click: Go to diary activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TimeLineModel tlm = (TimeLineModel) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(OneDayActivity.this, DiaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(DiaryActivity.ARG_KEY, tlm);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
