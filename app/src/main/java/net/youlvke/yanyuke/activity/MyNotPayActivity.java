package net.youlvke.yanyuke.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.youlvke.yanyuke.view.SwipeMenuListView;
import net.youlvke.yanyuke.R;
import net.youlvke.yanyuke.adapter.SwipeMenuAdapter;
import net.youlvke.yanyuke.bean.ShopCarBean;
import net.youlvke.yanyuke.view.SwipeMenu;
import net.youlvke.yanyuke.view.SwipeMenuCreator;
import net.youlvke.yanyuke.view.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.rgb;
import static net.youlvke.yanyuke.utils.ScreenUtils.dp2px;

public class MyNotPayActivity extends BaseActivity {

    private SwipeMenuListView lvNotUse;
    private List<ShopCarBean> shopCarBeens = new ArrayList<>();
    private TextView tvTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_not_pay;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        lvNotUse = (SwipeMenuListView) findViewById(R.id.lv_not_use);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("待付款商品");
        final MyAdapter adapter = new MyAdapter();
        lvNotUse.setMenuOrientation(SwipeMenuAdapter.Orientation.HORIZONTAL);
        lvNotUse.setAdapter(adapter);
        lvNotUse.setMenuCreator(creator);
        lvNotUse.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                shopCarBeens.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        ShopCarBean shopCarBean = new ShopCarBean("成具有特定格式的字符串", "1", "2", true);
        ShopCarBean shopCarBean1 = new ShopCarBean("对字符串进行格式化的时候，特别是字符串输出，是功能强", "2", "3", false);
        ShopCarBean shopCarBean2 = new ShopCarBean("使用这个函数。第一个参数为格式化串：由指示符", "4", "5", true);
        ShopCarBean shopCarBean3 = new ShopCarBean("发啊手动阀 阿斯弗", "6", "7", false);
        shopCarBeens.add(shopCarBean);
        shopCarBeens.add(shopCarBean1);
        shopCarBeens.add(shopCarBean2);
        shopCarBeens.add(shopCarBean3);

    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem openItem = new SwipeMenuItem(MyNotPayActivity.this);
            openItem.setBackground(new ColorDrawable(rgb(197, 85, 85)));
            openItem.setWidth(dp2px(100));
            openItem.setTitle("删除");
            openItem.setTitleSize(20);
            openItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(openItem);
        }
    };

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return shopCarBeens.size();
        }

        @Override
        public Object getItem(int position) {
            return shopCarBeens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_not_pay, viewGroup, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ShopCarBean shopCrData = shopCarBeens.get(position);
            holder.tvPayTitle.setText(shopCrData.ShopTitle);
            String str = String.format("数量：%s人次\n需付：￥%s", shopCrData.count, shopCrData.price);
            holder.tvPayDesc.setText(str);
            holder.cbIsChoise.setChecked(shopCrData.isChoise);
            if (holder.cbIsChoise.isChecked()) {
                holder.llShopBg.setBackgroundColor(Color.rgb(255, 242, 242));

            } else {
                holder.llShopBg.setBackgroundColor(Color.rgb(255, 255, 255));
            }
            final ViewHolder finalHolder = holder;

            holder.cbIsChoise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        finalHolder.llShopBg.setBackgroundColor(Color.rgb(255, 242, 242));
                    } else {
                        finalHolder.llShopBg.setBackgroundColor(Color.rgb(255, 255, 255));
                    }
                }
            });

            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public ImageView ivPayImg;
            public TextView tvPayTitle;
            public TextView tvPayDesc;
            public CheckBox cbIsChoise;
            public LinearLayout llShopBg;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.ivPayImg = (ImageView) rootView.findViewById(R.id.iv_pay_img);
                this.tvPayTitle = (TextView) rootView.findViewById(R.id.tv_pay_title);
                this.tvPayDesc = (TextView) rootView.findViewById(R.id.tv_pay_desc);
                this.cbIsChoise = (CheckBox) rootView.findViewById(R.id.cb_is_choise);
                this.llShopBg = (LinearLayout) rootView.findViewById(R.id.ll_shop_bg);
            }
        }
    }
}
