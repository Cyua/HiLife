package cyua.hilife.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cyua on 15/12/8.
 */
public class DbOpenHelper extends SQLiteOpenHelper{
    public DbOpenHelper(Context context){
        super(context, "hilife.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS diary (datetime TEXT, location TEXT, title TEXT, content TEXT, audio TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS account (username TEXT, passwd TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS question (time TEXT, sentence TEXT)");

        /*
            place all the questions here
            time = {all, morning, afternoon, evening}
            all = 00:00 ~ 24:00
            morning = 8:00 ~ 10:00
            afternoon = 13:00 ~ 17:00
            evening = 19:00 ~ 22:00
         */
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天的天气怎么样?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢小猫小狗吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢什么样的音乐呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天和朋友聊天了吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天有什么开心的事吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "最近有没有什么烦恼呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "听说现在很多人都喜欢仓鼠呢!你喜欢吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "你喜欢看电视剧吗?喜欢看什么类型的呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "你在外学习工作还是在家呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "有什么印象深刻的小说吗?"});
        //10
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "听说许多日本动漫很好看!你有喜欢的吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "纸牌屋很好看喔~你有看过吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "绝命毒师很好看喔~你有看过吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "疑犯追踪很好看喔~你有看过吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢纯音乐吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天有做什么娱乐活动吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢下雨天吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "你觉得恐怖分子可恶吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天气温适合人类生存吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天有看电影吗?最近有什么想看的吗?"});
        //20
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢晴天吗?我说的是周杰伦的那首歌喔~"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢哪些歌手呢?今天有关注他们的动态吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢玩手机吗?最喜欢用什么APP呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢玩电脑吗?今天玩过什么游戏吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢看小说吗?最近有什么想看的小说吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "最近有什么想买的东西吗?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "今天有做一些运动吗?喜欢做什么运动呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢玩手机吗?最喜欢用什么APP呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"all", "喜欢下雨天吗?"});


        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"morning", "今天吃早餐了吗?吃了什么呢?"});
        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"morning", "今天起床的时候心情怎么样?"});

        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"afternoon", "下午困不困?想不想睡觉?"});

        db.execSQL("INSERT INTO question (time, sentence) VALUES (?,?)",new String[]{"evening", "今天的晚餐可口吗?"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
