package guo.custompathtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ${GuoZhaoHui} on 2017/12/19.
 * Email:guozhaohui628@gmail.com
 */

public class Bezier1 extends View {

    private Paint mPaint;
    private int centerX,centerY;
    private PointF start,end,control;

    public Bezier1(Context context) {
        super(context);
    }


    public Bezier1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        start.x = centerX-200;
        start.y = centerY;
        end.x = centerX+200;
        end.y = centerY;
        control.x = centerX;
        control.y = centerY-100;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         *  绘制数据点和控制点
         */
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x,start.y,mPaint);
        canvas.drawPoint(end.x,end.y,mPaint);
        canvas.drawPoint(control.x,control.y,mPaint);

        /**
         * 绘制两点之间的线条
         */
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        /**
         * 绘制贝塞尔曲线
         */
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.RED);
        Path mPath = new Path();
        mPath.moveTo(start.x, start.y);
        mPath.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(mPath, mPaint);

    }
}
