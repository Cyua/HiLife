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
    private String passwd;
    public DbQueryHelper(Context context){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        passwd = "";
    }
    public String getUserName(){
        Cursor cursor = db.rawQuery("SELECT * FROM account", new String[]{});
        if (cursor.moveToNext()){
            passwd = cursor.getString(cursor.getColumnIndex("passwd"));
            return cursor.getString(cursor.getColumnIndex("username"));
        }
        return "";
    }

    public String getPasswd(String usrname){
        Cursor cursor = db.rawQuery("SELECT * FROM account", new String[]{});
        while (cursor.moveToNext()) {
            if(cursor.getString(cursor.getColumnIndex("username")).equals(usrname)) {
                passwd = cursor.getString(cursor.getColumnIndex("passwd"));
                break;
            }
        }
        return passwd;
    }


    public void closeDb(){
        db.close();
    }
}
