package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yolanda.nohttp.rest.Response;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.VoucherBean;
import net.youlvke.yanyuke.fragment.HomeFragment;
import net.youlvke.yanyuke.network.IRequest;
import net.youlvke.yanyuke.network.RequestListener;
import net.youlvke.yanyuke.utils.AuthResult;
import net.youlvke.yanyuke.utils.Constants;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.NetWorkUtils;
import net.youlvke.yanyuke.utils.PayResult;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.PayRadioGroup;
import net.youlvke.yanyuke.view.PayRadioPurified;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.youlvke.yanyuke.fragment.HomeFragment.city;
import static net.youlvke.yanyuke.fragment.MeFragment.session;


/**
 * 支付界面
 */
public class PayActivity extends BaseActivity {

    private TextView tvComboDesc;//套餐详情
    private TextView tvTitle;
    private RelativeLayout rlBgColor;
    private PayRadioGroup payRadioGroup;
    private Button btnPay;
    private String payNum;

    private static final int ALIPAY_PAY = 1;
    private static final int WECHAT_PAY = 2;
    private static final int UNION_PAY = 3;
    private static final int YANYU_PAY = 4;
    private static int PAY_METHOD = ALIPAY_PAY; //默认第一个为阿里支付

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;
    private IWXAPI api;
    private int userId;
    private SharedPreferences sp;
    private RelativeLayout tvVouchers;
    private ImageView ivCancel;
    private ListView lvVouchers;
    private AlertDialog mDialog;
    private Context mContext;
    private List<VoucherBean.DataBean> dataVouchers;
    private MyAdapter adapter;
    private long VouchersId;
    private long sequenceId = 0;

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
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };


    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {
        //初始化微信
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        Intent intent = getIntent();
        payNum = intent.getStringExtra("PAY_NUM");
        tvComboDesc = (TextView) findViewById(R.id.tv_combo_desc);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);
        payRadioGroup = (PayRadioGroup) findViewById(R.id.pay_radio_group);
        btnPay = (Button) findViewById(R.id.btn_pay);
        tvVouchers = (RelativeLayout) findViewById(R.id.tv_vouchers);

    }


    @Override
    protected void initListener() {
        payRadioGroup.setOnCheckedChangeListener(new PayRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(PayRadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                PayRadioPurified radioButton = (PayRadioPurified) PayActivity.this.findViewById(checkedRadioButtonId);
                for (int i = 0; i < group.getChildCount(); i++) {
                    ((PayRadioPurified) group.getChildAt(i)).setChangeImg(checkedId);
                }
                selectePay(radioButton);
            }
        });
        btnPay.setOnClickListener(this);
        tvVouchers.setOnClickListener(this);
        //lvVouchers.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    @Override
    protected void initData() {
        sequenceId = getIntent().getLongExtra("SequenceId", 0);
        sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        tvTitle.setText("支付方式");
        rlBgColor.setBackgroundColor(Color.parseColor("#F0F0F0"));
        String totalNum = String.format("确认支付 ￥%s", payNum);
        btnPay.setText(totalNum);
        showDoubleTextColor(tvComboDesc, "天生一对情侣套餐", " (5人次)");
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

    /**
     * 支付的请求参数
     *
     * @return
     * @throws Exception
     */
    private Map createPayParams(String count) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("action", "20160401");
        params.put("sessionToken", session);
        params.put("sequenceId", String.valueOf(sequenceId));
        params.put("userId", String.valueOf(userId));
        params.put("userIp", NetWorkUtils.getLocalIpAddress(this));
        params.put("purchaseCount", count);
        params.put("payType", String.valueOf(PAY_METHOD));
        params.put("userLat", String.valueOf(HomeFragment.longitude));
        params.put("userLng", String.valueOf(HomeFragment.latitude));
        params.put("userLocation", city);
        params.put("vouchersId", String.valueOf(VouchersId));
        return params;
    }


    /**
     * 支付宝支付
     */
    private void alipay(String Info) {

        final String orderInfo = Info;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
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

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.btn_pay:
                //startActivity(new Intent(this,PaySuccessActivity.class));
                switch (PAY_METHOD) {
                    case ALIPAY_PAY:
                        getOrderInfo();
                        break;
                    case WECHAT_PAY:
                        getOrderInfo();
                        break;
                    case UNION_PAY:
                        ToastUtils.showToast(PayActivity.this, "银联支付,该功能暂未开发敬请期待");
                        break;
                    case YANYU_PAY:
                        ToastUtils.showToast(PayActivity.this, "烟雨货支付");
                        break;
                }
                break;
            case R.id.tv_vouchers:
                loadVoucher();
                showVouchersDialog();
                break;
            case R.id.iv_cancel:
                mDialog.dismiss();
                break;
        }
    }

    private void loadVoucher() {
        try {
            IRequest.post(mContext, Constants.BASEURL_YYKORDER, createVoucherParams()).execute(new RequestListener() {


                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("createVoucherParams", "onSuccess: " + response.get());
                    try {
                        Gson gson = new Gson();
                        VoucherBean voucherBean = gson.fromJson(response.get(), VoucherBean.class);
                        if (voucherBean.getCode() == 1 && voucherBean.getData() != null) {
                            dataVouchers = voucherBean.getData();
                            lvVouchers.setAdapter(adapter);
                        }
                    } catch (JsonParseException e) {
                        ToastUtils.showToast(mContext, "数据解析异常");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map createVoucherParams() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "20160403");
        params.put("sessionToken", session);
        params.put("goodsCategory", String.valueOf(getIntent().getIntExtra("goodsCategory", 0)));
        params.put("userId", String.valueOf(userId));
        return params;
    }

    private void showVouchersDialog() {

        mDialog = new AlertDialog.Builder(this, R.style.theme_dialog)
                .create();
        // 调用系统的输入法
        mDialog.setView(new EditText(this));

        mDialog.show();
        Window window = mDialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_vouchers);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        ivCancel = (ImageView) window.findViewById(R.id.iv_cancel);
        lvVouchers = (ListView) window.findViewById(R.id.lv_vouchers);
        ivCancel.setOnClickListener(this);
        lvVouchers.setOnItemClickListener(new MyOnItemClickListener());
        adapter = new MyAdapter();
        mDialog.setCanceledOnTouchOutside(true);
    }


    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (dataVouchers.get(position).getMoney() < Integer.parseInt(payNum)) {
                VouchersId = dataVouchers.get(position).getVouchersId();
                btnPay.setText(String.format("确认支付 ￥%s", String.valueOf(Integer.parseInt(payNum) - dataVouchers.get(position).getMoney())));
                mDialog.dismiss();
            } else {
                ToastUtils.showToast(mContext, "不符合使用规则请重新选择");
            }
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataVouchers.size();
        }

        @Override
        public Object getItem(int position) {
            return dataVouchers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_get_vouchers, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            VoucherBean.DataBean dataBean = dataVouchers.get(position);
            holder.tvVouchersNum.setText(String.valueOf(dataBean.getMoney()));
            holder.tvVouchersTime.setText(String.format("有效期限%s", DateUtils.formatDate(dataBean.getAddTime())));

            switch (dataBean.getVoucherstype()) {
                case 0:
                    holder.tvVouchersType.setText("通用卷");
                    break;
                case 1:
                    holder.tvVouchersType.setText("美食券");
                    break;
                case 2:
                    holder.tvVouchersType.setText("实用券");
                    break;
                case 3:
                    holder.tvVouchersType.setText("数码券");
                    break;
                case 4:
                    holder.tvVouchersType.setText("全国购");
                    break;
            }
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tvVouchersNum;
            public TextView tvVouchersType;
            public TextView tvVouchersTime;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tvVouchersNum = (TextView) rootView.findViewById(R.id.tv_vouchers_num);
                this.tvVouchersType = (TextView) rootView.findViewById(R.id.tv_vouchers_type);
                this.tvVouchersTime = (TextView) rootView.findViewById(R.id.tv_vouchers_time);
            }
        }
    }

    private void getOrderInfo() {
        try {
            IRequest.post(this, Constants.BASEURL_YYKORDER, createPayParams(payNum))
                    .loading(true)
                    .loadingTitle("生产订单……")
                    .execute(new RequestListener() {

                        @Override
                        public void onSuccess(Response<String> response) {
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

    private void wechatPay(JSONObject data) throws JSONException {

        if (null != data && !data.has("retcode")) {
            PayReq req = new PayReq();
            req.appId = data.getString("appid");
            req.partnerId = data.getString("partnerid");
            req.prepayId = data.getString("prepayid");
            req.nonceStr = data.getString("noncestr");
            req.timeStamp = data.getString("timestamp");
            req.packageValue = data.getString("package");
            req.sign = data.getString("sign");
            req.extData = "app data"; // optional
            Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } else {
            Log.d("PAY_GET", "返回错误" + data.getString("retmsg"));
            Toast.makeText(PayActivity.this, "返回错误" + data.getString("retmsg"), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDoubleTextColor(TextView view, String text1, String text2) {
        String str = "<font color='#333333'>" + text1 + "</font>"
                + "<font color= '#666666'>" + text2 + "</font>";
        view.setText(Html.fromHtml(str));
    }
}
