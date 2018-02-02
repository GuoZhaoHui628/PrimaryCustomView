package guo.musiclineview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by ${GuoZhaoHui} on 2018/2/2.
 * Email:guozhaohui628@gmail.com
 */

public class MusicLineView extends View {
    private static final String TAG = "MusicLineView";
    /**
     * 整个View的宽高
     */
    private int mWidth,mHeight;
    private Paint mPaint;
    /**
     * 每一个单独矩形的宽度，等值的
     */
    private int mRectWidth;
    /**
     *每个矩形之间的间距
     */
    private int offset;
    /**
     * 每个小矩形的高
     */
    private int mRectHeight;
    /**
     * 随机数
     */
    private Random mRandom;
    /**
     * 渐变渲染器
     */
    private LinearGradient mLinearGradient;


    public MusicLineView(Context context) {
        this(context, null);
    }

    public MusicLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        mRectWidth = 20;
        offset = 9;
        mRandom = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mLinearGradient = new LinearGradient(0,0,mWidth,mHeight,new int[]{Color.BLUE,Color.GREEN},null, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<24;i++){
            mRectHeight = mRandom.nextInt(720);
            canvas.drawRect((float)(mWidth*0.2+i*mRectWidth+offset*i),
                    (float)(mHeight-mRectHeight-mHeight*0.2),
                    (float)(mWidth*0.2+(i+1)*mRectWidth+offset*i),
                    (float)(mHeight-mHeight*0.2),
                    mPaint);
        }
        postInvalidateDelayed(300);
    }
}
