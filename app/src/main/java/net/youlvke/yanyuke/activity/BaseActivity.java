package net.youlvke.yanyuke.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.youlvke.yanyuke.R;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 基类的抽取
 */
public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener {

    // 共享资源
    private static List<BaseActivity> activities = new ArrayList<BaseActivity>();
    public static Activity activity;
    private SwipeBackLayout mSwipeBackLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        setContentView(getLayoutResId());//把设置布局文件的操作交给继承的子类

        mSwipeBackLayout = getSwipeBackLayout();
        synchronized (activities) {
            activities.add(this);
        }

        /** 初始化控件 */
        initView();
        /**  初始化监听 */
        initListener();
        /** 初始化数据 */
        initData();
        /**处理相同逻辑 */
        dealCommon();
    }

    /**
     * 添加控件
     */
    public abstract int getLayoutResId();
    /**
     * 初始化View
     */
    protected abstract void initView();
    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化操作
     */
    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (activities) {
            activities.remove(this);
        }
    }

    /**
     * 自杀
     */
    public void killAll() {
        List<BaseActivity> copy;
        synchronized (activities) {
            copy = new ArrayList<BaseActivity>(activities);
        }
        // 遍历关闭所有Activity
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死所在进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 处理相同逻辑
     */
    private void dealCommon() {

        View btn = findViewById(R.id.back);  // 如果没有限定子Activity的返回按钮的类型
        if (btn != null) {
            // 如果actvity内部没有返回按钮
            btn.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                //返回按钮
                finish();
                break;
            default:
                onInnerClick(v);
                break;
        }
    }

    /**
     * 处理非返回事件
     * @param v
     */
    protected void onInnerClick(View v) {

    }


}