package net.youlvke.yanyuke.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.AuthResult;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.NetWorkUtils;
import net.youlvke.yanyuke.utils.PayResult;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.PayRadioGroup;
import net.youlvke.yanyuke.view.PayRadioPurified;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.youlvke.yanyuke.activity.SplashActivity.userId;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 充值
 */
public class RechargeActivity extends BaseActivity {


    private static final int TYPE_CHOISE = 1;
    private static final int TYPE_EDIT = 2;
    private TextView tvTitle;
    private GridView gvRechargeType;
    private String[] strings = {"10", "30", "50", "100", "300", "500"};
    private ArrayAdapter<String> adapter;
    private  int lastPressIndex = -1;
    private EditText etChoiseInput;
    private  String mRechargeNum ;
    private Button mBtnNext;
    private PayRadioGroup mPayRadioGroup;
    private IWXAPI api;


    private static final int ALIPAY_PAY = 0;
    private static final int WECHAT_PAY = 1;
    private static final int UNION_PAY = 2;
    private static final int YANYU_PAY = 3;
    private static int PAY_METHOD = ALIPAY_PAY; //默认第一个为阿里支付

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     *对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(RechargeActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(RechargeActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    private  class OnRechargeTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            mRechargeNum = editable.toString();

        }
    }


    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            etChoiseInput.setText("");
            etChoiseInput.clearFocus();

                ToastUtils.showToast(RechargeActivity.this,"获取焦点"+position+":"+lastPressIndex);
                if (lastPressIndex == position){
                    lastPressIndex = -1;
                    view.setSelected(false);

                }else {
                    lastPressIndex = position;
                    view.setSelected(true);
                    mRechargeNum = strings[position];
                }
        }
    }
    @Override
    public int getLayoutResId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        gvRechargeType = (GridView) findViewById(R.id.gv_recharge_type);
        etChoiseInput = (EditText) findViewById(R.id.et_choise_input);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mPayRadioGroup = (PayRadioGroup)findViewById(R.id.pay_radio_group);
    }

    @Override
    protected void initListener() {
        mBtnNext.setOnClickListener(this);
        etChoiseInput.addTextChangedListener(new OnRechargeTextWatcher());
    }

    @Override
    protected void initData() {
        tvTitle.setText("充值");
        adapter = new ArrayAdapter<>(this, R.layout.item_recharge_choise,R.id.tv_commom_btn, this.strings);
        gvRechargeType.setAdapter(adapter);
        gvRechargeType.setOnItemClickListener(new MyOnItemClickListener());
        mPayRadioGroup.setOnCheckedChangeListener(new PayRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(PayRadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                PayRadioPurified radioButton = (PayRadioPurified) RechargeActivity.this.findViewById(checkedRadioButtonId);
                for (int i = 0; i < group.getChildCount(); i++) {
                    ((PayRadioPurified) group.getChildAt(i)).setChangeImg(checkedId);
                }
                selectePay(radioButton);
            }
        });
    }
    /**
     * 选择支付方式
     */
    private void selectePay(PayRadioPurified radioButton) {
        switch (radioButton.getId()) {
            case R.id.prp_alipay://支付宝支付
                PAY_METHOD = ALIPAY_PAY;
                //发送订单相关信息
                String localIpAddress = NetWorkUtils.getLocalIpAddress(this);
                ToastUtils.showToast(this, "支付宝支付,IP:" + localIpAddress);
                break;
            case R.id.prp_wechat://微信支付
                PAY_METHOD = WECHAT_PAY;
                ToastUtils.showToast(this, "微信宝支付");
                break;
            case R.id.prp_union://银联支付
                PAY_METHOD = UNION_PAY;
                break;
            case R.id.prp_yanyu://烟雨币支付
                PAY_METHOD = YANYU_PAY;
                break;
        }
    }
    private void getOrderInfo() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKORDER, createRechargeParams())
                    .loading(true)
                    .loadingTitle("产生订单……")
                    .execute(new RequestListener() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.d("tag_onSuccess", "onSuccess: " + response.get());
                            try {
                                JSONObject data = new JSONObject(response.get());
                                selectePayMethod(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map createRechargeParams() {
        //yykOrder   action： userId    （0是支付宝。1是微信） （提现金额） userIp
        Map<String, String> params = new HashMap<>();
        params.put("action", "20160404");
        params.put("userId", String.valueOf(userId));
        params.put("sessionToken", session);
        params.put("rechargeWay", String.valueOf(PAY_METHOD));
        params.put("rechargeAmount", String.valueOf(mRechargeNum));
        params.put("userIp", NetWorkUtils.getLocalIpAddress(this));
        return params;
    }

    private void selectePayMethod(JSONObject data) throws JSONException {
        switch (PAY_METHOD) {
            case ALIPAY_PAY:
                String sign = data.getString("sign");
                alipay(sign);
                break;
            case WECHAT_PAY:
                wechatPay(data);
                break;
            case UNION_PAY:
                break;
            case YANYU_PAY:
                break;
        }
    }
    /**
     * 支付宝支付
     */
    private void alipay(String Info) {

        final String orderInfo = Info;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.d("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     *
     * @param data
     * @throws JSONException
     */
    private void wechatPay(JSONObject data) throws JSONException {

        if (null != data && !data.has("retcode")) {
            Log.d("wechatPay", "wechatPay: "+data.toString());
            PayReq req = new PayReq();
            req.appId = data.getString("appid");
            req.partnerId = data.getString("partnerid");
            req.prepayId = data.getString("prepayid");
            req.nonceStr = data.getString("noncestr");
            req.timeStamp = data.getString("timestamp");
            req.packageValue = data.getString("package");
            req.sign = data.getString("sign");
            req.extData = "app data"; // optional
            Toast.makeText(RechargeActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } else {
            Log.d("PAY_GET", "返回错误" + data.getString("retmsg"));
            ToastUtils.showToast(RechargeActivity.this,"服务器繁忙请稍后再试！");
        }
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.btn_next:
                if (mRechargeNum == null){
                    ToastUtils.showToast(this,"请选择充值的钱数");
                    return;
                }
                //startActivity(new Intent(this,PaySuccessActivity.class));
                switch (PAY_METHOD) {
                    case ALIPAY_PAY:
                        getOrderInfo();
                        ToastUtils.showToast(RechargeActivity.this, "支付宝支付");
                        break;
                    case WECHAT_PAY:
                        getOrderInfo();
                        ToastUtils.showToast(RechargeActivity.this, "微信支付");
                        break;
                    case UNION_PAY:
                        ToastUtils.showToast(RechargeActivity.this, "银联支付,该功能暂未开发敬请期待");
                        break;
                    case YANYU_PAY:
                        ToastUtils.showToast(RechargeActivity.this, "烟雨货支付");
                        break;
                }
                break;
        }
    }
}
