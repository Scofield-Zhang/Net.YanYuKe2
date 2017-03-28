package net.youlvke.yanyuke.activity;

import android.view.View;
import android.widget.ImageView;

import net.youlvke.yanyuke.R;


public class SelectPicDialogActivity extends BaseActivity {
	
	private static final int HEAD_IMAGE = 0x01;
	private ImageView iv_img_head;


	@Override
	public int getLayoutResId() {
		return R.layout.activity_show_photo_dialog;
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initData() {

	}

	/**
	 * 初始化控件
	 */
	protected void initView() {
		
		
	}

	@Override
	protected void onInnerClick(View v) {
		super.onInnerClick(v);
		switch (v.getId()) {
			case R.id.iv_img_head:
			/* Intent intent = new Intent(this,)
			startActivityForResult(intent, HEAD_IMAGE);*/
				break;

			default:
				break;
		}
	}
}