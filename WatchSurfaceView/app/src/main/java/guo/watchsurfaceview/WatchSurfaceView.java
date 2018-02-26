package guo.watchsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${GuoZhaoHui} on 2018/2/26.
 * Email:guozhaohui628@gmail.com
 */

public class WatchSurfaceView extends View {

    private int mWidth,mHeight;
    /**
     * 外圈圆画笔
     */
    private Paint paintCircle;
    /**
     * 刻度画笔
     */
    private Paint paintDegree;
    /**
     * 半径
     */
    private float mRadius;
    /**
     * 圆心画笔
     */
    private Paint paintHour,paintMinute;

    public WatchSurfaceView(Context context) {
        this(context,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mRadius = (float) ((mWidth/2)*0.7);
    }

    public WatchSurfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5);

        paintDegree = new Paint();
        paintDegree.setStrokeWidth(3);

        paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth/2,mHeight/2,mRadius,paintCircle);
        for(int i=0;i<24;i++){
            if(i==0 || i==6 || i==12 || i==18){
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                /**
                 * 画刻度
                 */
                canvas.drawLine(mWidth/2,mHeight/2-mRadius,mWidth/2,mHeight/2-mRadius+60,paintDegree);
                /**
                 * 画长刻度
                 */
                String value = String.valueOf(i);
                canvas.drawText(value,mWidth/2-paintDegree.measureText(value)/2,mHeight/2-mRadius+90,paintDegree);
            }else{
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(20);
                /**
                 * 画短刻度，区别只是长度是 长度的一半
                 */
                canvas.drawLine(mWidth / 2, mHeight / 2 - mRadius, mWidth / 2, mHeight / 2 - mRadius + 30, paintDegree);
                String value = String.valueOf(i);
                canvas.drawText(value, mWidth / 2 - paintDegree.measureText(value) / 2, mHeight / 2 - mRadius + 60, paintDegree);
            }
            canvas.rotate(15,mWidth/2,mHeight/2);
        }

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(0, 0, 100, 100, paintHour);
        canvas.drawLine(0, 0, 100, 200, paintMinute);
        canvas.restore();
    }
}
