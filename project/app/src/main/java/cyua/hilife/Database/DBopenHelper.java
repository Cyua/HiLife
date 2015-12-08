package cyua.hilife.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cyua on 15/12/8.
 */
public class DbOpenHelper extends SQLiteOpenHelper{
    public DbOpenHelper(Context context){
        super(context, "hilife.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS dialog ()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
