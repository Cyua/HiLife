package cyua.hilife.CustomerView;

public class ChatMessage {
    public boolean isLeft;
    public boolean isAudio;
    public String message;

    public ChatMessage(boolean isLeft, boolean isAudio, String message) {
        super();
        this.isLeft = isLeft;
        this.isAudio = isAudio;
        this.message = message;
    }

    public ChatMessage() {}
}
