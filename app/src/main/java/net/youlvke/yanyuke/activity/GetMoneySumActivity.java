package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.BankInfo;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.tv_next;
import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * 提现金额界面
 */
public class GetMoneySumActivity extends BaseActivity {


    private int imageInfo;
    private TextView tvTitle;
    private ImageView ivBankIcon;
    private TextView tvBankName;
    private TextView tvBankCard;

    private Context mContext;
    private ImageView ivCancel;
    private GridPasswordView gpvContent;
    private TextView tvSetDepositPswd;
    private TextView tvAction;
    private TextView tvForgetDepositPswd;
    private int errorNum;
    private Boolean isLock;
    private int validationNum = 1;
    private Window window;
    public ImageView ivValidationResult;
    public boolean isForget;
    private InputMethodManager imm;
    //public AlertDialog dialog;
    private int typePswd;
    protected boolean isFirst = true;
    protected String firstPswd;
    protected boolean isValidation = true;
    private BankInfo bankInof;
    private String bankName;
    private EditText tvMoneyCount;
    private TextView tvNext;
    private AlertDialog dialog;

    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_get_money_sum;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBankIcon = (ImageView) findViewById(R.id.iv_bank_icon);
        tvBankName = (TextView) findViewById(R.id.tv_bank_name);
        tvBankCard = (TextView) findViewById(R.id.tv_bank_card);
        tvMoneyCount = (EditText) findViewById(R.id.tv_money_count);
        tvNext = (TextView) findViewById(R.id.tv_next);
    }

    @Override
    protected void initListener() {
        tvNext.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        tvTitle.setText("提现金额");
        bankInof = (BankInfo) getIntent().getSerializableExtra("bank_Inof");
        imageInfo = bankInof.bankIconInfo;
        parserBankInfo();
        tvBankCard.setText(String.valueOf(bankInof.cardNum));
    }

    private void loadData(String pswd) {
        try {
            IRequest.post(this, Constants.BASEURL_YYKUSER, createRechargeParams(pswd)).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        if (jsonObject.getInt("code") == 1) {
                            Intent intent = new Intent();
                            intent.setClass(mContext, WithdrawalSuccess.class);
                            intent.putExtra("cardNum", bankInof.cardNum);
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(mContext, "提现失败");
                            isValidation = false;
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

    /**
     * 20160129
     * 请求参数
     * userId
     * applyAmount      申请提现金额
     * applyAccount     转至账户
     * applyWay         提现路径1、支付宝     2、微信    3、银联卡
     * applyPassword    提现密码
     * sessionToken     提现银行
     * applybank        提现银行
     * applyname        卡持有人名字
     */
    public Map createRechargeParams(String pswd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160129");
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", session);
        params.put("applyAmount", tvMoneyCount.getText().toString().trim());  //申请提现金额
        params.put("applyAccount", String.valueOf(bankInof.cardNum)); //转至账户
        params.put("applyWay", "3"); //提现路径1、支付宝     2、微信    3、银联卡
        params.put("applybank", bankName); //提现路径1、支付宝     2、微信    3、银联卡
        params.put("applyname", bankInof.name); //提现路径1、支付宝     2、微信    3、银联卡
        params.put("applyPassword", pswd);
        Log.d("createRechargeParams", "applyPassword" + "<<" + pswd + "applybank" + "<<<" + bankName + "applyname" + "<<<" + bankInof.name);
        return params;
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case tv_next:
                showAlterManagerDialog();
                break;
        }
    }

    private void parserBankInfo() {

        switch (imageInfo) {
            case 0:
                ivBankIcon.setImageDrawable(getResources().getDrawable(R.mipmap.img_ny));
                bankName = "农业银行";
                tvBankName.setText("农业银行");
                break;
            case 1:
                ivBankIcon.setImageDrawable(getResources().getDrawable(R.mipmap.img_js));
                tvBankName.setText("");
                bankName = "建设银行";
                break;
            case 2:
                ivBankIcon.setImageDrawable(getResources().getDrawable(R.mipmap.img_gs));
                tvBankName.setText("工商银行");
                bankName = "工商银行";
                break;
            case 3:
                ivBankIcon.setImageDrawable(getResources().getDrawable(R.mipmap.img_zg));
                tvBankName.setText("中国银行");
                bankName = "中国银行";
                break;
        }
    }

    public Boolean showAlterManagerDialog() {

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
        tvForgetDepositPswd.setOnClickListener((View.OnClickListener) mContext);
        ivCancel.setOnClickListener((View.OnClickListener) mContext);
        gpvContent.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {

            @Override
            public void onTextChanged(String psw) {
            }

            @Override
            public void onInputFinish(String psw) {
                validationNum++;
                //1.得到密码联网验证
                if (isValidation) {  //密码验证成功进入设置密码接口
                    //setPswd();
                    tvForgetDepositPswd.setVisibility(View.INVISIBLE);
                    tvAction.setVisibility(View.INVISIBLE);
                } else {

                    if (validationNum < 3) {//验证的次数小于3


                        tvAction.setVisibility(View.VISIBLE);
                        int chance = 4 - validationNum;
                        tvAction.setText("密码输入错误,您还有" + chance + "次机会");
                        deExcuse();
                    } else {//验证的次数大于3，设置标志提示用户
                        showResultDialog("超过规定的次数！", R.mipmap.success);
                        // dialog.dismiss();
                    }
                }
                loadData(gpvContent.getPassWord());
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        return isLock;
    }

    /**
     * 设置密码
     */
    public void setPswd() {
        String secondPswd;
        if (isFirst) {     //第一次 保存密码  清空重新输入
            firstPswd = gpvContent.getPassWord();
            tvAction.setVisibility(View.INVISIBLE);
            deExcuse();
            tvSetDepositPswd.setText("请再次输入密码");
            isFirst = false;
        } else { //第二次
            secondPswd = gpvContent.getPassWord();
            if (secondPswd.equals(firstPswd)) {
                // 设置密码  密码设置成功
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                changePswSuccess(1, "20160120", secondPswd);
            } else {
                tvAction.setVisibility(View.VISIBLE);
                tvAction.setText("两次输入密码不一致，请重新设置");
                gpvContent.clearPassword();
                firstPswd = null;
                tvSetDepositPswd.setText("请重新输入密码");
                isFirst = true;
            }
        }
    }

    /**
     * 延时执行
     */
    private void deExcuse() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gpvContent.clearPassword();
            }
        }, 200);
    }

    /**
     * 修改密码
     */
    private void changePswSuccess(int type, String act, String secondPswd) {
        setPwsdParams(type, act, secondPswd);
    }

    private void setPwsdParams(final int promptType, String act, String pws) {
        try {
            IRequest.post(mContext, Constants.BASEURL_YYKUSER, creatPswdParas(act, pws)).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("setPwsdParams", "onSuccess: " + response.get());
                    try {
                        JSONObject jsonObject = new JSONObject(response.get());
                        if (jsonObject.getInt("code") == 1) {
                            if (promptType == 0) {//密码验证成功
                                isValidation = true;
                                deExcuse();
                            } else if (promptType == 1) {
                                showResultDialog("密码修改成功", R.mipmap.success);
                            }
                        } else {
                            if (promptType == 0) {
                                tvForgetDepositPswd.setVisibility(View.VISIBLE);
                               /* //显示忘记密码  isForget = true;*/
                                deExcuse();//清空再次输入
                            } else if (promptType == 1) {
                                ToastUtils.showToast(mContext, "修改失败");
                            }
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

    /**
     * 验证提现密码
     *
     * @param pwd
     * @return
     */
    private Map creatPswdParas(String act, String pwd) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", act);
        params.put("sessionToken", session);
        params.put("userId", String.valueOf(userId));
        params.put("password", pwd);
        return params;
    }

    private void showResultDialog(final String resultText, final int PicId) {
        new Handler().postDelayed(new Runnable() {
            private TextView tvSetAlterPswd;

            @Override
            public void run() {
                window.setContentView(R.layout.dialog_deposit_success);
                window.setGravity(Gravity.CENTER);
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvSetAlterPswd = (TextView) window.findViewById(R.id.tv_set_alter_pswd);
                ivValidationResult = (ImageView) window.findViewById(R.id.iv_validation_result);
                ivValidationResult.setBackgroundResource(PicId);
                tvSetAlterPswd.setText(resultText);
                dialog.setCanceledOnTouchOutside(true);
            }
        }, 300);
    }
}
