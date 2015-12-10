package cyua.hilife.CustomerView;

import java.util.Random;

public class HintMessage extends ChatMessage {
    public static String[] hints = {
            "早上好，昨晚睡得怎么样？",
            "你最喜欢的电影是什么？",
            "周末到了，有什么出行安排吗？"};

    public HintMessage() {
        this.left = true;
        Random rand = new Random();
        int idx = rand.nextInt(hints.length);
        this.message = hints[idx];
    }
}
