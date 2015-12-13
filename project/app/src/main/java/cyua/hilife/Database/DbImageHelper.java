package cyua.hilife.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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

    public void modifyAvatar (SQLiteDatabase db,String username,Drawable drawable) {
        if(drawable==null)
            Log.e("asdfasdfasdfasd","null");
        db.execSQL("UPDATE account SET avatar = ? ",
               new byte[][]{getPicture(drawable)});
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
