package cyua.hilife.Aty;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.R;

public class EditAccountActivity extends AppCompatActivity {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);


        ImageButton btn = (ImageButton)findViewById(R.id.newaccountbtn);

        dbOpenHelper = new DbOpenHelper(this);
        db = dbOpenHelper.getWritableDatabase();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    db.execSQL("INSERT INTO account(username) VALUES(?)",
//                            new String[]{usrName});
                    db.close();
                    Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

            }
        });
    }
}
