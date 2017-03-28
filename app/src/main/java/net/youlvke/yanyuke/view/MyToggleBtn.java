package net.youlvke.yanyuke.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import net.youlvke.yanyuke.R;


public class MyToggleBtn extends View implements OnClickListener {

	public MyToggleBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyToggleBtn(Context context) {
		super(context);
		init();
	}

	private Bitmap bitmapBg;
	
	private Bitmap newSlideBitmap;

	private Bitmap newBitmapZhu;

	/*private Bitmap bitmapSlideBtn;
	private Bitmap bitmapZhu;*/

	private Paint paint;

	private float btnLeft;

	private void init() {

		bitmapBg = BitmapFactory.decodeResource(getResources(),
				R.mipmap.bg_xh_03);
		Bitmap bitmapSlideBtn = BitmapFactory.decodeResource(getResources(),
				R.mipmap.zhuce_xh_03);
		Bitmap bitmapZhu = BitmapFactory.decodeResource(getResources(),
				R.mipmap.denglu_xh_03);
		
		btnLeftMax = bitmapBg.getWidth()/2;
		
		newSlideBitmap = Bitmap.createScaledBitmap(bitmapSlideBtn, bitmapBg.getWidth()/2,  bitmapBg.getHeight(), true);
		newBitmapZhu = Bitmap.createScaledBitmap(bitmapZhu, bitmapBg.getWidth()/2,  bitmapBg.getHeight(), true);
		
		paint = new Paint();

		setOnClickListener(this);

	}

	private int downX;

	private int lastX;

	private boolean isDrop;

	private boolean currState = false;


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 0
			lastX = downX = (int) event.getX();
			isDrop = false;
			break;
		case MotionEvent.ACTION_MOVE: // 2

			int moveX = (int) event.getX();

			int disX = moveX - lastX;
			btnLeft += disX;
			flushView();
			lastX = moveX;
			if (Math.abs(downX - moveX) > 15) {// 滑动事件
				isDrop = true;
			} else {// 点击事件
				flushState();
			}

			break;
		case MotionEvent.ACTION_UP: // 1
			if (isDrop) {// 滑动事件
				if (btnLeft > btnLeftMax / 2) {
					currState = true;
					if (null != onStateChangedListener) {
						onStateChangedListener.onStateChanged(false);
					}
				} else if(btnLeft < btnLeftMax / 2 ){
					currState =  false;
					if (null != onStateChangedListener ) {
						onStateChangedListener.onStateChanged(true);
					}
				}
			} else {// 点击事件
				if (true == currState) {
					if (null != onStateChangedListener) {
						onStateChangedListener.onStateChanged(true);
					}
				} else if (false == currState) {
					if (null != onStateChangedListener) {
						onStateChangedListener.onStateChanged(false);
					}
				}
			}
			flushState();
			break;
		}
		return true;
	}

	private void flushView() {
		if (btnLeft < 0) {
			btnLeft = 0;
		}
		if (btnLeft > btnLeftMax) {
			btnLeft = btnLeftMax;
		}
		invalidate(); //
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = bitmapBg.getWidth();
		int height = bitmapBg.getHeight();
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawBitmap(bitmapBg, 0, 0, paint);
		if (currState == true) {
			canvas.drawBitmap(newSlideBitmap, btnLeft, 0, paint);
		} else if(currState == false){
			canvas.drawBitmap(newBitmapZhu, btnLeft, 0, paint);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	private int btnLeftMax;

	private OnStateChangedListener onStateChangedListener;





	@Override
	public void onClick(View v) {
		if (isDrop) {
			return;
		}
		currState = !currState;
		flushState();
	}

	private void flushState() {
			if (currState) {
				btnLeft = btnLeftMax;
			} else {
				btnLeft = 0;
			}
		flushView();
	}

	/* 设置开关状态改变监听器 */
	public void setOnStateChangedListener(OnStateChangedListener o) {
		this.onStateChangedListener = o;
	}

	/* 内部接口，开关状态改变监听器 */
	public interface OnStateChangedListener {
		public void onStateChanged(boolean state);
	}
}
