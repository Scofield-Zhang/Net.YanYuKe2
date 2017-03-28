package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.CountDownTimerUtils;
import net.youlvke.yanyuke.utils.ShowSettingAndAlterPwdDialog;
import net.youlvke.yanyuke.utils.TelphoneUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.btn_test_code;
import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 我的管理界面
 */
public class MyManagerActivity extends BaseActivity {

    private TextView tv_title;
    private RelativeLayout rlAlterDepositPswd;
    private SharedPreferences sp;
    private AlertDialog dialog;
    private TextView tvBoundNum;
    private EditText etTestCode;
    private Button btnTestCode;
    private TextView tvAction;
    private String testCode;
    private Boolean isLock = false;
    private TextView tvFeferralCode;
    private TextView tvReferrer;
    private AlertDialog dialog1;
    private Context mContext;
    private String phone;
    private String message;
    private ShowSettingAndAlterPwdDialog showSettingAndAlterPwdDialog;


    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_my_manager;
    }

    @Override
    protected void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        rlAlterDepositPswd = (RelativeLayout) findViewById(R.id.rl_alter_deposit_pswd);
        tvFeferralCode = (TextView) findViewById(R.id.tv_referralCode);
        tvReferrer = (TextView) findViewById(R.id.tv_referrer);

    }

    @Override
    protected void initListener() {
        tv_title.setOnClickListener(this);
        rlAlterDepositPswd.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadData();
        sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        tv_title.setText("管理");
    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createManagerParams())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("MyManagerActivity", "onSuccess: " + response.get());
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.optInt("code");
                                if (code == 1) {
                                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                    tvFeferralCode.setText(jsonObject1.optString("referralCode"));
                                    if (jsonObject1.optString("referees") != null) {
                                        tvReferrer.setText(TelphoneUtils.subTel(jsonObject1.optString("referees")));
                                    }
                                    phone = jsonObject1.getString("phone");

                                } else {
                                    ToastUtils.showToast(MyManagerActivity.this, "服务器出现异常");
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

    private Map createManagerParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160118");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.rl_alter_deposit_pswd://显示修改弹窗
                showSettingAndAlterPwdDialog = new ShowSettingAndAlterPwdDialog(mContext);
                dialog = new AlertDialog.Builder(this, R.style.theme_dialog).create();
                showSettingAndAlterPwdDialog.showAlterManagerDialog(dialog,false);
                break;
            case R.id.iv_cancel:  //返回关闭
                dialog.dismiss();
                break;
            case R.id.tv_forget_deposit_pswd:  //忘记密码短息验证
                showGetVerificationCodeDialog();
                break;
            case btn_test_code:
                CountDownTimerUtils cdtu = new CountDownTimerUtils(btnTestCode, 60000, 1000);
                cdtu.start();
                //获取验证码
                getTestCode();
                dialog.dismiss();
                break;
        }
    }
    //20160301




    private void getTestCode() {
        try {
            IRequest.post(mContext,Constants.BASEURL_YYKSYSTEM,createGetCodeParams()).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        int code = jsonObject.optInt("code");
                        if (code==1) {
                            message =  jsonObject.optString("message");
                            Log.d("getTestCode", "onSuccess: "+message);
                        }else if (code ==2){
                            ToastUtils.showToast(mContext,"验证码失效");
                        }else{
                            ToastUtils.showToast(mContext,"验证码错误");
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
    private Map createGetCodeParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160301");
        params.put("phone", phone);
        params.put("type","2");
        return params;
    }

    /**
     *验证码
     */
    private void validationPswd(String testCode) {
        try {
            IRequest.post(mContext,Constants.BASEURL_YYKSYSTEM,createValidationParams(testCode)).execute(new RequestListener() {

                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        int code = jsonObject.optInt("code");
                        if (code==1) {
                            dialog1.dismiss();
                            dialog.show();
                            showSettingAndAlterPwdDialog.showAlterManagerDialog(dialog,true);

                        }else if (code ==2){
                            ToastUtils.showToast(mContext,"验证码失效");
                        }else{
                            ToastUtils.showToast(mContext,"验证码错误");
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

    private Map createValidationParams(String message) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160302");
        params.put("phone", phone);
        params.put("code",message);
        return params;
    }


    /**
     * 显示获取验证码界面
     */
    private void showGetVerificationCodeDialog() {

        dialog1 = new AlertDialog.Builder(this, R.style.theme_dialog).create();
        // 调用系统的输入法
        dialog1.setView(new EditText(this));
        dialog1.show();
        Window window = dialog1.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_forget_pwd);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        tvBoundNum = (TextView) window.findViewById(R.id.tv_bound_num);
        etTestCode = (EditText) window.findViewById(R.id.et_test_code);
        btnTestCode = (Button) window.findViewById(R.id.btn_test_code);
        tvAction = (TextView) window.findViewById(R.id.tv_action);
        btnTestCode.setOnClickListener(this);
        testCode = etTestCode.getText().toString().trim();
        etTestCode.addTextChangedListener(new MyTextWatcher());
        etTestCode.setOnKeyListener(new MyOnKeyListener());
        dialog1.setCanceledOnTouchOutside(true);
    }

    /**
     * 文字输入监听
     */
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (charSequence.length() == 6 && message!= null) {
                    validationPswd(charSequence.toString());
                return;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
//                ToastUtils.showToast(MyManagerActivity.this,"正在进行联网验证！___"+editable.toString());
        }
    }

    /**
     * 屏蔽确定按钮
     */
    private class MyOnKeyListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int event, KeyEvent keyEvent) {
            if (event == KeyEvent.KEYCODE_ENTER) {
                if (testCode.length() < 6)
                    ToastUtils.showToast(MyManagerActivity.this, "请正确输入验证码");
                return true;
            }
            return false;
        }
    }
}
