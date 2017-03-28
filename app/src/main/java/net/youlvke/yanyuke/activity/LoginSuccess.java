package net.youlvke.yanyuke.activity;

import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.utils.ToastUtils;

public class LoginSuccess extends BaseActivity {

    private TextView tvTitle;
    private TextView tvMycode;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login_success;
    }

    @Override
    protected void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMycode = (TextView) findViewById(R.id.tv_mycode);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String stringExtra = getIntent().getStringExtra("referralCode");
        ToastUtils.showToast(this,stringExtra);
        tvTitle.setText("登陆成功");
        tvMycode.setText(stringExtra);
    }
}
