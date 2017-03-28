package net.youlvke.yanyuke.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import net.youlvke.yanyuke.bean.DragableBean;


public class DragableBtnGroup extends ViewGroup implements DragableImage.SingleClickListener {

//    连线粗细 颜色
    private static final float STROKEWIDTH = 2f;
    private static final String LINECOLOR = "#535A6A";

//    标题大小 颜色
    private static final float TITLESIZE = 16f;
    private static final String TITLECOLOR = "#BDD8F1";

    private DragableImage[] children;
    private TextView[] titles;
    private int mWidth;
    private int mHeight;
    private DragableBean[] childrenBeans;
    private Context mContext;
    private Paint p;

    private VincentClickListener callback;

    public DragableBtnGroup(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public DragableBtnGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public DragableBtnGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        p = new Paint();
        p.setStrokeWidth(STROKEWIDTH);
        p.setColor(Color.parseColor(LINECOLOR));
        p.setAntiAlias(true);
    }


    public void initParams(DragableBean[] childrenBeans) {
        if (childrenBeans == null || childrenBeans.length <= 0) {
            throw new RuntimeException("r u kidding me? don't give me null data!");
        }
        this.removeAllViews();
        this.childrenBeans = childrenBeans;
        children = new DragableImage[childrenBeans.length];
        titles = new TextView[childrenBeans.length];
        for (int i = 0; i < childrenBeans.length; i++) {
            DragableImage child = new DragableImage(mContext);
            child.setImageResource(childrenBeans[i].getResourceId());
            child.setScaleType(childrenBeans[i].getScaleType());
            child.setAdjustViewBounds(true);
            child.setLayoutParams(childrenBeans[i].getLp());
            child.setParams(childrenBeans[i]);
            child.setSingleClickListener(this);
            children[i] = child;
            TextView title = new TextView(mContext);
            title.setTextSize(TITLESIZE);
            title.setTextColor(Color.parseColor(TITLECOLOR));
            title.setText(TextUtils.isEmpty(childrenBeans[i].getTitle()) ? "" : childrenBeans[i].getTitle());
            titles[i] = title;
            this.addView(children[i]);
            this.addView(titles[i]);
            children[i].bindTitle(titles[i]);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
//                : 720, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
//                : 1080);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (children == null || children.length <= 0) {
            return;
        }
        for (int i = 0; i < childrenBeans.length; i++) {
            int tempL = l + childrenBeans[i].getInitX();
            int tempT = t + childrenBeans[i].getInitY();
            children[i].layout(tempL
                    , tempT
                    , tempL + children[i].getMeasuredWidth()
                    , tempT + children[i].getMeasuredHeight());
            titles[i].layout(tempL + children[i].getMeasuredWidth() / 2 - titles[i].getMeasuredWidth() / 2
                    , tempT + children[i].getMeasuredHeight()
                    , tempL + children[i].getMeasuredWidth() / 2 + titles[i].getMeasuredWidth() / 2
                    , tempT + children[i].getMeasuredHeight() + titles[i].getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (children != null && children.length > 1) {
            for (int i = 0; i < children.length - 1; i++) {
                canvas.drawLine(children[i].getLeft() + children[i].getMeasuredWidth() / 2
                        , children[i].getTop() + children[i].getMeasuredHeight() / 2
                        , children[i + 1].getLeft() + children[i + 1].getMeasuredWidth() / 2
                        , children[i + 1].getTop() + children[i + 1].getMeasuredHeight() / 2
                        , p);
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setAlexClickListener(VincentClickListener callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(DragableBean vb) {
        if (callback != null) {
            callback.onClick(vb.getTag(), vb.getResourceId(), vb.getTitle());
        }
    }

    public interface VincentClickListener {
        void onClick(int tag, int resourceId, String title);
    }
}
