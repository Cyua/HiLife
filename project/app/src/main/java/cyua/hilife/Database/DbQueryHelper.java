package cyua.hilife.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Cyua on 12/11/15.
 */
public class DbQueryHelper {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private String passwd;
    private String motto;
    private boolean m,a,e;
    public DbQueryHelper(Context context){
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        passwd = "";
        motto = "";
        m = false;
        a = false;
        e = false;
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

    public Drawable getAvatar(String username){
        Cursor c = db.query("account",null,null,null,null,null,null);
        Drawable drawable = null;
        while(c.moveToNext()){
            if(c.getString(0).equals(username)){
                byte[] b = c.getBlob(c.getColumnIndex("avatar"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                drawable = bitmapDrawable;
            }
        }
        return drawable;
    }

    public void updateSetting(){
        Cursor cursor = db.rawQuery("SELECT * FROM setting", new String[]{});
        if (cursor.moveToNext()){
            if(cursor.getString(0).equals("true"))
                m = true;
            else
                m = false;
            if(cursor.getString(1).equals("true"))
                a = true;
            else
                a = false;

            if(cursor.getString(2).equals("true"))
                e = true;
            else
                e = false;
        }
    }

    public boolean isMorningSetted(){
        return m;
    }

    public boolean isAfternoonSetted(){
        return a;
    }

    public boolean isEveningSetted(){
        return e;
    }

    public void closeDb(){
        db.close();
    }
}
