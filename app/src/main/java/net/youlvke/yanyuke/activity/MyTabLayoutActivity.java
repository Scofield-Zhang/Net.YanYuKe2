package net.youlvke.yanyuke.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.MyViewPagerAdapter;
import net.youlvke.yanyuke.bean.RewardBean;
import net.youlvke.yanyuke.fragment.PagerFragmentRedDiamond;
import net.youlvke.yanyuke.fragment.PagerFragmentRerword;
import net.youlvke.yanyuke.fragment.PagerFragmentSpend;
import net.youlvke.yanyuke.fragment.PagerFragmentTotal;
import net.youlvke.yanyuke.utils.DateUtils;
import net.youlvke.yanyuke.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyTabLayoutActivity extends BaseActivity {

    private List<Fragment> fragmentList = new ArrayList<>();

    private ViewPager mViewpager;
    private TabLayout mTabLayout;
    public Context mContext;
    private TextView tvDateIn;
    private TextView tvUserIn;
    private String date;
    private int type = 0;
    private List<RewardBean.DataBean> datas;
    private ImageView ivCancel;
    private LinearLayout bigLayout;

    @Override
    public int getLayoutResId() {
        this.mContext = this;
        return R.layout.activity_my_tab_layout;
    }

    @Override
    protected void initView() {
        date = getIntent().getStringExtra("date");
        fragmentList.add(new PagerFragmentTotal());
        fragmentList.add(new PagerFragmentRerword());
        fragmentList.add(new PagerFragmentRedDiamond());
        fragmentList.add(new PagerFragmentSpend());

        ivCancel= (ImageView) findViewById(R.id.iv_close);
        bigLayout = (LinearLayout) findViewById(R.id.activity_my_tab_layout);


        //第一步：初始化ViewPager并设置adapter
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        //第二步：初始化Tablayout，给ViewPager设置标题（选项卡）
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(ScreenUtils.dp2px(10));
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_vertical));

        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText("消费奖励"));
        mTabLayout.addTab(mTabLayout.newTab().setText("红钻奖励"));
        mTabLayout.addTab(mTabLayout.newTab().setText("支出"));

        //第三步：关联ViewPager
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.getTabAt(0).setText("全部");
        mTabLayout.getTabAt(1).setText("消费奖励");
        mTabLayout.getTabAt(2).setText("红钻奖励");
        mTabLayout.getTabAt(3).setText("支出");
        tvDateIn = (TextView) findViewById(R.id.tv_date_in);
        tvUserIn = (TextView) findViewById(R.id.tv_user_in);

    }

    @Override
    protected void initListener() {
        ivCancel.setOnClickListener(this);
        bigLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvDateIn.setText(DateUtils.formatDate(date));
        tvUserIn.setText("+" + getIntent().getStringExtra("income"));
        mTabLayout.getChildAt(0).isSelected();

    }


    public List<RewardBean.DataBean> getData() {
        return datas;
    }


    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);
        switch (v.getId()){
            case R.id.iv_close:
            case R.id.activity_my_tab_layout:
                finish();
                break;
        }

    }


    public String getDate() {
        return date;
    }
}
