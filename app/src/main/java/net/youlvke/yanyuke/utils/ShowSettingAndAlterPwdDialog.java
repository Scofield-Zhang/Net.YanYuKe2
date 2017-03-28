package net.youlvke.yanyuke.utils;

import android.app.AlertDialog;
import android.content.Context;
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
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;

/**
 * Created by Administrator on 2016/11/10 0010.
 *
 * @author zhangtao
 */

public class ShowSettingAndAlterPwdDialog {
    private Context mContext;
    private ImageView ivCancel;
    private GridPasswordView gpvContent;
    private TextView tvSetDepositPswd;
    private TextView tvAction;
    private TextView tvForgetDepositPswd;
    private int errorNum;
    private Boolean isLock;
    private boolean isValidation;//是否验证成功
    private int validationNum = 1;
    private Window window;
    public ImageView ivValidationResult;
    public boolean isForget;
    private InputMethodManager imm;
    public AlertDialog dialog;
    private int typePswd;

    public ShowSettingAndAlterPwdDialog(Context mContext) {
        this.mContext = mContext;
    }

    /**

     */
    public Boolean showAlterManagerDialog(AlertDialog dialog, final Boolean isValidation ) {
        this.dialog = dialog;
        this.isValidation =isValidation;

//        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.theme_dialog).create();
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

                //1.得到密码联网验证

                if (isValidation) {  //密码验证成功进入设置密码接口
                    setPswd();
                    tvForgetDepositPswd.setVisibility(View.INVISIBLE);
                    tvAction.setVisibility(View.INVISIBLE);
                } else {
                    if (validationNum < 3) {//验证的次数小于3
                        //延迟执行
                        setPwsdParams(0, "20160119", gpvContent.getPassWord());
                        validationNum++;
                        tvAction.setVisibility(View.VISIBLE);
                        int chance = 4 - validationNum;
                        tvAction.setText("密码输入错误,您还有" + chance + "次机会");

                    } else {//验证的次数大于3，设置标志提示用户
                        showResultDialog("超过规定的次数！", R.mipmap.success);

                        // dialog.dismiss();
                    }

                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        return isLock;
    }

    protected boolean isFirst = true;
    protected String firstPswd;

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
                //密码上传服务器
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
        }, 300);
    }

    /**
     * 修改密码成功
     *
     * @param type
     * @param act
     * @param secondPswd
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
                               /* //显示忘记密码
                                isForget = true;*/
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
}
