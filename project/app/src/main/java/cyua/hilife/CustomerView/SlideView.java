package cyua.hilife.CustomerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import cyua.hilife.CustomerView.AvatarImageView;
import cyua.hilife.R;
import com.nineoldandroids.view.ViewHelper;


public class SlideView extends HorizontalScrollView implements View.OnClickListener{
    private int screenWidth;
    private int menuWidth;
    private int halfMenuWidth;
    private int menuOffsetX;  //菜单的偏移
    private int downX,downY; //按下的坐标
    private boolean isFirst = true;
    private boolean isMenuOpen = false ,isMoveFinish = true;
    private ViewGroup menuView,mainView;
    private AvatarImageView titleAvatar;
    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
