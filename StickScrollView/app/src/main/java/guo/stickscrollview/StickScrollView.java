package guo.stickscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by ${GuoZhaoHui} on 2018/2/2.
 * Email:guozhaohui628@gmail.com
 */

public class StickScrollView extends ViewGroup {
    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    private Scroller mScroller;
    /**
     * 最新当前的Y坐标位置
     */
    private int mLastY;
    /**
     * 最初的Y坐标位置
     */
    private int mStartY;


    public StickScrollView(Context context) {
        this(context,null);
    }

    public StickScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreenHeight(context);

    }

    /**
     * 获取屏幕高度
     * @param context
     */
    private void getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;

        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 通知子View进行测量
         */
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        /**
         * 学习下面的写法，在自定义ViewGroup中是如下的写法
         */
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = count * mScreenHeight;
        /**
         * 设置ViewGroup的高度
         */
        setLayoutParams(mlp);
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l,mScreenHeight*i,r,(i+1)*mScreenHeight);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 手指在屏幕上的位置的Y坐标
         */
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                //mStartY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 如果上次执行没有结束，则取消执行新的滑动
                 */
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY-y;
                if(getScaleY()<0){
                    dy = 0;
                }
                if(getScaleY()>getHeight()-mScreenHeight){
                    dy = 0;
                }
                scrollBy(0,dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return true;
    }
}
