package guo.stickscrollview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by ${GuoZhaoHui} on 2018/2/5.
 * Email:guozhaohui628@gmail.com
 * 有本事拖动我 :|
 */

public class ScrollLayout extends LinearLayout {
    private static final String TAG = "ScrollLayout";
    /**
     * 手指在屏幕上最新坐标 X Y
     */
    private int mLastX;
    private int mLastY;

    /**
     * 手指按下时View的相对坐标,一般来说初始的坐标是(0,0)
     */
    private int mDownViewX;
    private int mDownViewY;

    private Scroller mScroller;

    public ScrollLayout(Context context) {
        this(context,null);
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                /**
                 * 记录View相对于初始位置的滚动坐标
                 */
                mDownViewX = getScrollX();
                mDownViewY = getScrollY();
                Log.d(TAG, "  ---------MotionEvent.ACTION_DOWN------------  " + mDownViewX + "   " + mDownViewY);

                /**
                 * 更新手指在屏幕上的最新位置坐标
                 */
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 计算出手指当前的位置坐标和上次位置坐标的距离差
                 */
                int dx = x-mLastX;
                int dy = y-mLastY;

                /**
                 * 更新手指在屏幕上的最新位置坐标
                 */
                mLastX = x;
                mLastY = y;
                scrollBy(-dx,-dy);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                scrollTo(mDownViewX,mDownViewY);
                mScroller.startScroll(getScrollX(),getScrollY(),-getScrollX(),-getScrollY(),1000);
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
