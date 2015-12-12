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
    private String motto;
    public DbQueryHelper(Context context){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        passwd = "";
        motto = "";
    }
    public String getUserName(){
        Cursor cursor = db.rawQuery("SELECT * FROM account", new String[]{});
        if (cursor.moveToNext()){
            passwd = cursor.getString(1);
            motto = cursor.getString(2);
            return cursor.getString(0);
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

    public String getMotto(){
        return motto;
    }

    public void closeDb(){
        db.close();
    }
}
