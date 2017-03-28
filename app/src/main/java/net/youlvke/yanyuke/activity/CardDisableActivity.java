package net.youlvke.yanyuke.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.DefaultBaseAdapter;
import net.youlvke.yanyuke.bean.CardDisableBean;

import java.util.ArrayList;
import java.util.List;

public class CardDisableActivity extends BaseActivity {
    private List<CardDisableBean> datas = new ArrayList<>();

    private TextView tvTitle;
    private ListView lvCardDisable;



    @Override
    public int getLayoutResId() {
        return R.layout.activity_card_disable;
    }

    @Override
    protected void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvCardDisable = (ListView) findViewById(R.id.lv_card_disable);
        lvCardDisable.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("卡券到期提醒");

        CardDisableBean cardDisableBean = new CardDisableBean("5", "大运河餐厅");
        CardDisableBean cardDisableBean1 = new CardDisableBean("4", "餐厅");
        CardDisableBean cardDisableBean2 = new CardDisableBean("9", "大运河");
        CardDisableBean cardDisableBean3 = new CardDisableBean("7", "大运河餐厅");

        datas.add(cardDisableBean);
        datas.add(cardDisableBean1);
        datas.add(cardDisableBean2);
        datas.add(cardDisableBean3);
    }

    DefaultBaseAdapter<CardDisableBean> adapter = new DefaultBaseAdapter<CardDisableBean>(datas) {


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_card_disable, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CardDisableBean cardDisableBean = datas.get(position);
            holder.tvDayNum.setText(cardDisableBean.dayNum);
            holder.tvShopDisableTitle.setText(cardDisableBean.shopDetail);
            return convertView;
        }
    };


    public class ViewHolder {
        public View rootView;
        public TextView tvDayNum;
        public TextView tvShopDisableTitle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvDayNum = (TextView) rootView.findViewById(R.id.tv_day_num);
            this.tvShopDisableTitle = (TextView) rootView.findViewById(R.id.tv_shop_disable_title);
        }

    }
}
