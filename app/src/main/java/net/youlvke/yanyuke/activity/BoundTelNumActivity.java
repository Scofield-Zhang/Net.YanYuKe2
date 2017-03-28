package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.fragment.MeFragment;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.CountDownTimerUtils;
import net.youlvke.yanyuke.utils.TelphoneUtils;
import net.youlvke.yanyuke.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.utils.Constants.BASEURL_YYKUSER;

public class BoundTelNumActivity extends BaseActivity {

    private TextView tvTitle;
    private EditText etTel;
    private TextView tvTelTestCode;
    private EditText etTelTestCode;
    private Button btnGetTestCode;
    private Button btnBound;
    public static BoundTelNumActivity instance = null;
    private String tel;
    private String random;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_bound_tel_num;
    }


    @Override
    protected void initView() {
        instance = this;
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etTel = (EditText) findViewById(R.id.et_tel);
        tvTelTestCode = (TextView) findViewById(R.id.tv_tel_test_code);
        etTelTestCode = (EditText) findViewById(R.id.et_tel_test_code);
        btnGetTestCode = (Button) findViewById(R.id.btn_get_test_code);
        btnBound = (Button) findViewById(R.id.btn_bound);
    }

    @Override
    protected void initListener() {
        btnGetTestCode.setOnClickListener(this);
        btnBound.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        tvTitle.setText("绑定手机");
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.btn_get_test_code:
                tel = etTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel) || TelphoneUtils.isMobileExact(tel) == false) {
                    Toast.makeText(this, "确保您的手机能正常接收到验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(
                        btnGetTestCode, 60000, 1000);
                countDownTimerUtils.start();
                //请求数据 得到验证码
                getVerificationCode(tel);
                break;
            case R.id.btn_bound:
                submit();
                break;
        }
    }



    private void getVerificationCode(String phone) {
        try {
            IRequest.post(this,Constants.BASEURL_YYKSYSTEM,verificationTelNum(phone))
                    .execute(new RequestListener() {

                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("verification", "onSuccess: "+response.get());
                            try {
                                JSONObject jsonObject = new JSONObject(response.get());
                                int code = jsonObject.optInt("code");
                                if (code == 1){
                                    showToast("验证成功");
                                    random = jsonObject.optString("random");
                                }else if (code == 4){
                                    showToast("该号码已经被绑定！");
                                }else if (code == 2 && code == 3){
                                    showToast("服务器开小差了0.0 , 请稍后再试！");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit() {
        String code = etTelTestCode.getText().toString().trim();
        if (random == null || TextUtils.isEmpty(code)){
            showToast("输入您的短信验证码,或则您的验证码失效");
            return;
        }
        try {
            IRequest.post(this, BASEURL_YYKUSER,BoundTelphone(tel,random)).execute(new RequestListener() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        Log.d("BASEURL_YYKUSER", "onSuccess: "+response.get());
                        JSONObject jsonObject = new JSONObject(response.get());
                        int code = jsonObject.optInt("code");
                        if (code == 1 ){
                            showToast("绑定成功！");
                            getSharedPreferences("loginInfo",MODE_PRIVATE).edit().putString("boundtelphone",tel).commit();
                        }else {
                            showToast("绑定失败，请稍后再试！");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(new Intent(BoundTelNumActivity.this,BoundSuccessActivty.class));
    }

    private Map verificationTelNum(String phone) throws Exception{
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160301");
        params.put("phone", phone);
        params.put("type", "3");
        return params;
    }
    private Map BoundTelphone(String phone,String code) throws Exception{
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "20160134");
        params.put("phone", phone);
        params.put("code", code);
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", MeFragment.session);
        return params;
    }
    private void showToast(String showText){
        ToastUtils.showToast(this,showText);
    }
}
