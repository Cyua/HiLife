package cyua.hilife.Aty;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.R;

public class NewAccountActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        dbOpenHelper = new DbOpenHelper(this);
        db = dbOpenHelper.getWritableDatabase();


        final EditText input = (EditText)findViewById(R.id.newaccounttext);
        ImageButton btn = (ImageButton)findViewById(R.id.newaccountbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usrName = input.getText().toString();
                if(!usrName.equals("")){
                    db.execSQL("INSERT INTO account(username) VALUES(?)",
                            new String[]{usrName});
                    db.close();
                    Intent intent = new Intent(NewAccountActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }
}
