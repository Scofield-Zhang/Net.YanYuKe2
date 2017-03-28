package net.youlvke.yanyuke.activity;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.youlvke.yanyuke.adapter.DefaultBaseAdapter;
import net.youlvke.yanyuke.bean.WinDetailBean;
import net.youlvke.yanyuke.R;

import java.util.ArrayList;
import java.util.List;

public class WinRuleActivity extends BaseActivity {


    private TextView tvTitle;
    private TextView tvWinRule;
    private TextView tvJoinNum;
    private TextView tvUpDown;
    private ListView lvWinList;
    private TextView tvAnnouncing;
    private LinearLayout llMasker;
    private List<WinDetailBean> datas = new ArrayList<>();
    private TextView tvLuckCode;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_win_rule;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvJoinNum = (TextView) findViewById(R.id.tv_join_num);
        tvUpDown = (TextView) findViewById(R.id.tv_up_down);
        lvWinList = (ListView) findViewById(R.id.lv_win_list);
        tvAnnouncing = (TextView) findViewById(R.id.tv_announcing);
        llMasker = (LinearLayout) findViewById(R.id.ll_masker);
        tvLuckCode = (TextView) findViewById(R.id.tv_luck_code);

        initTextColor("#C02237", "=%s", "5531543973", tvJoinNum);
        initTextColor("#C63E4B", "=%s（第20161212035 期）", "正在等待揭晓…", tvAnnouncing);
        initTextColor("#CE5D67", "幸运号码：%s", "等待揭晓…", tvLuckCode);

    }

    @Override
    protected void initListener() {
        tvUpDown.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("如何开奖");

        lvWinList.setAdapter(adapter);

        for (int i= 0 ; i<9; i++){
            WinDetailBean winDetailBean = new WinDetailBean("2016-"+i+"-"+i+"  15:30:23.569","10390806"+i,"黄建佳"+i+"个");
            datas.add(winDetailBean);
        }

    }

    @Override
    protected void onInnerClick(View v) {
        super.onInnerClick(v);

        switch (v.getId()) {
            case R.id.tv_up_down:
                llMasker.setVisibility(llMasker.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                setListViewHeightBasedOnChildren(lvWinList);
                lvWinList.setAdapter(adapter);
                break;
        }
    }
    DefaultBaseAdapter<WinDetailBean> adapter = new DefaultBaseAdapter<WinDetailBean>(datas) {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holer = null;
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_win_detail,null);
                holer = new ViewHolder(convertView);
                convertView.setTag(holer);
            }else {
               holer = (ViewHolder) convertView.getTag();
            }
            WinDetailBean winDetailBean = datas.get(position);
            holer.tvWinTime.setText(winDetailBean.winTime);
            holer.tvWinId.setText(winDetailBean.winId);
            holer.tvWinName.setText(winDetailBean.winName);

            return convertView;
        }
    };
    class ViewHolder {
        public View rootView;
        public TextView tvWinTime;
        public TextView tvWinId;
        public TextView tvWinName;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvWinTime = (TextView) rootView.findViewById(R.id.tv_win_time);
            this.tvWinId = (TextView) rootView.findViewById(R.id.tv_win_id);
            this.tvWinName = (TextView) rootView.findViewById(R.id.tv_win_name);
        }

    }
    private void initTextColor(String color1, String text1, String text2, TextView view) {
        String str = String.format(text1, "<font color='" + color1 + "'>" + text2 + "</font>");
        /*String str1 = "<font color='" + color1 + "'>text1</font>"
                + "<font color= '" + color2 + "'>text2</font>";*/
        view.setText(Html.fromHtml(str));
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
