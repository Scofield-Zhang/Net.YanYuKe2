package net.youlvke.yanyuke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import net.youlvke.yanyuke.R;

/**
 * Created by Administrator on 2016/11/15 0015.
 * 卡券包控件
 */

@SuppressLint("DrawAllocation")
public class RegisterSuccessView extends LinearLayout {


    private Paint mPaint;
    //边缘小半圆的半径
    private float radius = 20;
    //小半圆之间的间距
    private float spacing = 20;
    //左右边距
    private float paddingLeft;
    private float paddingRight;
    //半圆的个数
    private int numCircle;
    //控件的高宽度
    private float height;
    private float width;
    private Context mContext;
    private float remain;
    private Paint paintLine;


    public RegisterSuccessView(Context context) {
        this(context,null);
    }

    public RegisterSuccessView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public RegisterSuccessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(getResources().getColor(R.color.text_color_gray));//透明的画笔

        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(getResources().getColor(R.color.text_color_gray));//颜色可以自己设置

    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getWidth();
        height = this.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        //画虚线
        Path path = new Path();
        path.moveTo(0, height*920/1280);//起始坐标
        path.lineTo(width, height*920/1280);//终点坐标
        PathEffect effects = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);//设置虚线的间隔和点的长度
        paintLine.setPathEffect(effects);
        canvas.drawPath(path, paintLine);
        float heightRadius = dp2px(mContext,15);
        float widthRadius = dp2px(mContext,22);
        //画左边椭圆
        RectF ovalLeft = new RectF(-heightRadius,height*920/1280-widthRadius,heightRadius,height*920/1280+widthRadius);
        Log.d("ovalLeft", "onDraw: "+heightRadius);
        canvas.drawOval(ovalLeft,mPaint);
        //画右边椭圆
        RectF ovalRight= new RectF(width-heightRadius,height*920/1280-widthRadius,heightRadius+width,height*920/1280+widthRadius);
        canvas.drawOval(ovalRight,mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
