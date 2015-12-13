package cyua.hilife.Aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cyua.hilife.Database.DbQueryHelper;
import cyua.hilife.R;

public class CheckAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_account);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        final EditText usrname = (EditText)findViewById(R.id.checkname);
        final EditText passwd = (EditText)findViewById(R.id.checkpasswd);
        final TextView warning = (TextView)findViewById(R.id.passwdwarning);
        final DbQueryHelper dbq = new DbQueryHelper(this);

        Button btn = (Button)findViewById(R.id.checkAccountBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usrname.getText().toString().equals("") && !passwd.getText().toString().equals("")) {
                    if (passwd.getText().toString().equals(dbq.getPasswd(usrname.getText().toString()))) {
                        Intent intent = new Intent(CheckAccountActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dbq.closeDb();
                        CheckAccountActivity.this.finish();
                        startActivity(intent);
                    } else {
                        warning.setText("* 账号密码错误");
                    }
                } else {
                    warning.setText("* 请输入账号密码");
                }
            }
        });

    }
}
