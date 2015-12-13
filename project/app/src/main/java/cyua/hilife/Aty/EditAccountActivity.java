package cyua.hilife.Aty;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import java.io.File;


import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.Database.DbImageHelper;
import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.Database.DbQueryHelper;
import cyua.hilife.R;
import cyua.hilife.utils.AppConstant;
import cyua.hilife.utils.Utils;

public class EditAccountActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    AvatarImageView avatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        DbQueryHelper dbq = new DbQueryHelper(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        avatar = (AvatarImageView)findViewById(R.id.editAvatar);
        DbQueryHelper dbQueryHelper = new DbQueryHelper(this);
        avatar.setImageDrawable(dbQueryHelper.getAvatar(dbQueryHelper.getUserName()));
        dbQueryHelper.closeDb();

        final EditText editName = (EditText)findViewById(R.id.editName);
        final EditText motto = (EditText)findViewById(R.id.editMotto);
        final EditText passwd = (EditText)findViewById(R.id.editPasswd);
        Button btn = (Button)findViewById(R.id.editAccountBtn);

        final String oriName = dbq.getUserName();
        editName.setText(dbq.getUserName());
        motto.setText(dbq.getMotto());
        final String realPassWd = dbq.getPasswd(dbq.getUserName());
        passwd.setText("      ");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pushName = editName.getText().toString();
                String pushMotto = motto.getText().toString();
                String pushPass = passwd.getText().toString();
                if (!pushName.equals("")) {
                    if (pushPass.equals("      "))
                        pushPass = realPassWd;
                    dbOpenHelper = new DbOpenHelper(EditAccountActivity.this);
                    db = dbOpenHelper.getWritableDatabase();
                    db.execSQL("UPDATE account SET username = ?, passwd = ?, motto = ? where username = ?",
                            new String[]{pushName, pushPass, pushMotto, oriName});
                    db.close();

                    Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    EditAccountActivity.this.finish();
                    startActivity(intent);
                }
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.getInstance().selectPicture(EditAccountActivity.this);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (null == data) {
            return;
        }
        Uri uri = null;
        if (requestCode == AppConstant.KITKAT_LESS) {
            uri = data.getData();
            Log.d("tag", "uri=" + uri);
            // 调用裁剪方法
            Utils.getInstance().cropPicture(this, uri);
        } else if (requestCode == AppConstant.KITKAT_ABOVE) {
            uri = data.getData();
            Log.d("tag", "uri=" + uri);
            // 先将这个uri转换为path，然后再转换为uri
            String thePath = Utils.getInstance().getPath(this, uri);
            Utils.getInstance().cropPicture(this,
                    Uri.fromFile(new File(thePath)));
        } else if (requestCode == AppConstant.INTENT_CROP) {
            Bitmap bitmap = data.getParcelableExtra("data");
            avatar.setImageBitmap(bitmap);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            Drawable drawable = bitmapDrawable;
            DbOpenHelper dbOpenHelper = new DbOpenHelper(EditAccountActivity.this);
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            DbImageHelper.getInstance().modifyAvatar(db,"username",drawable);
        }
    }
}
