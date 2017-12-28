package guo.custompathtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuPopupHelper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ${GuoZhaoHui} on 2017/12/15.
 * Email:guozhaohui628@gmail.com
 */

public class Path1 extends View {

    private static final String TAG = "Path1";

    private Paint mPaint;

    private PaintFlagsDrawFilter pfd;

    /**
     * 文字画笔
     */
    private TextPaint textPaint;
    /**
     *   数据区域画笔
     */
    private Paint dataPaint;
    private int mWidth,mHeight;
    private int counts = 6;

    /**
     * 最大的半径
     */
    private float maxRadius ;

    /**
     * 多边形平均角度
     */
    private float avgAngle = (float) ((Math.PI*2)/counts);

    private String [] contents = {"销售","市场","研发","客服","信息技术","管理"};
    /**
     * 各维度分值
     */
    private double[] data = {100,60,60,60,100,50,10,20};
    /**
     * 数据最大值
     */
    private float maxValue = 100;
    public Path1(Context context) {
        super(context);
    }

    public Path1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        maxRadius = (float) (Math.min(w,h)/2*0.7);

        mWidth = w;
        mHeight = h;
        postInvalidate();
    }

    private void initPaint() {
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#CF5B56"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#139D57"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(30);
        textPaint.setStrokeWidth(3);

        dataPaint = new Paint();
        dataPaint.setColor(Color.parseColor("#1196DB"));
        dataPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        drawSpi(canvas);
        drawLine(canvas);
        drawTextContent(canvas);
        drawDataArea(canvas);
    }


    /**
     * 绘制蜘蛛网络
     * @param canvas
     */
    private void drawSpi(Canvas canvas){

        Path mPath = new Path();

        //将最大半径均分
        float avgRadius = maxRadius/(counts-1);

        for(int i=1;i<counts;i++){
            mPath.reset();

            //当前多边形半径
            float currentRadius = i*avgRadius;
            for(int j=0;j<counts;j++){
                if(j==0){  //多边形第一个点
                    mPath.moveTo(currentRadius,0);
                }else{
                    float x = (float) (Math.cos(avgAngle*j)*currentRadius);
                    float y = (float) (Math.sin(avgAngle * j) * currentRadius);
                    Log.d(TAG, "-- x --  y -- " + x + "  --  " + y);
                    mPath.lineTo(x, y);
                }
            }
            mPath.close();
            canvas.drawPath(mPath, mPaint);
            Log.d(TAG, "----------------------分割线--------------------------");
        }

    }

    /**
     * 绘制蜘蛛网中的直线
     * @param canvas
     */
    private void drawLine(Canvas canvas){
        Path mPath = new Path();
        for(int i=0;i<counts;i++){
            mPath.reset();
            float x = (float) (Math.cos(avgAngle*i)*maxRadius);
            float y = (float) (Math.sin(avgAngle*i)*maxRadius);
            mPath.lineTo(x, y);
            canvas.drawPath(mPath,mPaint);
        }

    }


    /**
     * 绘制文本
     * @param canvas
     */
    private void drawTextContent(Canvas canvas){
        /*
        FontMetrics是一个Paint的内部类，作用是“字体测量”。它里面呢就定义了top,ascent,descent,bottom,leading五个成员变量其他什么也没有，和rect很相似
         */
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight  = fontMetrics.descent-fontMetrics.ascent;
        for(int i=0;i<counts;i++){
            float x = (float) ((maxRadius + fontHeight/2) * Math.cos(avgAngle * i));
            float y = (float) ((maxRadius + fontHeight/2) * Math.sin(avgAngle * i));
            canvas.drawText(contents[i],x,y,textPaint);
        }

    }


    /**
     * 绘制区域
     * @param canvas
     */
    private void drawDataArea(Canvas canvas){
        Path mPath = new Path();
        dataPaint.setAlpha(255);
        for(int i=0;i<counts;i++){
            double valuePercent = data[i] / maxValue;
            float x = (float) (Math.cos(i * avgAngle) * maxRadius * valuePercent);
            float y = (float) (Math.sin(i * avgAngle) * maxRadius * valuePercent);
            if(i == 0){
                mPath.moveTo(x,y);
            }else{
                mPath.lineTo(x, y);
            }

            /**
             * 绘制小圆点
             */
            canvas.drawCircle(x, y, 10, dataPaint);

        }
        /**
         * 绘制多边形 周围周长
         */
        dataPaint.setStyle(Paint.Style.STROKE);
        dataPaint.setAntiAlias(true);
        mPath.close();
        canvas.drawPath(mPath, dataPaint);

        /**
         * 绘制多边形填充区域
         */
        dataPaint.setAlpha(125);
        dataPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, dataPaint);

    }



}
