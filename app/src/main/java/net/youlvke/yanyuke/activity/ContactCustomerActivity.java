package net.youlvke.yanyuke.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.bean.DragableBean;
import net.youlvke.yanyuke.utils.ScreenUtils;
import net.youlvke.yanyuke.view.DragableBtnGroup;
import net.youlvke.yanyuke.view.MyRadioGroup;

/**
 * 联系客服
 */
public class ContactCustomerActivity extends BaseActivity {


    private RelativeLayout rlBgColor;
    private View title;
    private DragableBtnGroup imageViewShow;
    private DragableBean[] vb = new DragableBean[3];
    private TextView tvCancel;
    private TextView tvCallPhone;
    private AlertDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_contact_customer;
    }

    @Override
    protected void initView() {
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);
        title = findViewById(R.id.tv_title);
        Rect outRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);

        imageViewShow = (DragableBtnGroup) findViewById(R.id.imageView_show);
            addBtn();
        imageViewShow.initParams(vb);
    }

    private void addBtn() {
        DragableBean temp = new DragableBean();
        temp.setResourceId(R.mipmap.img_contact_qq);
        temp.setLp(new ViewGroup.LayoutParams(MyRadioGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        temp.setScaleType(ImageView.ScaleType.FIT_CENTER);
        temp.setInitLocation(ScreenUtils.px2dp(ScreenUtils.getScreenW()/2),ScreenUtils.px2dp(ScreenUtils.getScreenH()/2));
        temp.setTitle("2558633");
        temp.setTag(0);
        vb[0] = temp;
        DragableBean temp1 = new DragableBean();
        temp1.setResourceId(R.mipmap.img_telphone_num);
        temp1.setLp(new ViewGroup.LayoutParams(MyRadioGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        temp1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        temp1.setInitLocation(ScreenUtils.getScreenW()/2,ScreenUtils.getScreenH()/2);

        temp1.setTitle("400-1399939");
        temp1.setTag(1);
        vb[1] = temp1;
        DragableBean temp2 = new DragableBean();
        temp2.setResourceId(R.mipmap.img_contact_wechat);
        temp2.setLp(new ViewGroup.LayoutParams(MyRadioGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        temp2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        temp2.setInitLocation(ScreenUtils.getScreenW(), ScreenUtils.getScreenH());
        Log.d("addBtn", "addBtn: "+ScreenUtils.getScreenW()+"<<<<<"+ScreenUtils.getScreenH());
        temp2.setTitle("yanyuke");
        temp2.setTag(2);
        vb[2] = temp2;
    }

    @Override
    protected void initListener() {
        imageViewShow.setAlexClickListener(new DragableBtnGroup.VincentClickListener() {
            @Override
            public void onClick(int tag, int resourceId, String title) {
                switch (tag){
                    case 0:

                        break;
                    case 1:
                        showCallPhone();

                        break;
                    case 2:
                        break;
                }

            }
        });
    }

    private void showCallPhone() {
        {
            {

                dialog = new AlertDialog.Builder(this, R.style.theme_dialog)
                        .create();
                // 调用系统的输入法
                dialog.setView(new EditText(this));
                dialog.show();
                Window window = dialog.getWindow();
                // 设置布局
                window.setContentView(R.layout.dialog_call_phone);
                // 设置宽高
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);
                // 设置弹出的动画效果
                window.setWindowAnimations(R.style.AnimBottom);
                tvCallPhone = (TextView) window.findViewById(R.id.tv_call_phone);
                tvCancel = (TextView) window.findViewById(R.id.tv_cancel);

                //加减添加监听
                tvCallPhone.setOnClickListener(this);
                tvCancel.setOnClickListener(this);
                dialog.setCanceledOnTouchOutside(true);
            }
        }
    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.tv_call_phone:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + "400-1399939");
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
        }
    }

    @Override
    protected void initData() {
        title.setVisibility(View.INVISIBLE);
        rlBgColor.setBackgroundColor(Color.TRANSPARENT);
    }
}
