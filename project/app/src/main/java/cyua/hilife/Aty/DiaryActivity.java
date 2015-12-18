package cyua.hilife.Aty;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

import cyua.hilife.CustomerView.TimeLineModel;
import cyua.hilife.R;

public class DiaryActivity extends AppCompatActivity {
    public static final String ARG_KEY = "tlm";

    private MediaPlayer player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Bundle bundle = getIntent().getExtras();
        TimeLineModel tlm = (TimeLineModel) bundle.getSerializable(ARG_KEY);

        String title = tlm.getTitle();
        String content = tlm.getContent();
        String date = tlm.getDate();
        String audio = tlm.getAudio();

        TextView titleView = (TextView) findViewById(R.id.diary_title);
        TextView contentView = (TextView) findViewById(R.id.diary_content);
        TextView dateView = (TextView) findViewById(R.id.diary_date);
        LinearLayout audioButtons = (LinearLayout) findViewById(R.id.diary_audio);

        titleView.setText(title);
        contentView.setText(content);
        dateView.setText(date);

        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        String[] audios = new String[] {};
        if (!audio.isEmpty())
            audios = audio.split("\n");


        for (int i = 0; i < audios.length; i++) {
            String rawAudioInfo = audios[i];
            String[] audioInfo = rawAudioInfo.split(",");    // array[0]: duration, array[1]: path
            // Button btn = new AudioPlayButton(this, audioInfo[0], audioInfo[1]);
            Button btn = new Button(this);
            btn.setLayoutParams(lparams);
            btn.setText(audioInfo[0]);
            btn.setTag(audioInfo[1]);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play((String) v.getTag());
                }
            });
            audioButtons.addView(btn, lparams);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void play(String path) {
        if (player != null) {    // being played
            player.release();
            player = null;
        }
        player = new MediaPlayer();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            player.setDataSource(fis.getFD());
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.prepare();
            player.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
