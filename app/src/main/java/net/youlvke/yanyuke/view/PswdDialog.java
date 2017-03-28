package net.youlvke.yanyuke.view;

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
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.R.id.iv_cancel;
import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class PswdDialog implements View.OnClickListener {

    private AlertDialog dialog;
    private InputMethodManager imm;
    private ImageView ivCancel;
    private GridPasswordView gpvContent;
    private TextView tvSetDepositPswd;
    private TextView tvAction;
    private TextView tvForgetDepositPswd;
    private boolean isFirst = true;
    private Window window;
    private Context mContext;
    private Boolean isSuccess = false;

    public PswdDialog(Context mContext) {
        this.mContext = mContext;
    }

    public Boolean showAlertPswdDialog() {

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
        return isSuccess;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case iv_cancel:  //返回关闭
                dialog.dismiss();
                break;
        }
    }

    private class MyOnPasswordChangedListener implements GridPasswordView.OnPasswordChangedListener {
        String firstPswd;

        @Override
        public void onTextChanged(String psw) {
        }

        @Override
        public void onInputFinish(String psw) {
            Log.d("onInputFinish", "onInputFinish: " + psw);
            //第一次 保存密码  清空重新输入
            if (isFirst) {
                tvAction.setVisibility(View.INVISIBLE);
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
    }
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
                                    isSuccess =true;
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
}
