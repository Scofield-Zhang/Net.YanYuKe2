package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.CountDownTimerUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.MyToggleBtn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.iv_qq;
import static net.youlvke.yanyuke.R.id.iv_wechat;
import static net.youlvke.yanyuke.R.id.iv_weibo;
import static net.youlvke.yanyuke.R.id.tv_invite_code;
import static net.youlvke.yanyuke.activity.MainActivity.cid;
import static net.youlvke.yanyuke.utils.Constants.BASEURL_YYKSYSTEM;
import static net.youlvke.yanyuke.utils.Constants.BASEURL_YYKUSER;


/**
 * 登陆注册界面
 *
 * @author Administrator
 */
public class LoginAndRegisterActivity extends BaseActivity {

    private ImageView ivQq;
    private ImageView ivWechat;
    private ImageView ivWeibo;
    private String appid;
    private String appsecret;
    private String appkey;
    private EditText etReferees;
    private TextView tvProtocol;
    public static String sessiontoken;
    private int Third_Login_Type;

    /**
     * 开关状态改变的监听
     *
     * @author Administrator
     */
    private final class OnMyBtnStateChangedListener implements
            MyToggleBtn.OnStateChangedListener {

        @Override
        public void onStateChanged(boolean state) {
            fl_log_reg.removeAllViews();

            if (state == true) {
                ToastUtils.showToast(LoginAndRegisterActivity.this, "登录");

                loadLoginPager();// 加载登录界面

            } else if (state == false) {

                ToastUtils.showToast(LoginAndRegisterActivity.this, "注册");
                registerView = View.inflate(LoginAndRegisterActivity.this,
                        R.layout.register_step1, null);
                tvTel = (EditText) registerView.findViewById(R.id.tv_tel);
                etReferees = (EditText) registerView.findViewById(tv_invite_code);
                tvTestCode = (EditText) registerView
                        .findViewById(R.id.tv_test_code);
                btnTestCode = (Button) registerView.findViewById(R.id.btn_test_code);
                protocol = (Button) registerView.findViewById(R.id.protocol);
                registerOne = (Button) registerView.findViewById(R.id.register_one);
                tvInviteCode = findViewById(R.id.tv_invite_code);
                tvProtocol = (TextView) registerView.findViewById(R.id.tv_protocol);
                protocol.setOnClickListener(LoginAndRegisterActivity.this);
                btnTestCode.setOnClickListener(LoginAndRegisterActivity.this);
                registerOne.setOnClickListener(LoginAndRegisterActivity.this);
                tvProtocol.setOnClickListener(LoginAndRegisterActivity.this);
                fl_log_reg.addView(registerView);
            }
        }
    }

    private MyToggleBtn btnLoginRegister;
    private int resultCode = 0;
    private FrameLayout fl_log_reg;
    private View loginMe;// 登陆界面
    private View registerView;// 注册界面
    private Intent intent;
    private EditText userName;
    private EditText userPswd;
    private SharedPreferences sp;
    private Button btnLogin;
    private Button registerOne;// 注册第一步
    private String checkText1;
    private EditText tvTel;// 电话号码输入框
    private EditText tvTestCode;// 验证码输入框
    private Button btnTestCode;// 验证码按钮
    private EditText tv_pwsd1;
    private EditText tv_pwsd2;
    private CheckBox btnArrows;
    private boolean isPressed;//开关状态
    private LinearLayout llThirdLogin;
    private Button protocol2;//协议对象
    private boolean isAgreeProtocol;//协议标签
    private Button btnNext;
    private Button protocol;
    private View tvInviteCode;//邀请码


    @Override
    public int getLayoutResId() {
        return R.layout.activity_me_detial;
    }


    @Override
    protected void initView() {
        parseManifests();

       /* PushManager.getInstance().initialize(this.getApplicationContext(), YanyukePushService.class);
        cid = PushManager.getInstance().getClientid(this);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), YanyukeIntentService.class);*/
        intent = new Intent();
        sp = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        this.setResult(resultCode, intent);
        btnLoginRegister = (MyToggleBtn) findViewById(R.id.btn_login_register);
        fl_log_reg = (FrameLayout) findViewById(R.id.fl_log_reg);
        loadLoginPager();// 记载登录界面
    }

