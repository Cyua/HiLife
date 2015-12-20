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
        db.execSQL("CREATE TABLE IF NOT EXISTS diary (datetime TEXT, location TEXT, title TEXT, content TEXT, audio TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS account (username TEXT, passwd TEXT, motto TEXT, avatar BLOB)");
        db.execSQL("CREATE TABLE IF NOT EXISTS setting (morning TEXT, afternoon TEXT, evening TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
