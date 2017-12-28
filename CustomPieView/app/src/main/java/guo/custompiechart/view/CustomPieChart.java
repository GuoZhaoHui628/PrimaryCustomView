package guo.custompiechart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import guo.custompiechart.bean.PieData;

import static android.content.ContentValues.TAG;

/**
 * Created by ${GuoZhaoHui} on 2017/8/25.
 * email:guozhaohui628@gmail.com
 */

public class CustomPieChart extends View{

    private static final String TAG = "CustomPieChart";

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private List<PieData> mList ;

    private int mStartAngle = 0;

    private Paint mpaint = new Paint();

    private int mWidth,mHeight;




    public CustomPieChart(Context context) {
        this(context,null);
    }

    public CustomPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mList == null){
            return;
        }else{

            float mCurrentAngle = mStartAngle;

            //将画布坐标原点移动到自定义view的中心位置
            canvas.translate(mWidth / 2, mHeight / 2);

            float r = (float) (Math.min(mWidth,mHeight)/2*0.8);

            float r2 = (float) (Math.min(mWidth,mHeight)/2*0.8);

            Log.d(TAG, "mWidth=" + mWidth + "   ||   mHeight=" + mHeight + "   ||   r=" + r + "    ||    r2=" + r2);

            //饼状图绘制的区域
            RectF rect = new RectF(-r, -r, r, r);

            for(int i=0;i<mList.size();i++){
                PieData data = mList.get(i);
                mpaint.setColor(data.getColor());
                canvas.drawArc(rect,mCurrentAngle,data.getAngle(),true,mpaint);
//                mpaint.setColor(Color.BLACK);
//                mpaint.setTextSize(40);
                mCurrentAngle += data.getAngle();
            }

        }
    }

    /**
     * 设置开始角度
     * @param startAngle
     */
    public void setStartAngle(int startAngle){
        this.mStartAngle = startAngle;
        invalidate();
    }

    /**
     * 在外部传入数据
     * @param data
     */
    public void setData(List<PieData> data){

        this.mList = data;
        initData(mList);
        invalidate();

    }

    /**
     * 对传入的数据进行初始化，附加设置空的值
     * @param datas
     */
    private void initData(List<PieData> datas) {

        if(datas==null || datas.size()==0){
            return;
        }else{
            float sumValues = 0;
            for(int i=0;i<datas.size();i++){
                PieData data = datas.get(i);

                //计算数值和
                sumValues+=data.getValue();

                //设置颜色
                int j = i%mColors.length;
                data.setColor(mColors[j]);
            }

            for(int i=0;i<datas.size();i++){

                PieData data = datas.get(i);

                //百分比
                float percent = data.getValue()/sumValues;

                //角度
                float angle = 360*percent;

                data.setPercent(percent);
                data.setAngle(angle);

            }
        }

    }
}