    @Override
    protected void initListener() {
        btnLoginRegister.setOnStateChangedListener(new OnMyBtnStateChangedListener());
        ivQq.setOnClickListener(this);
        ivWechat.setOnClickListener(this);
        ivWeibo.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void parseManifests() {
        String packageName = getApplicationContext().getPackageName();
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                appid = appInfo.metaData.getString("PUSH_APPID");
                appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
                appkey = appInfo.metaData.getString("PUSH_APPKEY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载登录界面
     */
    private void loadLoginPager() {
        loginMe = View.inflate(this, R.layout.login_me, null);
        btnLogin = (Button) loginMe.findViewById(R.id.btn_login);
        btnArrows = (CheckBox) loginMe.findViewById(R.id.btn_arrows);
        userName = (EditText) loginMe.findViewById(R.id.user_name);
        userPswd = (EditText) loginMe.findViewById(R.id.user_pwsd);
        ivQq = (ImageView) loginMe.findViewById(iv_qq);
        ivWechat = (ImageView) loginMe.findViewById(iv_wechat);
        ivWeibo = (ImageView) loginMe.findViewById(iv_weibo);
        llThirdLogin = (LinearLayout) loginMe.findViewById(R.id.ll_third_login);
        // setCheckData(userPswd, "pswd");
        btnLogin.setOnClickListener(this);
        btnArrows.setOnClickListener(this);
        fl_log_reg.addView(loginMe);
    }

    /**
     * 检查数据
     */
    private boolean setCheckData(EditText text1, EditText text2) {
        checkText1 = text1.getText().toString().trim();
        String checkText2 = text2.getText().toString().trim();
        if (!TextUtils.isEmpty(checkText1) && !TextUtils.isEmpty(checkText2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取验证码
     */
    private Map createCodeParams() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160301");
        params.put("type", "1");
        params.put("phone", tvTel.getText().toString().trim());
        return params;
    }

    //
    private Map createParams() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160302");
        params.put("code", tvTestCode.getText().toString().trim());
        params.put("phone", tvTel.getText().toString().trim());
        return params;
    }

    private Map createRegistertParams() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160101");
        params.put("referees", etReferees.getText().toString().trim());
        params.put("password", tv_pwsd2.getText().toString().trim());
        params.put("phone", tvTel.getText().toString().trim());
        return params;
    }

    /**
     * 登录接口
     */
    private Map createLoginParams() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160102");
        params.put("password", userPswd.getText().toString().trim());
        params.put("phone", userName.getText().toString().trim());
        params.put("deviceToken", cid);
        return params;
    }


    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.btn_login:

                if (setCheckData(userName, userPswd)) {
                    try {
                        IRequest.post(this, BASEURL_YYKUSER, createLoginParams())
                                .execute(new RequestListener() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        Log.d("register_onsuccess", response.get());
                                        try {
                                            JSONObject json = new JSONObject(response.get());
                                            int code = json.getInt("code");
                                            switch (code) {
                                                case 1:
                                                    JSONObject data = json.getJSONObject("data");
                                                    int userId = data.getInt("userId");
                                                    sp.edit().putInt("userId", userId).apply();
                                                    sp.edit().putString("username", checkText1).apply();
                                                    JSONObject usersession = json.getJSONObject("usersession");
                                                    sessiontoken = usersession.getString("sessionToken");
                                                    Log.d("SESSIONTOKEN", "onSuccess: " + sessiontoken);
                                                    sp.edit().putString("session", sessiontoken).apply();
                                                    LoginAndRegisterActivity.this.setResult(resultCode, intent);
                                                    ToastUtils.showToast(LoginAndRegisterActivity.this, json.get("message").toString());
                                                    finish();
                                                    break;
                                                case 2:
                                                    userPswd.setText("");
                                                    userName.setText("");
                                                    ToastUtils.showToast(LoginAndRegisterActivity.this, "您的手机号码尚未注册");
                                                    break;
                                                case 3:
                                                    userPswd.setText("");
                                                    ToastUtils.showToast(LoginAndRegisterActivity.this, "您的账号、密码错误");
                                                    break;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("yichang", "onInnerClick: " + e.toString());
                    }
                } else {
                    showToastPrompt("账号密码不能为空");
                }

                break;
            case R.id.register_one:// 注册下一步
//                fl_log_reg.removeViewAt(0);
                if (setCheckData(tvTel, tvTestCode)) {// 第二部
                    try {
                        IRequest.post(LoginAndRegisterActivity.this, BASEURL_YYKSYSTEM, createParams()).execute(new RequestListener() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.get();
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int code = jsonObject.getInt("code");
                                    switch (code) {
                                        case 1:
                                            final View registerTwo = View.inflate(LoginAndRegisterActivity.this, R.layout.register_step2, null);
                                            initRegiserStep2(registerTwo);
                                            fl_log_reg.addView(registerTwo);
                                            break;
                                        case 2:
                                            ToastUtils.showToast(LoginAndRegisterActivity.this, "验证码无效");
                                            break;
                                        case 3:
                                            ToastUtils.showToast(LoginAndRegisterActivity.this, "验证码失效");
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showToastPrompt("电话号码或验证码不能为空");
                }
                break;

            case R.id.btn_test_code:// 验证码
                if (!TextUtils.isEmpty(tvTel.getText().toString().trim())) {
                    CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(
                            btnTestCode, 60000, 1000);
                    countDownTimerUtils.start();
                    try {
                        IRequest.post(this, BASEURL_YYKSYSTEM, createCodeParams()
                        ).execute(new RequestListener() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                try {
                                    JSONObject parser = new JSONObject(response.get());
                                    int code = parser.getInt("code");
                                    switch (code) {
                                        case 2:
                                            ToastUtils.showToast(LoginAndRegisterActivity.this, "该用户已注册");
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                                super.onFailed(what, url, tag, exception, responseCode, networkMillis);
                                ToastUtils.showToast(LoginAndRegisterActivity.this, responseCode + exception.toString());
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("tag", "json构建失败");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast(LoginAndRegisterActivity.this, "请输入手机号");
                }
                break;

            case R.id.btn_register_next:// 下一步

                if (setCheckData(tv_pwsd1, tv_pwsd2)) {
                    String user_pswd1 = tv_pwsd1.getText().toString().trim();
                    String user_pswd2 = tv_pwsd2.getText().toString().trim();
                    if (user_pswd1.equals(user_pswd2)) {

                        try {
                            IRequest.post(LoginAndRegisterActivity.this, BASEURL_YYKUSER, createRegistertParams())
                                    .execute(new RequestListener() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            Log.d("onSuccess", "onSuccess: " + response.get());
                                            try {
                                                JSONObject json = new JSONObject(response.get());
                                                int code = json.getInt("code");
                                                switch (code) {
                                                    case 1://登录成功
                                                        String referralCode = json.optString("referralCode");
                                                        Intent intent = new Intent();
                                                        intent.setClass(LoginAndRegisterActivity.this, LoginSuccess.class);
                                                        intent.putExtra("referralCode", referralCode);
                                                        startActivity(intent);
                                                        finish();
                                                        break;
                                                    case 2:
                                                        View registerThree1 = View.inflate(
                                                                LoginAndRegisterActivity.this,
                                                                R.layout.register_step1, null);
                                                        ToastUtils.showToast(LoginAndRegisterActivity.this, "该手机号已注册");
                                                        fl_log_reg.addView(registerThree1);
                                                        break;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.d("JSONException", e.toString());
                                            }
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastPrompt("两次输入的密码不一致");
                        tv_pwsd1.setText("");
                        tv_pwsd2.setText("");
                    }
                    return;
                } else {
                    showToastPrompt("密码不能为空");
                }
                break;
            case R.id.btn_exit://退出
                finish();
                break;
            case R.id.btn_arrows:
                if (btnArrows.isChecked()) {
                    llThirdLogin.setVisibility(View.VISIBLE);
                } else {
                    llThirdLogin.setVisibility(View.GONE);
                }
                break;
            case R.id.protocol2:
                isConfirmProtocol(protocol2, btnNext);
                break;
            case R.id.protocol:
                isConfirmProtocol(protocol, registerOne);
                break;
            case R.id.iv_qq://QQ登录
                Third_Login_Type = 2;
                thirdLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_wechat://微信登录
                Third_Login_Type = 1;
                thirdLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_weibo://微博登录
                Third_Login_Type = 3;
                thirdLogin(SHARE_MEDIA.SINA);
            case R.id.tv_protocol:
                startActivity(new Intent(this, ProtocolActivity.class));
                break;
        }

    }

    private void thirdLogin(SHARE_MEDIA platform) {
        //boolean isauth = UMShareAPI.get(this).isAuthorize(this,platform);
        UMShareAPI.get(this).getPlatformInfo(this, platform, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            String temp = "";
            for (String key : map.keySet()) {
                temp = temp + key + " : " + map.get(key) + "\n";
            }
            Log.d("authListener", "onComplete: " + temp);
            ThirdLogin(map);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtils.showToast(LoginAndRegisterActivity.this, throwable.getMessage());
            Log.d("onError", "onError: " + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    private void ThirdLogin(Map<String, String> map) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createThirdLogParams(map))
                    .loading(true)
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("ThirdLogin", "onSuccess: " + response.get());
                            ToastUtils.showToast(LoginAndRegisterActivity.this, "第三方登录成功！");
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createThirdLogParams(Map<String, String> map) {


        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160139");
        params.put("authorType", "1");
        params.put("openid", map.get("openid"));
        params.put("wayType", String.valueOf(Third_Login_Type));
        params.put("nickname", map.get("name"));
        params.put("avatar", map.get("iconurl"));
        params.put("userCid", map.get("uid"));
        params.put("deviceToken", cid);
        return params;
    }

    /**
     * @param btn
     * @param btn1 下一步按钮
     */
    private void isConfirmProtocol(Button btn, Button btn1) {
        if (isAgreeProtocol) {
            btn.setBackgroundResource(R.mipmap.ring_nornal);
            btn1.setClickable(true);
            btn1.setPressed(false);
        } else {
            btn.setBackgroundResource(R.mipmap.ring_press);
            btn1.setClickable(false);
            btn1.setPressed(true);
        }
        isAgreeProtocol = !isAgreeProtocol;
    }


    /**
     * 第三方回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 初始化第二部
     *
     * @param registerTwo
     */
    private void initRegiserStep2(View registerTwo) {
        tv_pwsd1 = (EditText) registerTwo.findViewById(R.id.tv_pwsd1);
        tv_pwsd2 = (EditText) registerTwo.findViewById(R.id.tv_pwsd2);
        protocol2 = (Button) registerTwo.findViewById(R.id.protocol2);
        protocol2.setOnClickListener(this);
        btnNext = (Button) registerTwo.findViewById(R.id.btn_register_next);
        btnNext.setOnClickListener(LoginAndRegisterActivity.this);
    }

    /**
     * 提示显示信息
     *
     * @param text
     */
    private void showToastPrompt(String text) {
        Toast.makeText(LoginAndRegisterActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
