package net.youlvke.yanyuke.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import net.youlvke.yanyuke.bean.CityModel;
import net.youlvke.yanyuke.bean.ProvinceModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RollCircleView extends View {

    private Context mContext = null;
    private AttributeSet mAttrs = null;

    //    当前省市index
    private int mCurProvince;
    private int mCurCity;
    //  文字最大缩放
    private final float TEXTSCALE = 1.2f;
    //    文字大小 单位sp
    private final int TEXTSIZE = 14;
    //    背景色值
    private final String BCCOLOR = "#4E7DA4";
    private final String TEXTCOLOR = "#89B6DA";
    private final String TEXTCOLOR_SELECTED = "#F4FC1A";
    //    垂直校正 dp
    private final int CORRECTY = 0;
    //    水平校正 dp
    private final int CORRECTX = 0;
    //    半径矫正 dp
    private final int CORRECTR = 10;

    private static final int TOUCH_STATE_REST = 0;

    // 单位y轴滑动转动的角度
    private float angelPerY = 0.1f;

    //    半瓶条目数
    private int mCount = 10;
    /**
     * 记录当前的触摸状态
     */
    private int mTouchState = TOUCH_STATE_REST;

    /**
     * 记录上次触摸的横坐标值
     */
    private float mLastMotionY;

    private float mLastMotionYB;

    // 转过角度
    private float curAngel = 0;
    private float curAngelB = 0;
    // 平均角度
    private int aveAngel;
    // 半径
    private int radius;

    private static Handler handler;
    private Timer timer;
    private TimerTask a;
    private Timer timerB;
    private TimerTask aB;
    private int mProvinceCount;
    //    控件宽高
    private int mViewWidth;
    private int mViewHeight;
    //    两侧半圆中心
    private int leftcenterX;
    private int rightcenterX;
    private int leftcenterY;
    private int rightcenterY;
    private RectF mArcLeftRectF = null;

    private RectF mArcRightRectF = null;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private int mTextSize;
    private int textredius;

    //    省市数据源
    private List<ProvinceModel> mlist;
    private List<CityModel> mCitylist;
    private SelectChangedListener selectChangeListener;
    //    点击左侧还是右侧
    private boolean touchingLeft;
    //  自定义背景圆
    private Paint mCirclePaint;
    //  背景圆的半径
    private float mBackCircleRadius = 75;
    //  背景圆的颜色
    private String CIRCLECOLOR = "#FBFDEA";


    public RollCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mAttrs = attrs;
        init();
    }


    public RollCircleView(Context context) {
        this(context, null);
    }

    public RollCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        timer = new Timer();
        timerB = new Timer();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                handlewhat(msg.what, 1);
            }
        };

        mBackgroundPaint = new Paint();
        //      可自定义属性
        mBackgroundPaint.setColor(Color.parseColor(BCCOLOR));
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mArcLeftRectF = new RectF();
        mArcRightRectF = new RectF();
        //      可自定义属性
        mTextSize = dp2px(mContext, TEXTSIZE);
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor(TEXTCOLOR));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        aveAngel = 180 / (mCount - 1);

        //      自定义背景圆
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.parseColor(CIRCLECOLOR));
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

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

    public void setProvinceList(List<ProvinceModel> mlist) {
        if (mlist == null || mlist.size() <= 0) {
            throw new NullPointerException("不能传入空集合");
        }
        this.mlist = mlist;
        mProvinceCount = mlist.size();
        mCurProvince = 0;
        mCitylist = mlist.get(mCurProvince).getCityList();
        invalidate();
    }

    public void changeCityList(ProvinceModel province) {
        if (province == null) {
            throw new NullPointerException("不能传入空省");
        }
        this.mCitylist = province.getCityList();
        mCurCity = 0;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        radius = mViewHeight < mViewWidth ? mViewHeight >> 1 : mViewWidth >> 1;
        textredius = radius - dp2px(mContext, CORRECTR);
        leftcenterX = 0;
        rightcenterX = mViewWidth;
        rightcenterY = leftcenterY = mViewHeight >> 1;
        mArcLeftRectF.set(leftcenterX - radius, leftcenterY - radius, leftcenterX + radius, leftcenterY + radius);
        mArcRightRectF.set(rightcenterX - radius, rightcenterY - radius, rightcenterX + radius, rightcenterY + radius);
//        /**
//         * 如果是wrap_content设置为我们计算的值 否则：直接设置为父容器计算的值
//         */
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int screenWidth = dm.widthPixels;
//        int screenHeigh = dm.heightPixels;
//        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
//                : screenWidth., (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
//                : screenHeigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawBackCircle(canvas);
        drawText(canvas);
    }

    private void drawBackCircle(Canvas canvas) {
        //Log.d("drawBackCircle", "drawBackCircle: "+mViewHeight+":"+mViewWidth);
        canvas.drawCircle(0,mViewHeight/2, dp2px(mContext, mBackCircleRadius), mCirclePaint);
        canvas.drawCircle(mViewWidth,mViewHeight/2, dp2px(mContext, mBackCircleRadius), mCirclePaint);
    }

    private void drawText(Canvas canvas) {
        drawProvince(canvas);
        drawCity(canvas);
    }

    private void drawProvince(Canvas canvas) {
        if (curAngel > 15 && mCurProvince < mlist.size() - 1) {
            mCurProvince++;
            curAngel -= aveAngel;
            changeCityList(mlist.get(mCurProvince));
        } else if (curAngel < -15 && mCurProvince > 0) {
            mCurProvince--;
            curAngel += aveAngel;
            changeCityList(mlist.get(mCurProvince));
        }
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize * TEXTSCALE);
        mTextPaint.setColor(Color.parseColor(TEXTCOLOR_SELECTED));
        float textWidth = mTextPaint.measureText(mlist.get(mCurProvince).getName());
        canvas.drawText(mlist.get(mCurProvince).getName(),
                (float) (leftcenterX + textredius * Math.cos(curAngel * Math.PI / 180) - textWidth / 2 - dp2px(mContext, CORRECTX)),
                (float) (leftcenterY + textredius * Math.sin(-curAngel * Math.PI / 180)
                        + mTextSize * TEXTSCALE / 2),
                mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.parseColor(TEXTCOLOR));
        for (int i = 1; i <= 4; i++) {
            if (mCurProvince - i < 0) {
                continue;
            } else {
                canvas.drawText(mlist.get(mCurProvince - i).getName(),
                        (float) (leftcenterX + textredius * Math.cos((curAngel + aveAngel * i) * Math.PI / 180) - dp2px(mContext, CORRECTX)),
                        (float) (leftcenterY + textredius * Math.sin((-curAngel - aveAngel * i) * Math.PI / 180)
                                + mTextSize + dp2px(mContext, CORRECTY)),
                        mTextPaint);
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (mCurProvince + i >= mlist.size()) {
                continue;
            } else {
                canvas.drawText(mlist.get(mCurProvince + i).getName(),
                        (float) (leftcenterX + textredius * Math.cos((curAngel - aveAngel * i) * Math.PI / 180) - dp2px(mContext, CORRECTX)),
                        (float) (leftcenterY + textredius * Math.sin((-curAngel + aveAngel * i) * Math.PI / 180)
                                - dp2px(mContext, CORRECTY)),
                        mTextPaint);
            }
        }
    }

    private void drawCity(Canvas canvas) {
        if (mCitylist == null || mCitylist.size() <= 0) {
            if (selectChangeListener != null) {
                selectChangeListener.onChange(mCurProvince, -1);
            }
            return;
        }
        if (curAngelB > 15 && mCurCity < mCitylist.size() - 1) {
            mCurCity++;
            curAngelB -= aveAngel;
        } else if (curAngelB < -15 && mCurCity > 0) {
            mCurCity--;
            curAngelB += aveAngel;
        }
        if (selectChangeListener != null) {
            selectChangeListener.onChange(mCurProvince, mCurCity);
        }
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setTextSize(mTextSize * TEXTSCALE);
        mTextPaint.setColor(Color.parseColor(TEXTCOLOR_SELECTED));
        canvas.drawText(mCitylist.get(mCurCity).getName(),
                (float) (rightcenterX - textredius * Math.cos(curAngelB * Math.PI / 180) + dp2px(mContext, CORRECTX)),
                (float) (rightcenterY + textredius * Math.sin(-curAngelB * Math.PI / 180)
                        + mTextSize * TEXTSCALE / 2),
                mTextPaint);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.parseColor(TEXTCOLOR));
        for (int i = 1; i <= 4; i++) {
            if (mCurCity - i < 0) {
                continue;
            } else {
                canvas.drawText(mCitylist.get(mCurCity - i).getName(),
                        (float) (rightcenterX - textredius * Math.cos((curAngelB + aveAngel * i) * Math.PI / 180) + dp2px(mContext, CORRECTX)),
                        (float) (rightcenterY + textredius * Math.sin((-curAngelB - aveAngel * i) * Math.PI / 180)
                                + mTextSize + dp2px(mContext, CORRECTY)),
                        mTextPaint);
            }
        }
        for (int i = 1; i <= 4; i++) {
            if (mCurCity + i >= mCitylist.size()) {
                continue;
            } else {
                canvas.drawText(mCitylist.get(mCurCity + i).getName(),
                        (float) (rightcenterX - textredius * Math.cos((curAngelB - aveAngel * i) * Math.PI / 180) + dp2px(mContext, CORRECTX)),
                        (float) (rightcenterY + textredius * Math.sin((-curAngelB + aveAngel * i) * Math.PI / 180)
                                - dp2px(mContext, CORRECTY)),
                        mTextPaint);
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawArc(this.mArcLeftRectF, -90F, 180.0F, false, mBackgroundPaint);
        canvas.drawArc(this.mArcRightRectF, 90F, 180.0F, false, mBackgroundPaint);
    }

    public void setSelectChangeListener(SelectChangedListener selectChangeListener) {
        this.selectChangeListener = selectChangeListener;
    }

    public interface SelectChangedListener {
        void onChange(int province, int cityinprovince);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY(0);
        float xt = event.getX(0);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                mLastMotionYB = y;
                if (judgeTouchLOrR(xt)) {
                    touchingLeft = true;
                } else {
                    touchingLeft = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchingLeft) {
                    if (judgeTouchLOrR(xt)) {
                        if ((curAngel < 60 && curAngel > -60) || (curAngel >= 60 && mLastMotionY < y) || (curAngel <= -60 && mLastMotionY > y)) {
                            curAngel += (mLastMotionY - y) * angelPerY;
                            Log.e("tag", "curangel::" + curAngel);
                        }
                        mLastMotionY = y;
                        mLastMotionYB = y;
                        invalidate();
                    } else {
                        touchingLeft = !touchingLeft;
                        runUIchange();
                        break;
                    }
                } else {
                    if (!judgeTouchLOrR(xt)) {
                        if ((curAngelB < 60 && curAngelB > -60) || (curAngelB >= 60 && mLastMotionYB < y) || (curAngelB <= -60 && mLastMotionYB > y)) {
                            curAngelB += (mLastMotionYB - y) * angelPerY;
                        }
                        mLastMotionY = y;
                        mLastMotionYB = y;
                        invalidate();
                    } else {
                        touchingLeft = !touchingLeft;
                        runUIchangeB();
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                releaseTouch();
                break;
            default:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return true;
    }


    private void releaseTouch() {
        runUIchange();
        runUIchangeB();
    }

    private boolean judgeTouchLOrR(float xt) {
        if (xt <= mViewWidth / 2 && xt >= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void runUIchange() {
        if (a != null) {
            a.cancel();
            a = null;
        }
        a = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(a, 0, 5);
    }

    private void runUIchangeB() {
        if (aB != null) {
            aB.cancel();
            aB = null;
        }
        aB = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timerB.schedule(aB, 0, 5);
    }


    public void handlewhat(int what, int step) {
        switch (what) {
            case 0:
                if (curAngel > 0) {
                    curAngel -= step;
                    if (curAngel < 0) {
                        curAngel = 0;
                        a.cancel();
                    }
                } else {
                    curAngel += step;
                    if (curAngel > 0) {
                        curAngel = 0;
                        a.cancel();
                    }
                }
                break;
            case 1:
                if (curAngelB > 0) {
                    curAngelB -= step;
                    if (curAngelB < 0) {
                        curAngelB = 0;
                        aB.cancel();
                    }

                } else {
                    curAngelB += step;
                    if (curAngelB > 0) {
                        curAngelB = 0;
                        aB.cancel();
                    }
                }
                break;
        }
        invalidate();

    }
}
