package guo.custompiechart.bean;

/**
 * Created by ${GuoZhaoHui} on 2017/8/25.
 * email:guozhaohui628@gmail.com
 */

public class PieData {

    //用户关系的数据
    private String name;     //每一块名字
    private float value;      //每一块的数值(来自用户)
    private float percent;      //每一块的占用百分数(算出来显示给用户看的)

    //非用户关系的数据
    private float angle;     //每一块占用的角度
    private int color;      //颜色

    public PieData(String name, float value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
