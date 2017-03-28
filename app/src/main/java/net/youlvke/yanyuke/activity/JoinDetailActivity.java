package net.youlvke.yanyuke.activity;

import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.R;



/**
 * 参加详情
 */
public class JoinDetailActivity extends BaseActivity {


    private GridView gvNum;
    private TextView tvTitle;
    private TextView tvJoinDetail;
    private RelativeLayout rlBgColor;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_join_detail;
    }

    @Override
    protected void initView() {
        gvNum = (GridView) findViewById(R.id.gv_num);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvJoinDetail = (TextView) findViewById(R.id.tv_join_detail);
        rlBgColor = (RelativeLayout) findViewById(R.id.rl_bg_color);
        showDoubleTextColor(tvJoinDetail,"第20161109期 / 已参与","9人次");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        rlBgColor.setBackgroundResource(android.R.color.white);
        tvTitle.setText("参与详情");

        CharSequence[] s = new CharSequence[]{Html.fromHtml("<font color='#C55555'>神秘号码</font>"),"132131","1231345","1231345","1231345","1231345","1231345","1231345"};

        gvNum.setAdapter(new ArrayAdapter<CharSequence>(this, R.layout.item_text_code,R.id.tv_code,s));

    }

    private void showDoubleTextColor(TextView view, String text1, String text2) {
        String str = "<font color='#666666'>"+text1+"</font>"
                + "<font color= '#cd7070'>"+text2+"</font>";
        view.setText(Html.fromHtml(str));
    }
}
