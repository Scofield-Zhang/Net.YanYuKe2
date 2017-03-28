package net.youlvke.yanyuke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.utils.ScreenUtils;

/**
 * Created by Administrator on 2016/11/15 0015.
 * 卡券包控件
 */


public class CouponView extends RelativeLayout {


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

    private float remain;

    public CouponView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//      initView(context);
    }

    public CouponView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CouponView(Context context) {
        super(context);
//     initView(context);
    }

    private void initView(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.parseColor("#F0F0F0"));//透明的画笔

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CouponView);
        float radius = ta.getDimension(R.styleable.CouponView_radius, 20);
        float spacing = ta.getDimension(R.styleable.CouponView_spacing, 20);
        setRadius(radius);
        setSpacing(spacing);

        ta.recycle();

        //paddingLeft = paddingRight = spacing;
    }


    private void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    private void setRadius(float radius) {
        this.radius = radius;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getWidth();
        height = this.getHeight();
      /*  System.out.println("这里");
        // getWidth在OnCreat的时候得到的是0. 当一个view对象创建时，android并不知道其大小，所以getWidth()和   getHeight()返回的结果是0，真正大小是在计算布局时才会计算.

        //圆的数量始终比边距数量少一个，所以总长度减去左边距除以2*radius+spacing的值就是圆的数量
        numCircle = (int) ((width - paddingLeft) / (2 * radius + spacing));
        //除以所有圆和边距的所余下的长度
        remain = ((width - paddingLeft) % (2 * radius + spacing));
        System.out.println("圆的个数==" + numCircle);
        System.out.println("remain==" + remain);*/
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //remain/2保证左右两边边距一样
        //float cx = (paddingLeft + radius + remain / 2);
          //  canvas.drawCircle(0, height/2, radius, mPaint);
        RectF ovalLeft = new RectF(-ScreenUtils.dp2px(10),height/2-ScreenUtils.dp2px(15),ScreenUtils.dp2px(10),height/2+ScreenUtils.dp2px(15)) ;
        canvas.drawOval(ovalLeft,mPaint);
        RectF ovalRight = new RectF(width-ScreenUtils.dp2px(13),height/2-ScreenUtils.dp2px(15),width+ScreenUtils.dp2px(13),height/2+ScreenUtils.dp2px(15)) ;
        canvas.drawOval(ovalRight,mPaint);
           // cx = (int) (cx + (2 * radius + spacing));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
