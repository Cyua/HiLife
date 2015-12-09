package cyua.hilife.CustomerView;

/**
 * Created by Cyua on 15/12/9.
 */
public class TimeLineModel {
    private int imageview;
    private String text;
    private String date;

    public int getImageview(){
        return imageview;
    }

    public void setImageview(int imageview){
        this.imageview = imageview;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public TimeLineModel(int imageview, String date, String text){
        super();
        this.imageview = imageview;
        this.text = text;
        this.date = date;
    }
}
