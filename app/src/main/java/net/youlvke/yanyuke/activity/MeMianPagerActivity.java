package net.youlvke.yanyuke.activity;

import android.view.ViewGroup;

import net.youlvke.yanyuke.R;


public class MeMianPagerActivity extends BaseActivity {
	


	@Override
	public int getLayoutResId() {
		return R.layout.activity_me_detial;
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initData() {
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	@Override
	protected void initView() {

	}

}
