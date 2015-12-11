package cyua.hilife.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Cyua on 12/11/15.
 */
public class DbQueryHelper {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public DbQueryHelper(Context context){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
    }
    public String getUserName(){
        Cursor cursor = db.rawQuery("SELECT * FROM account", new String[]{});
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex("username"));
        }
        return "";
    }

    public void closeDb(){
        db.close();
    }
}
