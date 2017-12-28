package guo.custompiechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import guo.custompiechart.bean.PieData;
import guo.custompiechart.view.CustomPieChart;

/**
 * 这个自定义的饼状图是跟着http://www.gcssloop.com/customview/Canvas_BasicGraphics
 */
public class MainActivity extends AppCompatActivity {

    private CustomPieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = (CustomPieChart) this.findViewById(R.id.pieChartView);
        setData(pieChart);

    }


    private void setData(CustomPieChart view){
        view.setStartAngle(0);
        List<PieData> datas = new ArrayList<>();
        PieData data = new PieData("java", 12);
        datas.add(data);
        data = new PieData("android", 44);
        datas.add(data);
        data = new PieData("php",5);
        datas.add(data);
        data = new PieData("js", 9);
        datas.add(data);
        view.setData(datas);
    }
}
