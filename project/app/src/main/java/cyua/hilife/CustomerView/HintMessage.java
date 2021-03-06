package cyua.hilife.CustomerView;

import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Random;

public class HintMessage extends ChatMessage {
    private static String[] morningHints = {
            "早上好，昨晚睡得怎么样？",
            "为新的一天做好准备了吗？",
            "精神不错吧？",
            "让我们规划一下今天的安排吧？",
            "吃早饭了吗？吃了些什么呢？",
            "此时此刻，你身处何处，在做些什么？",
    };

    private static String[] afternoonHints = {
            "中午吃了什么？",
            "晚饭打算吃点什么呢？",
            "此时此刻，你身处何处，在做些什么？",
            "上午过得怎么样？",
    };

    private static String[] eveningHints = {
            "晚饭吃了什么呢？",
            "今天看书了吗？看了些什么？",
            "晚上有什么规划？",
            "总结一下今天的生活吧",
            "此时此刻，你身处何处，在做些什么？",
            "忙了一天，现在最想做些什么？",
    };

    private static String[] weekendHints = {
            "总结一下这一周的工作进展吧",
            "周末了，有什么出行安排吗？",
            "你所在的城市有什么有意思的景点吗？最近想去吗?",
    };

    private static String[] allHints = {
            "你最喜欢的电影是什么？",
            "你最喜欢的书是什么？",
            "谈谈你的烹饪技术吧",
            "你喜欢听音乐吗？最喜欢的音乐是什么？",
            "你最得意的运动项目是什么？",
            "谈谈你自己吧",
            "你有没有美梦成真的经历？",
            "今天的天气怎么样?",
            "喜欢小猫小狗吗?",
            "喜欢什么样的音乐呢?",
            "今天和朋友聊天了吗?",
            "今天有什么开心的事吗?",
            "最近有没有什么烦恼呢?",
            "听说现在很多人都喜欢仓鼠呢!你喜欢吗?",
            "你喜欢看电视剧吗?喜欢看什么类型的呢?",
            "你在外学习工作还是在家呢?",
            "有什么印象深刻的小说吗?",
            "听说许多日本动漫很好看!你有喜欢的吗?",
            "纸牌屋很好看喔~你有看过吗?",
            "绝命毒师很好看喔~你有看过吗?",
            "疑犯追踪很好看喔~你有看过吗?",
            "喜欢纯音乐吗?",
            "今天有做什么娱乐活动吗?",
            "喜欢下雨天吗?",
            "你觉得恐怖分子可恶吗?",
            "今天气温适合人类生存吗?",
            "今天有看电影吗?最近有什么想看的吗?",
            "喜欢晴天吗?我说的是周杰伦的那首歌喔~",
            "喜欢哪些歌手呢?今天有关注他们的动态吗?",
            "喜欢玩手机吗?最喜欢用什么APP呢?",
            "喜欢玩电脑吗?今天玩过什么游戏吗?",
            "喜欢看小说吗?最近有什么想看的小说吗?",
            "最近有什么想买的东西吗?",
            "今天有做一些运动吗?喜欢做什么运动呢?",
            "喜欢玩手机吗?最喜欢用什么APP呢?",
            "最近想去什么地方旅游吗",
            "喜欢什么球类运动吗?最近有玩过吗?",
            "喜欢游泳吗?上次游泳是什么时候?",
            "有什么讨厌吃的东西吗?为什么呢?",
            "最近有遇到什么不顺心的事吗?",
            "最近有和谁发生争执吗?",
            "你的朋友最近有什么烦恼吗?",
            "最近学习工作压力大吗?",
            "今天有没有达成自己的目标呢?",
            "今天有设立什么要完成的目标吗?",
            "最近有什么遗憾的事吗?",
            "你是单身还是?..你懂的",
            "今天空气质量怎么样?",
            "你所在的地方PM2.5高吗?",
            "最近国际上有发生什么大事吗?",
            "最近国内有发生什么大事吗?",
            "喜欢刷微博吗?今天你刷了吗",
            "上网的时候喜欢逛什么网站呢?",
            "你有什么崇拜的人吗?现在依然崇拜TA吗",
            "喜欢看娱乐圈的新闻吗?今天有什么新闻吗?",
            "你和你的朋友对军事国防感兴趣吗?",
            "最近朋友和你吐槽过哪些事情呢?",
            "喜欢华语音乐还是外语音乐?有什么推荐吗?",
            "有什么喜欢的华语歌手吗?",
            "有什么喜欢的外语歌手吗?",
            "你的朋友中有喜欢玩电子游戏的吗?你对此怎么看",
            "喜欢看严肃文学吗?最近有看过吗?",
            "最近感觉疲劳吗?为什么呢?",
            "最近感觉轻松吗?为什么呢?",
            "最近有关注什么购物促销活动吗?",
            "喜欢吃甜食吗?最近吃的多吗?",
            "喜欢吃火锅吗?经常吃吗?夏天也吃吗?",
            "喜欢吃冰淇淋吗?",
            "最近有养宠物的想法吗?你的朋友有养宠物吗?",
            "你曾经暗恋的对象最近过得好吗?",
            "最近老师/领导有批评或赞赏过你吗?",
            "最近你的朋友有夸奖过你吗?",
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
