package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.utils.ToastUtils;
import net.youlvke.yanyuke.view.PswdDialog;

import static net.youlvke.yanyuke.R.id.btn_get_money;
import static net.youlvke.yanyuke.R.id.tv_title2;


/**
 * 提现界面
 *
 * @author Administrator
 */
public class MyMoneyActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvTilte2;
    private TextView tvMoneyNumber;
    private Button btnGetMoney;//提现
    private Context mContext;
    private boolean isSetPswd;
    private SharedPreferences sp;
    private String commission;

    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_my_money;
    }

    @Override
    protected void initView() {
        isSetPswd = getIntent().getBooleanExtra("isSetPswd", false);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTilte2 = (TextView) findViewById(R.id.tv_title2);
        tvMoneyNumber = (TextView) findViewById(R.id.tv_money_number);
        btnGetMoney = (Button) findViewById(R.id.btn_get_money);
        tvTilte2.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        btnGetMoney.setOnClickListener(this);
        tvTilte2.setOnClickListener(this);
        sp = getSharedPreferences("loginstatus",MODE_PRIVATE);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的佣金");
        tvTilte2.setText("账单");
        commission = String.valueOf(getIntent().getDoubleExtra("commission", 0));
        tvMoneyNumber.setText(commission);

    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()) {
            case tv_title2://账单
                startActivity(new Intent(mContext, BillDetailActivity.class));
                break;
            case btn_get_money://体现界面
                Log.d("onInnerClick", "onInnerClick: "+isSetPswd);
                if (isSetPswd){
                    if (!"1".equals(sp.getString("isFirst","0"))) {
                        startActivity(new Intent(mContext, GetBlankMoneyActivity.class));
                    }else if (Double.parseDouble(commission)==0){
                        startActivity(new Intent(mContext, GetBlankMoneyActivity.class));
                    }else {
                        ToastUtils.showToast(mContext,"你的提现条件不符合要求");
                    }
                }else {
                    PswdDialog pswdDialog = new PswdDialog(mContext);
                    pswdDialog.showAlertPswdDialog();
                    //showAlertPswdDialog();
                    isSetPswd =true;
                }
                break;
        }
    }


}
