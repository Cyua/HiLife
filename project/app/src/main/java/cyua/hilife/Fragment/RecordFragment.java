package cyua.hilife.Fragment;

import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private Button buttonAudio;

    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    private String openDatetime = null;
    private String audioFilename = null;

    private static final String LOG_TAG = "AudioRecord";
    private boolean startRecording = false;
    private MediaRecorder recorder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_record_fragment,null);

        dbOpenHelper = new DbOpenHelper(this.getContext());

        buttonSend = (Button) view.findViewById(R.id.buttonSend);
        buttonAudio = (Button) view.findViewById(R.id.buttonAudio);
        listView = (ListView) view.findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(this.getContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        provideHint();
        openDatetime = getDateTime();
        audioFilename = this.getContext().getFilesDir().toString() + "/" + openDatetime.hashCode() + ".3gp";

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

        buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!startRecording) {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setOutputFile(audioFilename);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    try {
                        recorder.prepare();
                        recorder.start();
                        buttonAudio.setText("Stop");
                        startRecording = true;
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                }
                else {
                    recorder.stop();
                    recorder.reset();
                    recorder.release();
                    recorder = null;

                    buttonAudio.setText("Audio");
                    startRecording = false;
                }
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

    @Override
    public void onPause() {
        super.onPause();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(false, false, chatText.getText().toString()));    // parameters: isLeft, isAudio
        chatText.setText("");
        return true;
    }

    private void provideHint() {
        chatArrayAdapter.add(new HintMessage());
    }

    private void saveDiary() {
        String diaryTitle;
        String diaryText;
        String diaryAudio;

        // Get diary title, diary content and diary audio
        diaryTitle = chatArrayAdapter.getItem(0).message;

        StringBuilder stringBuilderText = new StringBuilder();
        StringBuilder stringBuilderAudio = new StringBuilder();
        for (int i = 1; i < chatArrayAdapter.getCount(); i++) {
            ChatMessage cm = chatArrayAdapter.getItem(i);
            if (!cm.isAudio)    // The message is not audio, but text
                stringBuilderText.append(cm.message + "\n");
            else
                stringBuilderAudio.append(cm.message + "\n");
        }
        diaryText = stringBuilderText.toString();
        diaryAudio = stringBuilderAudio.toString();

        // Write to db if diary is not empty
        if (diaryText.isEmpty() && diaryAudio.isEmpty())
            return;

        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("INSERT INTO diary(datetime,title,content,audio) VALUES(?,?,?,?)",
                new String[] {openDatetime, diaryTitle, diaryText, diaryAudio});
        db.close();
    }
}
