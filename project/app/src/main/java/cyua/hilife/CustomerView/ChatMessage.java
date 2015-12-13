package cyua.hilife.CustomerView;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.FileInputStream;
import java.io.IOException;

public class ChatMessage {
    public boolean isLeft;
    public boolean isAudio;
    public String message;
    public String audioDuration = null;

    public ChatMessage(boolean isLeft, boolean isAudio, String message) {
        super();
        this.isLeft = isLeft;
        this.isAudio = isAudio;
        this.message = message;

        // Get duration if not exist
        if (isAudio && audioDuration == null) {
            MediaPlayer player = new MediaPlayer();
            FileInputStream fis = null;
            System.out.println(message);
            try {
                fis = new FileInputStream(message);
                player.setDataSource(fis.getFD());
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.prepare();
                int audioDurationTotalSec = player.getDuration() / 1000 + 1;
                int audioDurationMin = audioDurationTotalSec / 60;
                int audioDurationSec = audioDurationTotalSec % 60;
                audioDuration = audioDurationMin > 0 ? audioDurationMin + " min " : "";
                audioDuration += audioDurationSec + " sec";
                player.release();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (fis != null) {
                    try {
                        fis.close();
                    }
                    catch (IOException ignore) { }
                }
            }
        }
    }

    public ChatMessage() {}
}
