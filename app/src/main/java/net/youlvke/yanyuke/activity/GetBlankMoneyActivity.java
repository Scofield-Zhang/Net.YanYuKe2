package net.youlvke.yanyuke.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.BankInfo;
import net.youlvke.yanyuke.utils.ToastUtils;

/**
 *
 */
public class GetBlankMoneyActivity extends BaseActivity {

    private TextView tvNongyeBank;
    private TextView tvJianshenBank;
    private TextView tvGongshangBank;
    private TextView tvZhongguoBank;
    private TextView tvTitle;
    private ImageView ivDropDown;
    private EditText etInputName;
    private TextView tvBankName;
    private EditText etBankCount;
    private TextView tvNext;
    private int bankName = -1;
    private Dialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_get_blank_money;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivDropDown = (ImageView) findViewById(R.id.iv_drop_down);
        ivDropDown = (ImageView) findViewById(R.id.iv_drop_down);
        etInputName = (EditText) findViewById(R.id.et_input_name);
        tvBankName = (TextView) findViewById(R.id.tv_bank_name);
        etBankCount = (EditText) findViewById(R.id.et_bank_count);
        tvNext = (TextView) findViewById(R.id.tv_next);
    }

    @Override
    protected void initListener() {
        ivDropDown.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("提现到银行卡");

    }


    private void showNotifyImageDialog() {
        View view = getLayoutInflater().inflate(
                R.layout.activity_choise_bank, null);
        dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        tvNongyeBank = (TextView) window.findViewById(R.id.tv_nongye_bank);
        tvJianshenBank = (TextView) window.findViewById(R.id.tv_jianshen_bank);
        tvGongshangBank = (TextView) window.findViewById(R.id.tv_gongshang_bank);
        tvZhongguoBank = (TextView) window.findViewById(R.id.tv_zhongguo_bank);

        tvNongyeBank.setOnClickListener(this);
        tvJianshenBank.setOnClickListener(this);
        tvGongshangBank.setOnClickListener(this);
        tvZhongguoBank.setOnClickListener(this);


    }

    @Override
    protected void onInnerClick(View v) {

        super.onInnerClick(v);
        switch (v.getId()) {
            case R.id.tv_nongye_bank:
                bankName = 0;
                tvBankName.setText("农业银行");
                dialog.dismiss();
                break;
            case R.id.tv_jianshen_bank:
                bankName = 1;
                tvBankName.setText("建设银行");
                dialog.dismiss();
                break;
            case R.id.tv_gongshang_bank:
                bankName = 2;
                tvBankName.setText("工商银行");
                dialog.dismiss();
                break;
            case R.id.tv_zhongguo_bank:
                bankName = 3;
                tvBankName.setText("中国银行");
                dialog.dismiss();
                break;
            case R.id.iv_drop_down:
                showNotifyImageDialog();
                break;
            case R.id.tv_next:
                if (bankName == -1) {
                    ToastUtils.showToast(this, "请选择银行");
                    return;
                }
                /*isNull(etInputName, "请输入正确的姓名");
                isNull(etBankCount, "请输入正确的银行卡号");*/
                String name = etInputName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast(this, "请输入正确的姓名");
                    return;
                }
                String cardNum = etBankCount.getText().toString().trim();
                if (TextUtils.isEmpty(cardNum)) {
                    ToastUtils.showToast(this, "请输入正确的银行卡号");
                    return;
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                //姓名  银行卡号  银行
                BankInfo bankInof = new BankInfo(name,cardNum , 0);
                intent.setClass(this, GetMoneySumActivity.class);
                bundle.putSerializable("bank_Inof", bankInof);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
    }


}
