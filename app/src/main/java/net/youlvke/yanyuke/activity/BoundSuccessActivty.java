package net.youlvke.yanyuke.activity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.youlvke.yanyuke.R;

import static net.youlvke.yanyuke.R.id.btn_exit;

public class BoundSuccessActivty extends BaseActivity {


    private TextView tv1;
    private TextView tv2;
    private Button btnExit;

    @Override
    public int getLayoutResId() {
        return R.layout.register_step3;
    }

    @Override
    protected void initView() {
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        btnExit = (Button) findViewById(btn_exit);
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {
        tv1.setText("恭喜您手机绑定成功");
        tv2.setText("为确保您的账号完全请注意使用");
        btnExit.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                BoundTelNumActivity.instance.finish();
            }
        },3*1000);
    }

}
