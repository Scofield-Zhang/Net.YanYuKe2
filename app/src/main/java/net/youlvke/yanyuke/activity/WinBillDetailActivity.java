package net.youlvke.yanyuke.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import net.youlvke.yanyuke.adapter.DefaultBaseAdapter;
import net.youlvke.yanyuke.bean.NotifyInfoBean;
import net.youlvke.yanyuke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 详情
 */
public class WinBillDetailActivity extends BaseActivity {

    private TextView tvTitle;
    private ListView lvWinDetail;
    private List<NotifyInfoBean> datas = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_win_bill_detail;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvWinDetail = (ListView) findViewById(R.id.lv_win_detail);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        NotifyInfoBean notifyBean = new NotifyInfoBean(1, "实物派200元购物卡", "恭喜您，大润发200元购物卡恭喜您，大润发200元购物卡");
        NotifyInfoBean notifyBean1 = new NotifyInfoBean(2, "数码控200元购物卡", "恭喜您，大润发200元购物卡恭喜您，大润发200元购物卡");
        NotifyInfoBean notifyBean2 = new NotifyInfoBean(3, "美食200元购物卡", "恭喜您，大润发200元购物卡恭喜您，大润发200元购物卡");
        NotifyInfoBean notifyBean3 = new NotifyInfoBean(4, "娱乐的200元购物卡", "恭喜您，大润发200元购物卡恭喜您，大润发200元购物卡");
        NotifyInfoBean notifyBean4 = new NotifyInfoBean(5, "全国的200元购物卡", "恭喜您，大润发200元购物卡恭喜您，大润发200元购物卡");
        datas.add(notifyBean);
        datas.add(notifyBean1);
        datas.add(notifyBean2);
        datas.add(notifyBean3);
        datas.add(notifyBean4);
        tvTitle.setText("中单消息");
        lvWinDetail.setAdapter(adapter);


    }

    public DefaultBaseAdapter<NotifyInfoBean> adapter = new DefaultBaseAdapter<NotifyInfoBean>(datas) {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_card_win, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NotifyInfoBean notifyInfoBean = datas.get(position);
            switch (notifyInfoBean.ITEM_TYPE) {
                case 1:
                    holder.tvNotifyTitle.setText(notifyInfoBean.notifyTitle);
                    holder.tvNotifyTitle.setBackgroundColor(getResources().getColor(R.color.actoin_red));
                    holder.tvNotityDetail.setText(notifyInfoBean.notityDetail);
                    break;

                case 2:
                    holder.tvNotifyTitle.setText(notifyInfoBean.notifyTitle);
                    holder.tvNotifyTitle.setBackgroundColor(getResources().getColor(R.color.it_blue));
                    holder.tvNotityDetail.setText(notifyInfoBean.notityDetail);
                    break;
                case 3:
                    holder.tvNotifyTitle.setText(notifyInfoBean.notifyTitle);
                    holder.tvNotifyTitle.setBackgroundColor(getResources().getColor(R.color.play_orange));
                    holder.tvNotityDetail.setText(notifyInfoBean.notityDetail);
                    break;
                case 4:
                    holder.tvNotifyTitle.setText(notifyInfoBean.notifyTitle);
                    holder.tvNotifyTitle.setBackgroundColor(getResources().getColor(R.color.food_yellow));
                    holder.tvNotityDetail.setText(notifyInfoBean.notityDetail);
                    break;
                case 5:
                    holder.tvNotifyTitle.setText(notifyInfoBean.notifyTitle);
                    holder.tvNotifyTitle.setBackgroundColor(getResources().getColor(R.color.county_blue));
                    holder.tvNotityDetail.setText(notifyInfoBean.notityDetail);
                    break;
            }
            return convertView;
        }
    };


    public class ViewHolder {
        public View rootView;
        public TextView tvNotifyTitle;
        public TextView tvNotityDetail;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvNotifyTitle = (TextView) rootView.findViewById(R.id.tv_notify_title);
            this.tvNotityDetail = (TextView) rootView.findViewById(R.id.tv_notity_detail);
        }

    }
}

