package cyua.hilife.CustomerView;

import android.media.MediaPlayer;

import java.io.IOException;

public class ChatMessage {
    public boolean isLeft;
    public boolean isAudio;
    public String message;
    public String audioDuration;

    public ChatMessage(boolean isLeft, boolean isAudio, String message) {
        super();
        this.isLeft = isLeft;
        this.isAudio = isAudio;
        this.message = message;

        if (isAudio) {
            MediaPlayer player = new MediaPlayer();
            System.out.println(message);
            try {
                player.setDataSource(message);
                player.prepare();
                int audioDurationTotalSec = player.getDuration() / 1000;
                int audioDurationMin = audioDurationTotalSec / 60;
                int audioDurationSec = audioDurationTotalSec % 60;
                audioDuration = audioDurationMin + "分" + audioDurationSec + "秒";
                player.release();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public ChatMessage() {}
}
