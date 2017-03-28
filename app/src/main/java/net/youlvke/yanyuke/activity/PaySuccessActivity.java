package net.youlvke.yanyuke.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.utils.ToastUtils;

/**
 * 加入成功
 */
public class PaySuccessActivity extends BaseActivity {


    private TextView textView;//标题
    private Button btnContinueJoin;//继续参与按钮
    private TextView tvPresell;//预售
    private TextView tvCheckAll;//查看全部
    private RelativeLayout rlBgColor;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_join_success;
    }

    @Override
    protected void initView() {
        textView = (TextView) findViewById(R.id.tv_title);
        btnContinueJoin = (Button) findViewById(R.id.btn_continue_join);
        tvPresell = (TextView) findViewById(R.id.tv_presell);
        tvCheckAll = (TextView) findViewById(R.id.tv_check_all);
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);

    }

    @Override
    protected void initListener() {
        btnContinueJoin.setOnClickListener(this);
        tvCheckAll.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        textView.setVisibility(View.INVISIBLE);
        showDoubleTextColor(tvPresell,"【预售】天生一对情侣套餐 ","（9人次）");
        rlBgColor.setBackgroundResource(android.R.color.white);
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.btn_continue_join:
                ToastUtils.showToast(this,"1232");
            break;
            case R.id.tv_check_all:
                startActivity(new Intent(this,JoinDetailActivity.class));
            break;
        }
    }

    private void showDoubleTextColor(TextView view, String text1, String text2) {
        String str = "<font color='#333333'>"+text1+"</font>"
                + "<font color= '#c55555'>"+text2+"</font>";
        view.setText(Html.fromHtml(str));
    }
}
