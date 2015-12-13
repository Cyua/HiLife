package cyua.hilife.CustomerView;

import java.io.Serializable;

/**
 * Created by Cyua on 15/12/9.
 */
public class TimeLineModel implements Serializable {
    private int imageview;
    private String title;
    private String content;
    private String date;
    private String audio;

    public int getImageview(){
        return imageview;
    }

    public void setImageview(int imageview){
        this.imageview = imageview;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public TimeLineModel(int imageview, String date, String title, String content, String audio){
        super();
        this.imageview = imageview;
        this.title = title;
        this.content = content;
        this.date = date;
        this.audio = audio;
    }
}
