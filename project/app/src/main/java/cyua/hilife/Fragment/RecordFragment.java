package cyua.hilife.Fragment;

import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cyua.hilife.Adapter.ChatArrayAdapter;
import cyua.hilife.CustomerView.ChatMessage;
import cyua.hilife.CustomerView.HintMessage;
import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.R;

/**
 * Created by Cyua on 15/12/7.
 */
public class RecordFragment extends Fragment {
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_record_fragment,null);

        dbOpenHelper = new DbOpenHelper(this.getContext());

        buttonSend = (Button) view.findViewById(R.id.buttonSend);
        listView = (ListView) view.findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(this.getContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        provideHint();

        chatText = (EditText) view.findViewById(R.id.chatText);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)
                        && (!chatText.getText().toString().isEmpty())) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        // To scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveDiary();
    }

    private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(false, chatText.getText().toString()));
        chatText.setText("");
        return true;
    }

    private void provideHint() {
        chatArrayAdapter.add(new HintMessage());
    }

    private String getDiaryTitle() {
        return chatArrayAdapter.getItem(0).message;
    }

    private String getDiaryContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < chatArrayAdapter.getCount(); i++) {
            stringBuilder.append(chatArrayAdapter.getItem(i).message + "\n");
        }
        return stringBuilder.toString();
    }

    private void saveDiary() {
        String diaryContent = getDiaryContent();
        String diaryTitle = getDiaryTitle();
        if (diaryContent.isEmpty())
            return;

        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("INSERT INTO diary(datetime,title,content) VALUES(datetime(),?,?)",
                new String[] {diaryTitle, diaryContent});
        db.close();
    }
}
