package net.youlvke.yanyuke.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import net.youlvke.yanyuke.bean.DragableBean;

public class DragableImage extends ImageView {

    private int mLastY;
    private int mLastX;
    private GestureDetector mGestureDetector;

    private SingleClickListener mSingleClickListener;
    private float mDensity;
    private DragableBtnGroup parent;
    private int mParentWidth;
    private int mParentHeight;
    private TextView title;


    private DragableBean params;

    public DragableImage(Context context) {
        super(context);
        init(context);
    }

    public DragableImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setParams(DragableBean params) {
        this.params = params;
    }

    public void bindTitle(TextView title) {
        this.title = title;
    }


    private void init(Context context) {

        mDensity = getResources().getDisplayMetrics().density;

        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                mLastX = (int) motionEvent.getRawX();
                mLastY = (int) motionEvent.getRawY();
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (mSingleClickListener != null) {
                    mSingleClickListener.onClick(params);
                }
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                float y = motionEvent1.getRawY();
                float x = motionEvent1.getRawX();
                setLocation(((int) x - mLastX), ((int) y - mLastY));
                mLastY = (int) y;
                mLastX = (int) x;
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }

    public DragableImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSingleClickListener(SingleClickListener mSingleClickListener) {
        this.mSingleClickListener = mSingleClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    // 让View移动的方法
    private void setLocation(int x, int y) {
        parent = (DragableBtnGroup) getParent();
        mParentWidth = parent.getMeasuredWidth();
        mParentHeight = parent.getMeasuredHeight();
        int left = getLeft() + x;
        int top = getTop() + y;
        int right = getRight() + x;
        int bottom = getBottom() + y;
        if (left < 0) {
            left = 0;
            right = getWidth();
        } else if (right > mParentWidth) {
            left = mParentWidth - getWidth();
            right = mParentWidth;
        }
        if (top < 0) {
            top = 0;
            bottom = getHeight();
        } else if (bottom > mParentHeight) {
            top = mParentHeight - getHeight();
            bottom = mParentHeight;
        }
        this.setFrame(left, top, right, bottom);
        if (title != null) {
            title.layout(left + getMeasuredWidth() / 2 - title.getMeasuredWidth() / 2
                    , bottom
                    , left + getMeasuredWidth() / 2 + title.getMeasuredWidth() / 2
                    , bottom + title.getMeasuredHeight());
        }
    }

    public interface SingleClickListener {
        void onClick(DragableBean vb);
    }

}
