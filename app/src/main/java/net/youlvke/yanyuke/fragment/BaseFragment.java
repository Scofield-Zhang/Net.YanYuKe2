package net.youlvke.yanyuke.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
	//上下文
	protected Context mContext;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		setStatusColor(Color.TRANSPARENT);
		View view = initView();//初始化控件
		return view;
	}

	protected void setStatusColor(int colorValues) {
		Window window = getActivity().getWindow();
		//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		//设置状态栏颜色
		window.setStatusBarColor(colorValues);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		initListener();
		super.onActivityCreated(savedInstanceState);
		initData();//初始化数据
	}

	/**
	 * 初始化控件
	 */
	public abstract View initView() ;

	/**
	 * 初始化监听
	 */
	protected abstract void initListener();
	/**
	 * 初始化数据
	 */
	public abstract void initData();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.mContext = context;
	}
}
