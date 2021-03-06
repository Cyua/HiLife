package cyua.hilife.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cyua.hilife.R;
import cyua.hilife.CustomerView.TimeLineModel;
/**
 * Created by Cyua on 15/12/9.
 */
public class TimeLineAdapter extends BaseAdapter{
    Context context;
    List<TimeLineModel> list;

    public  TimeLineAdapter(Context context, List<TimeLineModel> list){
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.timeline_item, null);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }

        hold.dateshow = (TextView) convertView.findViewById(R.id.left_dateview);
        hold.imageView = (ImageView) convertView.findViewById(R.id.middle_imageview);
        hold.show = (TextView) convertView.findViewById(R.id.right_textview);

        hold.dateshow.setText(list.get(position).getDate());
        hold.imageView.setImageResource(list.get(position).getImageview());
        if (list.get(position).getContent().isEmpty())
            hold.show.setText("（音频）");
        else
            hold.show.setText(list.get(position).getContent());

        return convertView;
    }

    static class ViewHold{
        public TextView dateshow;
        public TextView show;
        public ImageView imageView;
    }
}
