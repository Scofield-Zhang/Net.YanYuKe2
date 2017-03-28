package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.NetWorkUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.imid.swipebacklayout.lib.SwipeBackLayout;


/**
 * 闪屏界面
 */
public class SplashActivity extends BaseActivity {


    private RelativeLayout activitySplashActivity;
    private boolean isNetWork;
    private TextView tvVersion;
    private TelephonyManager tm;
    public static int userId;
    private SharedPreferences sp;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash_actvity;
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //取消活动关闭
        forbidGestures();
        isFirst();
        userId = getSharedPreferences("loginInfo",MODE_PRIVATE).getInt("useId",0);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        isNetWork = NetWorkUtils.checkEnable(this);
        splash();
        //initOss();

    }

    private void isFirst() {
        sp = getSharedPreferences("loginstatus",MODE_PRIVATE);
        sp.edit().putString("isFirst","1").apply();
    }


    //20160305
    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKSYSTEM, createParams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonOject = new JSONObject(response.get());
                        int code = jsonOject.optInt("code");
                        if (code == 1 ){
                            //需要更新
                        }else if(code == 2){
                            //不需要更新
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

    private Map createParams() {

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160305");
        params.put("accessType", "1");
       // params.put("appMarket", BuildConfig.FLAVOR);
        params.put("appMarket", "360");
        params.put("appVersion", getVersionInfo()[0]);
        params.put("deviceId", tm.getDeviceId());
        params.put("deviceModel", Build.MODEL);
        params.put("deviceOs", Build.VERSION.RELEASE);
        params.put("firstLaunch", sp.getString("isFirst", "1"));
        params.put("remoteIp", NetWorkUtils.getLocalIpAddress(this));
        params.put("screenWidth", String.valueOf(getScreenInfo()[0]));
        params.put("screenHeight", String.valueOf(getScreenInfo()[1]));
        params.put("userId", String.valueOf(userId));

        Log.d("createParams", "createParams: "+ Build.MODEL+Build.VERSION.RELEASE);
        return params;
    }

    /**
     * 闪屏逻辑
     */
    private void splash() {
        activitySplashActivity = (RelativeLayout) findViewById(R.id.activity_splash_actvity);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        activitySplashActivity.startAnimation(alphaAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    /**
     * 屏蔽删除
     */
    private void forbidGestures() {
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEnableGesture(false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        loadData();
        tvVersion.setText(String.format("V%s版",getVersionInfo()[1]));

    }
    /** 获取版本信息 */
    private String[] getVersionInfo() {
        String[] versionInfo=  new String[2];
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(SplashActivity.this.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            versionInfo[0] = String.valueOf(versionCode);
            versionInfo[1] = String.valueOf(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionInfo;
    }
    /** 获取屏幕信息 */
    private int[] getScreenInfo(){
        int[] screenInfo = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenInfo[0] = dm.widthPixels;
        screenInfo[1] = dm.heightPixels;
        return screenInfo;
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
                                    /*access_key_id = data.optString("ACCESS_KEY_ID");
                                    access_key_secret =  data.optString("ACCESS_KEY_SECRET");
                                    bucket = data.optString("BUCKET");*/
                                    //ToastUtils.showToast(SplashActivity.this,"数据解析异常"+access_key_id);
                                } else {
                                    ToastUtils.showToast(SplashActivity.this,"数据解析异常");
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
}
