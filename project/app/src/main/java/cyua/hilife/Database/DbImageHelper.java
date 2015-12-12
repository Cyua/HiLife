package cyua.hilife.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.io.ByteArrayOutputStream;

import cyua.hilife.R;

/**
 * Created by Cyua on 12/12/15.
 */
public class DbImageHelper {
    private static DbImageHelper dbImageHelper;
    public static DbImageHelper getInstance(){
        if(dbImageHelper == null){
            dbImageHelper = new DbImageHelper();
        }
        return dbImageHelper;
    }

    private void initDataBase (SQLiteDatabase db,Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context,id);
        ContentValues cv = new ContentValues();
        cv.put("avatar", getPicture(drawable));
        db.insert("account", null, cv);
    }

    private byte[] getPicture(Drawable drawable) {
        if(drawable == null) {
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }
}
