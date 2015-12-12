package cyua.hilife.Aty;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.R;

public class WelcomeActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    Boolean jumpDirction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        dbOpenHelper = new DbOpenHelper(this);
        db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM account", new String[]{});
        jumpDirction = false;
        while(cursor.moveToNext()){
            jumpDirction = true;
        }
        db.close();
        /*
          延迟跳转
        */
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 1){
                    finish();
                    if(jumpDirction == true) {
                        Intent intent = new Intent(WelcomeActivity.this,CheckAccountActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(WelcomeActivity.this,NewAccountActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
        handler.sendEmptyMessageDelayed(1, 2000);
    }
}
