package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.youlvke.yanyuke.R;

/**
 *
 *
 */
public class WithdrawalSuccess extends BaseActivity {
    private Context mContext;
    private int cardNum;
    private TextView tvWithdrawalAmount;
    private Button btnCommit;

    @Override
    public int getLayoutResId() {
        this.mContext =this;
        return R.layout.activity_withdrawal_success;
    }

    @Override
    protected void initView() {
        cardNum = getIntent().getIntExtra("cardNum", 0);
        tvWithdrawalAmount = (TextView) findViewById(R.id.tv_withdrawal_amount);
        btnCommit = (Button) findViewById(R.id.btn_commit);
    }

    @Override
    protected void initListener() {
        btnCommit.setOnClickListener((View.OnClickListener) mContext);
    }

    @Override
    protected void initData() {
        tvWithdrawalAmount.setText(String.format("本次%s元提现申请提交成功",String.valueOf((double)cardNum)));
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.btn_commit:
                Intent intent = new Intent(mContext,MyWalletActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
}
