package guo.custompathtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ${GuoZhaoHui} on 2017/12/20.
 * Email:guozhaohui628@gmail.com
 */

public class OvalWaveView extends View {

    private static final String TAG = "OvalWaveView";

    /**
     * 内部圆形画笔
     */
    private Paint mInPaint;

    /**
     * 中间文字画笔
     */
    private Paint mTextPaint;

    /**
     *
     */
    private Paint mOutPaint;

    private int mWidth,mHeight;

    /**
     * 内部圆半径
     */
    private float mInRadius;

    private float mWidthBoder = 5f;





    public OvalWaveView(Context context) {
        super(context);
    }


    public OvalWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mInRadius = (float) (Math.min(w,h)/2*0.6);
        Log.d(TAG,"----onSizeChanged-----  mInRadius:"+mInRadius);
        mWidth = w;
        mHeight = h;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 移动画布原点到中心
         */
        canvas.translate(mWidth/2,mHeight/2);
        drawOutOval(canvas);
        drawInOval(canvas);
        //drawInText(canvas);

    }

    private void initPaint() {
        mInPaint = new Paint();
        mInPaint.setStyle(Paint.Style.FILL);
        mInPaint.setColor(Color.parseColor("#0260B2"));
        mInPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(30);

        mOutPaint = new Paint();
        mOutPaint.setColor(Color.parseColor("#4B99DA"));
        mOutPaint.setStyle(Paint.Style.FILL);
        mOutPaint.setAlpha(240);
        mOutPaint.setAntiAlias(true);
    }

    /**
     * 画内部圆(一共分为三层)
     * @param canvas
     */
    private void drawInOval(Canvas canvas){

        /**
         * 画内部圆
         */
        canvas.drawCircle(0,0,mInRadius-mWidthBoder,mInPaint);

        mInPaint.setColor(Color.parseColor("#4B99DA"));
        mInPaint.setStyle(Paint.Style.STROKE);
        mInPaint.setStrokeWidth(mWidthBoder);
        /**
         * 最外层
         */
        canvas.drawCircle(0,0,mInRadius-mWidthBoder/2,mInPaint);

    }

    /**
     * 画内部文字
     * @param canvas
     */
    private void drawInText(Canvas canvas){

    }

    /**
     * 画另外两个圆
     * @param canvas
     */
    private void drawOutOval(Canvas canvas){
        Log.d(TAG,"----drawOutOval-----  mInRadius:"+mInRadius);
        canvas.drawCircle(0,0,mInRadius,mOutPaint);
    }


}
