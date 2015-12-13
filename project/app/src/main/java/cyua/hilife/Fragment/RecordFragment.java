package cyua.hilife.Fragment;

import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cyua.hilife.Adapter.ChatArrayAdapter;
import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.CustomerView.ChatMessage;
import cyua.hilife.CustomerView.HintMessage;
import cyua.hilife.Database.DbOpenHelper;
import cyua.hilife.Database.DbQueryHelper;
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
    private int audioCount = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_record_fragment,null);

        dbOpenHelper = new DbOpenHelper(this.getContext());

        buttonSend = (Button) view.findViewById(R.id.buttonSend);
        buttonAudio = (Button) view.findViewById(R.id.buttonAudio);
        listView = (ListView) view.findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(this.getContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        DbQueryHelper dbQueryHelper = new DbQueryHelper(this.getContext());
        AvatarImageView avatarImageView = (AvatarImageView)view.findViewById(R.id.title_avatar);
        avatarImageView.setImageDrawable(dbQueryHelper.getAvatar(dbQueryHelper.getUserName()));
        dbQueryHelper.closeDb();


        provideHint();
        openDatetime = getDateTime();
        audioFilename = this.getContext().getFilesDir().toString() + "/" + getHexHash(openDatetime);

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
                String currentFilename = audioFilename + "_" + audioCount + ".3gp";
                if (!startRecording) {
                    // start button pressed
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setOutputFile(currentFilename);
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
                    // stop button pressed
                    recorder.stop();
                    recorder.reset();
                    recorder.release();
                    recorder = null;

                    audioCount++;
                    chatArrayAdapter.add(new ChatMessage(false, true, currentFilename));

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

    private String getHexHash(String s) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            hash = String.format("%032X", new BigInteger(1, md.digest()));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
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
        String diaryContent;
        String diaryAudio;

        // Get diary title, diary content and diary audio
        diaryTitle = chatArrayAdapter.getItem(0).message;

        List<String> lContent = new ArrayList<String>();
        List<String> lAudio = new ArrayList<String>();

        for (int i = 1; i < chatArrayAdapter.getCount(); i++) {
            ChatMessage cm = chatArrayAdapter.getItem(i);
            if (cm.isAudio)
                lAudio.add(cm.audioDuration + "," + cm.message);    // a line containing duration, comma, and path
            else    // The message is not audio, but text
                lContent.add(cm.message);
        }
        diaryContent = TextUtils.join("\n", lContent);
        diaryAudio = TextUtils.join("\n", lAudio);

        // Write to db if diary is not empty
        if (diaryContent.isEmpty() && diaryAudio.isEmpty())
            return;

        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("INSERT INTO diary(datetime,title,content,audio) VALUES(?,?,?,?)",
                new String[] {openDatetime, diaryTitle, diaryContent, diaryAudio});
        db.close();
    }
}
