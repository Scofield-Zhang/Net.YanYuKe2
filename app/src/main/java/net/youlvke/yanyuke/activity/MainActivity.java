package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.igexin.sdk.PushManager;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.fragment.HomeFragment;
import net.youlvke.yanyuke.fragment.MeFragment;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.service.YanyukeIntentService;
import net.youlvke.yanyuke.service.YanyukePushService;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import me.imid.swipebacklayout.lib.SwipeBackLayout;


public class MainActivity extends BaseActivity {

    private RadioGroup rg_home;//
    private FrameLayout fl_home;
    private ImageView ivCenter;
    private RadioButton rb_home;
    private BGABadgeRadioButton rbMe;
    private boolean isFirstExit = false;// 是否是第一次点退出
    private long FristTime = 0; //记录第一次点击的时间
    public static String cid;
    public static String access_key_id;
    public static String access_key_secret;
    public static String bucket;

    /**
     * 点击单选按钮监听
     *
     * @author Administrator
     */
    private class OnMainCheckedChangeListenerImplementation implements
            OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (checkedId) {
                case R.id.rb_home:
                    fragmentTransaction.replace(R.id.fl_home, new HomeFragment()).commit();
                    break;
                case R.id.rb_me:// 开启我的界面
                    fragmentTransaction.replace(R.id.fl_home, new MeFragment()).commit();
                    break;
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //初始化个推sdk
        PushManager.getInstance().initialize(this.getApplicationContext(), YanyukePushService.class);
        cid = PushManager.getInstance().getClientid(this);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), YanyukeIntentService.class);
        rg_home = (RadioGroup) findViewById(R.id.rg_home);
        fl_home = (FrameLayout) findViewById(R.id.fl_home);
        ivCenter = (ImageView) findViewById(R.id.iv_center);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rbMe = (BGABadgeRadioButton) findViewById(R.id.rb_me);
        initOss();
        forbidGestures();
    }

    @Override
    protected void initListener() {
        rg_home.setOnCheckedChangeListener(new OnMainCheckedChangeListenerImplementation());
        ivCenter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        rb_home.setChecked(true);
    }


    private void initOss() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKSYSTEM, createOSSParams())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("getAddress_tag", "onSuccess: " + response.get());
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                if (jsonObject.getInt("code") == 1) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    access_key_id = data.optString("ACCESS_KEY_ID");
                                    access_key_secret = data.optString("ACCESS_KEY_SECRET");
                                    bucket = data.optString("BUCKET");
                                    Log.d("getAddress_tag", "onSucc123ess: "+access_key_id+"\n"+access_key_secret+"\n"+bucket+"\n");
                                } else {
                                    ToastUtils.showToast(MainActivity.this, "数据解析异常");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createOSSParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160304");
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);

        switch (v.getId()) {
            case R.id.iv_center:
                startActivity(new Intent(this, GlobalShopActivity.class));
                break;
        }
    }

    private void forbidGestures() {
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEnableGesture(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    /**
     * 设置badgeView
     *//*
    public void setRadioButtonBadgeView(){
        rbMe.showCirclePointBadge();
//        rbMe.showTextBadge("10");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rbMe.showTextBadge("1");
            }
        }, 5000);
        rbMe.isShowBadge();
    }*/

    /**
     * 点击两次退出
     */
    @Override
    public void onBackPressed() {
        if (isFirstExit) {//不是第一次
            long SecondTime = System.currentTimeMillis();
            if (SecondTime - FristTime > 2000) {
                FristTime = System.currentTimeMillis();
                isFirstExit = true;
                ToastUtils.showToast(this, "再点一次退出！");
            } else {
                finish();
            }
        } else {//是第一次
            FristTime = System.currentTimeMillis();
            isFirstExit = true;
            ToastUtils.showToast(this, "再点一次退出！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 当界面销毁的时候清除所有的View
        rg_home.removeAllViews();
    }
}
