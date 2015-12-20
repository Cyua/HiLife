package cyua.hilife.Aty;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.Database.DbImageHelper;
import cyua.hilife.R;

public class NewAccountActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        dbOpenHelper = new DbOpenHelper(this);
        db = dbOpenHelper.getWritableDatabase();


        final EditText newname = (EditText)findViewById(R.id.newname);
        final EditText passwd = (EditText)findViewById(R.id.newpasswd);
        final EditText repeat = (EditText)findViewById(R.id.newrepeatpasswd);
        final TextView warning = (TextView)findViewById(R.id.passwdwarning);
        Button btn = (Button)findViewById(R.id.newaccountbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usrName = newname.getText().toString();
                String password = passwd.getText().toString();
                String repeatpasswd = repeat.getText().toString();
                if(!usrName.equals("")){
                    if (password.equals(repeatpasswd) && !password.equals("")) {

                        db.execSQL("INSERT INTO account(username,passwd,motto) VALUES(?,?,?)",
                                new String[]{usrName, password,"No motto now..."});
                        db.execSQL("INSERT INTO setting(morning,afternoon,evening) VALUES(?,?,?)",
                                new String[]{"true","true","true"});
                        DbImageHelper.getInstance().modifyAvatar(db,usrName,
                                ContextCompat.getDrawable(NewAccountActivity.this,R.drawable.avatar));
                        db.close();
                        Intent intent = new Intent(NewAccountActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        NewAccountActivity.this.finish();
                        startActivity(intent);
                    }
                    else{
                        passwd.setText("");
                        repeat.setText("");
                        warning.setText("* 两次密码输入不一致");
                        Toast.makeText(NewAccountActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    warning.setText("* 请输入用户名");
                    Toast.makeText(NewAccountActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
