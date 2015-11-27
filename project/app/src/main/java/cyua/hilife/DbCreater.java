package cyua.hilife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cyua on 15/11/27.
 */
public class DbCreater extends SQLiteOpenHelper {

    public DbCreater(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "hilifeDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS diaryLog(" +
                "date)";
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
