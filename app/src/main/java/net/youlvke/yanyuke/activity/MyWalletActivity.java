package net.youlvke.yanyuke.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jungly.gridpasswordview.GridPasswordView;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.TelphoneUtils;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.CircleImageView;
import net.youlvke.yanyuke.view.PswdDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.iv_cancel;
import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 我的钱包界面
 */
@SuppressLint("WrongViewCast")
public class MyWalletActivity extends BaseActivity {

    private CircleImageView ivHead;
    private RelativeLayout rlMyMoney;
    private ImageView back;
    private TextView tvRechargeRecord;
    private View tvShopRecod;
    private TextView tvManager;//管理界面
    private SharedPreferences sp;
    private TextView tvYuanyuMoneyNumber;
    private Context mContext;
    private TextView tvYuanyuName;
    private GridPasswordView gpvContent;
    private TextView tvSetDepositPswd;
    private TextView tvAction;
    private TextView tvForgetDepositPswd;
    private ImageView ivCancel;
    private AlertDialog dialog;
    private boolean isFirst = true;
    private Window window;
    private InputMethodManager imm;
    private boolean isSetPswd;
    private Intent intent;
    private double commission;


    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_me_mywallet;
    }


    @Override
    protected void initView() {
        ivHead = (CircleImageView) findViewById(R.id.iv_head);
        rlMyMoney = (RelativeLayout) findViewById(R.id.rl_my_money);
        back = (ImageView) findViewById(R.id.back);
        tvShopRecod = findViewById(R.id.tv_shop_record);
        tvRechargeRecord = (TextView) findViewById(R.id.tv_recharge_record);
        tvManager = (TextView) findViewById(R.id.tv_manager);
        tvYuanyuMoneyNumber = (TextView) findViewById(R.id.tv_yuanyu_money_number);
        tvYuanyuName = (TextView) findViewById(R.id.tv_yuanyu_name);
    }

    @Override
    protected void initListener() {
        rlMyMoney.setOnClickListener(this);
        back.setOnClickListener(this);
        tvRechargeRecord.setOnClickListener(this);
        tvShopRecod.setOnClickListener(this);
        tvManager.setOnClickListener(this);
        tvYuanyuMoneyNumber.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        intent = new Intent();
        loadData();
    }

    private void loadData() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createRechargeParams())
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("MyWalletActivity", "onSuccess: " + response.get());
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.optInt("code");
                                if (code == 1) {
                                    JSONObject data = jsonObject.optJSONObject("data");
                                    tvYuanyuMoneyNumber.setText(data.getString("yykCoin"));
                                    tvYuanyuName.setText(TelphoneUtils.subTel(data.optString("nickname")));
                                    commission = data.getDouble("commission");
                                    Glide.with(mContext)
                                            .load(data.optString("avatar"))
                                            .into(ivHead);
                                    if (data.optInt("empty") == 1) {
                                        Log.d("MyWalletActivity", "onSuccess: " + jsonObject.optInt("empty"));
                                        new PswdDialog(mContext);
                                        isSetPswd = false;
                                        //showAlertPswdDialog();
                                    }else {
                                        isSetPswd = true;
                                    }

                                } else {
                                    ToastUtils.showToast(mContext, "服务器开小差了！");
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

    /*private void showAlertPswdDialog() {

        dialog = new AlertDialog.Builder(mContext, R.style.theme_dialog).create();
        // 调用系统的输入法
        dialog.setView(new EditText(mContext));
        dialog.show();
        // 隐藏输入法
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_setting_diposit);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        ivCancel = (ImageView) window.findViewById(R.id.iv_cancel);
        gpvContent = (GridPasswordView) window.findViewById(R.id.gpv_content);
        tvSetDepositPswd = (TextView) window.findViewById(R.id.tv_set_deposit_pswd);
        tvAction = (TextView) window.findViewById(R.id.tv_action);
        tvForgetDepositPswd = (TextView) window.findViewById(R.id.tv_forget_deposit_pswd);
        ivCancel.setOnClickListener(this);
        gpvContent.setOnPasswordChangedListener(new MyOnPasswordChangedListener());

        dialog.setCanceledOnTouchOutside(false);
    }*/

    private void setPwsdParams(String pws) {
        try {
            IRequest.post(mContext, Constants.BASEURL_YYKUSER, creatPswdParas(pws)).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("setPwsdParams", "onSuccess: " + response.get());
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        if (jsonObject.getInt("code") == 1) {

                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            new Handler().postDelayed(new Runnable() {
                                private TextView tvSetAlterPswd;
                                @Override
                                public void run() {
                                    window.setContentView(R.layout.dialog_deposit_success);
                                    window.setGravity(Gravity.CENTER);
                                    window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    tvSetAlterPswd = (TextView) window.findViewById(R.id.tv_set_alter_pswd);
                                    tvSetAlterPswd.setText("密码设置成功");
                                    dialog.setCanceledOnTouchOutside(true);
                                }
                            }, 500);
                            ToastUtils.showToast(mContext, "成功！");
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

    private Map creatPswdParas(String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160137");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        params.put("password", pwd);
        return params;
    }

    public Map createRechargeParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160114");
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        return params;
    }

    //获取焦点的时候加载头像
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);

        switch (v.getId()) {
            case R.id.rl_my_money://我的佣金
                intent.setClass(mContext,MyMoneyActivity.class);
                intent.putExtra("isSetPswd",isSetPswd);
                intent.putExtra("commission",commission);
                startActivity(intent);
                break;
            case R.id.tv_recharge_record://充值记录
                intent.setClass(mContext,RechargeRecodeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_shop_record://购买记录
                intent.setClass(mContext,MyBuyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_manager:
                intent.setClass(mContext,MyManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.back:  //返回关闭
                finish();
                break;
            case iv_cancel:  //返回关闭
                dialog.dismiss();
                break;
            case R.id.tv_yuanyu_money_number:  //返回关闭
                intent.setClass(mContext,RechargeActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

   /* private class MyOnPasswordChangedListener implements GridPasswordView.OnPasswordChangedListener {
        String firstPswd;

        @Override
        public void onTextChanged(String psw) {
        }

        @Override
        public void onInputFinish(String psw) {
            Log.d("onInputFinish", "onInputFinish: " + psw);
            //第一次 保存密码  清空重新输入
            if (isFirst) {
                tvSetDepositPswd.setText("请设置提现密码");
                firstPswd = gpvContent.getPassWord();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gpvContent.clearPassword();
                    }
                }, 500);
                tvSetDepositPswd.setText("再次输入密码");
                isFirst = false;
            } else { //第二次
                String secondPswd = gpvContent.getPassWord();
                Log.d("onInputFinish", "onInputFinish: " + secondPswd + "<<<<<<<" + firstPswd);
                if (secondPswd.equals(firstPswd)) {
                    setPwsdParams(secondPswd);
                    //设置提现密码
                } else {
                    tvAction.setVisibility(View.VISIBLE);
                    tvAction.setText("两次输入密码不一致，请重新设置");
                    gpvContent.clearPassword();
                    firstPswd = null;
                    tvSetDepositPswd.setText("请重新设置密码");
                    isFirst = true;
                }
            }
        }
    }*/
}
