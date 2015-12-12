package cyua.hilife.CustomerView;

import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Random;

public class HintMessage extends ChatMessage {
    private static String[] morningHints = {
            "早上好，昨晚睡得怎么样？",
            "为新的一天做好准备了吗？",
    };

    private static String[] afternoonHints = {
            "中午吃了什么？",
            "晚饭打算吃点什么呢？",
    };

    private static String[] eveningHints = {
            "晚饭吃了什么呢？",
            "今天看书了吗？看了些什么？",
            "晚上有什么规划？",
    };

    private static String[] weekendHints = {
            "总结一下这一周的工作进展吧",
            "周末了，有什么出行安排吗？",
            "你所在的城市有什么有意思的景点吗？",
    };

    private static String[] allHints = {
            "你最喜欢的电影是什么？",
            "你最喜欢的书是什么？",
            "谈谈你的烹饪技术吧",
            "你喜欢听音乐吗？最喜欢的音乐是什么？",
            "你最得意的运动项目是什么？",
            "谈谈你自己吧",
            "你有没有美梦成真的经历？"
    };

    @Nullable
    private String[] selectHints() {
        Calendar now = Calendar.getInstance();
        Random rand = new Random();

        int type = rand.nextInt(3);
        if (type == 0) {    // hints that given by day of week
            int day = now.get(Calendar.DAY_OF_WEEK);
            if (day >= 5 && day <= 7)
                return weekendHints;
            else
                return allHints;
        }
        else {    // hints that given by hour of day
            int hour = now.get(Calendar.HOUR_OF_DAY);
            if (hour >= 4 && hour <= 12)
                return morningHints;
            else if (hour > 12 && hour <= 17)
                return afternoonHints;
            else if (hour > 17 && hour <= 23)
                return eveningHints;
        }

        return null;
    }

    public HintMessage() {
        this.isLeft = true;
        Random rand = new Random();
        String[] hints = selectHints();
        int idx = rand.nextInt(hints.length);
        this.message = hints[idx];
    }
}
